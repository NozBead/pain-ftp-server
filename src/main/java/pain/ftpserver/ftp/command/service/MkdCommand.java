package pain.ftpserver.ftp.command.service;

import java.io.IOException;

import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.command.AuthenticatedCommand;
import pain.ftpserver.ftp.exception.CommandException;
import pain.ftpserver.ftp.exception.InternalErrorException;
import pain.ftpserver.ftp.exception.SyntaxErrorException;

public class MkdCommand extends AuthenticatedCommand {

	public MkdCommand(Client source) {
		super(source);
	}

	@Override
	public void execute(String arg) throws CommandException, IOException {
		super.execute(arg);
		if (arg == null || arg.isEmpty()) {
			throw new SyntaxErrorException("Missing directory");
		}
		
		if (!source.getPath().addDir(arg)) {
			throw new InternalErrorException();
		}
		source.getControl().send("257 Directory created.");
	}
}
