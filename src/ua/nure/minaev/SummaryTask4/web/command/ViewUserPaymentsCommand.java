package ua.nure.minaev.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.minaev.SummaryTask4.Path;
import ua.nure.minaev.SummaryTask4.db.dao.PaymentDAO;
import ua.nure.minaev.SummaryTask4.db.dao.UserDAO;
import ua.nure.minaev.SummaryTask4.db.entity.Payment;
import ua.nure.minaev.SummaryTask4.db.entity.User;
import ua.nure.minaev.SummaryTask4.exception.AppException;

/**
 * 
 * @author M.Minaiev
 * 
 */
public class ViewUserPaymentsCommand extends Command {

	private static final long serialVersionUID = -824025928378333205L;

	private static final Logger LOG = Logger
			.getLogger(ViewUserPaymentsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		PaymentDAO paymentDAO = PaymentDAO.getInstance();
		UserDAO userDAO = UserDAO.getInstance();

		int id = Integer.parseInt(request.getParameter("id"));

		User user = userDAO.findUserById(id);

		List<Payment> paymentList = paymentDAO.findUserPayments(user);

		LOG.trace("Obtained payments for user with id: " + id);

		request.setAttribute("paymentList", paymentList);

		String forward = Path.PAGE_LIST_PAYMENTS_ADMIN;

		LOG.debug("Command finished");

		return forward;
	}

}