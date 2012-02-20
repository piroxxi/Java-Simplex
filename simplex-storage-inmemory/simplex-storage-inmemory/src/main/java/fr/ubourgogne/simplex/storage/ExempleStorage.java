package fr.ubourgogne.simplex.storage;

import fr.ubourgogne.simplex.model.java.entity.JavaMethod;
import fr.ubourgogne.simplex.storage.exceptions.StorageException;

public class ExempleStorage {

	public static void main(String[] args) {
		Storage storage = new StorageImpl();
		try {
			fillStorage(storage);
		} catch (StorageException e) {
			e.printStackTrace();
		}
	}

	public static void fillStorage(Storage storage) throws StorageException {
		System.out.println("TEST => Remplissage du storage");
		JavaMethod methode1 = new JavaMethod();
		storage.store(methode1);
	}
}
