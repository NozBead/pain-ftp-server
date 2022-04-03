package pain.ftpserver.ftp.command.service;

import java.io.IOException;

import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.command.AuthenticatedCommand;
import pain.ftpserver.ftp.exception.CommandException;
import pain.ftpserver.ftp.exception.SyntaxErrorException;

public class RntoCommand extends AuthenticatedCommand {

	public RntoCommand(Client source) {
		super(source);
	}

	@Override
	public void execute(String arg) throws CommandException, IOException {
		super.execute(arg);
		if (arg == null || arg.isEmpty()) {
			throw new SyntaxErrorException("Missing directory");
		}
		
		if (!source.getPath().hasRenamingCandidate()) {
			source.getControl().send("503 Use RNFR first.");
		}
		else if (source.getPath().renameCandidate(arg)) {
			source.getControl().send("250 File renamed.");
		}
		else {
			source.getControl().send("553 File name not allowed.");
		}
	}
}
