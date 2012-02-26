package fr.ubourgogne.simplex.model.java.object;

import java.util.ArrayList;
import java.util.List;

import fr.ubourgogne.simplex.model.java.JavaEntity;
import fr.ubourgogne.simplex.model.java.JavaObject;
import fr.ubourgogne.simplex.model.java.entity.JavaParam;
import fr.ubourgogne.simplex.model.java.entity.JavaReferenceClass;
import fr.ubourgogne.simplex.model.java.entity.JavaReferenceInterface;

public class JavaClass extends JavaObject {
	private static final long serialVersionUID = 3569382617831254352L;

	private String modifiers = "";
	private List<JavaReferenceInterface> implementedInterfaces = new ArrayList<JavaReferenceInterface>();
	private JavaReferenceClass superClass;
	private List<JavaEntity> content = new ArrayList<JavaEntity>();
	private List<JavaParam> params = new ArrayList<JavaParam>();

	public JavaClass() {
		super();
	}

	public JavaClass(String modifiers, String name) {
		super(name);
		this.modifiers = modifiers;
	}

	public List<JavaReferenceInterface> getImplementedInterfaces() {
		return implementedInterfaces;
	}

	/**
	 * @param implementedInterfacesIds
	 *            the implementedInterfacesIds to set
	 */
	public void setImplementedInterfaces(
			List<JavaReferenceInterface> implementedInterfaces) {
		this.implementedInterfaces = implementedInterfaces;
	}

	public List<JavaEntity> getContent() {
		return this.content;
	}

	public void setContent(List<JavaEntity> content) {
		this.content = content;
	}

	public JavaReferenceClass getSuperClass() {
		return superClass;
	}

	public void setSuperClass(JavaReferenceClass javaReferenceObject) {
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
		String ret = prefix + ((modifiers.isEmpty()) ? "" : modifiers + " ")
				+ "class " + getName();
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
