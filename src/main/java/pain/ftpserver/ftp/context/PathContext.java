package pain.ftpserver.ftp.context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserPrincipal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

import pain.ftpserver.ftp.exception.RootJailException;

/**
 * Représente le contexte du système de fichier d'un utilisateur FTP.
 * @author Enzo Pain
 */
public class PathContext {
	private Path currentDir;
	private Path root;
	
	private File renamingCandidate;
	
	public PathContext(Path currentDir, Path root) {
		this.currentDir = currentDir;
		this.root = root;
	}
	
	public Path getCurrentDir() {
		return currentDir;
	}

	public Path getRoot() {
		return root;
	}

	public File getRenamingCandidate() {
		return renamingCandidate;
	}

	/**
	 * Change le répertoire courant.
	 * @param dir Le nom du répertoire vers lequel changer
	 * @throws NotDirectoryException Si le nom donné ne représente pas un répertoire
	 * @throws RootJailException Si le nom donné est en dessous de la racine
	 * @throws FileNotFoundException Si le nom donné représente est incorrect 
	 */
	public void changeWorkingDir(String dir) throws NotDirectoryException, RootJailException, FileNotFoundException {
		File f = getFile(dir);
		if (!f.exists()) {
			throw new FileNotFoundException();
		}
		Path fPath = f.toPath();
		if (root.relativize(fPath).startsWith("..")) {
			throw new RootJailException(dir);
		}
		
		if (!f.isDirectory()) {
			throw new NotDirectoryException(dir);
		}
		currentDir = fPath.normalize();
	}
	
	/**
	 * Ajoute un répertoire dans le répertoire courant.
	 * @param name Le nom du répertoire à créer
	 * @return true si le répertoire à bien été créé, false sinon
	 */
	public boolean addDir(String name) {
		File dir = getFile(name);
		return dir.mkdirs();
	}
	
	/**
	 * Indique si un fichier candidat à renommé a été spécifié.
	 * @return true si un candidat a été spécifié, false sinon.
	 */
	public boolean hasRenamingCandidate() {
		return renamingCandidate != null;
	}
	
	/**
	 * Spécifie le fichier à renommer
	 * @param name Le nom du fichier
	 * @throws FileNotFoundException Si le fichier n'existe pas
	 */
	public void setRenamingCandidate(String name) throws FileNotFoundException {
		File candidate = getFile(name);
		if (candidate.exists()) {
			renamingCandidate = candidate;
		}
		else {
			throw new FileNotFoundException();
		}
	}
	
	/**
	 * Renomme le fichier candidat.
	 * @param newName Le nouveau nom du fichier
	 * @return true si le renommage s'est effectué, false sinon
	 */
	public boolean renameCandidate(String newName) {
		if (renamingCandidate == null) {
			return false;
		}
		
		File f = getFile(newName);
		return renamingCandidate.renameTo(f);
	}
	
	/**
	 * Supprime un fichier du répertoire courant.
	 * @param name Le nom du fichier à supprimer
	 * @return true si le fichier a été supprimé, false sinon
	 * @throws FileNotFoundException Si le fichier n'existe pas
	 */
	public boolean delete(String name) throws FileNotFoundException {
		File file = new File(currentDir.toFile(), name);
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		return file.delete();
	}
	
	/**
	 * Indique le répertoire courant.
	 * @return Le répertoire
	 */
	public Path getWorkingDir() {
		return root.relativize(currentDir);
	}
	
	/**
	 * Récupère le fichier représenté par le nom donné depuis la racine ou le répertoire courant
	 * @param pathname Le nom du fichier
	 * @return Le fichier récupèré
	 */
	public File getFile(String pathname) {
		if (pathname == null || pathname.isEmpty()) {
			return currentDir.toFile();
		}
		else {
			Path resolver = currentDir;
			if (pathname.charAt(0) == File.separatorChar) {
				resolver = root;
				pathname = pathname.substring(1);
			}
			
			return resolver.resolve(pathname).toFile();
		}
	}
	
	/**
	 * Renvoie la liste des fichiers dans le répertoire courant au format de la commande Unix ls.
	 * @param arg Le répertoire à lister
	 * @return Le listing
	 * @throws IOException En cas d'erreur de lecture du répertoire
	 */
	public String getList(String arg) throws IOException {
		File[] files = getFile(arg).listFiles();
		StringBuilder builder = new StringBuilder();
		for (File f : files) {
			builder.append(getInfo(f)).append('\n');
		}
		return builder.toString();
	}
	
	/**
	 * Indique les infos d'un fichier au format de la commande Unix ls
	 * @param f Le fichier
	 * @return Les infos du fichier
	 * @throws IOException En cas d'erreur de lecture du fichier
	 */
	public String getInfo(File f) throws IOException {
		String str = null;
		if (f.exists()) {
			Path fPath = f.toPath();
			
			BasicFileAttributes attr = null;
			String perms = "rwxrwxrwx";
			UserPrincipal owner = null;
			UserPrincipal group = null;
			try {
				attr = Files.readAttributes(fPath, PosixFileAttributes.class);
				PosixFileAttributes posixAttr = (PosixFileAttributes) attr;
				perms = PosixFilePermissions.toString(posixAttr.permissions());
				owner = posixAttr.owner();
				group = posixAttr.group();
			}
			catch(UnsupportedOperationException e) {
				FileOwnerAttributeView ownerView = Files.getFileAttributeView(fPath, FileOwnerAttributeView.class);
				attr = Files.readAttributes(fPath, BasicFileAttributes.class);
				owner = ownerView.getOwner();
				group = ownerView.getOwner();
			}
			
			boolean isDir = attr.isDirectory();
			Instant last = attr.lastModifiedTime().toInstant();
			long size = attr.size();
			
			StringBuilder builder = new StringBuilder();
			Formatter formatter = new Formatter(builder);
			formatter.format("%c%s 1 %s %s %d",
					isDir ? 'd' : '-',
					perms, owner.getName(), group.getName(), size);
			formatter.close();
			DateTimeFormatter.ofPattern(" L d Y ").withZone(ZoneId.systemDefault()).formatTo(last, builder);
			builder.append(f.getName());
			str = builder.toString();
		}
		return str;
	}
	
	/**
	 * Indique les infos d'un fichier au format de la commande Unix ls
	 * @param name Le nom du fichier
	 * @return Les infos du fichier
	 * @throws IOException En cas d'erreur de lecture du fichier
	 */
	public String getInfo(String name) throws IOException {
		File f = getFile(name);
		return getInfo(f);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('"').append(File.separator).append(getWorkingDir());
		builder.append("\" est le répertoire courant.");
		return builder.toString();
	}
}
