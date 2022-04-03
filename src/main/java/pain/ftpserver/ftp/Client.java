package pain.ftpserver.ftp;

import java.io.IOException;

import pain.ftpserver.connection.AsciiConnection;
import pain.ftpserver.connection.BinaryConnection;
import pain.ftpserver.connection.DataType;
import pain.ftpserver.ftp.command.Command;
import pain.ftpserver.ftp.command.CommandExecutor;
import pain.ftpserver.ftp.command.CommandFactory;
import pain.ftpserver.ftp.context.ClientState;
import pain.ftpserver.ftp.context.PathContext;
import pain.ftpserver.ftp.exception.CommandException;
import pain.ftpserver.ftp.exception.InternalErrorException;
import pain.ftpserver.ftp.exception.NotAuthenticatedException;
import pain.ftpserver.ftp.exception.SyntaxErrorException;
import pain.ftpserver.ftp.exception.UnknownCommandException;

public class Client extends Thread {
	private boolean listen;
	private CommandExecutor exec;
	
	private AsciiConnection controlConnection;
	
	private BinaryConnection transferConnection;
	private Thread currentTransfer;
	private DataType type;
	
	private PathContext path;
	
	private String username;
	private ClientState clientState;

	public Client(AsciiConnection connection) throws IOException {
		super();
		this.listen = true;
		this.exec = new CommandExecutor();
		this.controlConnection = connection;
		this.clientState = ClientState.ANONYMOUS;
		this.type = DataType.ASCII;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public Thread getCurrentTransfer() {
		return currentTransfer;
	}
	
	public void setCurrentTransfer(Thread transferThread) {
		currentTransfer = transferThread;
	}

	public String getUsername() {
		return username;
	}
	
	public DataType getType() {
		return type;
	}

	public void setType(DataType type) {
		this.type = type;
	}
	
	public BinaryConnection getTransferConnection() {
		return transferConnection;
	}

	public void setTransferConnection(BinaryConnection transferConnection) {
		this.transferConnection = transferConnection;
	}

	public ClientState getClientState() {
		return clientState;
	}

	public synchronized void setClientState(ClientState clientState) {
		this.clientState = clientState;
	}

	public PathContext getPath() {
		return path;
	}
	
	public void setPath(PathContext path) {
		this.path = path;
	}

	public synchronized AsciiConnection getControl() {
		return controlConnection;
	}
	
	public String[] parseCommand(String str) {
		String[] parts = new String[2];
		int firstSpace = str.indexOf(' ');
		
		if (firstSpace == -1) {
			parts[0] = str;
		}
		else {
			parts[0] = str.substring(0, firstSpace);
			parts[1] = str.substring(firstSpace + 1);
		}
		
		return parts;
	}
	
	public void stopListen() {
		listen = false;
	}
	
	public void listen() throws IOException { 
		try {
			String cmdStr = controlConnection.recieveString();
			if (cmdStr == null) {
				listen = false;
			}
			else {
				String[] parts = parseCommand(cmdStr);
				Command cmd = CommandFactory.createCommand(parts[0], this);
				exec.setCmd(cmd);
				exec.executeCommand(parts[1]);
			}
		} catch (UnknownCommandException e) {
			e.printStackTrace();
			controlConnection.send("502 Command not implemented.");
		} catch (SyntaxErrorException e) {
			e.printStackTrace();
			controlConnection.send("501 Syntax Error.");
		} catch (NotAuthenticatedException e) {
			controlConnection.send("530 Please log in with PASS and USER first.");
		} catch (InternalErrorException e) {
			e.printStackTrace();
			controlConnection.send("550 Internal Error.");
		} catch (CommandException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (listen) {
				listen();
			}
			controlConnection.close();
		} catch (IOException e) {
			e.printStackTrace();
			stopListen();
		}
	}
}
