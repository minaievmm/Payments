package ua.nure.minaev.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.minaev.SummaryTask4.Path;
import ua.nure.minaev.SummaryTask4.db.dao.CardDAO;
import ua.nure.minaev.SummaryTask4.exception.AppException;

/**
 * 
 * @author M.Minaiev
 * 
 */
public class UnblockCardCommand extends Command {

	private static final long serialVersionUID = 2874010570942164592L;
	private static final Logger LOG = Logger
			.getLogger(UnblockCardCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		int cardNumber = Integer.parseInt(request.getParameter("cardNumber"));

		CardDAO cardDAO = CardDAO.getInstance();

		cardDAO.unblockCard(cardNumber);

		LOG.debug("Command finished");

		String forward = Path.COMMAND_LIST_ACCOUNT;

		return forward;
	}

}
