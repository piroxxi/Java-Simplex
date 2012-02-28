package fr.ubourgogne.simplex.model.java.entity;

import java.io.Serializable;
import java.util.ArrayList;

import fr.ubourgogne.simplex.model.java.JavaObject;

public class JavaReferenceObject implements Serializable {
	private static final long serialVersionUID = -7354348310792167308L;

	private String objectId;
	private String objectName;
	private String objectClazz;
	private String objectJavaDoc;
	private ArrayList<JavaParam> params = new ArrayList<JavaParam>();

	public JavaReferenceObject() {
	}

	public JavaReferenceObject(JavaObject object) {
		this(object, (JavaParam[]) null);
	}

	public JavaReferenceObject(JavaObject object, JavaParam... params) {
		objectId = object.getId();
		objectName = object.getName();
		objectClazz = object.getClass().getName();
		objectJavaDoc = "NO JAVADOC YET!";
		if (params != null && params.length > 0) {
			for (JavaParam p : params) {
				this.getParams().add(p);
			}
		}
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectClazz() {
		return objectClazz;
	}

	public void setObjectClazz(String objectClazz) {
		this.objectClazz = objectClazz;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectJavaDoc() {
		return objectJavaDoc;
	}

	public void setObjectJavaDoc(String objectJavaDoc) {
		this.objectJavaDoc = objectJavaDoc;
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
}
