package fr.ubourgogne.simplex.model.java.entity;

import fr.ubourgogne.simplex.model.java.JavaEntity;
import fr.ubourgogne.simplex.model.java.meta.JavaReferenceObject;

public class JavaVariable extends JavaEntity {
	private static final long serialVersionUID = 6854183583392505352L;

	private JavaReferenceObject type;
	// private String name; //=> existe déja pour toutes les entitées
	private String modifiers = "";
	private String allocation = "";
	private String defaultValue = "";

	public JavaVariable() {
		super();
	}

	public JavaVariable(String name, JavaReferenceObject type) {
		super(name);
		this.type = type;
	}

	public JavaVariable(String modifiers, String name, JavaReferenceObject type) {
		super(name);
		this.setModifiers(modifiers);
		this.type = type;
	}

	public JavaReferenceObject getType() {
		return type;
	}

	public void setType(JavaReferenceObject type) {
		this.type = type;
	}

	public String getAllocation() {
		return allocation;
	}

	public void setAllocation(String allocation) {
		this.allocation = allocation;
	}

	@Override
	public String print(String prefix) {
		return prefix + ((!getModifiers().isEmpty()) ? getModifiers() + " " : "")
				+ type.print() + " " + getName()
				+ ((!allocation.isEmpty()) ? " = " + allocation : "") + ";\n";
	}

	public String printAsParamFonction() {
		return ((getModifiers().indexOf("final") != -1) ? "final " : "")
				+ type.print() + " " + getName();
	}

	public String getModifiers() {
		return modifiers;
	}

	public void setModifiers(String modifiers) {
		this.modifiers = modifiers;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
