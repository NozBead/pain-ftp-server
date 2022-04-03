package pain.ftpserver.ftp.exception;

/**
 * Exception lors de l'éxecution d'une commande qui ne respecte pas la règle du Root Jail.
 * @author Enzo Pain
 */
public class RootJailException extends CommandException {

	public RootJailException(String message) {
		super(message);
	}
}
