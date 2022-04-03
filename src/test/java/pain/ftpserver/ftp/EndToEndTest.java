package pain.ftpserver.ftp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import pain.ftpserver.connection.AsciiConnection;
import pain.ftpserver.connection.strategy.WrappedStrategy;
import pain.ftpserver.ftp.config.UserConfiguration;
import pain.ftpserver.ftp.context.ClientState;

public abstract class EndToEndTest extends FileTest {
	@FunctionalInterface
	protected interface StringCreator {
		public String getString();
	}
	
	protected class ControlConnectionMock extends AsciiConnection {
		public StringCreator commandCreator;
		public String lastRecieved;

		public ControlConnectionMock() {
			super(new WrappedStrategy(null));
		}
		
		@Override
		public void connect() throws IOException {}
		
		@Override
		public synchronized void send(String toSend) throws IOException {
			lastRecieved = toSend;
		}
		
		@Override
		public String recieveString() throws IOException {
			return commandCreator.getString();
		}
	}
	
	protected ControlConnectionMock control;
	protected Client newClient;

	@Before
	public void init() throws IOException {
		super.init();
		UserConfiguration userConfig = UserConfiguration.getInstance();
		userConfig.addUser("testuser", "testpassword", root);
		control = new ControlConnectionMock();
		newClient = new Client(control);
	}
	
	@Test
	public void goodAuthTest() throws IOException {
		control.commandCreator = () -> "USER testuser";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("331"));
		control.commandCreator = () -> "PASS testpassword";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("230"));
		assertEquals(ClientState.IDLE, newClient.getClientState());
		assertEquals(root.toPath(), newClient.getPath().getRoot());
	}
}
