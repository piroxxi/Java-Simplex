package fr.ubourgogne.simplex.webapp.client.activities.main;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import fr.ubourgogne.simplex.webapp.client.places.MainPlace;
import fr.ubourgogne.simplex.webapp.client.rpc.ServiceProvider;

public class MainActivity extends AbstractActivity implements MainView.Delegate {
	private final MainView view;

	@Inject
	public MainActivity(MainView view, @Assisted MainPlace place,
			ServiceProvider serviceProvider) {
		this.view = view;
		this.view.setDelegate(this);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);
	}
}
