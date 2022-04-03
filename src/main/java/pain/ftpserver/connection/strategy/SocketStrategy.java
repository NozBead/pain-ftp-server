package pain.ftpserver.connection.strategy;

import java.io.IOException;
import java.net.Socket;

/**
 * Représente une Strategy spécifiant comment créer une Socket utilisée dans des connexions.
 * 
 * @author Enzo Pain
 */
public interface SocketStrategy {
	/**
	 * Retourne une Socket pour les échanges entre client et serveur.
	 * 
	 * @return La Socket pour les échanges
	 * @throws IOException En cas d'erreur réseau lors de la création
	 */
	public Socket getSocket() throws IOException;
	public void close() throws IOException;
}
