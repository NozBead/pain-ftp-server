package pain.ftpserver.connection.util;

import pain.ftpserver.connection.AsciiConnection;
import pain.ftpserver.connection.BinaryConnection;
import pain.ftpserver.connection.DataType;
import pain.ftpserver.connection.strategy.SocketStrategy;

/**
 * Classe pour la création de connexion.
 * @author Enzo Pain
 */
public class ConnectionFactory {
	/**
	 * Permet la création d'une connexion selon le type de données échangées et la Strategy de Socket.
	 * @param type Le type de données échangées
	 * @param strategy La Strategy pour obtenir la Socket de la connexion
	 * @return La connexion
	 */
	public static BinaryConnection createTransmitter(DataType type, SocketStrategy strategy) {
		BinaryConnection transmitter = null;
		
		
		switch (type) {
		case ASCII:
			transmitter = new AsciiConnection(strategy);
		break;
		
		case BINARY:
			transmitter = new BinaryConnection(strategy);
		break;
		}
		
		return transmitter;
	}
}
