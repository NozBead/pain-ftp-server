package pain.ftpserver.ftp.command.params;

import java.io.IOException;

import pain.ftpserver.connection.BinaryConnection;
import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.command.Command;
import pain.ftpserver.ftp.exception.CommandException;

public class FeatCommand extends Command {

	public FeatCommand(Client source) {
		super(source);
	}

	@Override
	public void execute(String arg) throws CommandException, IOException {
		BinaryConnection c = source.getControl();
		c.send("221- \r\nUTF8\r\n221 end");
	}

}
