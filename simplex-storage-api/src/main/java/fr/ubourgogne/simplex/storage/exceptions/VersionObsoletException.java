package fr.ubourgogne.simplex.storage.exceptions;

/**
 * Cette exception est levée lorsque l'on tente de sauvegarder une entité, et
 * qu'il existe déjà une entité ayant la même ID et un numéro de version
 * supérieur en base.
 * 
 * @author POITTEVIN Raphaël
 */
public class VersionObsoletException extends StorageException {
	private static final long serialVersionUID = 4425131462846897828L;

	public VersionObsoletException() {
		super();
	}

	public VersionObsoletException(String msg) {
		super(msg);
	}
}
