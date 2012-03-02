package fr.ubourgogne.simplex.parser;

import java.util.ArrayList;

import fr.ubourgogne.simplex.model.java.JavaEntity;
import fr.ubourgogne.simplex.model.java.entity.JavaMethod;
import fr.ubourgogne.simplex.model.java.entity.JavaParam;
import fr.ubourgogne.simplex.model.java.entity.JavaVariable;
import fr.ubourgogne.simplex.model.java.object.JavaAnnotation;
import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.model.java.object.JavaEnum;
import fr.ubourgogne.simplex.model.java.object.JavaInterface;

public abstract class BlocParser {

	public static JavaEntity decodeBloc(String entete, String contenu,
			int prefixe) {
		for (int i = 0; i < prefixe; i++)
			System.out.print("\t");
		System.out.print("bloc[" + entete + "] --> ");

		// on a soit une classe, soit un enum, soit une interface, soit une
		// methode
		if (entete.contains("class")) {
			System.out.println("classe");
			return BlocParser.decodeClasse(entete, contenu, prefixe);
		} else if (entete.contains("@interface")) {
			System.out.println("annotation");
			return BlocParser.decodeAnnotation(entete, contenu, prefixe);
		} else if (entete.contains("enum")) {
			System.out.println("enum");
			return BlocParser.decodeEnum(entete, contenu, prefixe);
		} else if (entete.contains("interface")) {
			System.out.println("interface");
			return BlocParser.decodeInterface(entete, contenu, prefixe);
		} else {
			System.out.println("methode");
			return BlocParser.decodeMethode(entete, contenu, prefixe);
		}
	}

	private static JavaClass decodeClasse(String entete, String contenu,
			int prefixe) {
		// only public, protected, private, static, abstract & final are
		// permitted
		JavaClass jc = new JavaClass();

		ArrayList<String> implementedInterfaces = null;
		ArrayList<String> implementedInterfacesParams = null;

		boolean classKeyWordFound = false;
		while (!classKeyWordFound) {
			String token = entete.substring(0, entete.indexOf(" "));
			entete = entete.substring(entete.indexOf(" ") + 1);
			String modif = "";

			if (token.equals("public")) {
				modif += "public ";
			} else if (token.equals("abstract")) {
				modif += "abstract ";
			} else if (token.equals("final")) {
				modif += "final ";
			} else if (token.equals("class")) {
				classKeyWordFound = true;
				jc.setModifiers(modif.substring(0,
						Math.max(modif.length() - 1, 0)));
			}
		}

		// donc maintenant, on a le nom de la classe
		String name = entete.substring(0, entete.indexOf(" "));
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
			JavaParam parametre = InlineParser.decodeParam(token);
			jc.getParams().add(parametre);
		}

		// eventuellement un extends
		if (entete.startsWith("extends")) {
			String superClasse = entete.substring(8, entete.indexOf(" ", 8));
			entete = entete.substring(entete.indexOf(" ", 8) + 1);
			if (entete.startsWith("<")) {
				JavaParam jp = InlineParser.decodeParam(entete.substring(2,
						entete.lastIndexOf(" >")));
				// TODO
				// superclasse.setParam(jp);
			}
			// TODO
			// jc.setSuperClass(superClasse);
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

			implementedInterfaces.add(inter);
			implementedInterfacesParams.add(param);
		}

		while (contenu.indexOf("{") != -1 && contenu.indexOf(";") != -1) {
			// soit on a un bloc (methode, classe imbriquee...), soit un field

			// cas du field
			if (contenu.indexOf(";") < contenu.indexOf("{")) {
				String defField = contenu.substring(0, contenu.indexOf(";"));
				contenu = contenu.substring(contenu.indexOf(";") + 2);

				JavaVariable tmp = InlineParser.decodeField(defField,
						prefixe + 1);
				jc.getContent().add(tmp);
			} else {
				String defBloc = contenu.substring(0, contenu.indexOf("{"));
				contenu = contenu.substring(contenu.indexOf("{") + 2);

				int compteurAccolade = 1;
				int i = 0;
				String contenuBloc = "";
				boolean inString = false;
				boolean lastCharWasAntiSlash = false;
				while (compteurAccolade > 0) {
					char actuel = contenu.charAt(i);

					if (inString) {
						if (actuel == '\\') {
							lastCharWasAntiSlash = true;
						} else if (actuel == '\"' && !lastCharWasAntiSlash) {
							inString = false;
						} else {
							lastCharWasAntiSlash = false;
						}
					} else {
						if (actuel == '\"')
							inString = true;

						if (actuel == '{') {
							compteurAccolade++;
						}

						if (actuel == '}') {
							compteurAccolade--;
						}
					}

					if (compteurAccolade > 0)
						contenuBloc += actuel;
					else
						i++;

					i++;
				}

				contenu = contenu.substring(i);

				JavaEntity tmp = decodeBloc(defBloc, contenuBloc, prefixe + 1);
				jc.getContent().add(tmp);
			}
		}
		return jc;
	}

	private static JavaEnum decodeEnum(String entete, String contenu,
			int prefixe) {
		// only public, protected, private & static are permitted
		return new JavaEnum();
	}

	private static JavaInterface decodeInterface(String entete, String contenu,
			int prefixe) {
		// only public, protected, private, static & abstract are permitted
		return new JavaInterface();
	}

	private static JavaMethod decodeMethode(String entete, String contenu,
			int prefixe) {
		// only public, protected, private, static, final, abstract,
		// synchronized & native are permitted
		JavaMethod jm = new JavaMethod();

		String name = null;
		String returnType = null;
		JavaParam parametre = null;

		// la on a pas vraiment de keyword a trouver, mais on s'arrete quand
		// on
		// trouve un mot qui n'est pas un mot clef en fait...
		boolean classKeyWordFound = false;
		while (!classKeyWordFound) {
			String token = entete.substring(0, entete.indexOf(" "));
			String modif = "";

			if (token.equals("public")) {
				modif += "public ";
			} else if (token.equals("protected")) {
				modif += "protected ";
			} else if (token.equals("private")) {
				modif += "private ";
			} else if (token.equals("static")) {
				modif += "static ";
			} else if (token.equals("final")) {
				modif += "final ";
			} else if (token.equals("abstract")) {
				modif += "abstract ";
			} else if (token.equals("synchronized")) {
				modif += "synchronized ";
			} else if (token.equals("native")) {
				modif += "native ";
			} else {
				classKeyWordFound = true;
				jm.setModifiers(modif.substring(0,
						Math.max(modif.length() - 1, 0)));
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
		entete = entete.substring(entete.indexOf(" ") + 1);
		if (entete.startsWith("<")) {
			JavaParam jp = InlineParser.decodeParam(entete.substring(2,
					entete.lastIndexOf(" >")));
			// TODO
			// superclasse.setParam(jp);
		}
		// TODO
		// jm.setReturnType(returnType);

		// donc maintenant, on a le nom de la methode
		name = entete.substring(0, entete.indexOf(" "));
		if (name.equals("(")) {
			// on a un constructeur, donc le nom est le token d'avant
			name = returnType;
		} else {
			entete = entete.substring(entete.indexOf(" ") + 1);
		}
		jm.setName(name);

		// et enfin, on a les parametres
		entete = entete.substring(entete.indexOf("(") + 2,
				entete.lastIndexOf(")"));

		// while (contenu.indexOf("{") != -1 && contenu.indexOf(";") != -1) {
		// // faut prevoir tous les cas... pas de la tarte
		//
		// if (contenu.indexOf(";") < contenu.indexOf("{")) {
		// String defField = contenu.substring(0, contenu.indexOf(";"));
		// InlineParser.decodeLocalVar(defField, prefixe + 1);
		// jm.getLines().add(defField);
		// contenu = contenu.substring(contenu.indexOf(";") + 2);
		//
		// } else {
		// String defBloc = contenu.substring(0, contenu.indexOf("{"));
		// contenu = contenu.substring(contenu.indexOf("{") + 2);
		//
		// int compteurAccolade = 1;
		// int i = 0;
		// String contenuBloc = "";
		// while (compteurAccolade > 0) {
		// char actuel = contenu.charAt(i);
		// if (actuel == '{') {
		// compteurAccolade++;
		// }
		// if (actuel == '}') {
		// compteurAccolade--;
		// }
		//
		// if (compteurAccolade > 0)
		// contenuBloc += actuel;
		// else
		// i++;
		//
		// i++;
		// }
		//
		// contenu = contenu.substring(i);
		//
		// }
		// }

		return jm;
	}

	private static JavaAnnotation decodeAnnotation(String entete,
			String contenu, int prefixe) {
		System.out.println("je suis passé la");
		return new JavaAnnotation();
	}
}
