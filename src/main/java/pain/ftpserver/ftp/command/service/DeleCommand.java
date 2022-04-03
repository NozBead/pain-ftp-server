package pain.ftpserver.ftp.command.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.command.AuthenticatedCommand;
import pain.ftpserver.ftp.exception.CommandException;
import pain.ftpserver.ftp.exception.InternalErrorException;
import pain.ftpserver.ftp.exception.SyntaxErrorException;

public class DeleCommand extends AuthenticatedCommand {

	public DeleCommand(Client source) {
		super(source);
	}

	@Override
	public void execute(String arg) throws CommandException, IOException {
		super.execute(arg);
		if (arg == null || arg.isEmpty()) {
			throw new SyntaxErrorException("Missing filename");
		}
		
		try {
			if (source.getPath().delete(arg)) {
				source.getControl().send("250 File deleted.");
			}
			else {
				source.getControl().send("450 Requested action not taken.");
			}
		} catch (FileNotFoundException e) {
			throw new InternalErrorException(e);
		}
	}
}
