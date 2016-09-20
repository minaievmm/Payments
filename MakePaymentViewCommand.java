package ua.nure.minaev.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.minaev.SummaryTask4.Path;
import ua.nure.minaev.SummaryTask4.db.dao.CardDAO;
import ua.nure.minaev.SummaryTask4.db.dao.UserDAO;
import ua.nure.minaev.SummaryTask4.db.entity.Card;
import ua.nure.minaev.SummaryTask4.db.entity.User;
import ua.nure.minaev.SummaryTask4.exception.AppException;

/**
 * Shows page of payment management for client
 * 
 * @author M.Minaiev
 * 
 */
public class MakePaymentViewCommand extends Command {

	private static final long serialVersionUID = -4011567702251876247L;
	private static final Logger LOG = Logger
			.getLogger(MakePaymentViewCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		CardDAO cardDAO = CardDAO.getInstance();
		UserDAO userDAO = UserDAO.getInstance();

		User user = (User) session.getAttribute("user");
		LOG.trace("Obtained session attribute for user --> " + user);

		List<User> userList = userDAO.findAllUsers();

		List<Card> cardList = cardDAO.findUserCards(user);

		request.setAttribute("userList", userList);
		LOG.trace("Request attribute for userList --> " + userList);
		request.setAttribute("cardList", cardList);
		LOG.trace("Request attribute for cardList --> " + cardList);
		session.setAttribute("user", user);
		LOG.trace("Session attribute for user --> " + user);
		String forward = Path.PAGE_MAKE_PAYMENT;

		LOG.debug("Command finished");

		return forward;
	}

}
