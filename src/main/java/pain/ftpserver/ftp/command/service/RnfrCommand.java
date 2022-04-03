package pain.ftpserver.ftp.command.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.command.AuthenticatedCommand;
import pain.ftpserver.ftp.exception.CommandException;
import pain.ftpserver.ftp.exception.InternalErrorException;
import pain.ftpserver.ftp.exception.SyntaxErrorException;

public class RnfrCommand extends AuthenticatedCommand {

	public RnfrCommand(Client source) {
		super(source);
	}

	@Override
	public void execute(String arg) throws CommandException, IOException {
		super.execute(arg);
		if (arg == null || arg.isEmpty()) {
			throw new SyntaxErrorException("Missing directory");
		}
		
		try {
			source.getPath().setRenamingCandidate(arg);
			source.getControl().send("350 Requested file action pending further information.");
		} catch (FileNotFoundException e) {
			throw new InternalErrorException(e);
		}
	}
}
