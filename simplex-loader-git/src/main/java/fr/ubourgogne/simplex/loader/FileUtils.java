package fr.ubourgogne.simplex.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtils {
	public static final long SECONDE = 1000;
	public static final long MINUTE = 60 * SECONDE;

	public static void clearLocalTemporaryDir(long dateLimite) {
		File directory = new File(System.getProperty("java.io.tmpdir")
				+ File.separatorChar + "simplex_temp");
		if (directory.listFiles() == null) {
			System.out
					.println("Le répertoire de stockage temporaire est déja propre.");
			return;
		}

		File listeRepositories = new File(directory, "repositories.txt");
		if (!listeRepositories.exists()) {
			return;
		}
		List<String> file = new ArrayList<String>();
		try {
			BufferedReader fichier = new BufferedReader(new FileReader(
					listeRepositories));
			String ligne;
			while ((ligne = fichier.readLine()) != null) {
				String[] champs = ligne.split(":", -1);
				Date dateRecup;
				try {
					dateRecup = new Date(Long.parseLong(champs[1]));
				} catch (NumberFormatException e) {
					e.printStackTrace();
					dateRecup = new Date(0); // si il est mal enregistré, c'est
												// un mauvais dossier...
				}
				if (dateRecup
						.before(new Date(new Date().getTime() - dateLimite))) {
					File toRemove = new File(directory, champs[0]);
					if (toRemove.exists()) {
						System.out.println("Le dépot " + champs[0]
								+ " a été récupéré il y a plus de "
								+ toDate(dateLimite));
						deleteRecursively(toRemove);
					} else {
						System.out.println("Le dépot " + champs[0]
								+ " a été récupéré il y a moins de "
								+ toDate(dateLimite) + " on le conserve donc.");
					}
				} else {
					file.add(ligne);
				}
			}
			fichier.close();

			PrintWriter writter = new PrintWriter(new FileWriter(
					listeRepositories));
			for (String l : file) {
				writter.println(l);
			}
			writter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		/*
		 * Suppression des projets pas dans le fichier. (devrait pas arriver,
		 * mais on sait jamais).
		 */
		// TODO(raphael);
	}

	private static String toDate(long dateLimite) {
		String minutes = (dateLimite / MINUTE < 0) ? "" : (dateLimite / MINUTE
				+ " minute" + ((dateLimite / MINUTE > 1) ? "s " : " "));
		dateLimite = dateLimite % MINUTE;
		if (dateLimite / SECONDE <= 0) {
			return minutes;
		}
		String secondes = (dateLimite / SECONDE < 0) ? ""
				: (dateLimite / SECONDE + " seconde" + ((dateLimite / SECONDE > 1) ? "s"
						: ""));
		return minutes + secondes;
	}

	public static void printDirectoryTree(File directory) {
		print(directory, "", true);
	}

	private static void print(File localRepo, String string, boolean lastNode) {
		System.out.println(string + "+ " + localRepo.getName());
		File[] f = localRepo.listFiles();
		if (f != null) {
			for (int i = 0; i < f.length; i++) {
				if (i < f.length - 1) {
					if (lastNode) {
						print(f[i], string + "   ", false);
					} else {
						print(f[i], string + "|  ", false);
					}
				} else {
					if (lastNode) {
						print(f[i], string + "   ", true);
					} else {
						print(f[i], string + "|  ", false);
					}
				}
			}
		}

	}

	public static void deleteRecursively(File file) {
		File[] aire = file.listFiles();
		if (aire != null) {
			for (File dent : aire) {// TODO(raphael) note the joke (sinon je
									// l'ai faite pour rien)
				deleteRecursively(dent);
			}
		}
		file.delete();
		file.deleteOnExit();
	}

	public static List<File> getJavaFiles(File file) {
		List<File> ret = new ArrayList<File>();
		if (file.getName().endsWith(".java")) {
			ret.add(file);
		}

		File[] fils = file.listFiles();
		if (fils != null) {
			for (File f : fils) {
				ret.addAll(getJavaFiles(f));
			}
		}
		return ret;
	}
}
