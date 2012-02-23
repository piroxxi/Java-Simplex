package fr.ubourgogne.simplex.model.java.entity;

import fr.ubourgogne.simplex.model.java.JavaEntity;

public class JavaParam extends JavaEntity { // TODO useful inheritence?

	private JavaReferenceObject<?> extent;

	public JavaParam(String name, JavaReferenceObject<?> extent) {
		super(name);
		this.setExtent(extent);
	}

	public JavaReferenceObject<?> getExtent() {
		return extent;
	}

	public void setExtent(JavaReferenceObject<?> extent) {
		this.extent = extent;
	}

	@Override
	public String print(String prefix) {
		String ret = getName();
		if (extent != null) {
			if (!ret.isEmpty()) {

				ret += " extends " + extent.print();
			} else {
				ret += extent.print();
			}
		}
		return ret;
	}
}
