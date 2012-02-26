package fr.ubourgogne.simplex.webapp.client.activities.main;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class MainViewImpl extends Composite implements MainView {

	public MainViewImpl() {
		initWidget(new Label("Bienvenu sur simplex. Ici, bientôt vous pourrez ajouter une adresse de dépôt, et vous aurez vos adresses favorites."));
	}

	@Override
	public void setDelegate(Delegate delegate) {

	}
}
