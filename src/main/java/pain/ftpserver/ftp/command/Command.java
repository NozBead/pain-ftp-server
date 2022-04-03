package pain.ftpserver.ftp.command;

import java.io.IOException;

import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.exception.CommandException;

/**
 * Représente une commande FTP.
 * @author Enzo Pain
 */
public abstract class Command {
	protected Client source;
	
	public Command(Client source) {
		this.source = source;
	}
	
	/**
	 * Execute la commande avec l'argument donné.
	 * @param arg L'argument à fournir à la commande
	 * @throws CommandException En cas d'erreur lié à l'éxecution de la commande
	 * @throws IOException En cas d'erreur réseau
	 */
	public abstract void execute(String arg) throws CommandException, IOException;
}
