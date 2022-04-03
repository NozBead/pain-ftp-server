package pain.ftpserver.connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import pain.ftpserver.connection.strategy.SocketStrategy;

/**
 * Permet la connexion et l'échange de fichier et de String en mode binaire.
 * La connexion peut se faire en parallèle.
 * 
 * @author Enzo Pain
 */
public class BinaryConnection extends Thread implements Closeable {
	private static final int BUFFER_SIZE = 1024;
	byte[] buffer = new byte[BUFFER_SIZE];
	
	protected Socket socket;
	protected SocketStrategy strat;
	
	protected BufferedInputStream in;
	protected BufferedOutputStream out;
	
	public BinaryConnection(SocketStrategy strat) {
		this.strat = strat;
	}

	/**
	 * Effectue la connexion en parallèle.
	 */
	public void run() {
		try {
			connect();
		} catch (IOException e) {
			interrupt();
		}
	}
	
	/**
	 * Setup la connexion.
	 * @throws IOException En cas d'erreur réseau
	 */
	public void connect() throws IOException {
		socket = strat.getSocket();
		in = new BufferedInputStream(socket.getInputStream());
		out = new BufferedOutputStream(socket.getOutputStream());
	}
	
	/**
	 * Permet de transférer des données d'un Stream à l'autre.
	 * @param to Stream des données à envoyer
	 * @param from Stream où écrire les données
	 * @throws IOException En cas d'erreur réseau
	 */
	protected void transmit(OutputStream to, InputStream from) throws IOException {
		int read = from.read(buffer);
		
		while (read != -1) {
			
			to.write(buffer, 0, read);
			read = from.read(buffer);
		}
		
		to.flush();
	}

	/**
	 * Transfère les données reçues dans la Socket vers le Stream en paramètre. 
	 * @param stream Le Stream qui va recevoir les données
	 * @throws IOException En cas d'erreur réseau
	 */
	public void recieve(OutputStream stream) throws IOException {
		transmit(stream, in);
	}

	/**
	 * Transfère les données du Stream en paramètre vers la Socket. 
	 * @param stream Le Stream des données à envoyer
	 * @throws IOException En cas d'erreur réseau
	 */
	public void send(InputStream stream) throws IOException {
		transmit(out, stream);
	}
	
	/**
	 * Réceptionne un fichier depuis la Socket.
	 * @return Le fichier reçu
	 * @throws IOException En cas d'erreur réseau
	 */
	public void recieveFile(File f) throws IOException {
		BufferedOutputStream file = new BufferedOutputStream(new FileOutputStream(f));
		recieve(file);
		file.close();
	}

	/**
	 * Réceptionne un String depuis la Socket.
	 * @return Le String reçu
	 * @throws IOException En cas d'erreur réseau
	 */
	public String recieveString() throws IOException {
		return null;
	}

	/**
	 * Envoie un fichier.
	 * @param toSend le fichier à envoyer
	 * @throws IOException En cas d'erreur réseau
	 */
	public void send(File toSend) throws IOException {
		BufferedInputStream file = new BufferedInputStream(new FileInputStream(toSend));
		send(file);
	}

	/**
	 * Envoie un String.
	 * @param toSend le String à envoyer
	 * @throws IOException En cas d'erreur réseau
	 */
	public synchronized void send(String toSend) throws IOException {
		send(new ByteArrayInputStream(toSend.getBytes()));
	}

	/**
	 * Ferme la connexion.
	 */
	@Override
	public void close() throws IOException {
		if (socket != null) {
			socket.close();
		}
		strat.close();
	}
}
