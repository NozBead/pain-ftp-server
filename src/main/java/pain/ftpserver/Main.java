package pain.ftpserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import pain.ftpserver.connection.AsciiConnection;
import pain.ftpserver.connection.strategy.WrappedStrategy;
import pain.ftpserver.ftp.Client;
import pain.ftpserver.ftp.config.ConfigurationException;
import pain.ftpserver.ftp.config.UserConfiguration;
import pain.ftpserver.ftp.config.UserConfigurationFactory;

public class Main {
	public static void main(String[] args) throws IOException, ConfigurationException {
		if (args.length != 2) {
			printUsage();
			System.exit(1);
		}
		
		int port = Integer.parseInt(args[0]);
		File configFile = new File(args[1]);
		
		UserConfiguration config = UserConfigurationFactory.createServerConfig(configFile);
		UserConfiguration.setInstance(config);
		ServerSocket server = new ServerSocket(port);
		
		while (true) {
			Socket client = server.accept();
			WrappedStrategy wrapper = new WrappedStrategy(client);
			AsciiConnection connectionClient = new AsciiConnection(wrapper);
			connectionClient.connect();
			connectionClient.send("220 Hello there.");
			Client newClient = new Client(connectionClient);
			newClient.start();
		}
	}
	
	private static void printUsage() {
		System.err.println("Usage: java -jar ftpserver-1.0.jar PORT USER_CONFIG");
	}
}
