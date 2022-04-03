package pain.ftpserver.ftp.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe permettant la création de configuration d'utilisateurs.
 * @author Enzo Pain
 */
public class UserConfigurationFactory {
	/**
	 * Créée une configuration d'utilisateurs selon un fichier.
	 * @param file Le fichier decrivant la configuration
	 * @return La configuration
	 * @throws IOException En cas d'erreur pendant la lecture du fichier
	 * @throws ConfigurationException En cas d'erreur dans le format du fichier
	 */
	public static UserConfiguration createServerConfig(File file) throws IOException, ConfigurationException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		UserConfiguration config = new UserConfiguration();
		
		String line = reader.readLine();
		while(line != null) {
			String[] parts = line.split("( |\t)+");

			File root = new File(parts[2]);
			if (!root.isDirectory()) {
				reader.close();
				throw new ConfigurationException(root + " not a directory");
			}
			config.addUser(parts[0], parts[1], root);
			line = reader.readLine();
		}
		
		reader.close();
		return config;
	}
}
