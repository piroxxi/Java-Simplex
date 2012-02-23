package fr.ubourgogne.simplex.model.java.entity;

import java.util.ArrayList;
import java.util.List;

import fr.ubourgogne.simplex.model.java.JavaObject;

public class JavaReferenceObject<T extends JavaObject> {
	private T object;
	private List<JavaParam> params = new ArrayList<JavaParam>();

	public JavaReferenceObject() {
		super();
	}

	public JavaReferenceObject(T object) {
		super();
		this.object = object;
	}

	public JavaReferenceObject(T object, JavaParam... params) {
		super();
		this.object = object;
		for (JavaParam p : params) {
			this.params.add(p);
		}
	}

	public String print() {
		String ret = object.getName();
		if (!params.isEmpty()) {
			ret += "<";
			for (int i = 0; i < params.size(); i++) {
				ret += params.get(i).print("");
				if (i < params.size() - 1) {
					ret += ", ";
				}
			}
			ret += ">";
		}
		return ret;
	}
}
