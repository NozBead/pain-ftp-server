package pain.ftpserver.ftp.command.service.transfer;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class StringActiveTest extends PassiveTest<String> {

	@Override
	protected String getData() {
		return "Données de test";
	}

	@Override
	protected void writeInFile(String str) throws FileNotFoundException, IOException {
		PrintWriter out = new PrintWriter(new FileWriter(rndFile));
		out.println(str);
		out.flush();
		out.close();
	}

	@Override
	protected String readFromFile() throws IOException {
		BufferedReader out = new BufferedReader( new FileReader(new File(newClient.getPath().getCurrentDir().toFile(), "teststor")));
		String s = out.readLine();
		out.close();
		return s;
	}

	@Override
	protected String recieve(Socket sock) throws IOException {
		BufferedReader out = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		String s = out.readLine();
		out.close();
		return s;
	}

	@Override
	protected void send(String data, Socket sock) throws IOException {
		PrintWriter out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
		out.println(data);
		out.flush();
		out.close();
	}

	@Override
	protected void checkData(String data, String recieved) {
		assertEquals(data, recieved);
	}
}
