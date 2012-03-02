package fr.ubourgogne.simplex.webapp.server;

import java.io.File;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fr.ubourgogne.simplex.loader.FileUtils;
import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.storage.EntityFactory;
import fr.ubourgogne.simplex.storage.Storage;
import fr.ubourgogne.simplex.storage.StorageFiller;
import fr.ubourgogne.simplex.storage.exceptions.StorageException;
import fr.ubourgogne.simplex.webapp.shared.SimplexBaseService;
import fr.ubourgogne.simplex.webapp.shared.security.ImpossibleToGetGitRepository;
import fr.ubourgogne.simplex.webapp.shared.security.SimplexSecurityException;
import fr.ubourgogne.simplex.webapp.shared.security.StorageExceptionHappenedException;

@SuppressWarnings("serial")
@Singleton
public class SimplexBaseServiceImpl extends RemoteServiceServlet implements
		SimplexBaseService {

	private final Storage storage;

	@Inject
	public SimplexBaseServiceImpl(Storage storage, EntityFactory entityFactory) {
		System.out.println("[SERVER] creation du SimplexBaseServiceImpl("
				+ this + ")");

		this.storage = storage;
		try {
			StorageFiller.fillStorage(storage, entityFactory);
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

	@Override
	public String loadGitProject(String adresse)
			throws SimplexSecurityException {
		System.out.println("[SERVER] loadGitProject(" + adresse + ") =>");

		// String localURL = GitLoader.loadExternalCode(adresse);
		String localURL = "C:\\Users\\PiroXXI\\AppData\\Local\\Temp\\simplex_temp\\4edf0ed6-6ff4-41ff-a9be-c167e5ab5616";
		if (localURL == null || localURL.isEmpty()) {
			throw new ImpossibleToGetGitRepository(
					"Echec lors de l'import du dépot distant '"
							+ adresse
							+ "'. Veuillez verifier que ce dépôt existe et qu'il est valide.");
		}

		String res = "";
		// FIXME(raphael) ENLEVER CETTE INFORMATION.
		res += "Le code du dépôt distant " + adresse
				+ " a été téléchargé dans le dossier " + localURL + "\n";

		res += "\n********* Liste des fichiers java récupérés ***************\n\n";

		File localRepo = new File(localURL);
		List<File> list = FileUtils.getJavaFiles(localRepo);
		for (File f : list) {
			res += f.getAbsolutePath().substring(
					localRepo.getAbsolutePath().length() + 1)
					+ "\n";
		}

		res += "\n******************************************************\n";

		return res;
	}
}
