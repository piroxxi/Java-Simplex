package fr.ubourgogne.simplex.webapp.client.utils.java;

public class LigneCodeRenderer {
	public static final String[] motsCles = { "abstract", "assert", "boolean",
			"break", "byte", "case", "catch", "char", "class", "const",
			"continue", "default", "do", "double", "else", "enum", "extends",
			"false", "final", "finally", "float", "for", "goto", "if",
			"implements", "import", "instanceof", "int", "interface", "long",
			"native", "new", "null", "package", "private", "protected",
			"public", "return", "short", "static", "super", "switch",
			"synchronized", "this", "throw", "throws", "transient", "true",
			"try", "void", "volatile", "while" };

	public static String render(String line) {
		if(line.isEmpty()){
			return "&nbsp;";
		}
		System.out.println("€=> Ligne "+line);
		line = line.replace("<","&lt;");
		line = line.replace(">","&gt;");
		line = line.replace("\t","&nbsp;&nbsp;&nbsp; ");
		String[] words = line.split(" ");

		String ret = "";
		for (String word : words) {
			System.out.println("  > mot :\t"+word);
			boolean isKeyWord = false;
			for (String motCle : motsCles) {
				if (motCle.equals(word)) {
					isKeyWord = true;
					break;
				}
			}
			if (isKeyWord) {
				ret += "<span class=\"java_motR\">" + word + "</span> ";
			} else {
				ret += word + " ";
			}
		}
		return ret;
	}
}
