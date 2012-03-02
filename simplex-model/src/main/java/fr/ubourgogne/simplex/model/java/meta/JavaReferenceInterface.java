package fr.ubourgogne.simplex.model.java.meta;

import fr.ubourgogne.simplex.model.java.entity.JavaParam;
import fr.ubourgogne.simplex.model.java.object.JavaInterface;

public class JavaReferenceInterface extends JavaReferenceObject {
	private static final long serialVersionUID = -4021843337770444326L;


	public JavaReferenceInterface(){
		super();
	}

	public JavaReferenceInterface(JavaInterface object) {
		this(object, (JavaParam[]) null);
	}

	public JavaReferenceInterface(JavaInterface object, JavaParam... params) {
		super(object,params);
	}
}
