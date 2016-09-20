package ua.nure.minaev.SummaryTask4.exception;

import ua.nure.minaev.SummaryTask4.exception.AppException;

/**
 * An exception that provides information on a database access error.
 * 
 * @author M.Minaiev
 * 
 */
public class DBException extends AppException {

	private static final long serialVersionUID = -3550446897536410392L;

	public DBException() {
		super();
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

}
