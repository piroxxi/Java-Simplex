package fr.ubourgogne.simplex.webapp.shared.security;

import fr.ubourgogne.simplex.storage.exceptions.StorageException;

/**
 * Exception levée lorsque l'on a eut une exception au niveau du storage.
 */
public class StorageExceptionHappenedException extends SimplexSecurityException {
	private static final long serialVersionUID = 8146335553342921966L;

	private final StorageException exception;

	public StorageExceptionHappenedException(StorageException storageException) {
		super("[STORAGE EXCEPTION] " + storageException.getMessage());
		this.exception = storageException;
	}

	public StorageException getStorageException() {
		return exception;
	}
}
