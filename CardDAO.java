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
import ua.nure.minaev.SummaryTask4.db.entity.User;
import ua.nure.minaev.SummaryTask4.exception.DBException;
import ua.nure.minaev.SummaryTask4.exception.Messages;

/**
 * Class for work with Card table from DB
 * 
 * @author M.Minaiev
 * 
 */
public class CardDAO extends DAOFactory {

	private static final String SQL_CREATE_CARD = "insert into card values (default, 0, ?, 0, 0)";

	private static final String SQL_REFILL_CARD = "update card set card_balance=? where card_number =?";

	private static final String SQL_BLOCK_CARD = "update card set isBlocked=true where card_number =?";

	private static final String SQL_REQUEST_UNBLOCK_CARD = "update card set isRequested=true where card_number =?";

	private static final String SQL_UNBLOCK_CARD = "update card set isBlocked=false, isRequested = false where card_number =?";

	private static final String SQL_FIND_CARD_BY_ID = "select * from card where card_number=?";

	private static final String SQL_FIND_USER_CARDS = "select * from card where user_card=?";

	private static final String SQL_FIND_USER_CARDS_ORDER_BY_CARD_NUMBER = "select * from card where user_card=? order by card_number";

	private static final String SQL_FIND_USER_CARDS_ORDER_BY_CARD_BALANCE = "select * from card where user_card=? order by card_balance desc";

	private static final Logger LOG = Logger.getLogger(CardDAO.class);

	protected CardDAO() throws DBException {
		super();
	}

	private static CardDAO instance;

	public static synchronized CardDAO getInstance() throws DBException {
		if (instance == null) {
			instance = new CardDAO();
		}
		return instance;
	}

	/**
	 * Sends request to administrator, if user card is blocked
	 * 
	 * @param cardNumber
	 * @return
	 * @throws DBException
	 */
	public boolean requestUnblockCard(int cardNumber) throws DBException {
		Connection con = null;
		PreparedStatement blockCardStatement = null;
		try {
			con = getConnection();
			blockCardStatement = con.prepareStatement(SQL_REQUEST_UNBLOCK_CARD);
			blockCardStatement.setInt(1, cardNumber);
			blockCardStatement.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error("Cannot request unblock card: ", ex);
		} finally {
			close(con);
		}
		return true;
	}

	/**
	 * If requested unblock from client, administrator unblocks card using this
	 * method
	 * 
	 * @param cardNumber
	 * @return
	 * @throws DBException
	 */
	public boolean unblockCard(int cardNumber) throws DBException {
		Connection con = null;
		PreparedStatement blockCardStatement = null;
		try {
			con = getConnection();
			blockCardStatement = con.prepareStatement(SQL_UNBLOCK_CARD);
			blockCardStatement.setInt(1, cardNumber);
			blockCardStatement.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error("Cannot unblock card: ", ex);
		} finally {
			close(con);
		}
		return true;
	}

	/**
	 * Client blocks selected card
	 * 
	 * @param cardNumber
	 * @return
	 * @throws DBException
	 */
	public boolean blockCard(int cardNumber) throws DBException {
		Connection con = null;
		PreparedStatement blockCardStatement = null;
		try {
			con = getConnection();
			blockCardStatement = con.prepareStatement(SQL_BLOCK_CARD);
			blockCardStatement.setInt(1, cardNumber);
			blockCardStatement.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error("Cannot block card: ", ex);
		} finally {
			close(con);
		}
		return true;
	}

	/**
	 * Client refills current card via reffilling terminal
	 * 
	 * @param card
	 * @param amount
	 * @return
	 * @throws DBException
	 */
	public boolean refillExistingCard(Card card, int amount) throws DBException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_REFILL_CARD);
			pstmt.setInt(1, card.getCardBalance() + amount);
			pstmt.setInt(2, card.getCardNumber());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error("Cannot refill existing card: ", ex);
		} finally {
			close(con, pstmt);
		}
		return true;
	}

	/**
	 * Client creates new card
	 * 
	 * @param user
	 * @return
	 * @throws DBException
	 */
	public boolean createNewCard(User user) throws DBException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			System.out.println(user);
			pstmt = con.prepareStatement(SQL_CREATE_CARD);
			pstmt.setInt(1, user.getId());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error("Cannot create new card: ", ex);
			throw new DBException(Messages.ERR_CANNOT_CREATE_NEW_CARD, ex);
		} finally {
			close(con, pstmt);
		}
		return true;
	}

	/**
	 * Finds all users' cards from DB
	 * 
	 * @param user
	 * @return
	 * @throws DBException
	 */
	public List<Card> findUserCards(User user) throws DBException {
		List<Card> cardList = new ArrayList<>();
		Card card = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_CARDS);
			pstmt.setInt(1, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				card = extractCard(rs);
				cardList.add(card);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error("Cannot find user cards: ", ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CARDS_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return cardList;
	}

	/**
	 * Finds all users' cards from DB ordered by card number
	 * 
	 * @param user
	 * @return
	 * @throws DBException
	 */
	public List<Card> findUserCardsOrderByNumber(User user) throws DBException {
		List<Card> cardList = new ArrayList<>();
		Card card = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pstmt = con
					.prepareStatement(SQL_FIND_USER_CARDS_ORDER_BY_CARD_NUMBER);
			pstmt.setInt(1, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				card = extractCard(rs);
				cardList.add(card);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error("Cannot find user cards ordered by number: ", ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CARDS_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return cardList;
	}

	/**
	 * Finds all users' cards from DB ordered by card balance
	 * 
	 * @param user
	 * @return
	 * @throws DBException
	 */
	public List<Card> findUserCardsOrderByBalance(User user) throws DBException {
		List<Card> cardList = new ArrayList<>();
		Card card = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pstmt = con
					.prepareStatement(SQL_FIND_USER_CARDS_ORDER_BY_CARD_BALANCE);
			pstmt.setInt(1, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				card = extractCard(rs);
				cardList.add(card);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error("Cannot find user cards ordered by balance: ", ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CARDS_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return cardList;
	}

	/**
	 * Finds user's cards by given id
	 * 
	 * @param id
	 * @return
	 * @throws DBException
	 */
	public List<Card> findUserCards(int id) throws DBException {
		List<Card> cardList = new ArrayList<>();
		Card card = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_CARDS);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				card = extractCard(rs);
				cardList.add(card);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error("Cannot find user cards by user id: ", ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CARDS_BY_USER_ID,
					ex);
		} finally {
			close(con, pstmt, rs);
		}
		return cardList;
	}

	/**
	 * Finds card by its id
	 * 
	 * @param id
	 * @return
	 * @throws DBException
	 */
	public Card findCardById(int id) throws DBException {
		Card card = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_CARD_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				card = extractCard(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error("Cannot find user card by id: ", ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CARD_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return card;
	}

	/**
	 * Extracts card from DB if all fields are needed
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Card extractCard(ResultSet rs) throws SQLException {
		Card card = new Card();
		card.setCardNumber(rs.getInt(Fields.CARD_NUMBER));
		card.setCardBalance(rs.getInt(Fields.CARD_BALANCE));
		card.setUserCard(rs.getInt(Fields.CARD_USER_CARD));
		card.setBlocked(rs.getBoolean(Fields.CARD_IS_BLOCKED));
		card.setRequested(rs.getBoolean(Fields.CARD_IS_REQUESTED));
		return card;
	}

}
