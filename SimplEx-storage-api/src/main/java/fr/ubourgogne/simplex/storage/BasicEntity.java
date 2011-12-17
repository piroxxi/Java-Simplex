package fr.ubourgogne.simplex.storage;

public class BasicEntity {
	private String id;
	private long version;

	/**
	 * 
	 */
	public BasicEntity() {
		super();
	}

	/**
	 * @param id
	 * @param version
	 */
	public BasicEntity(String id, long version) {
		super();
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
