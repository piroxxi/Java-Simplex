package fr.ubourgogne.simplex.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileFormater {
	public static final String directoryName = "formated";
	public static final String firstPasseFileName = ".commentsErased.java";
	public static final String secondPasseFileName = ".formated.java";

	private static Logger logger = LoggerFactory.getLogger(FileFormater.class);

	private File baseFile;
	private HashMap<String, String> javadoc;

	public FileFormater() {
		javadoc = new HashMap<String, String>();
		File parsed = new File(directoryName);
		parsed.mkdirs();
		logger.debug("dossier \"formated\" cree avec succes : "
				+ parsed.getAbsolutePath());
	}

	public void setFile(File f) {
		baseFile = f;
	}

	public String format() {
		logger.info("fichier : " + baseFile.getAbsolutePath());
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
		logger.info("premiere passe en cours, suppression des commentaires");
		File f = null;
		boolean inString = false;
		boolean javadocFound = false;
		boolean lastCharWasAntiSlash = false;
		String javadocContent = "";
		String javadocBlocDef = "";
		try {
			f = new File(directoryName, baseFile.getName().substring(0,
					baseFile.getName().lastIndexOf("."))
					+ firstPasseFileName);
			f.createNewFile();

			FileReader reader = new FileReader(fichier);
			FileWriter writer = new FileWriter(f);

			int lu;
			while ((lu = reader.read()) != -1) {
				char c = (char) lu;

				if (c == '/' && !inString) {
					char c2 = (char) reader.read();
					if (c2 == '/') {
						String com = "";
						char c3;
						while ((c3 = (char) reader.read()) != '\n') {
							com += c3;
						}

						logger.debug("commentaire \"//\" trouvé : " + com.substring(0,com.length()-1));
					} else if (c2 == '*') {
						char c3 = (char) reader.read();
						String com="";
						if (c3 == '*') {
							com = "javadoc trouvée : ";
							javadocFound = true;
							javadocContent = "";
							javadocBlocDef = "";
						} else {
							com = "commentaire \"/*\" trouvé : ";
						}
						char c4 = 'a';
						while (c4 != '/') {
							while ((c3 = (char) reader.read()) != '*') {
								com += c3;
								if (javadocFound && c3 != '\t')
									javadocContent += c3;
							}
							c4 = (char) reader.read();
						}
						logger.debug(com.substring(0,com.indexOf("\n")-1));
					} else {
						writer.write(c);
						writer.write(c2);
					}
				} else if (c == '@' && !inString) {
					char c2;
					// boolean espace = false;

					// tant qu'on rencontre pas d'espace ou de parenthese, on
					// vire
					// sauf si c'est une ***** de ***** de @interface
					while (isSpace(c2 = (char) reader.read())) {
					}

					String nomAnno = "@" + c2;
					while (!isSpace(c2 = (char) reader.read()) && c2 != '(') {
						nomAnno += c2;
					}

					if (nomAnno.contains("@interface")) {
						writer.write("@interface ");
					} else {
						logger.debug("annotation trouvée : " + nomAnno);
						// si on a recontré un espace, on continue de virer tant
						// qu'on a pas rencontré autre chose
						if (isSpace(c2))
							while (isSpace(c2 = (char) reader.read())) {
							}

						// des qu'on a rencontré autre chose, on regarde si
						// c'est
						// une parenthese
						if (c2 == '(') {
							boolean inEmbedString = false;
							int parenthesisCounter = 1;
							// si c'est une parenthese, faut virer tant qu'on a
							// pas
							// trouvé
							// la parenthèse fermante
							char c3;
							while (parenthesisCounter != 0) {
								c3 = (char) reader.read();
								if (c3 == '\"')
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
							if (javadocFound && c2 != '\n' && c2 != '\r'
									&& c2 != '\t') {
								javadocBlocDef += c2;
							}
						}
					}
				} else {
					if (c == '\\') {
						if (lastCharWasAntiSlash)
							lastCharWasAntiSlash = false;
						else
							lastCharWasAntiSlash = true;
						writer.write(c);
					} else if (c == '\'' && !inString) {
						writer.write(c);
						char c2 = (char) reader.read();
						writer.write(c2);
						writer.write((char) reader.read());
						if (c2 == '\\')
							writer.write((char) reader.read());
					} else {
						if (c == '\"' && !lastCharWasAntiSlash)
							inString = !inString;

						if (javadocFound) {
							boolean firstCharWritten = false;
							if (c != '{' && c != ';') {
								if (firstCharWritten) {
									javadocBlocDef += c;
								} else {
									if (c != '\n' && c != '\r' && c != '\t') {
										javadocBlocDef += c;
										firstCharWritten = true;
									}
								}
							} else {
								javadocFound = false;
								getJavadoc().put(javadocBlocDef.substring(0),
										javadocContent.substring(0));
							}
						}

						lastCharWasAntiSlash = false;
						writer.write(c);
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

	/**
	 * Réduit tous les espaces entre chaque entité d'un code source à un seul
	 * 
	 * @param fichier
	 *            le fichier a parser
	 */
	private File formatSpaces(File fichier) {
		logger.info("deuxieme passe en cours, formatage des espaces");
		File f = null;
		boolean inString = false;
		boolean lastCharWasSpace = false;
		boolean lastCharWasAntiSlash = false;

		try {
			f = new File(directoryName, baseFile.getName().substring(0,
					baseFile.getName().lastIndexOf("."))
					+ secondPasseFileName);
			f.createNewFile();

			FileReader reader = new FileReader(fichier);
			FileWriter writer = new FileWriter(f);

			int lu;
			while ((lu = reader.read()) != -1) {
				char c = (char) lu;
				if (inString) {
					if (c == '\\') {
						if (lastCharWasAntiSlash)
							lastCharWasAntiSlash = false;
						else
							lastCharWasAntiSlash = true;
					} else if (c == '\"' && !lastCharWasAntiSlash) {
						inString = false;
					} else {
						lastCharWasAntiSlash = false;
					}
					writer.write(c);
					lastCharWasSpace = false;
				} else if (c == '\'') {
					writer.write(c);
					char c2 = (char) reader.read();
					writer.write(c2);
					writer.write((char) reader.read());
					if (c2 == '\\')
						writer.write((char) reader.read());
				} else {
					if (c == '\"' && !lastCharWasAntiSlash) {
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
					} else if (c == '\\') {
						if (lastCharWasAntiSlash)
							lastCharWasAntiSlash = false;
						else
							lastCharWasAntiSlash = true;
						writer.write(c);
						lastCharWasSpace = false;
					} else {
						writer.write(c);
						lastCharWasSpace = false;
						lastCharWasAntiSlash = false;
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

	public HashMap<String, String> getJavadoc() {
		return javadoc;
	}
}
