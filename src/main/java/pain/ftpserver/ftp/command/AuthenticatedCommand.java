package pain.ftpserver.ftp.command;

import java.io.IOException;

import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.context.ClientState;
import pain.ftpserver.ftp.exception.CommandException;
import pain.ftpserver.ftp.exception.NotAuthenticatedException;

/**
 * Représente une commande FTP où un utilisateur doit être authentifié.
 * @author Enzo Pain
 */
public class AuthenticatedCommand extends Command {

	public AuthenticatedCommand(Client source) {
		super(source);
	}

	@Override
	public void execute(String arg) throws CommandException, IOException {
		if (source.getClientState() == ClientState.ANONYMOUS) {
			throw new NotAuthenticatedException();
		}
	}
}
