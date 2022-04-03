package pain.ftpserver.connection.strategy;

import java.io.IOException;
import java.net.Socket;

/**
 * Représente une Strategy de Socket où elle simplement passée à la classe l'utilisant.
 * @author Enzo Pain
 */
public class WrappedStrategy implements SocketStrategy {
	private Socket socket;

	public WrappedStrategy(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public Socket getSocket() throws IOException {
		return socket;
	}

	@Override
	public void close() throws IOException {
		
	}
}
