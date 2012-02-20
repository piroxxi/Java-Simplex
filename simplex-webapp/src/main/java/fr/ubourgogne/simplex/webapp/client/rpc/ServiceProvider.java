package fr.ubourgogne.simplex.webapp.client.rpc;

import com.google.gwt.core.client.GWT;

import fr.ubourgogne.simplex.webapp.shared.SimplexBaseService;
import fr.ubourgogne.simplex.webapp.shared.SimplexBaseServiceAsync;

//@Singleton
/**
 * Classe chargée d'instancier l'ensemble des services, afin que tout le monde puisse en disposer
 * en se faisant injecter cette classe. C'est la meilleur (seule?) manière d'avoir tous les services
 * en Singleton.
 */
public class ServiceProvider {
	private final SimplexBaseServiceAsync simplexBaseService = GWT
			.create(SimplexBaseService.class);

	public SimplexBaseServiceAsync getSimplexBaseService() {
		return simplexBaseService;
	}
}
