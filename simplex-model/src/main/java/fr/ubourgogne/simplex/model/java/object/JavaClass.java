package fr.ubourgogne.simplex.model.java.object;

import java.util.ArrayList;
import java.util.List;

import fr.ubourgogne.simplex.model.java.JavaEntity;
import fr.ubourgogne.simplex.model.java.JavaObject;
import fr.ubourgogne.simplex.model.java.entity.JavaParam;
import fr.ubourgogne.simplex.model.java.entity.JavaReferenceObject;

public class JavaClass extends JavaObject {
	private String modifiers = "";
	private List<JavaReferenceObject<JavaInterface>> implementedInterfaces = new ArrayList<JavaReferenceObject<JavaInterface>>();
	private JavaReferenceObject<JavaClass> superClass;
	private List<JavaEntity> content = new ArrayList<JavaEntity>();
	private List<JavaParam> params = new ArrayList<JavaParam>();

	public JavaClass() {
		super();
	}

	public JavaClass(String name) {
		super(name);
	}

	public List<JavaReferenceObject<JavaInterface>> getImplementedInterfaces() {
		return implementedInterfaces;
	}

	/**
	 * @param implementedInterfacesIds
	 *            the implementedInterfacesIds to set
	 */
	public void setImplementedInterfaces(
			List<JavaReferenceObject<JavaInterface>> implementedInterfaces) {
		this.implementedInterfaces = implementedInterfaces;
	}

	public List<JavaEntity> getContent() {
		return this.content;
	}

	public void setContent(List<JavaEntity> content) {
		this.content = content;
	}

	public JavaReferenceObject<JavaClass> getSuperClass() {
		return superClass;
	}

	public void setSuperClass(JavaReferenceObject<JavaClass> javaReferenceObject) {
		this.superClass = javaReferenceObject;
	}

	public String getModifiers() {
		return modifiers;
	}

	public void setModifiers(String modifiers) {
		this.modifiers = modifiers;
	}

	public List<JavaParam> getParams() {
		return params;
	}

	public void setParams(List<JavaParam> params) {
		this.params = params;
	}

	@Override
	public String print(String prefix) {
		String ret = prefix + modifiers + " " + getName();
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
		ret += " ";
		if (superClass != null) {
			ret += "extends " + superClass.print();
			if (!implementedInterfaces.isEmpty()) {
				ret += " ";
			}
		}
		if (!implementedInterfaces.isEmpty()) {
			ret += "implements ";
			for (int i = 0; i < implementedInterfaces.size(); i++) {
				ret += implementedInterfaces.get(i).print();
				if (i < implementedInterfaces.size() - 1) {
					ret += ", ";
				}
			}
		}
		ret += "{" + "\n";
		ret += prefix + "\n";
		{
			for (JavaEntity entity : content) {
				ret += entity.print(prefix + "\t");
				ret += prefix + "\n";
			}
		}
		ret += prefix + "}" + "\n";
		return ret;
	}

	@Override
	public String toString() {
		return "[" + super.toString() + "] " + getModifiers() + " " + getName();
	}
}
