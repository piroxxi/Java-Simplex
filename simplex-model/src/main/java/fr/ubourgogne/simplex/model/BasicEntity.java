package fr.ubourgogne.simplex.model;

import java.io.Serializable;

public class BasicEntity implements Serializable{
	private static final long serialVersionUID = 2267847744972734318L;
	
	private String id;
	private long version;

	public BasicEntity() {
		if (this.id == null) {
			// this.id = UUID.randomUUID().toString();
		}
	}

	public BasicEntity(String id, long version) {
		this();
		this.id = id;
		this.version = version;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(long version) {
		this.version = version;
	}
}
