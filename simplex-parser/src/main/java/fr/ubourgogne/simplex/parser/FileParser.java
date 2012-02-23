package fr.ubourgogne.simplex.parser;

import java.util.ArrayList;

import fr.ubourgogne.simplex.model.java.JavaObject;

public class FileParser {
	private String formatedBaseText;
	private JavaObject representedObject;

	public FileParser(String s) {
		formatedBaseText = s;
	}

	public JavaObject getRepresentedObject() {
		return representedObject;
	}

	/**
	 * Decode la definition de la classe
	 * 
	 * @param fichier
	 *            le fichier a parser
	 * 
	 * @return un JavaObject représentant la classe et remplie avec les infos de
	 *         base
	 */
	public void retrieveClassInfos() {
		String code = formatedBaseText.substring(0);

		// on va récupérer les elements suivants
		String ppackage = null;
		ArrayList<String> imports = null;

		// et c'est parti pour le decoupage \o/

		// au debut, on a le package et les import, mais pas forcement
		if (code.startsWith("package")) {
			ppackage = code
					.substring(code.indexOf(" ") + 1, code.indexOf(" ;"));
			code = code.substring(code.indexOf(";") + 2);
			System.out.println("le package est : \"" + ppackage + "\"");
			
		}

		while (code.startsWith("import")) {
			imports = new ArrayList<String>();
			String imp = code.substring(code.indexOf(" ") + 1,
					code.indexOf(" ;"));
			code = code.substring(code.indexOf(";") + 2);
			System.out.println("import : \"" + imp + "\"");
			imports.add(imp);
		}

		// ensuite on a en theorie la definition de la classe, vu qu'on a enleve
		// ces *** d'annotations

		String defClasse = code.substring(0, code.indexOf("{"));
		code = code.substring(code.indexOf("{") + 2, code.lastIndexOf("}"));

		representedObject = (JavaObject)BlocParser.decodeBloc(defClasse, code);
		representedObject.setPackage(ppackage);
		representedObject.setImports(imports);

		// et voila, on a fini la definition de la classe
	}
}
