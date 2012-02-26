package fr.ubourgogne.simplex.loader;

import java.io.File;
import java.io.IOException;

import fr.ubourgogne.simplex.loader.git.GitLoader;

public class GitSourceLoadingTest {

	public static void main(String[] args) throws IOException {
		FileUtils.clearLocalTemporaryDir(5 * FileUtils.MINUTE + 15
				* FileUtils.SECONDE);

		final String url = "https://code.google.com/p/java-simplex";
		// String url = "https://java-simplex.googlecode.com/";

		String localURL = GitLoader.loadExternalCode(url);
		if (localURL == null || localURL.isEmpty()) {
			System.out
					.println("Echec lors de l'import du dépot distant '"
							+ url
							+ "'. Veuillez verifier que ce dépôt existe et qu'il est valide.");
		}

		System.out.println("Le code du dépôt distant " + url
				+ " a été téléchargé dans le dossier " + localURL);

		File localRepo = new File(localURL);
		FileUtils.printDirectoryTree(localRepo);

		/*
		 * Do your stuff.
		 */

		FileUtils.deleteRecursively(localRepo);
	}

}
