package ua.nure.minaev.SummaryTask4.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * 
 * @author M.Minaiev
 * 
 */
public class CommandContainer {

	private static final Logger LOG = Logger.getLogger(CommandContainer.class);

	private static Map<String, Command> commands = new TreeMap<String, Command>();

	static {
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("register", new RegisterCommand());

		// client commands
		commands.put("listCards", new ListCardsCommand());
		commands.put("createCard", new CreateCardCommand());
		commands.put("preparePayment", new PreparePaymentCommand());
		commands.put("listPayment", new ListPaymentsCommand());
		commands.put("requestUnblock", new RequestUnblockCardCommand());
		commands.put("refillCardView", new RefillingTerminalViewCommand());
		commands.put("refillCard", new RefillCardCommand());
		commands.put("makePaymentView", new MakePaymentViewCommand());
		commands.put("makePayment", new MakePaymentCommand());
		commands.put("blockCard", new BlockCardCommand());
		commands.put("listCardsSorted", new ListCardsSortedCommand());
		commands.put("listPaymentsSorted", new ListPaymentsSortedCommand());

		// admin commands
		commands.put("listAccount", new ListAccountCommand());
		commands.put("unblockCard", new UnblockCardCommand());
		commands.put("viewUserCards", new ViewUserCardsCommand());
		commands.put("viewUserPayments", new ViewUserPaymentsCommand());

		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            Name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand");
		}

		return commands.get(commandName);
	}

}