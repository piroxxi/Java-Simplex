package fr.ubourgogne.simplex.model.java.entity;

import fr.ubourgogne.simplex.model.java.object.JavaClass;

public class JavaReferenceClass extends JavaReferenceObject {
	private static final long serialVersionUID = 2738123499114567149L;

	public JavaReferenceClass(){
		super();
	}
	
	public JavaReferenceClass(String serializedString) {
		setSerializedString(serializedString);
	}

	public JavaReferenceClass(JavaClass object) {
		this(object, (JavaParam[]) null);
	}

	public JavaReferenceClass(JavaClass object, JavaParam... params) {
		super(object,params);
	}
}
