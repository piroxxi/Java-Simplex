package fr.ubourgogne.simplex.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JavaCodeParser {
	public static void main(String args[]) {
		JavaCodeParser jcp = new JavaCodeParser("sample/SampleClass.java");
		jcp.premierePasse();
		jcp.deuxiemePasse();
		jcp.troisiemePasse();
	}

	private File fichierBase;

	public JavaCodeParser(File f) {
		fichierBase = f;
	}

	public JavaCodeParser(String s) {
		fichierBase = new File(s);
	}

	/**
	 * Enlève tous les commentaires et les annotations d'un code source
	 * 
	 * @return le fichier dans lequel le résultat est stocké
	 */
	public File premierePasse() {
		File f = null;
		boolean inString = false;
		try {
			f = new File("premierePasse.code");
			if (!f.exists())
				f.createNewFile();
			FileReader reader = new FileReader(fichierBase);
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
	 * @return le fichier dans lequel est stocké le résultat
	 */
	public File deuxiemePasse() {
		File f = null;
		boolean inString = false;
		boolean lastCharWasSpace = false;
		boolean lastCharWasKeyChar = false;
		try {
			f = new File("deuxiemePasse.code");
			if (!f.exists())
				f.createNewFile();
			FileReader reader = new FileReader("premierePasse.code");
			FileWriter writer = new FileWriter(f);

			int lu;
			while ((lu = reader.read()) != -1) {
				char c = (char) lu;
				if (inString) {
					if (c == '\"')
						inString = false;
					writer.write(c);
				} else {
					if (isSpace(c)) {
						lastCharWasSpace = true;
					} else if (c == '\"') {
						inString = true;
						writer.write(c);
					} else if (isKeyChar(c)) {
						writer.write(c);
						lastCharWasSpace = false;
						lastCharWasKeyChar = true;
					} else {
						if (lastCharWasSpace && !lastCharWasKeyChar)
							writer.write(' ');
						writer.write(c);
						lastCharWasSpace = false;
						lastCharWasKeyChar = false;
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

	public void troisiemePasse() {
		String code = "";

		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"deuxiemePasse.code"));
			code = reader.readLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(code);

		// et c'est parti pour le decoupage \o/
		String token = "";
		// au debut,, on a le package et les import, mais pas forcement
		if (code.startsWith("package")) {
			token = code.substring(0, code.indexOf(";"));
			code = code.substring(code.indexOf(";") + 1);
			System.out.println("le package est : "
					+ token.substring(token.indexOf(" ") + 1));
		}

		int i = 1;
		while (code.startsWith("import")) {
			token = code.substring(0, code.indexOf(";"));
			code = code.substring(code.indexOf(";") + 1);
			System.out.println("import " + i + " : "
					+ token.substring(token.indexOf(" ") + 1));
			i++;
		}

		// ensuite on a en theorie la definition de la classe, vu qu'on a enleve
		// ces *** d'annotations

		token = code.substring(0, code.indexOf("{"));
		code = code.substring(code.indexOf("{") + 1);
//
//		for (String tok : token.split(" ")) {
//			
//		}

	}

	private static boolean isSpace(char c) {
		return c == ' ' || c == '\t' || c == '\r' || c == '\n';
	}

	private static boolean isKeyChar(char c) {
		return c == '(' || c == ')' || c == '{' || c == '}' || c == '['
				|| c == ']' || c == ',' || c == ';' || c == '<' || c == '>';
	}

}
