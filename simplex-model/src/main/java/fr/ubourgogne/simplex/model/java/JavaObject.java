package fr.ubourgogne.simplex.model.java;

import java.util.ArrayList;

import fr.ubourgogne.simplex.model.java.meta.JavaObjectCommonInfos;
import fr.ubourgogne.simplex.model.java.meta.JavaObjectCommonInfosProvider;

public abstract class JavaObject extends JavaEntity {
	public static final int OBJECT = -1;
	public static final int CLASS = 0;
	public static final int INTERFACE = 1;
	public static final int ENUM = 2;
	public static final int ANNOTATION = 3;

	private static final long serialVersionUID = 2236523118747299153L;
	private String _package;
	private ArrayList<String> imports = new ArrayList<String>();
	private JavaObjectCommonInfos commonInfos = new JavaObjectCommonInfos();

	private int type = OBJECT;

	public JavaObject() {
		super();
	}

	public JavaObject(String id, int version, String name) {
		this();
		setId(id);
		setVersion(version);
		setName(name);
	}

	@Override
	public void setId(String id) {
		super.setId(id);
		setCommonInfos(JavaObjectCommonInfosProvider.getInfosForObject(this));
	}

	public String getPackage() {
		return _package;
	}

	public void setPackage(String _package) {
		this._package = _package;
	}

	public ArrayList<String> getImports() {
		return imports;
	}

	public void setImports(ArrayList<String> imports) {
		this.imports = imports;
	}

	public String getJavaDoc() {
		if (getCommonInfos() == null) {
			throw new NullPointerException();
		}
		return getCommonInfos().getObjectJavaDoc();
	}

	public void setJavaDoc(String javadoc) {
		if (getCommonInfos() == null) {
			throw new NullPointerException();
		}
		this.getCommonInfos().setObjectJavaDoc(javadoc);
	}

	public ArrayList<JavaObjectCommonInfos> getDependances() {
		if (getCommonInfos() == null) {
			throw new NullPointerException();
		}
		return getCommonInfos().getDependances();
	}

	public void setDependances(ArrayList<JavaObjectCommonInfos> dependancies) {
		if (getCommonInfos() == null) {
			throw new NullPointerException();
		}
		getCommonInfos().setDependances(dependancies);
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the commonInfos
	 */
	public JavaObjectCommonInfos getCommonInfos() {
		return commonInfos;
	}

	/**
	 * @param commonInfos
	 *            the commonInfos to set
	 */
	public void setCommonInfos(JavaObjectCommonInfos commonInfos) {
		this.commonInfos = commonInfos;
	}
}
