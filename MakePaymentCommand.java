package ua.nure.minaev.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.minaev.SummaryTask4.Path;
import ua.nure.minaev.SummaryTask4.db.dao.CardDAO;
import ua.nure.minaev.SummaryTask4.db.dao.PaymentDAO;
import ua.nure.minaev.SummaryTask4.db.entity.Card;
import ua.nure.minaev.SummaryTask4.db.entity.Payment;
import ua.nure.minaev.SummaryTask4.exception.AppException;

/**
 * Makes payment on request after preparing
 * 
 * @author M.Minaiev
 * 
 */
public class MakePaymentCommand extends Command {

	private static final long serialVersionUID = -8635390480238392869L;
	private static final Logger LOG = Logger
			.getLogger(MakePaymentCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		CardDAO cardDAO = CardDAO.getInstance();
		PaymentDAO paymentDAO = PaymentDAO.getInstance();

		int paymentId = Integer.parseInt(request.getParameter("paymentId"));
		LOG.trace("Obtained parameter for paymentID: " + paymentId);
		int userCard = Integer.parseInt(request.getParameter("userCard"));
		LOG.trace("Obtained parameter for userCard: " + userCard);

		Payment payment = paymentDAO.findPaymentById(paymentId);
		LOG.trace("Obtained payment by id:" + paymentId + " is --> " + payment);

		Card card = cardDAO.findCardById(userCard);
		LOG.trace("Obtained payer's card --> " + card);

		Card recipientCard = cardDAO.findCardById(payment.getUserRecipient());
		LOG.trace("Obtained recipient's card --> " + recipientCard);

		String forward = Path.PAGE_ERROR_PAGE;

		if (paymentDAO.makePayment(paymentId, payment.getAmount(),
				recipientCard, card)) {
			forward = Path.COMMAND_LIST_PAYMENTS;
		}

		LOG.debug("Command finished");

		return forward;
	}

}
