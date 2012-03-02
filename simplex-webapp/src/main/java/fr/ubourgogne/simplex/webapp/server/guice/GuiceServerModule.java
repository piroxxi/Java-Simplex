package fr.ubourgogne.simplex.webapp.server.guice;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import fr.ubourgogne.simplex.storage.EntityFactory;
import fr.ubourgogne.simplex.storage.EntityFactoryImpl;
import fr.ubourgogne.simplex.storage.Storage;
import fr.ubourgogne.simplex.storage.StorageImpl;

/**
 * C'est là qu'on configure GUICE pour qu'il injecte ce qu'il faut là ou il
 * faut. :)
 */
public class GuiceServerModule extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		bind(Storage.class).to(StorageImpl.class).in(Singleton.class);

	}

	@Provides
	@Inject
	public EntityFactory provideEntityFactory(Storage storage) {
		return new EntityFactoryImpl(storage);
	}
}