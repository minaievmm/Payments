package ua.nure.minaev.SummaryTask4.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.minaev.SummaryTask4.db.Fields;
import ua.nure.minaev.SummaryTask4.db.entity.Card;
import ua.nure.minaev.SummaryTask4.db.entity.Payment;
import ua.nure.minaev.SummaryTask4.db.entity.User;
import ua.nure.minaev.SummaryTask4.exception.DBException;
import ua.nure.minaev.SummaryTask4.exception.Messages;

/**
 * 
 * @author M.Minaiev
 * 
 */
public class PaymentDAO extends DAOFactory {

	private static final String GET_USER_PAYMENT_ORDER_BY_PAYMENT_ID = "select * from payment where user_payment=? order by payment_id";

	private static final String GET_USER_PAYMENT_ORDER_BY_DATE_ASC = "select * from payment where user_payment=? order by payment_date";

	private static final String GET_USER_PAYMENT_ORDER_BY_DATE_DESC = "select * from payment where user_payment=? order by payment_date desc";

	private static final String SQL_PREPARE_PAYMENT = "insert into payment values (default, 0, now(), ?, ?, ?, ?)";

	private static final String SQL_MAKE_PAYMENT = "update payment set state_id=1 where payment_id=?";

	private static final String SQL_UPDATE_CARD_ON_PAYMENT = "update card set card_balance=? where card_number =?";

	private static final String SQL_FIND_PAYMENT_BY_ID = "select * from payment where payment_id=?";

	private static final Logger LOG = Logger.getLogger(PaymentDAO.class);

	protected PaymentDAO() throws DBException {
		super();
	}

	private static PaymentDAO instance;

	public static synchronized PaymentDAO getInstance() throws DBException {
		if (instance == null) {
			instance = new PaymentDAO();
		}
		return instance;
	}

	/**
	 * Obtains prepared payment from DB and confirms it with changing state from
	 * 'prepared' to 'sent'. Makes transaction from user's card to recipient's
	 * card
	 * 
	 * @param paymentId
	 * @param amount
	 * @param recipientCard
	 * @param card
	 * @return
	 * @throws DBException
	 */
	public boolean makePayment(int paymentId, int amount, Card recipientCard,
			Card card) throws DBException {
		Connection con = null;
		PreparedStatement cardStatement = null;
		PreparedStatement cardRecipientStatement = null;
		PreparedStatement paymentConfirmStatement = null;
		try {
			con = getConnection();
			if (card.getCardBalance() >= amount) {
				cardStatement = con
						.prepareStatement(SQL_UPDATE_CARD_ON_PAYMENT);
				System.out.println("card balance: " + card.getCardBalance());
				System.out.println("payment amount: " + amount);
				cardStatement.setInt(1, card.getCardBalance() - amount);
				cardStatement.setInt(2, card.getCardNumber());
				cardStatement.executeUpdate();
				cardRecipientStatement = con
						.prepareStatement(SQL_UPDATE_CARD_ON_PAYMENT);
				cardRecipientStatement.setInt(1, recipientCard.getCardBalance()
						+ amount);
				cardRecipientStatement.setInt(2, recipientCard.getCardNumber());
				cardRecipientStatement.executeUpdate();
				paymentConfirmStatement = con
						.prepareStatement(SQL_MAKE_PAYMENT);
				paymentConfirmStatement.setInt(1, paymentId);
				paymentConfirmStatement.executeUpdate();
				con.commit();
			} else {
				throw new DBException(Messages.ERR_INSUFFICIENT_FUNDS, null);
			}
		} catch (SQLException ex) {
			rollback(con);
			LOG.error("Cannot make payment: " + ex);
		} finally {
			close(con);
			close(paymentConfirmStatement);
			close(cardRecipientStatement);
			close(cardStatement);
		}
		return true;
	}

	/**
	 * Prepares new payment and collects it in DB
	 * 
	 * @param amount
	 * @param recipientCard
	 * @param card
	 * @return
	 * @throws DBException
	 */
	public boolean preparePayment(int amount, Card recipientCard, Card card)
			throws DBException {
		Connection con = null;
		PreparedStatement paymentStatement = null;
		try {
			con = getConnection();
			paymentStatement = con.prepareStatement(SQL_PREPARE_PAYMENT);
			paymentStatement.setInt(1, amount);
			paymentStatement.setInt(2, card.getUserCard());
			paymentStatement.setInt(3, recipientCard.getCardNumber());
			paymentStatement.setInt(4, card.getCardNumber());
			paymentStatement.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error("Cannot prepare payment: " + ex);
			throw new DBException(Messages.ERR_CANNOT_PROCEED_PAYMENT, ex);
		} finally {
			close(con, paymentStatement);
		}
		return true;
	}

	/**
	 * Finds payment by it's id in DB
	 * 
	 * @param paymentId
	 * @return
	 * @throws DBException
	 */
	public Payment findPaymentById(int paymentId) throws DBException {
		Payment payment = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_PAYMENT_BY_ID);
			pstmt.setInt(1, paymentId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				payment = extractPayment(rs);
			}
		} catch (SQLException ex) {
			LOG.error("Cannot find payment by id: " + ex);
		} finally {
			close(con, pstmt, rs);
		}
		return payment;
	}

	/**
	 * Finds all user's payments ordered by payment number
	 * 
	 * @param user
	 * @return
	 * @throws DBException
	 */
	public List<Payment> findUserPayments(User user) throws DBException {
		List<Payment> paymentList = new ArrayList<>();
		Payment payment = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(GET_USER_PAYMENT_ORDER_BY_PAYMENT_ID);
			pstmt.setInt(1, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				payment = extractPayment(rs);
				paymentList.add(payment);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error("Cannot find user payments: " + ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_PAYMENTS_BY_LOGIN,
					ex);
		} finally {
			close(con, pstmt, rs);
		}
		return paymentList;
	}

	/**
	 * Finds user's payments by its id
	 * 
	 * @param id
	 * @return list of payments
	 * @throws DBException
	 */
	public List<Payment> findUserPaymentsById(int id) throws DBException {
		List<Payment> paymentList = new ArrayList<>();
		Payment payment = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(GET_USER_PAYMENT_ORDER_BY_PAYMENT_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				payment = extractPayment(rs);
				paymentList.add(payment);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_PAYMENTS_BY_LOGIN,
					ex);
		} finally {
			close(con, pstmt, rs);
		}
		return paymentList;
	}

	/**
	 * Finds all user's payments ordered by payment date in ascending order
	 * 
	 * @param user
	 * @return
	 * @throws DBException
	 */
	public List<Payment> findUserPaymentsOrderByDateAsc(User user)
			throws DBException {
		List<Payment> paymentList = new ArrayList<>();
		Payment payment = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(GET_USER_PAYMENT_ORDER_BY_DATE_ASC);
			pstmt.setInt(1, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				payment = extractPayment(rs);
				paymentList.add(payment);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_PAYMENTS_BY_LOGIN,
					ex);
		} finally {
			close(con, pstmt, rs);
		}
		return paymentList;
	}

	/**
	 * Finds all user's payments ordered by payment date in descending order
	 * 
	 * @param user
	 * @return
	 * @throws DBException
	 */
	public List<Payment> findUserPaymentsOrderByDateDesc(User user)
			throws DBException {
		List<Payment> paymentList = new ArrayList<>();
		Payment payment = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(GET_USER_PAYMENT_ORDER_BY_DATE_DESC);
			pstmt.setInt(1, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				payment = extractPayment(rs);
				paymentList.add(payment);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_PAYMENTS_BY_LOGIN,
					ex);
		} finally {
			close(con, pstmt, rs);
		}
		return paymentList;
	}

	/**
	 * Extracts payment from DB if all fields are needed
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Payment extractPayment(ResultSet rs) throws SQLException {
		Payment payment = new Payment();
		payment.setAmount(rs.getInt(Fields.PAYMENT_AMOUNT));
		payment.setPaymentId(rs.getInt(Fields.PAYMENT_ID));
		payment.setStateId(rs.getInt(Fields.PAYMENT_STATE));
		payment.setPaymentDate(rs.getDate(Fields.PAYMENT_DATE));
		payment.setUserId(rs.getInt(Fields.PAYMENT_USER_PAYMENT));
		payment.setUserRecipient(rs.getInt(Fields.PAYMENT_USER_RECIPIENT));
		payment.setUserCard(rs.getInt(Fields.PAYMENT_USER_CARD));
		return payment;
	}

}
