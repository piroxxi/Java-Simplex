package fr.ubourgogne.simplex.parser;

import java.io.File;
import java.util.List;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import fr.ubourgogne.simplex.loader.FileUtils;
import fr.ubourgogne.simplex.loader.git.GitLoader;

import fr.ubourgogne.simplex.storage.EntityFactory;
import fr.ubourgogne.simplex.storage.EntityFactoryImpl;
import fr.ubourgogne.simplex.storage.Storage;
import fr.ubourgogne.simplex.storage.StorageImpl;

public class Main {
	private final Provider<FileFormater> formaterProvider;

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
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
		m.start();
	}

	@Inject
	public Main(Provider<FileFormater> formaterProvider) {
		this.formaterProvider = formaterProvider;
	}
	
	public void start(){
		// FileUtils.clearLocalTemporaryDir(5 * FileUtils.MINUTE + 15
		// * FileUtils.SECONDE);

		final String url = "https://code.google.com/p/java-simplex";
		// String url = "https://java-simplex.googlecode.com/";

//		 String localURL = GitLoader.loadExternalCode(url);
		String localURL = "C:\\Users\\Fab\\AppData\\Local\\Temp\\simplex_temp\\ba2a7fe5-7c1b-4569-9d6f-2d8adc7c3fa1";

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
			System.out.println("le file " + f
					+ " est un fichier java. on le parse.");
			FileFormater ff = formaterProvider.get();
			ff.setFile(f);

			String formated = ff.format();
			FileParser fp = new FileParser(formated);

			fp.retrieveClassInfos();

		}
//		FileUtils.deleteRecursively(localRepo);
	}
}
