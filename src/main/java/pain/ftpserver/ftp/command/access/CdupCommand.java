package pain.ftpserver.ftp.command.access;

import java.io.IOException;

import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.exception.CommandException;

public class CdupCommand extends CwdCommand {

	public CdupCommand(Client source) {
		super(source);
	}

	@Override
	public void execute(String arg) throws CommandException, IOException {
		super.execute("..");
	}
}
