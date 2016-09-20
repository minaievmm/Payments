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
 * Blocks card on request
 * 
 * @author M.Minaiev
 * 
 */
public class BlockCardCommand extends Command {

	private static final long serialVersionUID = -3411755566432831019L;
	private static final Logger LOG = Logger.getLogger(BlockCardCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		int cardNumber = Integer.parseInt(request.getParameter("cardNumber"));

		LOG.trace("Obtained card number: " + cardNumber);

		CardDAO cardDAO = CardDAO.getInstance();

		cardDAO.blockCard(cardNumber);

		LOG.trace("Card " + cardNumber + " blocked");

		LOG.debug("Command finished");

		String forward = Path.COMMAND_LIST_CARDS;

		return forward;
	}
}
