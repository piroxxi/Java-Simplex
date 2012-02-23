package fr.ubourgogne.simplex.parser;

import fr.ubourgogne.simplex.model.java.entity.JavaParam;
import fr.ubourgogne.simplex.model.java.entity.JavaVariable;

public abstract class InlineParser {
	public static JavaVariable decodeField(String def) {
		System.out.println("field[" + def + "]");
		JavaVariable jv = new JavaVariable();

		// only public, protected, private, static, final, transient & volatile
		// are permitted

		boolean typeFound = false;
		boolean nameFound = false;
		while (!def.equals("")) {
			String token = def.substring(0, def.indexOf(" "));
			def = def.substring(def.indexOf(" ") + 1);

			if (token.equals("public")) {
				System.out.println("le field est public");
			} else if (token.equals("protected")) {
				System.out.println("le field est protected");
			} else if (token.equals("private")) {
				System.out.println("le field est private");
			} else if (token.equals("static")) {
				System.out.println("le field est static");
			} else if (token.equals("final")) {
				System.out.println("le field est final");
			} else if (token.equals("transient")) {
				System.out.println("le field est transient");
			} else if (token.equals("volatile")) {
				System.out.println("le field est volatile");
			} else if (token.equals("=")) {
				// il y aura une valeur par defaut
			} else {
				if (!typeFound) {
					System.out.println("le field est de type " + token);
					typeFound = true;
				} else if (!nameFound) {
					System.out.println("le field est nomme " + token);
					nameFound = true;
				} else {
					System.out.println("le field a pour valeur par defaut "
							+ token);
				}
			}
		}

		return jv;
	}
	
	public static JavaVariable decodeLocalVar(String def) {
		System.out.println("local var[" + def + "]");
		JavaVariable jv = new JavaVariable();

		// only final is permitted

		boolean typeFound = false;
		boolean nameFound = false;
		while (!def.equals("")) {
			String token = def.substring(0, def.indexOf(" "));
			def = def.substring(def.indexOf(" ") + 1);

			if (token.equals("final")) {
				System.out.println("le field est final");
			} else if (token.equals("=")) {
				// il y aura une valeur par defaut
			} else {
				if (!typeFound) {
					System.out.println("le field est de type " + token);
					typeFound = true;
				} else if (!nameFound) {
					System.out.println("le field est nomme " + token);
					nameFound = true;
				} else {
					System.out.println("le field a pour valeur par defaut "
							+ token);
				}
			}
		}

		return jv;
	}

	public static JavaParam decodeParam(String param) {
		String parametre, paramType;
		if (param.contains("extends")) {
			parametre = param.substring(0, param.indexOf(" extends"));
			paramType = param.substring(param.indexOf("extends") + 8);
			System.out.println("le parametre est : \"" + parametre
					+ "\" de type \"" + paramType + "\"");
		} else {
			parametre = param;
			paramType = null;
			System.out.println("le parametre est : \"" + param + "\"");
		}

		return null;
	}
}
