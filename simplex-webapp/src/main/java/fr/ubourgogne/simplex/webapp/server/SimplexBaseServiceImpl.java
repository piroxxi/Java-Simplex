package fr.ubourgogne.simplex.webapp.server;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import fr.ubourgogne.simplex.loader.FileUtils;
import fr.ubourgogne.simplex.loader.git.GitLoader;
import fr.ubourgogne.simplex.model.java.JavaProject;
import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.parser.FileFormater;
import fr.ubourgogne.simplex.parser.FileParser;
import fr.ubourgogne.simplex.storage.EntityFactory;
import fr.ubourgogne.simplex.storage.Storage;
import fr.ubourgogne.simplex.storage.StorageFiller;
import fr.ubourgogne.simplex.storage.exceptions.StorageException;
import fr.ubourgogne.simplex.webapp.shared.SimplexBaseService;
import fr.ubourgogne.simplex.webapp.shared.security.ImpossibleToGetGitRepository;
import fr.ubourgogne.simplex.webapp.shared.security.SimplexSecurityException;

@SuppressWarnings("serial")
@Singleton
public class SimplexBaseServiceImpl extends RemoteServiceServlet implements
		SimplexBaseService {

	private final Storage storage;
	private final Provider<FileFormater> formaterProvider;
	private static final Logger logger = LoggerFactory.getLogger(SimplexBaseService.class);

	@Inject
	public SimplexBaseServiceImpl(Storage storage, EntityFactory entityFactory,
			Provider<FileFormater> formaterProvider) {
		this.formaterProvider = formaterProvider;
		logger.info("Creation du SimplexBaseServiceImpl("
				+ this + ")");

		this.storage = storage;
		try {
			StorageFiller.fillStorage(storage, entityFactory);
		} catch (StorageException e) {
			e.printStackTrace();
		}

		// Lancement de la routine chargée de supprimer les projets
		// indésirables...
		Thread cleaner = new Thread() {
			@Override
			public void run() {
				super.run();
				while (true) {
					logger.debug("Vidage du répertoire temporaire local.");
					FileUtils.clearLocalTemporaryDir(5 * FileUtils.MINUTE + 15
							* FileUtils.SECONDE);
					try {
						Thread.sleep(FileUtils.MINUTE);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		cleaner.start();
	}

	@Override
	public void welcome(String accountId) throws SimplexSecurityException {
		logger.info("you called welcome");
		logger.debug("Du coup, on a bien toutes les classes suivantes dans le storage : ");
		List<JavaClass> classes = storage.getEntities(JavaClass.class);
		if (classes != null)
			for (JavaClass c : classes) {
				logger.debug(c.getName());
			}
	}

	@Override
	public JavaClass getJavaClass(String id) throws SimplexSecurityException {
		logger.debug("getJavaClass(" + id + ");");
		return storage.get(JavaClass.class, id);
	}

	@Override
	public String loadGitProject(String adresse)
			throws SimplexSecurityException {
		logger.info("loadGitProject(" + adresse + ") =>");

		String id = UUID.randomUUID().toString();
		JavaProject project = new JavaProject(id);
		project.setId(id);

		String localURL = GitLoader.loadExternalCode(adresse, project.getId());
		// String localURL =
		// "C:\\Users\\PiroXXI\\AppData\\Local\\Temp\\simplex_temp\\4edf0ed6-6ff4-41ff-a9be-c167e5ab5616";
		if (localURL == null || localURL.isEmpty()) {
			throw new ImpossibleToGetGitRepository(
					"Echec lors de l'import du dépot distant \""
							+ adresse
							+ "\". Veuillez verifier que ce dépôt existe et qu'il est valide.");
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

		List<File> files = FileUtils.getJavaFiles(localRepo);
		for (File f : files) {
			logger.debug("---------------------------------------------------------------------\n");
			logger.debug("le file " + f
					+ " est un fichier java. on le parse.");
			FileFormater ff = formaterProvider.get();
			ff.setFile(f);

			String formated = ff.format();
			FileParser fp = new FileParser(project, formated);

			fp.retrieveClassInfos();
		}

		storage.store(project);
		return project.getId() + ":" + res;
	}

	@Override
	public JavaProject getProject(String projectId) {
		logger.debug("getProject(" + projectId + ");");
		return storage.get(JavaProject.class, projectId);
	}
}
