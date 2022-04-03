package pain.ftpserver.ftp.command;

import java.io.IOException;

import pain.ftpserver.ftp.exception.CommandException;

/**
 * Exécute les 
 * @author Enzo Pain
 */
public class CommandExecutor {
	private Command cmd;

	public CommandExecutor(Command cmd) {
		this.cmd = cmd;
	}
	
	public CommandExecutor() {
		this(null);
	}

	public Command getCmd() {
		return cmd;
	}

	public void setCmd(Command cmd) {
		this.cmd = cmd;
	}
	
	public void executeCommand(String arg) throws CommandException, IOException {
		cmd.execute(arg);
	}
}
