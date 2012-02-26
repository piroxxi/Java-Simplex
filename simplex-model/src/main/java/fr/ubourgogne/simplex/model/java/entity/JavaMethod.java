package fr.ubourgogne.simplex.model.java.entity;

import java.util.ArrayList;
import java.util.List;

import fr.ubourgogne.simplex.model.java.JavaEntity;

/**
 * Représente une méthode java.
 */
public class JavaMethod extends JavaEntity {
	private static final long serialVersionUID = 1L;

	private String modifiers = "";
	private boolean hasCode = true;

	private JavaReferenceObject returnType;
	private List<JavaVariable> params = new ArrayList<JavaVariable>();
	private List<JavaParam> autreParams = new ArrayList<JavaParam>();
	private List<String> lines = new ArrayList<String>();

	public JavaMethod() {
		super();
	}

	public JavaMethod(String modifiers, JavaReferenceObject returnType,
			String name) {
		super(name);
		this.returnType = returnType;
		this.setModifiers(modifiers);
	}

	/**
	 * @return the params
	 */
	public List<JavaVariable> getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(List<JavaVariable> params) {
		this.params = params;
	}

	public void setRawCode(String... lines) {
		for (String l : lines) {
			this.lines.add(l);
		}
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	@Override
	public String print(String prefix) {
		String ret = prefix + getModifiers()
				+ ((returnType != null) ? " " + returnType.print() : "") + " "
				+ getName() + "(";
		for (int i = 0; i < params.size(); i++) {
			ret += params.get(i).printAsParamFonction();
			if (i < params.size() - 1) {
				ret += ", ";
			}
		}
		if (hasCode) {
			ret += ") {\n";
			for (String line : lines) {
				ret += prefix + "\t" + line + "\n";
			}
			ret += prefix + "}\n";
		} else {
			ret += ");\n";
		}
		return ret;
	}

	public JavaReferenceObject getReturnType() {
		return returnType;
	}

	public void setReturnType(JavaReferenceObject returnType) {
		this.returnType = returnType;
	}

	public boolean hasCode() {
		return hasCode;
	}

	public void setHasCode(boolean hasCode) {
		this.hasCode = hasCode;
	}

	public List<JavaParam> getAutreParams() {
		return autreParams;
	}

	public void setAutreParams(List<JavaParam> autreParams) {
		this.autreParams = autreParams;
	}

	public String getModifiers() {
		return modifiers;
	}

	public void setModifiers(String modifiers) {
		this.modifiers = modifiers;
	}
}
