package pain.ftpserver.ftp.command;

import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.command.access.CdupCommand;
import pain.ftpserver.ftp.command.access.CwdCommand;
import pain.ftpserver.ftp.command.access.PassCommand;
import pain.ftpserver.ftp.command.access.QuitCommand;
import pain.ftpserver.ftp.command.access.UserCommand;
import pain.ftpserver.ftp.command.params.FeatCommand;
import pain.ftpserver.ftp.command.params.PasvCommand;
import pain.ftpserver.ftp.command.params.PortCommand;
import pain.ftpserver.ftp.command.params.TypeCommand;
import pain.ftpserver.ftp.command.service.DeleCommand;
import pain.ftpserver.ftp.command.service.MkdCommand;
import pain.ftpserver.ftp.command.service.PwdCommand;
import pain.ftpserver.ftp.command.service.RmdCommand;
import pain.ftpserver.ftp.command.service.RnfrCommand;
import pain.ftpserver.ftp.command.service.RntoCommand;
import pain.ftpserver.ftp.command.service.transfer.ListCommand;
import pain.ftpserver.ftp.command.service.transfer.RetrCommand;
import pain.ftpserver.ftp.command.service.transfer.StorCommand;
import pain.ftpserver.ftp.exception.UnknownCommandException;

public class CommandFactory {
	public static Command createCommand(String name, Client source) throws UnknownCommandException {
		Command cmd = null;
		switch (name.toUpperCase()) {
		case "USER":
			cmd = new UserCommand(source);
		break;
		
		case "PASS":
			cmd = new PassCommand(source);
		break;
		
		case "PWD":
			cmd = new PwdCommand(source);
		break;
		
		case "CWD":
			cmd = new CwdCommand(source);
		break;
		
		case "MKD":
			cmd = new MkdCommand(source);
		break;
		
		case "RMD":
			cmd = new RmdCommand(source);
		break;
		
		case "DELE":
			cmd = new DeleCommand(source);
		break;
		
		case "RNTO":
			cmd = new RntoCommand(source);
		break;
		
		case "RNFR":
			cmd = new RnfrCommand(source);
		break;
		
		case "CDUP":
			cmd = new CdupCommand(source);
		break;
		
		case "QUIT":
			cmd = new QuitCommand(source);
		break;
		
		case "TYPE":
			cmd = new TypeCommand(source);
		break;
		
		case "PASV":
			cmd = new PasvCommand(source);
		break;
		
		case "PORT":
			cmd = new PortCommand(source);
		break;
		
		case "RETR":
			cmd = new RetrCommand(source);
		break;
		
		case "STOR":
			cmd = new StorCommand(source);
		break;
		
		case "LIST":
			cmd = new ListCommand(source);
		break;
		
		case "FEAT":
			cmd = new FeatCommand(source);
		break;
		
		default:
			throw new UnknownCommandException(name);
		}
		
		return cmd;
	}
}
