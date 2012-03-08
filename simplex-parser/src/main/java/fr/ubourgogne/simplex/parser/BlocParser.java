package fr.ubourgogne.simplex.parser;

import java.util.ArrayList;

import com.google.inject.Inject;

import fr.ubourgogne.simplex.model.java.JavaEntity;
import fr.ubourgogne.simplex.model.java.JavaProject;
import fr.ubourgogne.simplex.model.java.entity.JavaMethod;
import fr.ubourgogne.simplex.model.java.entity.JavaParam;
import fr.ubourgogne.simplex.model.java.entity.JavaSimpleType;
import fr.ubourgogne.simplex.model.java.entity.JavaVariable;
import fr.ubourgogne.simplex.model.java.meta.JavaReferenceClass;
import fr.ubourgogne.simplex.model.java.meta.JavaReferenceObject;
import fr.ubourgogne.simplex.model.java.object.JavaAnnotation;
import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.model.java.object.JavaEnum;
import fr.ubourgogne.simplex.model.java.object.JavaInterface;
import fr.ubourgogne.simplex.storage.EntityFactory;

public abstract class BlocParser {

	@Inject
	private static EntityFactory entityFactory;

	public static JavaEntity decodeBloc(JavaProject project, String entete,
			String contenu, int prefixe) {
		for (int i = 0; i < prefixe; i++)
			System.out.print("\t");
		System.out.print("bloc[" + entete + "] --> ");

		// on a soit une classe, soit un enum, soit une interface, soit une
		// methode
		if (entete.contains(" class ") || entete.startsWith("class")) {
			System.out.println("classe");
			return BlocParser.decodeClasse(project, entete, contenu, prefixe);
		} else if (entete.contains(" @interface ")
				|| entete.startsWith("@interface")) {
			System.out.println("annotation");
			return BlocParser.decodeAnnotation(entete, contenu, prefixe);
		} else if (entete.contains(" enum ") || entete.startsWith("enum")) {
			System.out.println("enum");
			return BlocParser.decodeEnum(project, entete, contenu, prefixe);
		} else if (entete.contains(" interface ")
				|| entete.startsWith("interface")) {
			System.out.println("interface");
			return BlocParser
					.decodeInterface(project, entete, contenu, prefixe);
		} else {
			System.out.println("methode");
			return BlocParser.decodeMethode(project, entete, contenu, prefixe);
		}
	}

	private static JavaClass decodeClasse(JavaProject project, String entete,
			String contenu, int prefixe) {
		// only public, protected, private, static, abstract & final are
		// permitted

		JavaClass jc;

		String modif = "";
		boolean classKeyWordFound = false;
		while (!classKeyWordFound) {
			String token = entete.substring(0, entete.indexOf(" "));
			entete = entete.substring(entete.indexOf(" ") + 1);

			if (token.equals("public")) {
				modif += "public ";
			} else if (token.equals("abstract")) {
				modif += "abstract ";
			} else if (token.equals("final")) {
				modif += "final ";
			} else if (token.equals("class")) {
				classKeyWordFound = true;
			}
		}

		// donc maintenant, on a le nom de la classe
		String name = entete.substring(0, entete.indexOf(" "));
		entete = entete.substring(entete.indexOf(" ") + 1);
		jc = entityFactory.getJavaClass(project, name);
		jc.setModifiers(modif);

		// la classe peut etre parametrisee

		ArrayList<String> params = new ArrayList<String>();
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

				if (compteurInf == 1 && actuel == ',') {
					params.add(token.substring(0));
					token = "";
					i++;
				}

				if (compteurInf > 0)
					token += actuel;
				else
					i++;
				i++;
			}

			entete = entete.substring(i);
			params.add(token);
			for (String s : params) {
				JavaParam parametre = InlineParser.decodeParam(project, s);
				jc.getParams().add(parametre);
			}
		}

		// eventuellement un extends
		if (entete.startsWith("extends")) {
			String superClasseName = entete
					.substring(8, entete.indexOf(" ", 8));

			JavaReferenceClass superClasse = new JavaReferenceClass(
					entityFactory.getJavaClass(project, superClasseName));
			;

			entete = entete.substring(entete.indexOf(" ", 8) + 1);
			if (entete.startsWith("<")) {
				JavaParam jp = InlineParser.decodeParam(project,
						entete.substring(2, entete.lastIndexOf(" >")));

				superClasse.getParams().add(jp);
			}
			jc.setSuperClass(superClasse);
		}

		ArrayList<String> implementedInterfaces = null;
		ArrayList<JavaParam> implementedInterfacesParams = null;
		// eventuellement un implements
		if (entete.startsWith("implements")) {
			implementedInterfaces = new ArrayList<String>();
			// implementedInterfaces = new ArrayList<JavaInterface>();
			implementedInterfacesParams = new ArrayList<JavaParam>();
			entete = entete.substring(11);

			while (entete.indexOf(",") != -1) {
				String interDef = entete.substring(0, entete.indexOf(" ,"));
				String paramDef = null;
				if (interDef.contains("<")) {
					paramDef = interDef.substring(interDef.indexOf("<") + 2,
							interDef.indexOf(" >"));
					implementedInterfacesParams.add(InlineParser.decodeParam(
							project, paramDef));
					interDef = interDef.substring(0, interDef.indexOf(" <"));
				}

				implementedInterfaces.add(interDef);
				// implementedInterfaces.add(storage.getByName(JavaInterface.class,interDef);
				entete = entete.substring(entete.indexOf(",") + 2);
			}

			String interDef = entete.substring(0, entete.lastIndexOf(" "));
			String paramDef = null;
			if (interDef.contains("<")) {
				paramDef = interDef.substring(interDef.indexOf("<") + 2,
						interDef.indexOf(" >"));
				implementedInterfacesParams.add(InlineParser.decodeParam(
						project, paramDef));
				interDef = interDef.substring(0, interDef.indexOf(" <"));
			}

			implementedInterfaces.add(interDef);
			// implementedInterfaces.add(storage.getByName(JavaInterface.class,interDef);
			entete = entete.substring(entete.indexOf(",") + 2);
		}

		while (contenu.indexOf("{") != -1 && contenu.indexOf(";") != -1) {
			// soit on a un bloc (methode, classe imbriquee...), soit un
			// field

			// cas du field
			if (contenu.indexOf(";") < contenu.indexOf("{")) {
				String defField = contenu.substring(0, contenu.indexOf(";"));
				contenu = contenu.substring(contenu.indexOf(";") + 2);

				JavaVariable tmp = InlineParser.decodeField(project, defField,
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
				// System.out.println(compteurAccolade);
				while (compteurAccolade > 0) {
					char actuel = contenu.charAt(i);

					if (inString) {
						if (actuel == '\\') {
							if (lastCharWasAntiSlash)
								lastCharWasAntiSlash = false;
							else
								lastCharWasAntiSlash = true;
						} else if (actuel == '\"' && !lastCharWasAntiSlash) {
							inString = false;
							lastCharWasAntiSlash = false;
						} else {
							lastCharWasAntiSlash = false;
						}
					} else if (actuel == '\'') {
						contenuBloc += actuel;
						char c2 = contenu.charAt(i + 1);
						contenuBloc += c2;
						actuel += contenu.charAt(i + 2);
						i = i + 2;
						if (c2 == '\\') {
							contenu += actuel;
							actuel = contenu.charAt(i + 1);
							i++;
						}
					} else {
						if (actuel == '\"' && !lastCharWasAntiSlash)
							inString = true;

						if (actuel == '{') {
							compteurAccolade++;
						}

						if (actuel == '}') {
							compteurAccolade--;
						}

						if (actuel == '\\') {
							if (lastCharWasAntiSlash)
								lastCharWasAntiSlash = false;
							else
								lastCharWasAntiSlash = true;
						} else {
							lastCharWasAntiSlash = false;
						}
					}

					if (compteurAccolade > 0) {
						contenuBloc += actuel;
					} else {
						i++;
					}

					i++;
				}

				boolean blocSpecial = false;
				if (i < contenu.length() && contenu.charAt(i) == ';') {
					blocSpecial = true;
					i = i + 2;
				}

				contenu = contenu.substring(i);

				// a priori, ca peut etre par exemple une declaration de
				// tableau
				// a la volee
				// on verra plus tard
				if (!blocSpecial) {
					JavaEntity tmp = decodeBloc(project, defBloc, contenuBloc,
							prefixe + 1);
					jc.getContent().add(tmp);
				}
			}
		}
		return jc;
	}

	private static JavaEnum decodeEnum(JavaProject project, String entete,
			String contenu, int prefixe) {
		// only public, protected, private & static are permitted
		return new JavaEnum();
	}

	private static JavaInterface decodeInterface(JavaProject project,
			String entete, String contenu, int prefixe) {
		// only public, protected, private, static & abstract are permitted
		return new JavaInterface();
	}

	private static JavaMethod decodeMethode(JavaProject project, String entete,
			String contenu, int prefixe) {
		// only public, protected, private, static, final, abstract,
		// synchronized & native are permitted
		JavaMethod jm = new JavaMethod();
		// JavaMethod jm;

		String name = null;
		String returnTypeName = null;
		JavaParam parametre = null;
		String modif = "";
		// la on a pas vraiment de keyword a trouver, mais on s'arrete quand
		// on
		// trouve un mot qui n'est pas un mot clef en fait...
		boolean classKeyWordFound = false;
		while (!classKeyWordFound) {
			String token = entete.substring(0, entete.indexOf(" "));

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
			parametre = InlineParser.decodeParam(project, token);
			jm.getParams().add(parametre);
		}

		// maintenant on a le type de retour
		returnTypeName = entete.substring(0, entete.indexOf(" "));
		JavaParam returnTypeParam = null;
		entete = entete.substring(entete.indexOf(" ") + 1);
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
			returnTypeParam = InlineParser.decodeParam(project, token);
		}

		// donc maintenant, on a le nom de la methode
		name = entete.substring(0, entete.indexOf(" "));
		if (name.equals("(")) {
			// on a un constructeur, donc le nom est le token d'avant
			name = returnTypeName;
		} else {
			entete = entete.substring(entete.indexOf(" ") + 1);

			JavaReferenceObject returnType = JavaSimpleType.getByName(returnTypeName);
			if (returnType == null)
				returnType = new JavaReferenceObject(
						entityFactory.getJavaClass(project, returnTypeName));
			
			if (returnTypeParam != null)
				returnType.getParams().add(returnTypeParam);
			jm.setReturnType(returnType);
		}
		jm.setName(name);

		// et enfin, on a les parametres
		if (!entete.equals("( ) ")) {
			System.out.println("[entete]" + entete);

			entete = entete.substring(entete.indexOf("(") + 2,
					entete.lastIndexOf(" )"));

			while (entete.indexOf(",") != -1) {
				String paramDef = entete.substring(0, entete.indexOf(" ,"));
				entete = entete.substring(entete.indexOf(",") + 2);

				String paramTypeName = paramDef.substring(0,
						paramDef.indexOf(" "));
				paramDef = paramDef.substring(paramDef.indexOf(" ") + 1);

				JavaParam paramParam = null;
				if (paramDef.startsWith("<")) {
					paramParam = InlineParser.decodeParam(project,
							paramDef.substring(2, paramDef.lastIndexOf(" >")));
					paramDef = paramDef
							.substring(paramDef.lastIndexOf(">") + 2);
				}

				String paramName = paramDef.substring(0);

				JavaReferenceObject paramType = new JavaReferenceObject(
						entityFactory.getJavaClass(project, paramTypeName));

				if (paramParam != null)
					paramType.getParams().add(paramParam);

				jm.getVarParams().add(new JavaVariable(paramName, paramType));
			}

			String paramDef = entete.substring(0);

			String paramTypeName = paramDef.substring(0, paramDef.indexOf(" "));
			paramDef = paramDef.substring(paramDef.indexOf(" ") + 1);

			JavaParam paramParam = null;
			if (paramDef.startsWith("<")) {
				paramParam = InlineParser.decodeParam(project,
						paramDef.substring(2, paramDef.lastIndexOf(" >")));
				paramDef = paramDef.substring(paramDef.lastIndexOf(">") + 2);
			}

			String paramName = paramDef.substring(0);

			JavaReferenceObject paramType = new JavaReferenceObject(
					entityFactory.getJavaClass(project, paramTypeName));

			if (paramParam != null)
				paramType.getParams().add(paramParam);

			jm.getVarParams().add(new JavaVariable(paramName, paramType));
		}

		// TODO aussi, mais plus complique...
		// faudrait au moins appeler jm.getLines.add(),
		while (contenu.indexOf(";") != -1) {

			jm.getLines().add(contenu.substring(0, contenu.indexOf(";")));
			contenu = contenu.substring(contenu.indexOf(";") + 1);
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
		}

		return jm;
	}

	private static JavaAnnotation decodeAnnotation(String entete,
			String contenu, int prefixe) {
		return new JavaAnnotation();
	}
}
