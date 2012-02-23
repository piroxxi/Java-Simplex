package fr.ubourgogne.simplex.model.java.entity;

import fr.ubourgogne.simplex.model.java.object.JavaClass;

/**
 * Représente un type simple
 * <p>
 * char, int, double, float, boolean, etc.
 * <p>
 * TODO, lors d'un stockage, on risque de ne pas pouvoir reconstruire les
 * entitées correctement.
 */
public class JavaSimpleType extends JavaReferenceObject<JavaClass> { // TODO
																		// ugly
																		// but
																		// so
																		// what?
																		// :p
	public static final JavaSimpleType INT = new JavaSimpleType("int");
	public static final JavaSimpleType DOUBLE = new JavaSimpleType("double");
	public static final JavaSimpleType BOOLEAN = new JavaSimpleType("boolean");
	public static final JavaSimpleType CHAR = new JavaSimpleType("char");
	public static final JavaSimpleType FLOAT = new JavaSimpleType("float");
	public static final JavaSimpleType SHORT = new JavaSimpleType("short");
	public static final JavaSimpleType LONG = new JavaSimpleType("long");

	private String name;

	private JavaSimpleType(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO a tester
		if (!(obj instanceof JavaSimpleType))
			return false;
		return this.getName().equals(((JavaSimpleType) obj).getName());
	}

	@Override
	public String print() {
		return getName();
	}
}
