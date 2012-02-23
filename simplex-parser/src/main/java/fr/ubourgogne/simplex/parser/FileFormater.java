package fr.ubourgogne.simplex.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileFormater {
	public static final String firstPasseFileName = "formated/commentsErased.code";
	public static final String secondPasseFileName = "formated/formated.code";

	private File baseFile;

	public FileFormater(File f) {
		baseFile = f;
	}

	public String format() {
		File formated = formatSpaces(removeComments(baseFile));
		BufferedReader read;
		String ret = null;
		try {
			read = new BufferedReader(new FileReader(formated));
			ret = read.readLine();
			read.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * Enlève tous les commentaires et les annotations d'un code source
	 * 
	 * @param fichier
	 *            le fichier a parser
	 * 
	 */
	private File removeComments(File fichier) {
		File f = null;
		boolean inString = false;
		try {
			f = new File(firstPasseFileName);
			if (!f.exists())
				f.createNewFile();
			FileReader reader = new FileReader(fichier);
			FileWriter writer = new FileWriter(f);

			int lu;
			while ((lu = reader.read()) != -1) {
				char c = (char) lu;

				if (c == '/' && !inString) {
					char c2 = (char) reader.read();
					if (c2 == '/') {
						System.out.print("commentaire \"//\" trouvé : ");
						char c3;
						while ((c3 = (char) reader.read()) != '\n') {
							System.out.print(c3);
						}
						System.out.println("\n");
					} else if (c2 == '*') {
						System.out.print("commentaire \"/*\" trouvé : ");
						char c4;
						char c3 = 'a';
						while (c3 != '/') {
							while ((c4 = (char) reader.read()) != '*') {
								System.out.print(c4);
							}
							c3 = (char) reader.read();
						}
						System.out.println("\n");
					} else {
						writer.write(c);
						writer.write(c2);
					}
				} else if (c == '@' && !inString) {
					char c2;
					// boolean espace = false;

					System.out.print("annotation trouvée : ");
					// tant qu'on rencontre pas d'espace ou de parenthese, on
					// vire
					while (!isSpace(c2 = (char) reader.read()) && c2 != '(') {
						System.out.print(c2);
					}
					System.out.println("\n");

					// si on a recontré un espace, on continue de virer tant
					// qu'on a pas rencontré autre chose
					if (isSpace(c2))
						while (isSpace(c2 = (char) reader.read())) {
						}

					// des qu'on a rencontré autre chose, on regarde si c'est
					// une parenthese
					if (c2 == '(') {
						boolean inEmbedString = false;
						int parenthesisCounter = 1;
						// si c'est une parenthese, faut virer tant qu'on a pas
						// trouvé
						// la parenthèse fermante
						char c3;
						while (parenthesisCounter != 0) {
							c3 = (char) reader.read();
							if (c3 == '"')
								inEmbedString = !inEmbedString;
							if (c3 == '(' && !inEmbedString)
								parenthesisCounter++;
							if (c3 == ')' && !inEmbedString)
								parenthesisCounter--;
						}
					} else {
						// c'est pas une parenthese, c'est la fin de
						// l'annotation
						writer.write(c2);
					}
				} else {
					if (c == '\"')
						inString = !inString;
					writer.write(c);
				}
			}

			reader.close();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return f;
	}

	/**
	 * Réduit tous les espaces entre chaque entité d'un code source à un seul
	 * 
	 * @param fichier
	 *            le fichier a parser
	 */
	private File formatSpaces(File fichier) {
		File f = null;
		boolean inString = false;
		boolean lastCharWasSpace = false;

		try {
			f = new File(secondPasseFileName);
			if (!f.exists())
				f.createNewFile();
			FileReader reader = new FileReader(fichier);
			FileWriter writer = new FileWriter(f);

			int lu;
			while ((lu = reader.read()) != -1) {
				char c = (char) lu;
				if (inString) {
					if (c == '\"')
						inString = false;
					writer.write(c);
					lastCharWasSpace = false;
				} else {
					if (c == '\"') {
						inString = true;
						writer.write(c);
						lastCharWasSpace = false;
					} else if (isSpace(c)) {
						if (!lastCharWasSpace)
							writer.write(" ");
						lastCharWasSpace = true;
					} else if (isKeyChar(c)) {
						if (!lastCharWasSpace)
							writer.write(" ");
						writer.write(c + " ");
						lastCharWasSpace = true;
					} else {
						writer.write(c);
						lastCharWasSpace = false;
					}
				}
			}

			reader.close();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return f;
	}

	private static boolean isSpace(char c) {
		return c == ' ' || c == '\t' || c == '\r' || c == '\n';
	}

	private static boolean isKeyChar(char c) {
		return c == '(' || c == ')' || c == '{' || c == '}' || c == '['
				|| c == ']' || c == ',' || c == ';' || c == '<' || c == '>';
	}
}
