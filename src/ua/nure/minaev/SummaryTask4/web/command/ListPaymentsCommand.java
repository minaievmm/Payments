package ua.nure.minaev.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.minaev.SummaryTask4.Path;
import ua.nure.minaev.SummaryTask4.db.dao.PaymentDAO;
import ua.nure.minaev.SummaryTask4.db.entity.Payment;
import ua.nure.minaev.SummaryTask4.db.entity.User;
import ua.nure.minaev.SummaryTask4.exception.AppException;

/**
 * Shows payment list for client and admin
 * 
 * @author M.Minaiev
 * 
 */
public class ListPaymentsCommand extends Command {

	private static final long serialVersionUID = -824025928378333205L;

	private static final Logger LOG = Logger
			.getLogger(ListPaymentsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		PaymentDAO paymentDAO = PaymentDAO.getInstance();

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		List<Payment> paymentList = paymentDAO.findUserPayments(user);

		LOG.trace("Obtained payments for " + user.getLogin());

		request.setAttribute("paymentList", paymentList);

		session.setAttribute("user", user);

		String forward = Path.PAGE_LIST_PAYMENTS;

		LOG.debug("Command finished");

		return forward;
	}

}
