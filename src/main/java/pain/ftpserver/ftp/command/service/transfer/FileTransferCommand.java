package pain.ftpserver.ftp.command.service.transfer;

import java.io.File;
import java.io.IOException;

import pain.ftpserver.connection.BinaryConnection;
import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.exception.CommandException;
import pain.ftpserver.ftp.exception.SyntaxErrorException;

public abstract class FileTransferCommand extends TransferCommand<File> {

	public FileTransferCommand(Client source) {
		super(source);
	}
	
	@Override
	public void execute(String arg) throws CommandException, IOException {
		if (arg == null || arg.isEmpty()) {
			throw new SyntaxErrorException("Missing File");
		}
		super.execute(arg);
	}
	
	@Override
	protected abstract void transfer(BinaryConnection transmitter, File toTransfer) throws IOException;

	@Override
	protected File getTransferred(String arg) throws IOException {
		return source.getPath().getFile(arg);
	}
}
