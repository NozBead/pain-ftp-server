package pain.ftpserver.ftp.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Représente la configuration des utilisateurs du serveur FTP.
 * @author Enzo Pain
 */
public class UserConfiguration {
	private static UserConfiguration instance;
	
	Map<String, String> passwords;
	Map<String, File> roots;
	
	public UserConfiguration() {
		passwords = new HashMap<>();
		roots = new HashMap<>();
	}
	
	public static UserConfiguration getInstance() {
		if (instance == null) {
			instance = new UserConfiguration();
		}
		return instance;
	}
	
	public static void setInstance(UserConfiguration config) {
		instance = config;
	}
	
	/**
	 * Vérifie le couple utilisateur mot de passe donné.
	 * @param username Le nom de l'utilisateur
	 * @param password Le mot de passe
	 * @return true si le couple est correct, false sinon
	 */
	public boolean authenticate(String username, String password) {
		String passwd = passwords.get(username);
		return passwd != null && password.equals(passwd);
	}
	
	/**
	 * Donne la racine du système de fichier de l'utilisateur.
	 * @param username Le nom de l'utilisateur
	 * @return La racine
	 */
	public File getRoot(String username) {
		return roots.get(username);
	}
	
	/**
	 * Ajoute un utilisateur dans la configuration.
	 * @param username Le nom de l'utilisateur
	 * @param password Le mot de passe de l'utilisateur
	 * @param root La racine de l'utilisateur
	 */
	public void addUser(String username, String password, File root) {
		passwords.put(username, password);
		roots.put(username, root);
	}

	
}
