package fr.ubourgogne.simplex.webapp.client.activities.java;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.webapp.client.places.ObjectPlace;
import fr.ubourgogne.simplex.webapp.client.rpc.OperationCallback;
import fr.ubourgogne.simplex.webapp.client.rpc.ServiceProvider;

public class ObjectExplorateurActivity extends AbstractActivity implements
		ObjectExplorateurView.Delegate {

	private final ObjectExplorateurView view;

	@Inject
	public ObjectExplorateurActivity(ObjectExplorateurView view,
			@Assisted ObjectPlace place, ServiceProvider serviceProvider) {
		this.view = view;
		serviceProvider.getSimplexBaseService().getClassByName(place.getObjectName(),
				new OperationCallback<JavaClass>() {
					@Override
					public void onSuccess(JavaClass result) {
						ObjectExplorateurActivity.this.view.printObject(result);
					}
				});
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);
	}
}
