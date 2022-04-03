package pain.ftpserver.ftp.command.params;

import java.io.IOException;

import pain.ftpserver.connection.strategy.PassiveStrategy;
import pain.ftpserver.connection.strategy.SocketStrategy;
import pain.ftpserver.ftp.Client;

public class PasvCommand extends DataPortCommand {
	public PassiveStrategy strat;

	public PasvCommand(Client source) {
		super(source);
	}

	@Override
	protected SocketStrategy getStrategy(String arg) throws IOException {
		strat = new PassiveStrategy();
		return strat;
	}

	@Override
	protected String getResponse() {
		return "227 Entering Passive Mode " + strat + '.';
	}
}
