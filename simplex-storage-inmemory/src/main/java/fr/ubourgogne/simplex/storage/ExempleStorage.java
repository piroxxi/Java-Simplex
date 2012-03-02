package fr.ubourgogne.simplex.storage;

import fr.ubourgogne.simplex.storage.exceptions.StorageException;


public class ExempleStorage {
	public static void main(String[] args) {
		Storage storage = new StorageImpl();
		try {
			StorageFiller.fillStorage(storage, new EntityFactoryImpl(storage));
		} catch (StorageException e) {
			e.printStackTrace();
		}
	}
}
