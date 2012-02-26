package fr.ubourgogne.simplex.webapp.client.menu;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import fr.ubourgogne.simplex.webapp.client.places.MainPlace;
import fr.ubourgogne.simplex.webapp.client.places.ObjectPlace;
import fr.ubourgogne.simplex.webapp.client.rpc.ServiceProvider;

public class MenuActivity extends AbstractActivity implements MenuView.Delegate {
	private final MenuView view;
	private final PlaceController placeController;

	@Inject
	public MenuActivity(MenuView view, ServiceProvider serviceProvider,
			PlaceController placeController) {
		this.view = view;
		this.view.setDelegate(this);

		this.placeController = placeController;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);
	}

	@Override
	public void goToMainPlace() {
		this.placeController.goTo(new MainPlace());
	}

	@Override
	public void goToExemple() {
		this.placeController.goTo(new ObjectPlace("Class1"));
	}
}
