package fr.ubourgogne.simplex.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import fr.ubourgogne.simplex.loader.FileUtils;
import fr.ubourgogne.simplex.model.java.JavaPackage;
import fr.ubourgogne.simplex.model.java.JavaProject;
import fr.ubourgogne.simplex.model.java.meta.JavaObjectCommonInfos;
import fr.ubourgogne.simplex.storage.EntityFactory;
import fr.ubourgogne.simplex.storage.EntityFactoryImpl;
import fr.ubourgogne.simplex.storage.Storage;
import fr.ubourgogne.simplex.storage.StorageImpl;

public class Main {
	private final Provider<FileFormater> formaterProvider;

	public static void main(String[] args) {
		System.out.println("lancement de parser.Main()");
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				requestStaticInjection(BlocParser.class);
				requestStaticInjection(InlineParser.class);

				bind(Storage.class).to(StorageImpl.class).in(Singleton.class);
			}

			@SuppressWarnings("unused")
			@Provides
			@Inject
			public EntityFactory provideEntityFactory(Storage storage) {
				return new EntityFactoryImpl(storage);
			}
		});

		Main m = injector.getInstance(Main.class);
		// m.test();
		m.start();
	}

	@Inject
	public Main(Provider<FileFormater> formaterProvider) {
		this.formaterProvider = formaterProvider;
	}

	public void test() {
		File testClass = new File("sample/sampleclass.java");
		FileFormater ff = formaterProvider.get();
		ff.setFile(testClass);

		String formated = ff.format();
		JavaProject p = new JavaProject("sample");
		FileParser fp = new FileParser(p, formated);

		fp.retrieveClassInfos();
	}

	public void start() {
		// FileUtils.clearLocalTemporaryDir(5 * FileUtils.MINUTE + 15
		// * FileUtils.SECONDE);

		final String id = UUID.randomUUID().toString();
		final String url = "https://code.google.com/p/java-simplex";

		JavaProject project = new JavaProject(id);

		// String url = "https://java-simplex.googlecode.com/";

		// String localURL = GitLoader.loadExternalCode(url, id);
		// String localURL =
		// "C:\\Users\\Fab\\AppData\\Local\\Temp\\simplex_temp\\b3e786ad-d78e-4629-b0d7-cc8c5c38b746";
		String localURL = "C:\\Users\\PiroXXI\\AppData\\Local\\Temp\\simplex_temp\\1f570c63-a402-4a4f-be1b-d57d5326fe43";
		if (localURL == null || localURL.isEmpty()) {
			System.out
					.println("Echec lors de l'import du dépot distant '"
							+ url
							+ "'. Veuillez verifier que ce dépôt existe et qu'il est valide.");
		}

		System.out.println("Le code du dépôt distant " + url
				+ " a été téléchargé dans le dossier " + localURL);

		File localRepo = new File(localURL);
		FileUtils.printDirectoryTree(localRepo);

		/*
		 * Do your stuff.
		 */
		List<File> files = FileUtils.getJavaFiles(localRepo);
		for (File f : files) {
			System.out
					.println("---------------------------------------------------------------------\n");
			System.out.println("le file " + f
					+ " est un fichier java. on le parse.");
			FileFormater ff = formaterProvider.get();
			ff.setFile(f);

			String formated = ff.format();
			FileParser fp = new FileParser(project, formated);

			fp.retrieveClassInfos();

			// TODO
			// projet.add(fp.getRepresentedObject());
		}

		// FileUtils.deleteRecursively(localRepo);

		System.out.println("\n\n\n\n\n-- affichage des packages --");
		printPackages(project.getPackages(), "");
	}

	private void printPackages(ArrayList<JavaPackage> packages, String entete) {
		for (JavaPackage p : packages) {
			System.out.println(entete
					+ ((p.getName().indexOf(".") != -1) ? p.getName()
							.substring(p.getName().lastIndexOf(".") + 1) : p
							.getName()));
			for (JavaObjectCommonInfos o : p.getObjects()) {
				System.out.println(entete + " | " + o.getObjectName() + " ("
						+ o.getObjectId() + ")");
			}
			printPackages(p.getPackages(), entete + " ");
		}
	}
}
