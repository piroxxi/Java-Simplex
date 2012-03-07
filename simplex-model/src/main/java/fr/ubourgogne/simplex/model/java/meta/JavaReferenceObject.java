package fr.ubourgogne.simplex.model.java.meta;

import java.io.Serializable;
import java.util.ArrayList;

import fr.ubourgogne.simplex.model.java.JavaObject;
import fr.ubourgogne.simplex.model.java.entity.JavaParam;

public class JavaReferenceObject implements Serializable {
	private static final long serialVersionUID = -7354348310792167308L;

	private String objectId;
	private int objectType;
	private String objectName;
	private JavaObjectCommonInfos objectInfos;
	private ArrayList<JavaParam> params = new ArrayList<JavaParam>();

	public JavaReferenceObject() {
	}

	public JavaReferenceObject(JavaObject object) {
		this(object, (JavaParam[]) null);
	}

	public JavaReferenceObject(JavaObject object, JavaParam... params) {
		if (object.getId() == null) {
			System.out.println("probleme => " + object + " = "
					+ object.getName());
		}
		objectId = object.getId();
		objectType = object.getType();
		objectName = object.getName();
		if (params != null && params.length > 0) {
			for (JavaParam p : params) {
				this.getParams().add(p);
			}
		}

		setObjectInfos(JavaObjectCommonInfosProvider.getInfosForObject(object));
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public int getObjectType() {
		return this.objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public ArrayList<JavaParam> getParams() {
		return params;
	}

	public void setParams(ArrayList<JavaParam> params) {
		this.params = params;
	}

	public String print() {
		String t = "";
		t += objectName;
		if (!params.isEmpty()) {
			t += "<";
			for (int i = 0; i < params.size(); i++) {
				t += params.get(i).print("");
				if (i < params.size() - 1) {
					t += ", ";
				}
			}
			t += ">";
		}
		return t;
	}

	public String getObjectJavaDoc() {
		if (getObjectInfos() == null) {
			throw new NullPointerException();
		}
		return getObjectInfos().getObjectJavaDoc();
	}

	public ArrayList<JavaObjectCommonInfos> getDependances() {
		if (getObjectInfos() == null) {
			throw new NullPointerException();
		}
		return getObjectInfos().getDependances();
	}

	/**
	 * @return the objectInfos
	 */
	public JavaObjectCommonInfos getObjectInfos() {
		return objectInfos;
	}

	/**
	 * @param objectInfos
	 *            the objectInfos to set
	 */
	public void setObjectInfos(JavaObjectCommonInfos objectInfos) {
		this.objectInfos = objectInfos;
	}
}
