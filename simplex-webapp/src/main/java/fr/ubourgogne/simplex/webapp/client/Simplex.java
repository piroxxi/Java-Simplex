package fr.ubourgogne.simplex.webapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import fr.ubourgogne.simplex.webapp.client.injector.MyWidgetGinjector;
import fr.ubourgogne.simplex.webapp.client.menu.MenuActivity;

public class Simplex implements EntryPoint {
	/**
	 * Initialisation de l'injecteur.
	 * <p>
	 * Après on demande à se faire injecter un truc. Et tous les trucs dont ils
	 * ont besoin se font injecter à leur tour (aller voir la classe pour bien
	 * comprendre).
	 */
	private final MyWidgetGinjector injector = GWT
			.create(MyWidgetGinjector.class);

	private final SimplePanel menuPanel = new SimplePanel();

	// private final SimplePanel mainPanel = new SimplePanel();

	public void onModuleLoad() {
		/*
		 * Starting the menu.
		 */
		MenuActivity menuActivity = injector.getMenuActivity();
		RootPanel.get("menu_panel").add(menuPanel);
		menuActivity.start(menuPanel, null);

		// TODO(raphael)
	}

}
