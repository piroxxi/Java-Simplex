package fr.ubourgogne.simplex.webapp.client.activities.main;

import java.util.List;

import com.google.inject.ImplementedBy;

import fr.ubourgogne.simplex.webapp.client.utils.Resources.Favori;
import fr.ubourgogne.simplex.webapp.client.utils.view.View;

@ImplementedBy(MainViewImpl.class)
public interface MainView extends View<MainView.Delegate> {
	void setFavoris(List<Favori> favoris);

	public interface Delegate extends View.Delegate {
		void goToFavori(Favori favori);

		void createAndGoFavori(String type, String adresse);
	}
}
