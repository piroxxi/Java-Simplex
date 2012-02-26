package fr.ubourgogne.simplex.webapp.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.storage.Storage;
import fr.ubourgogne.simplex.storage.StorageFiller;
import fr.ubourgogne.simplex.storage.exceptions.StorageException;
import fr.ubourgogne.simplex.webapp.shared.SimplexBaseService;
import fr.ubourgogne.simplex.webapp.shared.security.SimplexSecurityException;
import fr.ubourgogne.simplex.webapp.shared.security.StorageExceptionHappenedException;

@SuppressWarnings("serial")
@Singleton
public class SimplexBaseServiceImpl extends RemoteServiceServlet implements
		SimplexBaseService {

	private final Storage storage;

	@Inject
	public SimplexBaseServiceImpl(Storage storage) {
		System.out.println("[SERVER] creation du SimplexBaseServiceImpl("
				+ this + ")");

		this.storage = storage;
		try {
			StorageFiller.fillStorage(storage);
		} catch (StorageException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void welcome(String accountId) throws SimplexSecurityException {
		System.out.println("[SERVER] you called welcome");
		System.out
				.println("Du coup, on a bien toutes les classes suivantes dans le storage : ");
		List<JavaClass> classes = storage.getEntities(JavaClass.class);
		if (classes != null)
			for (JavaClass c : classes) {
				System.out.println(c.getName());
			}
	}

	@Override
	public JavaClass getClassByName(String className)
			throws SimplexSecurityException {
		System.out.println("[SERVER] getClassByName(" + className + ");");
		try {
			return storage.getByName(JavaClass.class, className);
		} catch (StorageException e) {
			throw new StorageExceptionHappenedException(e);
		}
	}
}
