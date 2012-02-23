package fr.ubourgogne.simplex.model.java;

import java.util.ArrayList;

public abstract class JavaObject extends JavaEntity {
	private String _package;
	private ArrayList<String> imports;
	
	public JavaObject() {
		super();
	}

	public JavaObject(String name) {
		super(name);
	}

	public JavaObject(String id, int version, String name) {
		super(id,version,name);
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
}
