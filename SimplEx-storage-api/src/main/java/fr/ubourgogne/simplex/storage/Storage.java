package fr.ubourgogne.simplex.storage;

import fr.ubourgogne.simplex.storage.exceptions.EntityIsObsoleteException;

/**
 * Definition de l'API qui se charge du stockage et de la récupération des
 * données en base.
 * <p>
 * Cette API propose trois méthodes:
 * <ul>
 * <li>store()</li>
 * <li>get()</li>
 * <li>remove()</li>
 * </ul>
 * Grace a ces trois méthodes, on peut mettre en place un cycle "CRUD" complet.
 * <p>
 * NB: L'update se fait par un simple save(). Le match des id implique qu'il
 * s'agit d'une même entité. Et donc, si l'ID est null, save() devient une
 * opération de création.
 * 
 * @version 1.3
 * @author POITTEVIN Raphael
 */
public interface Storage {
	/**
	 * Stock l'entité dans la base de données.
	 * <p>
	 * Si l'entité n'a pas d'ID, alors c'est qu'elle est neuve. Auquel cas on
	 * lui donne un ID et on la stock.
	 * <p>
	 * Sinon, on verifie qu'il n'existe pas d'entité ayant une version plus
	 * récente que l'entité que l'on souhaite stocker.
	 * 
	 * @param clazz
	 *            - Classe de l'entité à stocker.
	 * @param entity
	 *            - Entité à stocker.
	 */
	public <T extends BasicEntity> void store(Class<T> clazz, T entity)
			throws EntityIsObsoleteException;

	/**
	 * Renvoie l'entité en base en fonction de son ID.
	 * <p>
	 * Dans le cas où l'entité n'existe pas en base, on renvoie null.
	 * 
	 * @param clazz
	 *            - Classe de l'entité à stocker.
	 * @param id
	 *            - Id de l'entité que l'on souhaite récupérer.
	 * @return L'entité recherchée, null si elle n'existe pas.
	 */
	public <T extends BasicEntity> T get(Class<T> clazz, String id);

	/**
	 * Supprime l'entité dont l'ID est passé en paramètre.
	 * <p>
	 * Si l'entité n'existe pas, alors rien ne se passe.
	 * 
	 * @param clazz
	 *            - Classe de l'entité à supprimer.
	 * @param id
	 *            - Id de l'entité que l'on souhaite supprimer.
	 */
	public <T extends BasicEntity> void remove(Class<T> clazz, String id);
}
