package ua.nure.minaev.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.minaev.SummaryTask4.Path;
import ua.nure.minaev.SummaryTask4.db.dao.CardDAO;
import ua.nure.minaev.SummaryTask4.db.dao.PaymentDAO;
import ua.nure.minaev.SummaryTask4.db.entity.Card;
import ua.nure.minaev.SummaryTask4.exception.AppException;

/**
 * Prepares payment for client
 * 
 * @author M.Minaiev
 * 
 */
public class PreparePaymentCommand extends Command {

	private static final long serialVersionUID = -4011567702251876247L;
	private static final Logger LOG = Logger
			.getLogger(PreparePaymentCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		CardDAO cardDAO = CardDAO.getInstance();
		PaymentDAO paymentDAO = PaymentDAO.getInstance();

		int id = Integer.parseInt(request.getParameter("id"));
		LOG.trace("Obtained parameter for id: " + id);
		int amount = Integer.parseInt(request.getParameter("amount"));
		LOG.trace("Obtained parameter for amount: " + amount);
		int recipientCardId = Integer.parseInt(request
				.getParameter("recipientCard"));
		LOG.trace("Obtained parameter for recipientCardId: " + recipientCardId);

		Card card = cardDAO.findCardById(id);
		LOG.trace("Obtained card --> " + card);

		Card recipientCard = cardDAO.findCardById(recipientCardId);

		session.setAttribute("card", card);
		LOG.trace("Session attribute for card --> " + card);

		String forward = Path.PAGE_ERROR_PAGE;

		if (paymentDAO.preparePayment(amount, recipientCard, card)) {
			forward = Path.COMMAND_LIST_PAYMENTS;
		}

		LOG.debug("Command finished");

		return forward;
	}

}
