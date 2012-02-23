package fr.ubourgogne.simplex.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import fr.ubourgogne.simplex.model.BasicEntity;
import fr.ubourgogne.simplex.model.java.JavaEntity;
import fr.ubourgogne.simplex.storage.exceptions.StorageException;
import fr.ubourgogne.simplex.storage.exceptions.VersionObsoletException;

public class StorageImpl implements Storage {

	private final Map<Class<? extends BasicEntity>, Map<String, BasicEntity>> storage;

	public StorageImpl() {
		System.out.println("[STORAGE] Creation du storage");
		storage = new HashMap<Class<? extends BasicEntity>, Map<String, BasicEntity>>();
	}

	@Override
	public <E extends BasicEntity> void store(E entity) throws StorageException {
		System.out.println("[STORAGE] Sauvegarde de l'entité "+entity.getId()+"("+entity.getClass().getSimpleName()+")");
		@SuppressWarnings("unchecked")
		Map<String, E> collection = (Map<String, E>) storage.get(entity
				.getClass());
		if (collection == null) {
			collection = new HashMap<String, E>();
		}

		if (entity.getId() == null || entity.getId().isEmpty()) {
			entity.setId(UUID.randomUUID().toString());
			entity.setVersion(0);
		} else {
			E inStoreEntity = collection.get(entity.getId());
			if (inStoreEntity != null) {
				if (entity.getVersion() < inStoreEntity.getVersion()) {
					throw new VersionObsoletException(
							"L'entité "
									+ entity.getId()
									+ " que vous tentez de sauvegarder est en version "
									+ entity.getVersion()
									+ " tandis que cette entité existe déjà en version "
									+ inStoreEntity.getVersion());
				}
				entity.setVersion(entity.getVersion() + 1);
			}
		}
		collection.remove(entity.getId());
		collection.put(entity.getId(), entity);
	}

	@Override
	public <E extends BasicEntity> E get(Class<E> clazz, String id)
			throws StorageException {
		System.out.println("[STORAGE] Recupération de l'entité "+id+"("+clazz.getSimpleName()+")");
		@SuppressWarnings("unchecked")
		Map<String, E> collection = (Map<String, E>) storage.get(clazz);
		if (collection == null) {
			return null;
		}
		return collection.get(id);
	}

	@Override
	public <E extends BasicEntity> void remove(Class<E> clazz, String id)
			throws StorageException {
		System.out.println("[STORAGE] Suppression de l'entité "+id+"("+clazz.getSimpleName()+")");
		@SuppressWarnings("unchecked")
		Map<String, E> collection = (Map<String, E>) storage.get(clazz);
		if (collection != null) {
			collection.remove(id);
		}
	}

	@Override
	 public <E extends BasicEntity> E getByName(Class<E> clazz, String name)
	   throws StorageException {
	  System.out.println("[STORAGE] Recupération de l'entité par le nom"
	    + name + "(" + clazz.getSimpleName() + ")");
	  if (name == null) {
	   return null;
	  }

	  @SuppressWarnings("unchecked")
	  Map<String, E> collection = (Map<String, E>) storage.get(clazz);

	  if (collection == null) {
	   return null;
	  }

	  for (E e : collection.values()) {
	   if (e instanceof JavaEntity) {
	    if (name.equals(((JavaEntity) e).getName())) {
	     return e;
	    }
	   }
	  }
	  return null;
	 }

}