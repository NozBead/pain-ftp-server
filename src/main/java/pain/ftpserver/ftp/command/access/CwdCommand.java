package pain.ftpserver.ftp.command.access;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;

import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.command.AuthenticatedCommand;
import pain.ftpserver.ftp.exception.CommandException;
import pain.ftpserver.ftp.exception.InternalErrorException;
import pain.ftpserver.ftp.exception.RootJailException;
import pain.ftpserver.ftp.exception.SyntaxErrorException;

public class CwdCommand extends AuthenticatedCommand {

	public CwdCommand(Client source) {
		super(source);
	}

	@Override
	public void execute(String arg) throws CommandException, IOException {
		super.execute(arg);
		if (arg == null || arg.isEmpty()) {
			throw new SyntaxErrorException("Missing directory");
		}
		
		try {
			source.getPath().changeWorkingDir(arg);
			source.getControl().send("250 Directory changed.");
		} catch (NotDirectoryException | FileNotFoundException | RootJailException e) {
			throw new InternalErrorException(e);
		}
	}
}
