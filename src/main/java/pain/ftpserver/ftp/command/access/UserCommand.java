package pain.ftpserver.ftp.command.access;

import java.io.IOException;

import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.command.Command;
import pain.ftpserver.ftp.exception.InternalErrorException;
import pain.ftpserver.ftp.exception.SyntaxErrorException;

public class UserCommand extends Command {

	public UserCommand(Client source) {
		super(source);
	}

	@Override
	public void execute(String arg) throws SyntaxErrorException, InternalErrorException, IOException {
		if (arg == null || arg.isEmpty()) {
			throw new SyntaxErrorException("User missing");
		}
		
		source.setUsername(arg);
		source.getControl().send("331 User name okay, need password.");
	}

}
