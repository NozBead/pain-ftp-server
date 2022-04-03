package pain.ftpserver.ftp.exception;

/**
 * Exception lors de l'éxecution d'une commande sans être authentifié.
 * @author Enzo Pain
 */
public class NotAuthenticatedException extends CommandException {

	public NotAuthenticatedException() {
		super("");
	}
}
