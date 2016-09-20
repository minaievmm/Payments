package ua.nure.minaev.SummaryTask4.db;

/**
 * Holds fields from DB
 * 
 * @author M.Minaiev
 * 
 */
public final class Fields {

	// entities
	public static final String USER_ID = "user_id";
	public static final String USER_LOGIN = "login";
	public static final String USER_PASSWORD = "password";
	public static final String USER_FIRST_NAME = "first_name";
	public static final String USER_LAST_NAME = "last_name";
	public static final String USER_ROLE_ID = "role_id";

	public static final String PAYMENT_ID = "payment_id";
	public static final String PAYMENT_STATE = "state_id";
	public static final String PAYMENT_AMOUNT = "amount";
	public static final String PAYMENT_DATE = "payment_date";
	public static final String PAYMENT_USER_PAYMENT = "user_payment";
	public static final String PAYMENT_USER_RECIPIENT = "user_recipient";
	public static final String PAYMENT_USER_CARD = "user_card";

	public static final String CARD_NUMBER = "card_number";
	public static final String CARD_BALANCE = "card_balance";
	public static final String CARD_USER_CARD = "user_card";
	public static final String CARD_IS_BLOCKED = "isBlocked";
	public static final String CARD_IS_REQUESTED = "isRequested";

}