package ua.nure.minaev.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.minaev.SummaryTask4.Path;
import ua.nure.minaev.SummaryTask4.db.dao.CardDAO;
import ua.nure.minaev.SummaryTask4.db.entity.Card;
import ua.nure.minaev.SummaryTask4.exception.AppException;

/**
 * Refills client's selected card
 * 
 * @author M.Minaiev
 * 
 */
public class RefillCardCommand extends Command {

	private static final long serialVersionUID = -4011567702251876247L;
	private static final Logger LOG = Logger.getLogger(RefillCardCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		// obtain login and password from a request
		CardDAO cardDAO = CardDAO.getInstance();

		int amount = Integer.parseInt(request.getParameter("amount"));
		LOG.trace("Obtained parameter for amount: " + amount);
		int cardNumber = Integer.parseInt(request.getParameter("card"));
		LOG.trace("Obtained parameter for cardNumber: " + cardNumber);
		Card card = cardDAO.findCardById(cardNumber);

		cardDAO.refillExistingCard(card, amount);
		LOG.trace("Exxisting card " + card.getCardNumber() + " is refilled on "
				+ amount + " UAH");

		String forward = Path.COMMAND_LIST_CARDS;

		LOG.debug("Command finished");

		return forward;
	}

}
