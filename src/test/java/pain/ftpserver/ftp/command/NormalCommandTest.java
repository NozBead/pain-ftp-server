package pain.ftpserver.ftp.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import pain.ftpserver.ftp.EndToEndTest;
import pain.ftpserver.ftp.context.ClientState;

public class NormalCommandTest extends EndToEndTest {
	@Test
	public void badAuthTest() throws IOException {
		control.commandCreator = () -> "USER baduser";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("331"));
		control.commandCreator = () -> "PASS badpass";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("530"));
		assertEquals(ClientState.ANONYMOUS, newClient.getClientState());
	}
	
	@Test
	public void badMkdirTest() throws IOException {
		goodAuthTest();
		control.commandCreator = () -> "MKD dirafterroot";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("550"));
	}
	
	@Test
	public void mkdirTest() throws IOException {
		goodAuthTest();
		control.commandCreator = () -> "MKD newdir";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("257"));
		File newdir = new File(root, "newdir");
		assertTrue(newdir.exists());
		assertTrue(newdir.isDirectory());
	}
	
	@Test
	public void badCwdTest() throws IOException {
		goodAuthTest();
		control.commandCreator = () -> "CWD dirnonexistant";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("550"));
		assertEquals(root.toPath(), newClient.getPath().getCurrentDir());
	}
	
	@Test
	public void cwdTest() throws IOException {
		goodAuthTest();
		control.commandCreator = () -> "CWD dirafterroot";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("250"));
		assertEquals(dirAfterRoot.toPath(), newClient.getPath().getCurrentDir());
	}
	
	@Test
	public void cwdRootJailTest() throws IOException {
		goodAuthTest();
		control.commandCreator = () -> "CWD ..";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("550"));
		assertEquals(root.toPath(), newClient.getPath().getCurrentDir());
		control.commandCreator = () -> "CDUP";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("550"));
		assertEquals(root.toPath(), newClient.getPath().getCurrentDir());
	}
	
	@Test
	public void deleTest() throws IOException {
		goodAuthTest();
		control.commandCreator = () -> "DELE rndFile";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("250"));
		assertFalse(rndFile.exists());
	}
	
	@Test
	public void badDeleTest() throws IOException {
		goodAuthTest();
		control.commandCreator = () -> "DELE notafile";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("550"));
	}
	
	@Test
	public void pwdTest() throws IOException {
		Pattern pattern = Pattern.compile("\"[^\"]*\"");
		
		goodAuthTest();
		control.commandCreator = () -> "PWD";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("257"));
		Matcher matcher = pattern.matcher(control.lastRecieved);
		assertTrue(matcher.find());
		assertEquals('"' + File.separator + '"', matcher.group());
		
		cwdTest();
		control.commandCreator = () -> "PWD";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("257"));
		matcher = pattern.matcher(control.lastRecieved);
		assertTrue(matcher.find());
		assertEquals('"' + File.separator + "dirafterroot\"", matcher.group());
	}
	
	@Test
	public void renameTest() throws IOException {
		goodAuthTest();
		control.commandCreator = () -> "RNFR rndFile";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("350"));
		control.commandCreator = () -> "RNTO rndrenamed";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("250"));
		assertTrue(new File(newClient.getPath().getCurrentDir().toFile(), "rndrenamed").exists());
		assertFalse(rndFile.exists());
	}
	
	@Test
	public void badRenamefromTest() throws IOException {
		goodAuthTest();
		control.commandCreator = () -> "RNFR notexists";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("550"));
	}
	
	@Test
	public void badRenametoOrderTest() throws IOException {
		goodAuthTest();
		control.commandCreator = () -> "RNTO newName";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("503"));
	}
	
	@Test
	public void badRenametoNameTest() throws IOException {
		goodAuthTest();
		control.commandCreator = () -> "RNFR dirafterroot";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("350"));
		control.commandCreator = () -> "RNTO rndFile";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("553"));
	}
	
	@Test
	public void notAuthTest() throws IOException {
		control.commandCreator = () -> "RNFR dirafterroot";
		newClient.listen();
		assertTrue(control.lastRecieved.startsWith("530"));
	}
}
