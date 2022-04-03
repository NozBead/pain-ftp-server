package pain.ftpserver.ftp.exception;

/**
 * Exception lors d'une erreur de syntaxe d'une commande.
 * @author Enzo Pain
 */
public class SyntaxErrorException extends CommandException {

	public SyntaxErrorException(String message) {
		super(message);
	}
	
	public SyntaxErrorException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SyntaxErrorException(Throwable cause) {
		super(cause);
	}
}
