package pain.ftpserver.ftp;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.After;
import org.junit.Before;

public abstract class FileTest {
	
	protected File root;
	protected File dirAfterRoot;
	protected File rndFile;
	
	@Before
	public void init() throws IOException {
		root = new File(System.getProperty("java.io.tmpdir") + File.separator + "ftptest");
		root.mkdir();
		rndFile = new File(root, "rndFile");
		rndFile.createNewFile();
		dirAfterRoot = new File(root, "dirafterroot");
		dirAfterRoot.mkdir();
	}
	
	@After
	public void clean() throws IOException {
		Files.walk(root.toPath()).forEach(f -> f.toFile().delete());
	}
}
