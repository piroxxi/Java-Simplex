package fr.ubourgogne.simplex.webapp.client.activities.project;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import fr.ubourgogne.simplex.model.java.JavaProject;
import fr.ubourgogne.simplex.webapp.client.places.ObjectPlace;
import fr.ubourgogne.simplex.webapp.client.places.ProjectHomePlace;
import fr.ubourgogne.simplex.webapp.client.utils.LocalProjectCache;
import fr.ubourgogne.simplex.webapp.client.utils.LocalProjectCache.CacheCallback;

public class ProjectHomeActivity extends AbstractActivity implements
		ProjectHomeView.Delegate {

	private final ProjectHomeView view;
	private final String projectId;
	private final PlaceController placeController;

	@Inject
	public ProjectHomeActivity(ProjectHomeView view,
			@Assisted ProjectHomePlace place, LocalProjectCache projectCache,
			PlaceController placeController) {
		this.placeController = placeController;
		this.projectId = place.getProjectId();
		this.view = view;
		this.view.setDelegate(this);

		projectCache.getProjectFromCache(place.getProjectId(),
				new CacheCallback() {
					@Override
					public void onProjectFound(JavaProject project) {
						if (project == null) {
							Window.alert("Erreur! Le project " + projectId
									+ " n'existe pas coté serveur");
							return;
						}
						ProjectHomeActivity.this.view.setProject(project);
					}
				});
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);
	}

	@Override
	public void goToObject(int objectType, String objectId) {
		placeController.goTo(new ObjectPlace(projectId, objectType, objectId));
	}
}
