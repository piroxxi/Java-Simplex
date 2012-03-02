package fr.ubourgogne.simplex.webapp.client.activities.main;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import fr.ubourgogne.simplex.webapp.client.places.LoadProjectPlace;
import fr.ubourgogne.simplex.webapp.client.places.MainPlace;
import fr.ubourgogne.simplex.webapp.client.utils.Utils;
import fr.ubourgogne.simplex.webapp.client.utils.Utils.Favori;

public class MainActivity extends AbstractActivity implements MainView.Delegate {
	private final MainView view;
	private final PlaceController placeController;

	@Inject
	public MainActivity(MainView view, @Assisted MainPlace place,
			PlaceController placeController) {
		this.view = view;
		this.placeController = placeController;
		this.view.setDelegate(this);

		// Récupération des adresses récement utilisées :
		this.view.setFavoris(getFavoris());
	}

	private List<Favori> getFavoris() {
		String favorisToken = Cookies.getCookie(Utils.FAVORIS);
		if (favorisToken == null || favorisToken.isEmpty()) {
			return null;
		}
		return Utils.deserializeFavoris(favorisToken);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);
	}

	@Override
	public void goToFavori(Favori favori) {
		placeController.goTo(new LoadProjectPlace(favori));
	}

	@Override
	public void createAndGoFavori(String type, String adresse) {
		List<Favori> favoris = getFavoris();
		if (favoris == null) {
			favoris = new ArrayList<Favori>();
		}

		Favori f = new Favori(type, adresse);
		favoris.add(f);
		Cookies.setCookie(Utils.FAVORIS,
				Utils.serializeFavoris(favoris));
		goToFavori(f);
	}
}
