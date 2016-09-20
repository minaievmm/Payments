package ua.nure.minaev.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.minaev.SummaryTask4.Path;
import ua.nure.minaev.SummaryTask4.db.dao.CardDAO;
import ua.nure.minaev.SummaryTask4.db.entity.Card;
import ua.nure.minaev.SummaryTask4.exception.AppException;

/**
 * 
 * @author M.Minaiev
 * 
 */
public class ViewUserCardsCommand extends Command {

	private static final long serialVersionUID = -824025928378333205L;

	private static final Logger LOG = Logger
			.getLogger(ViewUserCardsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		CardDAO cardDAO = CardDAO.getInstance();

		int id = Integer.parseInt(request.getParameter("id"));

		List<Card> cardList = cardDAO.findUserCards(id);

		LOG.trace("Obtained cards for user with id: " + id);

		request.setAttribute("cardList", cardList);

		String forward = Path.PAGE_ERROR_PAGE;

		if (cardList.size() > 0) {
			forward = Path.PAGE_LIST_CARDS_ADMIN;
		}
		LOG.debug("Command finished");

		return forward;
	}

}
