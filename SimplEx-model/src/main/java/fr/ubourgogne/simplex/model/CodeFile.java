package fr.ubourgogne.simplex.model;

import java.util.List;

public class CodeFile {
	private String name;

	private List<CodeMethode> methodes;
	private String javadoc;

	/**
	 * 
	 */
	public CodeFile() {
		super();
	}

	/**
	 * @param name
	 */
	public CodeFile(String name) {
		super();
		this.name = name;
	}

	/**
	 * @param name
	 * @param methodes
	 * @param javadoc
	 */
	public CodeFile(String name, List<CodeMethode> methodes, String javadoc) {
		super();
		this.name = name;
		this.methodes = methodes;
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

	/**
	 * @return the methodes
	 */
	public List<CodeMethode> getMethodes() {
		return methodes;
	}

	/**
	 * @param methodes
	 *            the methodes to set
	 */
	public void setMethodes(List<CodeMethode> methodes) {
		this.methodes = methodes;
	}
}
