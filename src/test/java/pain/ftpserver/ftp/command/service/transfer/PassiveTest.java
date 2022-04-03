package pain.ftpserver.ftp.command.service.transfer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pain.ftpserver.ftp.context.ClientState;

public abstract class PassiveTest<T> extends TransferTest<T> {

	@Override
	protected Socket getSocket() throws IOException {
		control.commandCreator = () -> "PASV";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("227"));
		assertEquals(ClientState.TRANSFERT, newClient.getClientState());
		Pattern p = Pattern.compile("[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+");
		Matcher m = p.matcher(control.lastRecieved);
		assertTrue(m.find());
		String hostport = m.group();
		String[] parts = hostport.split(",");
		int p1 = Integer.parseInt(parts[4]);
		int p2 = Integer.parseInt(parts[5]);
		String host = parts[0] + '.' + parts[1] + '.' + parts[2] + '.' + parts[3];
		return new Socket(host, p1 << 8 | p2);
	}
}
