package ua.nure.minaev.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.minaev.SummaryTask4.Path;
import ua.nure.minaev.SummaryTask4.db.dao.*;
import ua.nure.minaev.SummaryTask4.db.entity.User;
import ua.nure.minaev.SummaryTask4.exception.AppException;

/**
 * Login command.
 * 
 * @author M.Minaiev
 * 
 */
public class RegisterCommand extends Command {

	private static final long serialVersionUID = 8845731975812604421L;

	private static final Logger LOG = Logger.getLogger(RegisterCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		UserDAO userDAO = UserDAO.getInstance();
		String login = request.getParameter("login");

		String password = request.getParameter("password");

		String firstName = request.getParameter("first_name");

		String lastName = request.getParameter("last_name");

		if (login == null || password == null || firstName == null
				|| lastName == null || login.isEmpty() || password.isEmpty()
				|| firstName.isEmpty() || lastName.isEmpty()) {
			throw new AppException("Each field must be filled");
		}

		User user = userDAO.findUserByLogin(login);
		LOG.trace("Searching for user --> " + login);

		if (user != null) {
			throw new AppException(
					"User with current login is currently exists");
		} else {
			LOG.trace("Request parameter: registering --> " + login);
			user = new User();
			user.setLogin(login);
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			userDAO.addNewUser(user);
			LOG.info("User " + user.getLogin() + " registered");
		}

		String forward = Path.PAGE_ERROR_PAGE;

		if (user != null) {
			forward = Path.PAGE_START;
		}
		LOG.debug("Command finished");

		return forward;
	}

}