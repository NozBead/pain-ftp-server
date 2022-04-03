package pain.ftpserver.ftp.command.access;

import java.io.IOException;

import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.command.Command;
import pain.ftpserver.ftp.exception.CommandException;

public class QuitCommand extends Command {

	public QuitCommand(Client source) {
		super(source);
	}

	@Override
	public void execute(String arg) throws CommandException, IOException {
		source.getControl().send("221 Service closing control connection.");
		source.stopListen();
	}
}
