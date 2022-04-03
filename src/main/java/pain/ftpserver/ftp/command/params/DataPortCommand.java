package pain.ftpserver.ftp.command.params;

import java.io.IOException;

import pain.ftpserver.connection.BinaryConnection;
import pain.ftpserver.connection.strategy.SocketStrategy;
import pain.ftpserver.connection.util.ConnectionFactory;
import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.command.AuthenticatedCommand;
import pain.ftpserver.ftp.context.ClientState;
import pain.ftpserver.ftp.exception.CommandException;

public abstract class DataPortCommand extends AuthenticatedCommand {

	public DataPortCommand(Client source) {
		super(source);
	}

	@Override
	public void execute(String arg) throws CommandException, IOException {
		super.execute(arg);
		BinaryConnection transmitter = ConnectionFactory.createTransmitter(source.getType(), getStrategy(arg));
		source.setTransferConnection(transmitter);
		transmitter.start();
		source.getControl().send(getResponse());
		source.setClientState(ClientState.TRANSFERT);
	}

	protected abstract String getResponse();
	protected abstract SocketStrategy getStrategy(String arg) throws IOException;
}
