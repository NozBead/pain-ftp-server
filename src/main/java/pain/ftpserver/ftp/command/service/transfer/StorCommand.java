package pain.ftpserver.ftp.command.service.transfer;

import java.io.File;
import java.io.IOException;

import pain.ftpserver.connection.BinaryConnection;
import pain.ftpserver.ftp.Client;

public class StorCommand extends FileTransferCommand {

	public StorCommand(Client source) {
		super(source);
	}
	
	@Override
	protected void transfer(BinaryConnection transmitter, File file) throws IOException {
		try {
			transmitter.recieveFile(file);
		}
		catch (IOException e) {
			file.delete();
			throw new IOException();
		}
	}
}