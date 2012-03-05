package fr.ubourgogne.simplex.loader.git;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.eclipse.jgit.api.CloneCommand;

public class GitLoader {
	/**
	 * Demande le téléchargement de en locale du code source d'un sépot GIT
	 * distant.
	 * <p>
	 * Le code est alors intégralement téléchargé en local. Cette méthode
	 * renvoie l'url en locale de l'endoit ou le code se situe désormais.
	 * 
	 * @param url
	 *            : URL du dépot GIT distant.
	 * @return L'url en locale de l'endroit ou le dépot distant à été chargé.
	 * @throws IOException
	 */
	public static String loadExternalCode(String url, String id){

		String date = new Date().getTime() + "";

		CloneCommand commande = new CloneCommand();
		commande.setURI(url);

		File directory = new File(System.getProperty("java.io.tmpdir")
				+ File.separatorChar + "simplex_temp");
		if (directory.mkdirs()) {
			System.out.println("CREATION DU CHEMIN "
					+ directory.getAbsolutePath());
		}

		File listeRepositories = new File(directory, "repositories.txt");
		try {
			if (listeRepositories.createNewFile()) {
				System.out.println("CREATION DU FICHIER "
						+ listeRepositories.getAbsolutePath());
			}
		} catch (IOException e2) {
			e2.printStackTrace();
			return null;
		}
		
		
		try {
		FileWriter writer = new FileWriter(listeRepositories, true);
		writer.write(id + ":" + date + ":" + url + ":"
				+ System.getProperty("line.separator"));
		writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		
		directory = new File(directory, id);

		commande.setDirectory(directory);

		/*
		 * C'est là qu'on en est...
		 */
		try {
			System.out.println("loading code from " + url + " ...");
			commande.call();
			// Problème principale ici :
			// Lorsqu'on initialise (ou clone) un dépot git, un verrou est posé
			// sur le dossier .git, or ce dossier pèse grave mechameant lourds,
			// et on veux pas le garder. On le supprime facilement après coup,
			// mais en attendant. Il nous gene.	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return directory.getAbsolutePath();
	}
}
