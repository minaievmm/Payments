package ua.nure.minaev.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.minaev.SummaryTask4.Path;

/**
 * Logout command.
 * 
 * @author M.Minaiev
 * 
 */
public class LogoutCommand extends Command {

	private static final long serialVersionUID = -892289951569172540L;
	private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		LOG.debug("Command starts");

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		LOG.debug("Command finished");
		return Path.PAGE_LOGIN;
	}

}