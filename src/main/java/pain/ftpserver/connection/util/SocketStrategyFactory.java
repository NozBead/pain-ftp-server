package pain.ftpserver.connection.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import pain.ftpserver.connection.strategy.ActiveStrategy;
import pain.ftpserver.connection.strategy.SocketStrategy;

/**
 * Classe permettant la création de Strategy de Socket.
 * 
 * @author Enzo Pain
 */
public class SocketStrategyFactory {

	/**
	 * Créé une Strategy de Socket en mode actif grâce à la réponse d'une commande FTP PORT.
	 * 
	 * @param dataPortStr L'argument d'une commande PORT FTP
	 * @return La Strategy de socket en mode actif liée
	 * @throws UnknownHostException Si l'IP envoyée n'est pas correcte
	 * @throws IOException En cas d'erreur réseau
	 */
	public static SocketStrategy createActive(String dataPortStr) throws UnknownHostException {
		String[] parts = partition(dataPortStr);
		if (parts.length != 6) {
			throw new UnknownHostException();
		}
		
		InetAddress addr = InetAddress.getByName(getHost(parts));
		int port = getPort(parts);
		return new ActiveStrategy(addr, port);
	}

	/**
	 * Sépare chaque élément d'une chaîne hôte port de la forme b1,b2,b3,b4,p1,p2 .
	 * 
	 * @param hostPort La chaîne hôte port
	 * @return Un tableau des éléments
	 */
	private static String[] partition(String hostPort) {
		return hostPort.split(",");
	}

	/**
	 * Reconstitu le port grâce aux deux dernière parties du hôte port.
	 * 
	 * @param parts Les éléments de la chaîne hôte port
	 * @return Le port
	 */
	private static int getPort(String[] parts) {
		int first = Integer.parseInt(parts[4]);
		int last = Integer.parseInt(parts[5]);

		return first << 8 | last;
	}

	/**
	 * Reconstitu l'hôte grâce aux quatres premiers éléments du hôte port.
	 * 
	 * @param parts Les éléments de la chaîne hôte port
	 * @return L'hôte
	 */
	private static String getHost(String[] parts) {
		StringBuilder builder = new StringBuilder();
		builder.append(parts[0]).append('.').append(parts[1]).append('.').append(parts[2]).append('.').append(parts[3]);
		return builder.toString();
	}
}
