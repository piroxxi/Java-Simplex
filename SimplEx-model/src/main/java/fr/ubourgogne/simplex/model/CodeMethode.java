package fr.ubourgogne.simplex.model;

import fr.ubourgogne.simplex.storage.BasicEntity;

public class CodeMethode extends BasicEntity {
	private String name;
	private Type returnType;

	private String javadoc;

	/**
	 * 
	 */
	public CodeMethode() {
		super();
	}

	/**
	 * @param name
	 * @param returnType
	 */
	public CodeMethode(String name, Type returnType) {
		super();
		this.name = name;
		this.returnType = returnType;
	}

	/**
	 * @param name
	 * @param returnType
	 * @param javadoc
	 */
	public CodeMethode(String name, Type returnType, String javadoc) {
		super();
		this.name = name;
		this.returnType = returnType;
		this.javadoc = javadoc;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the returnType
	 */
	public Type getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType
	 *            the returnType to set
	 */
	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}

	/**
	 * @return the javadoc
	 */
	public String getJavadoc() {
		return javadoc;
	}

	/**
	 * @param javadoc
	 *            the javadoc to set
	 */
	public void setJavadoc(String javadoc) {
		this.javadoc = javadoc;
	}
}
