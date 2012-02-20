package fr.ubourgogne.simplex.webapp.client.activities.main;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import fr.ubourgogne.simplex.webapp.client.places.MainPlace;

public class MainActivity extends AbstractActivity implements MainView.Delegate {
	private final MainView view;

	@Inject
	public MainActivity(MainView view, @Assisted MainPlace place) {
		this.view = view;
		System.out.println("Creation de l'activité principale avec la place "
				+ place);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);
	}
}
