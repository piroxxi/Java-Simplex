package fr.ubourgogne.simplex.model.java;

import fr.ubourgogne.simplex.model.BasicEntity;

public abstract class JavaEntity extends BasicEntity {
	private static final long serialVersionUID = -2009410419340835398L;
	
	private String name;

	/**
	 * 
	 */
	public JavaEntity() {
		super();
	}

	/**
	 * @param name
	 */
	public JavaEntity(String name) {
		super();
		this.setName(name);
	}

	/**
	 * @param id
	 * @param version
	 */
	public JavaEntity(String id, long version) {
		super(id, version);
	}

	/**
	 * @param id
	 * @param version
	 * @param name
	 */
	public JavaEntity(String id, long version, String name) {
		super(id, version);
		this.setName(name);
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

	public abstract String print(String prefix);
}
