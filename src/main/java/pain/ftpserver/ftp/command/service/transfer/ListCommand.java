package pain.ftpserver.ftp.command.service.transfer;

import java.io.IOException;

import pain.ftpserver.connection.BinaryConnection;
import pain.ftpserver.ftp.Client;

public class ListCommand extends TransferCommand<String> {

	public ListCommand(Client source) {
		super(source);
	}

	@Override
	protected String getTransferred(String arg) throws IOException {
		return source.getPath().getList(arg);
	}

	@Override
	protected void transfer(BinaryConnection transmitter, String toTransfer) throws IOException {
		transmitter.send(toTransfer);
	}
}
