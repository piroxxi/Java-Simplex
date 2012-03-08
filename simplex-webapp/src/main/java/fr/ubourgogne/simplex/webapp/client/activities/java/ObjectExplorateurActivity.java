package fr.ubourgogne.simplex.webapp.client.activities.java;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import fr.ubourgogne.simplex.model.java.JavaObject;
import fr.ubourgogne.simplex.model.java.JavaProject;
import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.webapp.client.places.ObjectPlace;
import fr.ubourgogne.simplex.webapp.client.rpc.OperationCallback;
import fr.ubourgogne.simplex.webapp.client.rpc.ServiceProvider;
import fr.ubourgogne.simplex.webapp.client.utils.LocalProjectCache;
import fr.ubourgogne.simplex.webapp.client.utils.LocalProjectCache.CacheCallback;

public class ObjectExplorateurActivity extends AbstractActivity implements
		ObjectExplorateurView.Delegate {

	private final ObjectExplorateurView view;
	private final PlaceController placeController;
	private final ObjectPlace place;

	@Inject
	public ObjectExplorateurActivity(ObjectExplorateurView view,
			@Assisted ObjectPlace place, ServiceProvider serviceProvider,
			PlaceController placeController, LocalProjectCache projectCache) {
		this.view = view;
		this.view.setDelegate(this);
		this.place = place;
		this.placeController = placeController;

		if (place.getObjectType() == JavaObject.CLASS) {
			serviceProvider.getSimplexBaseService().getJavaClass(
					place.getObjectId(), new OperationCallback<JavaClass>() {
						@Override
						public void onSuccess(JavaClass result) {
							printObject(result);
						}
					});
		} else {
			Window.alert("ce genre de cas n'est pas encore géré :/");
		}

		// TODO les autres IF...

		projectCache.getProjectFromCache(place.getProjectId(),
				new CacheCallback() {
					@Override
					public void onProjectFound(JavaProject project) {
						if (project == null) {
							Window.alert("Erreur! Le project "
									+ ObjectExplorateurActivity.this.place
											.getProjectId()
									+ " n'existe pas coté serveur");
							return;
						}
						ObjectExplorateurActivity.this.view.setProject(project);
					}
				});
	}

	private void printObject(JavaObject object) {
		if (object == null) {
			Window.alert("Suite à une erreure contraire à notre volontée, le code source de cette entité ne peut etre affiché");
			return;
		}
		ObjectExplorateurActivity.this.view.printObject(object);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);
	}

	@Override
	public void goToObject(int type, String name) {
		placeController.goTo(new ObjectPlace(place.getProjectId(), type, name));
	}
}
