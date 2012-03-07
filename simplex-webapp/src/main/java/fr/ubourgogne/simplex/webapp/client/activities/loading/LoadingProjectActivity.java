package fr.ubourgogne.simplex.webapp.client.activities.loading;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import fr.ubourgogne.simplex.webapp.client.places.ProjectHomePlace;
import fr.ubourgogne.simplex.webapp.client.rpc.OperationCallback;
import fr.ubourgogne.simplex.webapp.client.rpc.ServiceProvider;
import fr.ubourgogne.simplex.webapp.client.utils.Utils;
import fr.ubourgogne.simplex.webapp.client.utils.Utils.Favori;

public class LoadingProjectActivity extends AbstractActivity implements
		LoadingProjectView.Delegate {

	private final LoadingProjectView view;
	private String projectId = "";
	private final PlaceController placeController;

	@Inject
	public LoadingProjectActivity(LoadingProjectView view,
			@Assisted Favori favoriToLoad, ServiceProvider serviceProvider,
			PlaceController placeController) {
		this.view = view;
		this.view.setDelegate(this);
		this.placeController = placeController;

		if (favoriToLoad.type.equals(Utils.TYPE_GIT)) {
			serviceProvider.getSimplexBaseService().loadGitProject(
					favoriToLoad.adresse, new OperationCallback<String>() {
						@Override
						public void onSuccess(String result) {
							if (result == null || result.isEmpty()) {
								onFailure(new NullPointerException(
										"the result of loadGitProject() shouldn't be null"));
								return;
							}
							LoadingProjectActivity.this.projectId = result
									.substring(0, result.indexOf(":"));
							result = result.substring(result.indexOf(":") + 1);

							LoadingProjectActivity.this.view
									.loadingSuccesFull(result);
						}
					});
		}
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view);
	}

	@Override
	public void printArborecence() {
		placeController.goTo(new ProjectHomePlace(projectId));
	}

}
