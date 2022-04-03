package pain.ftpserver.ftp.command.service.transfer;

import java.io.FileNotFoundException;
import java.io.IOException;

import pain.ftpserver.connection.BinaryConnection;
import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.command.AuthenticatedCommand;
import pain.ftpserver.ftp.context.ClientState;
import pain.ftpserver.ftp.exception.CommandException;

public abstract class TransferCommand<T> extends AuthenticatedCommand implements Runnable {
	private BinaryConnection transmitter;
	private T transferred;

	public TransferCommand(Client source) {
		super(source);
	}

	@Override
	public void execute(String arg) throws CommandException, IOException {
		super.execute(arg);
		if (source.getClientState() == ClientState.TRANSFERT) {
			try {
				transferred = getTransferred(arg);
				transmitter = source.getTransferConnection();
				Thread t = new Thread(this);
				source.setCurrentTransfer(t);
				t.start();
			} catch (FileNotFoundException e) {
				source.getControl().send("550 Requested action not taken.");
			}
		}
		else {
			source.getControl().send("425 Use PORT or PASV first.");
		}
	}
	
	public void run() {
		try {
			source.getControl().send("150 File status okay; about to open data connection.");
			try {
				transmitter.join();
				transfer(transmitter, transferred);
				transmitter.close();
			} catch (IOException | InterruptedException e) {
				source.getControl().send("426 Connection closed; transfer aborted.");
			}
			source.getControl().send("226 Closing data connection.");
			source.setClientState(ClientState.IDLE);
		} catch (IOException e) {
			source.stopListen();
		}
	}

	protected abstract T getTransferred (String arg) throws IOException;
	protected abstract void transfer(BinaryConnection transmitter, T toTransfer) throws IOException;
}