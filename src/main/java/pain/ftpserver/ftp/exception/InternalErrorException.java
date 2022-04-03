package pain.ftpserver.ftp.exception;

/**
 * Exception lors d'une erreur interne au serveur FTP.
 * @author Enzo Pain
 */
public class InternalErrorException extends CommandException {

	public InternalErrorException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InternalErrorException(Throwable cause) {
		super(cause);
	}
	
	public InternalErrorException() {
		super("");
	}
}
