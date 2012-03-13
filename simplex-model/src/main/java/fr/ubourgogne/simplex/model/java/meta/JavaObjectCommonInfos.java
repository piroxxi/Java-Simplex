package fr.ubourgogne.simplex.model.java.meta;

import java.io.Serializable;
import java.util.ArrayList;

import fr.ubourgogne.simplex.model.java.JavaObject;

public class JavaObjectCommonInfos implements Serializable {
	private static final long serialVersionUID = -4492786533953039615L;

	private String objectId;
	private int objectType;
	private String objectName;

	// because those two must be stored/sent only once
	private String objectJavaDoc;
	private ArrayList<JavaObjectCommonInfos> dependances = new ArrayList<JavaObjectCommonInfos>();

	public JavaObjectCommonInfos() {

	}

	public JavaObjectCommonInfos(JavaObject object) {
		this.objectId = object.getId();
		this.objectName = object.getName();
		this.objectJavaDoc = object.getJavaDoc();
		this.objectType = object.getType();
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectJavaDoc() {
		return objectJavaDoc;
	}

	public void setObjectJavaDoc(String objectJavaDoc) {
		this.objectJavaDoc = objectJavaDoc;
	}

	public ArrayList<JavaObjectCommonInfos> getDependances() {
		return dependances;
	}

	public void setDependances(ArrayList<JavaObjectCommonInfos> dependances) {
		this.dependances = dependances;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public int getObjectType() {
		return objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
}
