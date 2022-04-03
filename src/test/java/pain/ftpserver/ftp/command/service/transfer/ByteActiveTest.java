package pain.ftpserver.ftp.command.service.transfer;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ByteActiveTest extends PassiveTest<byte[]>{
	
	@Override
	protected byte[] getData() {
		byte[] data = new byte[] {0x1, 0xE, 0x1, 0xF, 0xA, 0x1, 0xE, 0x1, 0xF, 0xA};
		return data;
	}

	@Override
	protected void writeInFile(byte[] data) throws FileNotFoundException, IOException {
		FileOutputStream out = new FileOutputStream(rndFile);
		out.write(data);
		out.flush();
		out.close();
	}

	@Override
	protected byte[] readFromFile() throws IOException {
		byte[] data = new byte[10];
		FileInputStream out = new FileInputStream(new File(newClient.getPath().getCurrentDir().toFile(), "teststor"));
		out.read(data);
		out.close();
		return data;
	}

	@Override
	protected byte[] recieve(Socket sock) throws IOException {
		byte[] data = new byte[10];
		InputStream out = sock.getInputStream();
		out.read(data);
		out.close();
		return data;
	}

	@Override
	protected void send(byte[] data, Socket sock) throws IOException {
		OutputStream out = sock.getOutputStream();
		out.write(data);
		out.flush();
		out.close();
	}

	@Override
	protected void checkData(byte[] data, byte[] recieved) {
		assertArrayEquals(data, recieved);
	}

}
