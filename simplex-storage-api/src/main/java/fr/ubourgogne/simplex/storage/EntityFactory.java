package fr.ubourgogne.simplex.storage;

import fr.ubourgogne.simplex.model.java.JavaProject;
import fr.ubourgogne.simplex.model.java.object.JavaAnnotation;
import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.model.java.object.JavaEnum;
import fr.ubourgogne.simplex.model.java.object.JavaInterface;
import fr.ubourgogne.simplex.storage.exceptions.StorageException;

public interface EntityFactory {

	/**
	 * Renvoie une instance d'une JavaClass dont le nom est passé en paramètre.
	 * Si l'entité existe déja en mémoire, on la renvoie, sinon on en crée une
	 * nouvelle (avec Id).
	 * <p>
	 * ATTENTION : L'entité renvoyé sera stockée en mémoire. Ainsi, si la
	 * méthode utilisant l'entité aborte, il peut être interessant de supprimer
	 * l'entité de la mémoire.
	 * 
	 * @param name
	 * @return
	 * @throws StorageException
	 */
	public JavaClass getJavaClass(JavaProject project, String name);

	public JavaInterface getJavaInterface(JavaProject project, String name);

	public JavaAnnotation getJavaAnnotation(JavaProject project, String name)
			throws StorageException;

	public JavaEnum getJavaEnum(JavaProject project, String name);
}
