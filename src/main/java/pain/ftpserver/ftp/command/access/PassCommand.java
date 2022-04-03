package pain.ftpserver.ftp.command.access;

import java.io.File;
import java.io.IOException;

import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.command.Command;
import pain.ftpserver.ftp.config.UserConfiguration;
import pain.ftpserver.ftp.context.ClientState;
import pain.ftpserver.ftp.context.PathContext;
import pain.ftpserver.ftp.exception.SyntaxErrorException;

public class PassCommand extends Command {

	public PassCommand(Client source) {
		super(source);
	}

	@Override
	public void execute(String arg) throws SyntaxErrorException, IOException {
		if (arg == null || arg.isEmpty()) {
			throw new SyntaxErrorException("Missing password");
		}

		if (source.getUsername() == null) {
			source.getControl().send("503 Please connect with USER first.");
		}
		else {
			UserConfiguration config = UserConfiguration.getInstance();
			String username = source.getUsername();
			if (config.authenticate(username, arg)) {
				source.setClientState(ClientState.IDLE);
				
				File rootFile = config.getRoot(username);
				source.setPath(new PathContext(rootFile.toPath(), rootFile.toPath()));
				source.getControl().send("230 User logged in, proceed.");
			}
			else {
				source.getControl().send("530 Not logged in.");
			}
		}
	}

}
