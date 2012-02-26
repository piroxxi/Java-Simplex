package fr.ubourgogne.simplex.model.java.entity;

import fr.ubourgogne.simplex.model.java.JavaEntity;

public class JavaVariable extends JavaEntity {
	private static final long serialVersionUID = 6854183583392505352L;
	
	private JavaReferenceObject type;
	// private String name; //=> existe déja pour toutes les entitées
	private String modifiers = "";
	private String allocation = "";

	public JavaVariable() {
		super();
	}

	public JavaVariable(String name, JavaReferenceObject type) {
		super(name);
		this.type = type;
	}

	public JavaVariable(String modifiers, String name,
			JavaReferenceObject type) {
		super(name);
		this.modifiers = modifiers;
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
		return prefix + ((!modifiers.isEmpty()) ? modifiers + " " : "")
				+ type.print() + " " + getName()
				+ ((!allocation.isEmpty()) ? " = " + allocation : "") + ";\n";
	}

	public String printAsParamFonction() {
		return ((modifiers.indexOf("final") != -1) ? "final " : "")
				+ type.print() + " " + getName();
	}
}
