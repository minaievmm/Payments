package ua.nure.minaev.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.minaev.SummaryTask4.Path;
import ua.nure.minaev.SummaryTask4.db.dao.CardDAO;
import ua.nure.minaev.SummaryTask4.db.entity.User;
import ua.nure.minaev.SummaryTask4.exception.AppException;

/**
 * Creates user's card
 * 
 * @author M.Minaiev
 * 
 */
public class CreateCardCommand extends Command {

	private static final long serialVersionUID = -4011567702251876247L;
	private static final Logger LOG = Logger.getLogger(CreateCardCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		CardDAO cardDAO = CardDAO.getInstance();

		User user = (User) session.getAttribute("user");

		LOG.trace("Obtained user " + user);

		cardDAO.createNewCard(user);

		LOG.trace("Card created for: " + user.getFirstName() + " "
				+ user.getLastName());

		String forward = Path.COMMAND_LIST_CARDS;

		LOG.debug("Command finished");

		return forward;
	}

}
