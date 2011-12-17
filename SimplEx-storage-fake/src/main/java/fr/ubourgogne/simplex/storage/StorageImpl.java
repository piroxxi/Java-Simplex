package fr.ubourgogne.simplex.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import fr.ubourgogne.simplex.storage.exceptions.EntityIsObsoleteException;

public class StorageImpl implements Storage {

	private Map<Class<? extends BasicEntity>, Map<String, ? extends BasicEntity>> database = new HashMap<Class<? extends BasicEntity>, Map<String, ? extends BasicEntity>>();

	@Override
	public <T extends BasicEntity> void store(Class<T> clazz, T entity)
			throws EntityIsObsoleteException {
		if (clazz == null || entity == null) {
			return;
		}

		@SuppressWarnings("unchecked")
		Map<String, T> collection = (Map<String, T>) database.get(clazz);

		if (collection == null) {
			collection = new HashMap<String, T>();
			database.put(clazz, collection);
		}

		if (entity.getId() == null) {
			/*
			 * It's a new entity...
			 */
			entity.setId(UUID.randomUUID().toString());
			entity.setVersion(0);
			collection.put(entity.getId(), entity);
			return;
		}
		// else
		T existingEntity = collection.get(clazz);
		if (existingEntity != null) {
			// NB: L'égalité des numeros de version est le cas normal.
			if (existingEntity.getVersion() > entity.getVersion()) {
				throw new EntityIsObsoleteException("L'entité "
						+ entity.getId() + " est obsolète.",
						entity.getVersion(), existingEntity.getVersion());
			}

			collection.remove(entity.getId());
		}
		entity.setVersion(entity.getVersion() + 1);
		collection.put(entity.getId(), entity);
	}

	@Override
	public <T extends BasicEntity> T get(Class<T> clazz, String id) {
		if (clazz == null || id == null || id.isEmpty()) {
			return null;
		}

		@SuppressWarnings("unchecked")
		Map<String, T> collection = (Map<String, T>) database.get(clazz);

		if (collection == null) {
			return null;
		}

		return collection.get(id);
	}

	@Override
	public <T extends BasicEntity> void remove(Class<T> clazz, String id) {
		if (clazz == null || id == null || id.isEmpty()) {
			return;
		}

		@SuppressWarnings("unchecked")
		Map<String, T> collection = (Map<String, T>) database.get(clazz);

		if (collection != null) {
			collection.remove(id);
		}
	}

}
