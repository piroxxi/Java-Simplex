package fr.ubourgogne.simplex.parser;

import com.google.inject.Inject;

import fr.ubourgogne.simplex.model.java.entity.JavaParam;
import fr.ubourgogne.simplex.model.java.entity.JavaVariable;
import fr.ubourgogne.simplex.model.java.meta.JavaReferenceObject;
import fr.ubourgogne.simplex.storage.EntityFactory;

public abstract class InlineParser {

	@Inject
	private static EntityFactory entityFactory;

	public static JavaVariable decodeField(String def, int prefixe) {
		for (int i = 0; i < prefixe; i++)
			System.out.print("\t");
		System.out.println("field[" + def + "]");
		JavaVariable jv = new JavaVariable();

		// only public, protected, private, static, final, transient & volatile
		// are permitted

		boolean typeFound = false;
		boolean nameFound = false;
		String modif = "";
		while (!def.isEmpty()) {
			String token = def.substring(0, def.indexOf(" "));
			def = def.substring(def.indexOf(" ") + 1);

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
			} else if (token.equals("transient")) {
				modif += "transient ";
			} else if (token.equals("volatile")) {
				modif += "volatile ";
			} else if (token.equals("=")) {
				jv.setModifiers(modif.substring(0,
						Math.max(modif.length() - 1, 0)));
			} else {
				if (!typeFound) {
					// System.out.println("le field est de type " + token);
					JavaReferenceObject type = new JavaReferenceObject(
							entityFactory.getJavaClass(token));
					jv.setType(type);
					typeFound = true;
				} else if (!nameFound) {
					// System.out.println("le field est nomme " + token);
					jv.setName(token);
					nameFound = true;
				} else {
					// System.out.println("le field a pour valeur par defaut "
					// + token);
					jv.setDefaultValue(token);
				}
			}
		}

		return jv;
	}

	public static JavaVariable decodeLocalVar(String def, int prefixe) {
		for (int i = 0; i < prefixe; i++)
			System.out.print("\t");
		System.out.println("local var[" + def + "]");
		JavaVariable jv = new JavaVariable();

		// only final is permitted

		boolean typeFound = false;
		boolean nameFound = false;
		while (!def.equals("")) {
			String token = def.substring(0, def.indexOf(" "));
			def = def.substring(def.indexOf(" ") + 1);
			String modif = "";
			if (token.equals("final")) {
				modif = "final";
			} else if (token.equals("=")) {
				jv.setModifiers(modif);
			} else {
				if (!typeFound) {
					// System.out.println("la variable est de type " + token);
					JavaReferenceObject type = new JavaReferenceObject(
							entityFactory.getJavaClass(token));
					jv.setType(type);
					typeFound = true;
				} else if (!nameFound) {
					// System.out.println("la variable est nomme " + token);
					jv.setName(token);
					nameFound = true;
				} else {
					// System.out.println("la variable a pour valeur par defaut "
					// + token);
					jv.setDefaultValue(token);
				}
			}
		}

		return jv;
	}

	
	public static JavaParam decodeParam(String param) {
		JavaParam jp = new JavaParam();
		String parametre, paramType;

		if (param.contains("extends")) {
			parametre = param.substring(0, param.indexOf(" extends"));
			paramType = param.substring(param.indexOf("extends") + 8);
			// System.out.println("le parametre est : \"" + parametre
			// + "\" de type \"" + paramType + "\"");
		} else {
			parametre = param;
			paramType = null;
			// System.out.println("le parametre est : \"" + param + "\"");
		}

		jp.setName(parametre);
		
		JavaParam jpp = null;
		if (paramType != null) {
			if (paramType.contains("<")) {
				jpp = InlineParser.decodeParam(paramType.substring(2,
						paramType.lastIndexOf(" >")));
			}
			JavaReferenceObject extent = new JavaReferenceObject(
					entityFactory.getJavaClass(paramType));
			if (jpp != null)
				extent.getParams().add(jpp);
			jp.setExtent(extent);
		}

		return jp;
	}
}
