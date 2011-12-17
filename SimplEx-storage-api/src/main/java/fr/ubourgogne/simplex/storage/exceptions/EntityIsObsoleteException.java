package fr.ubourgogne.simplex.storage.exceptions;

/**
 * Cette exception est levée lorsque l'on cherche à stocker/manipuler une entité
 * dont la version est plus ancienne que l'entité stockée en base..
 */
public class EntityIsObsoleteException extends Exception {
	private static final long serialVersionUID = 130785371091130919L;

	private final long actualVersion;
	private final long alreadyExistingVersion;

	/**
	 * 
	 */
	public EntityIsObsoleteException() {
		super();
		actualVersion = -1;
		alreadyExistingVersion = -1;
	}

	/**
	 * @param message
	 *            - the detail message. The detail message is saved for later
	 *            retrieval by the Throwable.getMessage() method.
	 */
	public EntityIsObsoleteException(String message) {
		super(message);
		actualVersion = -1;
		alreadyExistingVersion = -1;
	}

	/**
	 * @param message
	 *            - The detail message. The detail message is saved for later
	 *            retrieval by the Throwable.getMessage() method.
	 * @param actualVersion
	 *            - Version de l'entité que l'on manipule.
	 * @param alreadyExistingVersion
	 *            - Version de l'entité qui existe en base.
	 */
	public EntityIsObsoleteException(String message, long actualVersion,
			long alreadyExistingVersion) {
		super(message);
		this.actualVersion = actualVersion;
		this.alreadyExistingVersion = alreadyExistingVersion;
	}

	/**
	 * @return version de l'entité que l'on manipule.
	 */
	public long getActualVersion() {
		return actualVersion;
	}

	/**
	 * @return version de l'entité qui existe en base.
	 */
	public long getAlreadyExistingVersion() {
		return alreadyExistingVersion;
	}
}
