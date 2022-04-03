package pain.ftpserver.connection.strategy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Représente une Strategy de création de Socket en mode Actif. Dans ce mode, on se connecte à un serveur.
 * 
 * @author Enzo Pain
 */
public class ActiveStrategy implements SocketStrategy {
	private InetAddress addr;
	private int port;

	public ActiveStrategy(InetAddress addr, int port) {
		this.addr = addr;
		this.port = port;
	}

	@Override
	public Socket getSocket() throws IOException {
		return new Socket(addr, port);
	}

	public InetAddress getAddr() {
		return addr;
	}

	public int getPort() {
		return port;
	}

	@Override
	public void close() throws IOException {
		
	}
}
