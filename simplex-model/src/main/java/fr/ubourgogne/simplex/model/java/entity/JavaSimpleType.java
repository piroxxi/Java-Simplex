package fr.ubourgogne.simplex.model.java.entity;


/**
 * Représente un type simple
 * <p>
 * char, int, double, float, boolean, etc.
 * <p>
 * TODO, lors d'un stockage, on risque de ne pas pouvoir reconstruire les
 * entitées correctement.
 */
public class JavaSimpleType extends JavaReferenceClass { // TODO
																		// ugly
																		// but
																		// so
																		// what?
																		// :p
	private static final long serialVersionUID = 501276216194846244L;

	public static final JavaSimpleType INT = new JavaSimpleType("int");
	public static final JavaSimpleType DOUBLE = new JavaSimpleType("double");
	public static final JavaSimpleType BOOLEAN = new JavaSimpleType("boolean");
	public static final JavaSimpleType CHAR = new JavaSimpleType("char");
	public static final JavaSimpleType FLOAT = new JavaSimpleType("float");
	public static final JavaSimpleType SHORT = new JavaSimpleType("short");
	public static final JavaSimpleType LONG = new JavaSimpleType("long");

	private String name;

	public JavaSimpleType(){
		super();
	}
	
	private JavaSimpleType(String name) {
		super();
		this.setName(name);
		this.setObjectName(name);
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
