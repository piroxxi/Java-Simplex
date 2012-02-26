package fr.ubourgogne.simplex.webapp.server.guice;

import com.google.inject.Singleton;

import fr.ubourgogne.simplex.storage.Storage;
import fr.ubourgogne.simplex.storage.StorageImpl;
import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

/**
 * C'est là qu'on configure GUICE pour qu'il injecte ce qu'il faut là ou il
 * faut. :)
 */
public class GuiceServerModule extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		 bind(Storage.class).to(StorageImpl.class).in(Singleton.class);
	}

}