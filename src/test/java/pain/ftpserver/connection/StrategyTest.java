package pain.ftpserver.connection;

import static org.junit.Assert.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

import pain.ftpserver.connection.strategy.ActiveStrategy;
import pain.ftpserver.connection.util.SocketStrategyFactory;

public class StrategyTest {
	@Test
	public void factoryTest() throws UnknownHostException {
		String s = "127,0,0,1,54,23";
		ActiveStrategy strat = (ActiveStrategy) SocketStrategyFactory.createActive(s);
		assertEquals(InetAddress.getByName("127.0.0.1"), strat.getAddr());
		assertEquals(54 << 8 | 23, strat.getPort());
	}
}
