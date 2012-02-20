package fr.ubourgogne.simplex.storage.exceptions;

/**
 * Classe parente de toutes les exceptions renvoyée par le module Storage.
 * 
 * @author POITTEVIN Raphaël
 */
public class StorageException extends Exception {
	private static final long serialVersionUID = 8086862707683670363L;

	public StorageException() {
		super();
	}

	public StorageException(String msg) {
		super(msg);
	}
}
