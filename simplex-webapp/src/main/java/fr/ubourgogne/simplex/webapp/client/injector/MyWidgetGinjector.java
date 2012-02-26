package fr.ubourgogne.simplex.webapp.client.injector;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceHistoryHandler;

import fr.ubourgogne.simplex.webapp.client.menu.MenuActivity;
import fr.ubourgogne.simplex.webapp.client.utils.MainPanel;

/**
 * Voici la "Factory" que l'on souhaite obtenir. Toutes les entitées que l'on
 * obtient lorsque l'on appel une méthode sont injectées, avec toutes leures
 * dépendances de manière transitive.
 * <p>
 * Ainsi, l'appel à l'une de ces méthodes lancera une "chaine" d'injection...
 * <p>
 * De plus, ce "module" d'injection (puisque c'est comme ca qu'on le nomme) est
 * configuré grace à la classe MyWidgetClientModule.
 */
@GinModules(MyWidgetClientModule.class)
public interface MyWidgetGinjector extends Ginjector {
	PlaceHistoryHandler getPlaceHistoryHandler();

	MenuActivity getMenuActivity();

	/**
	 * Renvoie le paneau principal, avec le gestionnaire d'activité associé.
	 */
	MainPanel getMainPanel();
}