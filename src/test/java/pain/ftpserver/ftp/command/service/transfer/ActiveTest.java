package pain.ftpserver.ftp.command.service.transfer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import pain.ftpserver.ftp.context.ClientState;

public abstract class ActiveTest<T> extends TransferTest<T> {

	@Override
	protected Socket getSocket() throws IOException {
		ServerSocket sock = new ServerSocket(0);
		int port = sock.getLocalPort();
		control.commandCreator = () -> "PORT 127,0,0,1," + (port >> 8) + ',' + (port & 0xFF);
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("227"));
		assertEquals(ClientState.TRANSFERT, newClient.getClientState());
		return sock.accept();
	}

}
