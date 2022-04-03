package pain.ftpserver.ftp.command.service.transfer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import pain.ftpserver.connection.BinaryConnection;
import pain.ftpserver.ftp.Client;

public class RetrCommand extends FileTransferCommand {

	public RetrCommand(Client source) {
		super(source);
	}
	
	@Override
	protected File getTransferred(String arg) throws IOException {
		File f = super.getTransferred(arg);
		if (!f.exists()) {
			throw new FileNotFoundException();
		}
		return f;
	}
	
	@Override
	protected void transfer(BinaryConnection transmitter, File file) throws IOException {
		transmitter.send(file);
	}
} 