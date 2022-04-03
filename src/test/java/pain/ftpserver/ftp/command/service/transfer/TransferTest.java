package pain.ftpserver.ftp.command.service.transfer;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

import org.junit.Test;

import pain.ftpserver.ftp.EndToEndTest;

public abstract class TransferTest<T> extends EndToEndTest {
	@Test
	public void retrTest() throws IOException, InterruptedException {
		goodAuthTest();
		
		Socket socket = getSocket();
		T data = getData();
		writeInFile(data);
		
		control.commandCreator = () -> "RETR rndFile";
		newClient.listen();
		T transf = recieve(socket);
		newClient.getCurrentTransfer().join();
		assertTrue(control.lastRecieved.startsWith("226"));
		
		checkData(data, transf);
		socket.close();
	}
	
	@Test
	public void storTest() throws IOException, InterruptedException {
		goodAuthTest();
		
		Socket socket = getSocket();
		T data = getData();
		
		control.commandCreator = () -> "STOR teststor";
		newClient.listen();
		
		send(data,socket);
		newClient.getCurrentTransfer().join();
		assertTrue(control.lastRecieved.startsWith("226"));
		
		T fileData = readFromFile();
		
		checkData(data, fileData);
		socket.close();
	}
	
	protected abstract T getData();
	
	protected abstract void checkData(T data, T recieved);
	
	protected abstract void writeInFile(T data) throws FileNotFoundException, IOException;
	protected abstract T readFromFile() throws IOException;
	
	protected abstract T recieve(Socket sock) throws IOException;
	protected abstract void send(T data, Socket sock) throws IOException;
	
	protected abstract Socket getSocket() throws IOException;
}
