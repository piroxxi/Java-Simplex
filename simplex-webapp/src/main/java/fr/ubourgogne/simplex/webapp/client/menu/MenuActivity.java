package fr.ubourgogne.simplex.webapp.client.menu;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import fr.ubourgogne.simplex.webapp.client.rpc.OperationCallback;
import fr.ubourgogne.simplex.webapp.client.rpc.ServiceProvider;

public class MenuActivity extends AbstractActivity implements MenuView.Delegate {

	private final MenuView view;

	@Inject
	public MenuActivity(MenuView view, ServiceProvider serviceProvider) {
		this.view = view;
		System.out.println("[CLIENT] Creation du menu");

		serviceProvider.getSimplexBaseService().welcome("test",
				new OperationCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						System.out
								.println("[CLIENT] Retour de la fonction welcome.");
					}
				});
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);
	}
}
