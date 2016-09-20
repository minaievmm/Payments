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
import ua.nure.minaev.SummaryTask4.db.entity.Card;
import ua.nure.minaev.SummaryTask4.db.entity.User;
import ua.nure.minaev.SummaryTask4.exception.AppException;

/**
 * Shows card list for client and admin
 * 
 * @author M.Minaiev
 * 
 */
public class ListCardsCommand extends Command {

	private static final long serialVersionUID = -824025928378333205L;

	private static final Logger LOG = Logger.getLogger(ListCardsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		CardDAO cardDAO = CardDAO.getInstance();

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");
		List<Card> cardList = cardDAO.findUserCardsOrderByNumber(user);

		LOG.trace("Obtained list of cards for user " + user + ": " + cardList);

		LOG.trace("Obtained cards for " + user.getLogin() + " --> " + cardList);

		request.setAttribute("cardList", cardList);

		session.setAttribute("user", user);

		String forward = Path.PAGE_ERROR_PAGE;

		if (cardList.size() == 0) {
			forward = Path.COMMAND_CREATE_CARD;
		} else {
			forward = Path.PAGE_LIST_CARDS;
		}
		LOG.debug("Command finished");

		return forward;
	}

}
