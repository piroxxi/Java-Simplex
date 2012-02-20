package fr.ubourgogne.simplex.model;

public class BasicEntity {
	private String id;
	private long version;

	public BasicEntity() {

	}

	public BasicEntity(String id, long version) {
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