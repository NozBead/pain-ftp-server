package pain.ftpserver.ftp.exception;

/**
 * Exception lorsque la commande reçue n'est pas reconnue.
 * @author Enzo Pain
 */
public class UnknownCommandException extends CommandException {

	public UnknownCommandException(String commandName) {
		super("Command FTP inconnue: " + commandName);
	}
}
