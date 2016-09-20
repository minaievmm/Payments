package ua.nure.minaev.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.minaev.SummaryTask4.Path;
import ua.nure.minaev.SummaryTask4.db.dao.CardDAO;
import ua.nure.minaev.SummaryTask4.db.dao.PaymentDAO;
import ua.nure.minaev.SummaryTask4.db.dao.UserDAO;
import ua.nure.minaev.SummaryTask4.db.entity.Card;
import ua.nure.minaev.SummaryTask4.db.entity.Payment;
import ua.nure.minaev.SummaryTask4.db.entity.User;
import ua.nure.minaev.SummaryTask4.exception.AppException;

/**
 * Shows account list for admin
 * 
 * @author M.Minaiev
 * 
 */
public class ListAccountCommand extends Command {

	private static final long serialVersionUID = -3260438496320521759L;
	private static final Logger LOG = Logger
			.getLogger(ListAccountCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		UserDAO userDAO = UserDAO.getInstance();
		CardDAO cardDAO = CardDAO.getInstance();
		PaymentDAO paymentDAO = PaymentDAO.getInstance();

		List<User> userList = userDAO.findAllUsers();
		List<Card> cardList = null;
		List<Payment> paymentList = null;
		for (User user : userList) {
			int id = user.getId();
			cardList = cardDAO.findUserCards(id);
			LOG.trace("Obtained cardList for user: " + user.getFirstName()
					+ " " + user.getLastName());
			user.setCardQuantity(cardList.size());
			paymentList = paymentDAO.findUserPaymentsById(id);
			LOG.trace("Obtained paymentList for user: " + user.getFirstName()
					+ " " + user.getLastName());
			user.setPaymentQuantity(paymentList.size());
		}
		LOG.trace("Obtained list of users: " + userList);

		request.setAttribute("userList", userList);

		String forward = Path.PAGE_LIST_ACCOUNT;
		LOG.debug("Command finished");
		return forward;
	}

}
