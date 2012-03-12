package fr.ubourgogne.simplex.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import fr.ubourgogne.simplex.model.java.JavaProject;
import fr.ubourgogne.simplex.model.java.entity.JavaParam;
import fr.ubourgogne.simplex.model.java.entity.JavaSimpleType;
import fr.ubourgogne.simplex.model.java.entity.JavaVariable;
import fr.ubourgogne.simplex.model.java.meta.JavaReferenceObject;
import fr.ubourgogne.simplex.storage.EntityFactory;

public abstract class InlineParser {

	@Inject
	private static EntityFactory entityFactory;

	private static Logger logger = LoggerFactory.getLogger(InlineParser.class);

	public static JavaVariable decodeField(JavaProject project, String def,
			int prefixe) {
		String prefix = "";
		for (int i = 0; i < prefixe; i++)
			prefix += "\t";

		logger.info(prefix + "field[" + def + "]");
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
					JavaReferenceObject type = JavaSimpleType.getByName(token);

					if (type == null)
						type = new JavaReferenceObject(
								entityFactory.getJavaClass(project, token));
					jv.setType(type);
					typeFound = true;
				} else if (!nameFound) {
					jv.setName(token);
					nameFound = true;
				} else {
					jv.setDefaultValue(token);
				}
			}
		}

		return jv;
	}

	public static JavaVariable decodeLocalVar(JavaProject project, String def,
			int prefixe) {
		String prefix = "";
		for (int i = 0; i < prefixe; i++)
			prefix += "\t";

		logger.info(prefix + "local var[" + def + "]");
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
				if (!typeFound) {;
					JavaReferenceObject type = new JavaReferenceObject(
							entityFactory.getJavaClass(project, token));
					jv.setType(type);
					typeFound = true;
				} else if (!nameFound) {
					jv.setName(token);
					nameFound = true;
				} else {
					jv.setDefaultValue(token);
				}
			}
		}

		return jv;
	}

	public static JavaParam decodeParam(JavaProject project, String param) {
		JavaParam jp = new JavaParam();
		String parametre, paramType;

		if (param.contains("extends")) {
			parametre = param.substring(0, param.indexOf(" extends"));
			paramType = param.substring(param.indexOf("extends") + 8);
		} else {
			parametre = param;
			paramType = null;
		}

		jp.setName(parametre);

		JavaParam jpp = null;
		if (paramType != null) {
			if (paramType.contains("<")) {
				jpp = InlineParser.decodeParam(project,
						paramType.substring(2, paramType.lastIndexOf(" >")));
			}
			JavaReferenceObject extent = new JavaReferenceObject(
					entityFactory.getJavaClass(project, paramType));
			if (jpp != null)
				extent.getParams().add(jpp);
			jp.setExtent(extent);
		}

		return jp;
	}
}
