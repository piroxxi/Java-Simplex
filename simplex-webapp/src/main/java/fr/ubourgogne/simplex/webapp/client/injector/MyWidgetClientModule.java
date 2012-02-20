package fr.ubourgogne.simplex.webapp.client.injector;

import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import fr.ubourgogne.simplex.webapp.client.activities.AppActivityMapper;
import fr.ubourgogne.simplex.webapp.client.places.AppPlaceHistoryMapper;
import fr.ubourgogne.simplex.webapp.client.places.MainPlace;

public class MyWidgetClientModule extends AbstractGinModule {
	protected void configure() {
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

		bind(PlaceHistoryMapper.class).to(AppPlaceHistoryMapper.class).in(
				Singleton.class);
		bind(ActivityMapper.class).to(AppActivityMapper.class).in(
				Singleton.class);

		install(new GinFactoryModuleBuilder()
				.build(AppActivityMapper.AssistedActivitiesFactory.class));
	}

	/**
	 * Creates a new PlaceHistoryHandler. This object is responsible handling
	 * navigation based on the browser URL. You only need one of those for the
	 * entire app.
	 * 
	 * @param placeController
	 *            the place controller.
	 * @param historyMapper
	 *            This is used to map the URL to a Place object and vice versa.
	 * @param eventBus
	 *            the event bus.
	 * @return
	 */
	@Provides
	@Singleton
	public PlaceHistoryHandler getHistoryHandler(
			PlaceController placeController, PlaceHistoryMapper historyMapper,
			EventBus eventBus) {
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				historyMapper);
		historyHandler.register(placeController,
				(com.google.web.bindery.event.shared.EventBus) eventBus,
				new MainPlace());
		return historyHandler;
	}

	/**
	 * Creates a new PlaceController. The place controller is used by the
	 * PlaceHistoryHandler and activities to tell the app to navigate to a
	 * different place. You only need one for the entire app. However, it is
	 * essential for it to get instantiated in order for the application
	 * navigation to work.
	 * 
	 * @param eventBus
	 *            the event bus.
	 * @return
	 */
	@Provides
	@Singleton
	public PlaceController getPlaceController(EventBus eventBus) {
		return new PlaceController(
				(com.google.web.bindery.event.shared.EventBus) eventBus);
	}
}
