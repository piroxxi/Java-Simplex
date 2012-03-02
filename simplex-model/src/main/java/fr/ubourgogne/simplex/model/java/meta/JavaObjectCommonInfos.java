package fr.ubourgogne.simplex.model.java.meta;

import java.io.Serializable;
import java.util.ArrayList;

import fr.ubourgogne.simplex.model.java.JavaObject;

public class JavaObjectCommonInfos implements Serializable {
	private static final long serialVersionUID = -4492786533953039615L;

	private String objectId;

	// because those two must be stored/sent only once
	private String objectJavaDoc;
	private ArrayList<JavaObjectCommonInfos> dependances = new ArrayList<JavaObjectCommonInfos>();

	public JavaObjectCommonInfos() {

	}

	public JavaObjectCommonInfos(JavaObject object) {
		System.out.println("creation d'une JavaObjectCommonInfos + avec id : "
				+ object.getId());
		this.objectId = object.getId();
		this.objectJavaDoc = object.getJavaDoc();
	}

	public String getObjectId() {
		System.out.println("[JavaObjectCommonInfos:" + objectId
				+ "] Recuperation de l'ID (" + this + ")");
		return objectId;
	}

	public void setObjectId(String objectId) {
		System.out.println("[JavaObjectCommonInfos:" + objectId
				+ "] Mise à jour de l'ID (" + this + ")");
		this.objectId = objectId;
	}

	public String getObjectJavaDoc() {
		System.out.println("[JavaObjectCommonInfos:" + objectId
				+ "] Recuperation de la javadoc (" + this + ")");
		return objectJavaDoc;
	}

	public void setObjectJavaDoc(String objectJavaDoc) {
		System.out.println("[JavaObjectCommonInfos:" + objectId
				+ "] Mise à jour de la javadoc (" + this + ")");
		this.objectJavaDoc = objectJavaDoc;
	}

	public ArrayList<JavaObjectCommonInfos> getDependances() {
		System.out.println("[JavaObjectCommonInfos:" + objectId
				+ "] Recuperation des dépendances (" + this + ")");
		return dependances;
	}

	public void setDependances(ArrayList<JavaObjectCommonInfos> dependances) {
		System.out.println("[JavaObjectCommonInfos:" + objectId
				+ "] Mise à jour des dependances (" + this + ")");
		this.dependances = dependances;
	}
}
