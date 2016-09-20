package ua.nure.minaev.SummaryTask4.exception;

/**
 * Holder for messages of exceptions.
 * 
 * @author M.Minaiev
 * 
 */
public class Messages {

	private Messages() {

	}

	public static final String ERR_CANNOT_OBTAIN_CARD_BY_ID = "Cannot obtain card by id";

	public static final String ERR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain a connection from the pool";

	public static final String ERR_CANNOT_OBTAIN_USER_BY_ID = "Cannot obtain a user by its id";

	public static final String ERR_CANNOT_OBTAIN_USER_BY_LOGIN = "Cannot obtain a user by its login";

	public static final String ERR_CANNOT_OBTAIN_CARDS_BY_LOGIN = "Cannot obtain a cards by user";

	public static final String ERR_CANNOT_UPDATE_USER = "Cannot update a user";

	public static final String ERR_CANNOT_CLOSE_CONNECTION = "Cannot close a connection";

	public static final String ERR_CANNOT_CLOSE_RESULTSET = "Cannot close a result set";

	public static final String ERR_CANNOT_CLOSE_STATEMENT = "Cannot close a statement";

	public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain the data source";

	public static final String ERR_INSUFFICIENT_FUNDS = "Insufficient funds on card";

	public static final String ERR_CANNOT_OBTAIN_CARDS_BY_USER_ID = "Cannot obtain cards by user id";

	public static final String ERR_CANNOT_PROCEED_PAYMENT = "Cannot proceed payment";

	public static final String ERR_CANNOT_OBTAIN_PAYMENTS_BY_LOGIN = "Cannot obtain payments by user login";

	public static final String ERR_CANNOT_CREATE_NEW_CARD = "Cannot create new card";

}