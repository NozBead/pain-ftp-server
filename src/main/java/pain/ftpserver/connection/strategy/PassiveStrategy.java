package pain.ftpserver.connection.strategy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Représente une Strategy de création de Socket en mode Actif. Dans ce mode,
 * on attend la connexion d'un client.
 * 
 * @author Enzo Pain
 */
public class PassiveStrategy implements SocketStrategy {
	private ServerSocket serv;
	
	public PassiveStrategy() throws IOException {
		serv = new ServerSocket(0);
		serv.setSoTimeout(20000);
	}
	
	@Override
	public Socket getSocket() throws IOException {
		return serv.accept();
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		int port = serv.getLocalPort();
		
		str.append('(');
		str.append("127,0,0,1,").append(port >> 8).append(',').append(port & 0xFF);
		
		return str.append(')').toString();
	}

	@Override
	public void close() throws IOException {
		serv.close();
	}
}
