package fr.ubourgogne.simplex.storage;

import fr.ubourgogne.simplex.model.BasicEntity;
import fr.ubourgogne.simplex.storage.exceptions.StorageException;

public interface Storage {

	/**
	 * Sauvegarde l'entité en base.
	 * <p>
	 * Si l'entité n'a pas d'identifiant, alors on lui en crée un.
	 * 
	 * @param entity
	 */
	public <E extends BasicEntity> void store(E entity) throws StorageException;

	/**
	 * Renvoie l'entité dont l'identifiant est passé en parametre.
	 */
	public <E extends BasicEntity> E get(Class<E> clazz, String id) throws StorageException;

	/**
	 * Supprime l'entité dont l'identifiant est passé en parametre.
	 */
	public <E extends BasicEntity> void remove(Class<E> clazz, String id) throws StorageException;
}