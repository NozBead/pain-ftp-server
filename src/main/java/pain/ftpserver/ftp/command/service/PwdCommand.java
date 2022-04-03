package pain.ftpserver.ftp.command.service;

import java.io.IOException;

import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.command.AuthenticatedCommand;
import pain.ftpserver.ftp.context.PathContext;
import pain.ftpserver.ftp.exception.CommandException;

public class PwdCommand extends AuthenticatedCommand {

	public PwdCommand(Client source) {
		super(source);
	}

	@Override
	public void execute(String arg) throws CommandException, IOException {
		super.execute(arg);
		PathContext path = source.getPath();
		source.getControl().send("257 " + path.toString());
	}
}
