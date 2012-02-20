package fr.ubourgogne.simplex.webapp.server.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import fr.ubourgogne.simplex.webapp.server.SimplexBaseServiceImpl;

/**
 * Inspired from http://code.google.com/p/google-guice/wiki/GoogleAppEngine.
 * <p>
 * The responsibility of this servlet context listener is to construct an
 * injector (achieved by the last method). Here, our injector contains two
 * modules, ratechecker.server.guice.GuiceServerModule and
 * ratechecker.server.guice.DispatchServletModule.
 * <p>
 * L'id�e est de créer toutes les servlets avec Guice.
 */
public class GuiceServletConfig extends GuiceServletContextListener {
	@Override
	protected Injector getInjector() {
		System.out.println("[SERVER] Recupération de l'injector du serveur.");
		return Guice.createInjector(new GuiceServerModule(),
		/**
		 * This module has a mapping of URIs and its serving classes. It serves
		 * "/comptapp/base" with SimplexBaseServiceImpl, which is the entry
		 * point for GWT-dispatch.
		 * <p>
		 * Si j'ai tout bien compris, toutes les requêtes tappent ici, et nous
		 * on renvoie sur "ComptappDispatchServlet", qui s'occupera de
		 * redistribuer grace à GUICE.
		 * 
		 * @author PiroXXI
		 */
		new ServletModule() {
			@Override
			protected void configureServlets() {
				super.configureServlets();
				serve("/SimplEx/base").with(SimplexBaseServiceImpl.class);
			}
		});
	}
}
