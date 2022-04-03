package pain.ftpserver.ftp.command.params;

import java.io.IOException;

import pain.ftpserver.connection.DataType;
import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.command.AuthenticatedCommand;
import pain.ftpserver.ftp.exception.CommandException;
import pain.ftpserver.ftp.exception.SyntaxErrorException;

public class TypeCommand extends AuthenticatedCommand {

	public TypeCommand(Client source) {
		super(source);
	}

	@Override
	public void execute(String arg) throws CommandException, IOException {
		super.execute(arg);
		if (arg == null || arg.isEmpty()) {
			throw new SyntaxErrorException("Missing type");
		}
		
		DataType type = null;
		switch(arg.charAt(0)) {
			case 'I':
				type = DataType.BINARY;
			break;
			
			case 'A':
				type = DataType.ASCII;
			break;
		}
		
		source.setType(type);
		source.getControl().send("200 " + type);
	}
}