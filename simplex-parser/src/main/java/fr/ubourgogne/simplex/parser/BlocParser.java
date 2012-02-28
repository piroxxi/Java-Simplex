package fr.ubourgogne.simplex.parser;

import java.util.ArrayList;

import fr.ubourgogne.simplex.model.java.JavaEntity;
import fr.ubourgogne.simplex.model.java.entity.JavaMethod;
import fr.ubourgogne.simplex.model.java.entity.JavaParam;
import fr.ubourgogne.simplex.model.java.entity.JavaVariable;
import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.model.java.object.JavaEnum;
import fr.ubourgogne.simplex.model.java.object.JavaInterface;

public abstract class BlocParser {

	public static JavaEntity decodeBloc(String entete, String contenu) {
		System.out.println("bloc[" + entete + "]\n\t" + contenu);

		// on a soit une classe, soit un enum, soit une interface, soit une
		// methode
		if (entete.contains("class")) {
			return BlocParser.decodeClasse(entete, contenu);
		} else if (entete.contains("enum")) {
			return BlocParser.decodeEnum(entete, contenu);
		} else if (entete.contains("interface")) {
			return BlocParser.decodeInterface(entete, contenu);
		} else {
			return BlocParser.decodeMethode(entete, contenu);
		}
	}

	private static JavaClass decodeClasse(String entete, String contenu) {
		// only public, protected, private, static, abstract & final are
		// permitted
		JavaClass jc = new JavaClass();

		String name = null;
		JavaParam parametre = null;
		String superClasse = null;
		ArrayList<String> implementedInterfaces = null;
		ArrayList<String> implementedInterfacesParams = null;

		boolean classKeyWordFound = false;
		while (!classKeyWordFound) {
			String token = entete.substring(0, entete.indexOf(" "));
			entete = entete.substring(entete.indexOf(" ") + 1);
			String modif = "";

			if (token.equals("public")) {
				System.out.println("la classe est publique");
				modif += "public";
			} else if (token.equals("abstract")) {
				System.out.println("la classe est abstraite");
				modif += " abstract";
			} else if (token.equals("final")) {
				System.out.println("la classe est finale");
				modif += " final";
			} else if (token.equals("class")) {
				classKeyWordFound = true;
				jc.setModifiers(modif);
			}
		}

		// donc maintenant, on a le nom de la classe
		name = entete.substring(0, entete.indexOf(" "));
		System.out.println("son nom est : \"" + name + "\"");
		entete = entete.substring(entete.indexOf(" ") + 1);
		jc.setName(name);

		// la classe peut etre parametrisee

		if (entete.startsWith("<")) {
			int compteurInf = 1;
			int i = 2;
			String token = "";
			while (compteurInf > 0) {
				char actuel = entete.charAt(i);
				if (actuel == '<') {
					compteurInf++;
				}
				if (actuel == '>') {
					compteurInf--;
				}

				if (compteurInf > 0)
					token += actuel;
				else
					i++;
				i++;
			}

			entete = entete.substring(i);
			parametre = InlineParser.decodeParam(token);
			jc.getParams().add(parametre);
		}

		// eventuellement un extends
		if (entete.startsWith("extends")) {
			superClasse = entete.substring(8, entete.indexOf(" ", 8));
			System.out
					.println("la classe parent est : \"" + superClasse + "\"");
			entete = entete.substring(entete.indexOf(" ", 8) + 1);
			if (entete.startsWith("<")) {
				// TODO classe parent parametree
			}
			// TODO
			jc.setSuperClass(null);
		}

		// eventuellement un implements
		if (entete.startsWith("implements")) {
			implementedInterfaces = new ArrayList<String>();
			implementedInterfacesParams = new ArrayList<String>();
			entete = entete.substring(11);

			while (entete.indexOf(",") != -1) {
				String inter = entete.substring(0, entete.indexOf(" ,"));
				String param = null;
				if (inter.contains("<")) {
					param = inter.substring(inter.indexOf("<") + 2,
							inter.indexOf(" >"));
					inter = inter.substring(0, inter.indexOf(" <"));
				}
				System.out.println("Interface implementee : \""
						+ inter
						+ "\""
						+ (param != null ? " avec comme parametre \"" + param
								+ "\"" : ""));

				implementedInterfaces.add(inter);
				implementedInterfacesParams.add(param);
				entete = entete.substring(entete.indexOf(",") + 2);
			}

			String inter = entete.substring(0, entete.lastIndexOf(" "));
			String param = null;
			if (inter.contains("<")) {
				param = inter.substring(inter.indexOf("<") + 2,
						inter.indexOf(" >"));
				inter = inter.substring(0, inter.indexOf(" <"));
			}
			System.out.println("Interface implementee : \""
					+ inter
					+ "\""
					+ (param != null ? " avec comme parametre \"" + param
							+ "\"" : ""));

			implementedInterfaces.add(inter);
			implementedInterfacesParams.add(param);
		}

		while (!contenu.equals("")) {
			// soit on a un bloc (methode, classe imbriquee...), soit un field

			// cas du field
			if (contenu.indexOf(";") != -1
					&& contenu.indexOf(";") < contenu.indexOf("{")) {
				String defField = contenu.substring(0, contenu.indexOf(";"));
				contenu = contenu.substring(contenu.indexOf(";") + 2);

				JavaVariable tmp = InlineParser.decodeField(defField);
				jc.getContent().add(tmp);
			} else {
				String defBloc = contenu.substring(0, contenu.indexOf("{"));
				contenu = contenu.substring(contenu.indexOf("{") + 2);

				int compteurAccolade = 1;
				int i = 0;
				String contenuBloc = "";
				while (compteurAccolade > 0) {
					char actuel = contenu.charAt(i);
					if (actuel == '{') {
						compteurAccolade++;
					}
					if (actuel == '}') {
						compteurAccolade--;
					}

					if (compteurAccolade > 0)
						contenuBloc += actuel;
					else
						i++;

					i++;
				}

				contenu = contenu.substring(i);

				JavaEntity tmp = decodeBloc(defBloc, contenuBloc);
				jc.getContent().add(tmp);
			}
		}
		return jc;
	}

	private static JavaEnum decodeEnum(String entete, String contenu) {
		// only public, protected, private & static are permitted
		return null;
	}

	private static JavaInterface decodeInterface(String entete, String contenu) {
		// only public, protected, private, static & abstract are permitted
		return null;
	}

	private static JavaMethod decodeMethode(String entete, String contenu) {
		// only public, protected, private, static, final, abstract,
		// synchronized & native are permitted
		JavaMethod jm = new JavaMethod();

		String name = null;
		String returnType = null;
		JavaParam parametre = null;

		// la on a pas vraiment de keyword a trouver, mais on s'arrete quand on
		// trouve un mot qui n'est pas un mot clef en fait...
		boolean classKeyWordFound = false;
		while (!classKeyWordFound) {
			String token = entete.substring(0, entete.indexOf(" "));			
			String modif = "";

			if (token.equals("public")) {
				System.out.println("la méthode est public");
				modif += "public";
			} else if (token.equals("protected")) {
				System.out.println("la méthode est protected");
				modif += " protected";
			} else if (token.equals("private")) {
				System.out.println("la méthode est private");
				modif += " private";
			} else if (token.equals("static")) {
				System.out.println("la méthode est static");
				modif += " static";
			} else if (token.equals("final")) {
				System.out.println("la méthode est final");
				modif += " final";
			} else if (token.equals("abstract")) {
				System.out.println("la méthode est abstract");
				modif += " abstract";
			} else if (token.equals("synchronized")) {
				System.out.println("la méthode est synchronized");
				modif += " synchronized";
			} else if (token.equals("native")) {
				System.out.println("la méthode est native");
				modif += " native";
			} else {
				classKeyWordFound = true;
				jm.setModifiers(modif);
			}
			
			if (!classKeyWordFound)
				entete = entete.substring(entete.indexOf(" ") + 1);
		}

		// la methode peut etre parametrisee
		if (entete.startsWith("<")) {
			int compteurInf = 1;
			int i = 2;
			String token = "";
			while (compteurInf > 0) {
				char actuel = entete.charAt(i);
				if (actuel == '<') {
					compteurInf++;
				}
				if (actuel == '>') {
					compteurInf--;
				}

				if (compteurInf > 0)
					token += actuel;
				else
					i++;
				i++;
			}

			entete = entete.substring(i);
			parametre = InlineParser.decodeParam(token);
			jm.getParams().add(parametre);
		}

		// maintenant on a le type de retour
		returnType = entete.substring(0, entete.indexOf(" "));
		System.out.println("son type de retour est : \"" + returnType + "\"");
		entete = entete.substring(entete.indexOf(" ") + 1);
		if (entete.startsWith("<")) {
			// TODO returntype parametre
		}
		// TODO
		jm.setReturnType(null);

		// donc maintenant, on a le nom de la methode
		name = entete.substring(0, entete.indexOf(" "));
		if (name.equals("(")) {
			//on a un constructeur, donc le nom est le token d'avant
			name = returnType;
		} else {
			entete = entete.substring(entete.indexOf(" ") + 1);
		}
		System.out.println("son nom est : \"" + name + "\"");
		jm.setName(name);

		// et enfin, on a les parametres
		entete = entete.substring(entete.indexOf("(")+2, entete.lastIndexOf(")"));
		System.out.println("parametres : " + (entete.equals("") ? "aucun" : entete));

//		while (!contenu.equals("")) {
//			// soit on a un bloc (methode, classe imbriquee...), soit une
//			// variable locale, soit une action quelconque
//			
//			//TODO
//
//			// cas du field
//			if (contenu.indexOf(";") != -1
//					&& contenu.indexOf(";") < contenu.indexOf("{")) {
//				String defField = contenu.substring(0, contenu.indexOf(";"));
//				contenu = contenu.substring(contenu.indexOf(";") + 2);
//
//				JavaVariable tmp = InlineParser.decodeField(defField);
//				jc.getContent().add(tmp);
//			} else {
//				String defBloc = contenu.substring(0, contenu.indexOf("{"));
//				contenu = contenu.substring(contenu.indexOf("{") + 2);
//
//				int compteurAccolade = 1;
//				int i = 0;
//				String contenuBloc = "";
//				while (compteurAccolade > 0) {
//					char actuel = contenu.charAt(i);
//					if (actuel == '{') {
//						compteurAccolade++;
//					}
//					if (actuel == '}') {
//						compteurAccolade--;
//					}
//
//					if (compteurAccolade > 0)
//						contenuBloc += actuel;
//					else
//						i++;
//
//					i++;
//				}
//
//				contenu = contenu.substring(i);
//
//				JavaEntity tmp = decodeBloc(defBloc, contenuBloc);
//				jc.getContent().add(tmp);
//			}
//		}
		return jm;
	}
}
