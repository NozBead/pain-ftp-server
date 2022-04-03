package pain.ftpserver.ftp.command.params;

import java.io.IOException;
import java.net.UnknownHostException;

import pain.ftpserver.connection.strategy.SocketStrategy;
import pain.ftpserver.connection.util.SocketStrategyFactory;
import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.exception.CommandException;
import pain.ftpserver.ftp.exception.SyntaxErrorException;

public class PortCommand extends DataPortCommand {

	public PortCommand(Client source) {
		super(source);
	}

	@Override
	public void execute(String arg) throws CommandException, IOException {
		if (arg == null || arg.isEmpty()) {
			throw new SyntaxErrorException("Missing host port string");
		}
		super.execute(arg);
	}

	@Override
	protected SocketStrategy getStrategy(String arg) throws UnknownHostException {
		return SocketStrategyFactory.createActive(arg);
	}

	@Override
	protected String getResponse() {
		return "200 PORT Successful";
	}
}
