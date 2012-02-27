package fr.ubourgogne.simplex.storage;

import java.util.List;

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
	public <E extends BasicEntity> E get(Class<E> clazz, String id)
			throws StorageException;

	/**
	 * Renvoie l'entité dont le nom est passé en parametre.
	 */
	public <E extends BasicEntity> E getByName(Class<E> clazz, String name)
			throws StorageException;

	/**
	 * Supprime l'entité dont l'identifiant est passé en parametre.
	 */
	public <E extends BasicEntity> void remove(Class<E> clazz, String id)
			throws StorageException;

	/**
	 * Récupère toutes les entitées du type passé en paramètre.
	 */
	public <E extends BasicEntity> List<E> getEntities(Class<E> clazz);
	
}