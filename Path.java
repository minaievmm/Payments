package ua.nure.minaev.SummaryTask4;

/**
 * Path holder (jsp pages, controller commands).
 * 
 * @author M.Minaiev
 * 
 */
public final class Path {

	// pages
	public static final String PAGE_LOGIN = "/SummaryTask4";
	public static final String PAGE_START = "/login.jsp";
	public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE_LIST_CARDS = "/WEB-INF/jsp/client/list_cards.jsp";
	public static final String PAGE_LIST_ACCOUNT = "/WEB-INF/jsp/admin/list_account.jsp";
	public static final String PAGE_LIST_PAYMENTS = "/WEB-INF/jsp/client/list_payments.jsp";
	public static final String PAGE_REFILL_CARD = "/WEB-INF/jsp/client/refilling_terminal.jsp";
	public static final String PAGE_MAKE_PAYMENT = "/WEB-INF/jsp/client/payment_maker.jsp";
	public static final String PAGE_LIST_CARDS_ADMIN = "/WEB-INF/jsp/admin/list_cards_admin.jsp";
	public static final String PAGE_LIST_PAYMENTS_ADMIN = "/WEB-INF/jsp/admin/list_payments_admin.jsp";

	// commands
	public static final String COMMAND_LIST_PAYMENTS = "/controller?command=listPayment";
	public static final String COMMAND_LIST_ACCOUNT = "/controller?command=listAccount";
	public static final String COMMAND_LIST_CARDS = "/controller?command=listCards";
	public static final String COMMAND_REGISTER = "/controller?command=register";
	public static final String COMMAND_CREATE_CARD = "/controller?command=createCard";
	public static final String COMMAND_REQUEST_BLOCK = "/controller?command=requestBlock";
	public static final String COMMAND_UNBLOCK_ACCOUNT = "/controller?command=unblockAccount";
	public static final String COMMAND_MAKE_PAYMENT = "/controller?command=makePayment";
	public static final String COMMAND_REFILL_CARD = "/controller?command=refillCard";
	public static final String COMMAND_BLOCK_CARD = "/controller?command=blockCard";
	public static final String COMMAND_REQUEST_UNBLOCK_CARD = "/controller?command=requestUnblock";
	public static final String COMMAND_UNBLOCK_CARD = "/controller?command=unblockCard";

}