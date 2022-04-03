package pain.ftpserver.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import pain.ftpserver.connection.strategy.SocketStrategy;

/**
 * Permet la connexion et l'échange de fichier et de String en mode Ascii.
 * La connexion peut se faire en parallèle.
 * @author Enzo Pain
 */
public class AsciiConnection extends BinaryConnection {
	private static final int BUFFER_SIZE = 1024;
	char[] buffer = new char[BUFFER_SIZE];
	
	private BufferedWriter outString;
	private BufferedReader inString;

	public AsciiConnection(SocketStrategy strat) {
		super(strat);
	}
	
	public void connect() throws IOException {
		super.connect();
		inString = new BufferedReader(new InputStreamReader(in));
		outString = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
	}

	protected void transmit(Writer to, Reader from) throws IOException {
		int read = from.read(buffer);
		
		while (read != -1) {
			to.write(buffer, 0 , read);
			read = from.read(buffer);
		}
		
		to.flush();
	}
	
	public void recieve(Writer stream) throws IOException {
		transmit(stream, inString);
	}

	public void send(Reader stream) throws IOException {
		transmit(outString, stream);
	}

	@Override
	public void recieveFile(File f) throws IOException {
		BufferedWriter file = new BufferedWriter(new FileWriter(f));
		recieve(file);
	}

	@Override
	public String recieveString() throws IOException {
		return inString.readLine();
	}

	@Override
	public void send(File toSend) throws IOException {
		BufferedReader file = new BufferedReader(new FileReader(toSend));
		send(file);
	}

	@Override
	public synchronized void send(String toSend) throws IOException {
		outString.write(toSend + "\r\n");
		outString.flush();
	}
}
