package pain.ftpserver.ftp.exception;

/**
 * Exception lors de l'éxecution d'une commande.
 * @author Enzo Pain
 */
public abstract class CommandException extends Exception {
	
	public CommandException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CommandException(String message) {
		super(message);
	}
	
	public CommandException(Throwable cause) {
		super(cause);
	}
}
