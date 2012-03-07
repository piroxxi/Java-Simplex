package fr.ubourgogne.simplex.parser;

import java.util.ArrayList;

import fr.ubourgogne.simplex.model.java.JavaObject;
import fr.ubourgogne.simplex.model.java.JavaProject;

public class FileParser {
	private String formatedBaseText;
	private JavaObject representedObject;
	private JavaProject project;

	public FileParser(JavaProject project, String s) {
		this.project = project;
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
		if(formatedBaseText == null || formatedBaseText.isEmpty()){
			//FIXME(raphael) => wat?
			return;
		}
		
		
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
			// System.out.println("le package est : \"" + ppackage + "\"");

		}

		while (code.startsWith("import")) {
			imports = new ArrayList<String>();
			String imp = code.substring(code.indexOf(" ") + 1,
					code.indexOf(" ;"));
			code = code.substring(code.indexOf(";") + 2);
			// System.out.println("import : \"" + imp + "\"");
			imports.add(imp);
		}

		// ensuite on a en theorie la definition de la classe, vu qu'on a enleve
		// ces *** d'annotations

		String defClasse = code.substring(0, code.indexOf("{"));
		code = code.substring(code.indexOf("{") + 2, code.lastIndexOf("}"));

		representedObject = (JavaObject) BlocParser.decodeBloc(project, defClasse, code,
				0);
		representedObject.setPackage(ppackage);
		representedObject.setImports(imports);
		project.addJavaObject(representedObject);

		// et voila, on a fini la definition de la classe
	}
}
