package fr.ubourgogne.simplex.webapp.client.activities.loading;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import fr.ubourgogne.simplex.webapp.client.rpc.OperationCallback;
import fr.ubourgogne.simplex.webapp.client.rpc.ServiceProvider;
import fr.ubourgogne.simplex.webapp.client.utils.Utils;
import fr.ubourgogne.simplex.webapp.client.utils.Utils.Favori;

public class LoadingProjectActivity extends AbstractActivity implements
		LoadingProjectView.Delegate {

	private final LoadingProjectView view;

	@Inject
	public LoadingProjectActivity(LoadingProjectView view,
			@Assisted Favori favoriToLoad, ServiceProvider serviceProvider) {
		this.view = view;

		if (favoriToLoad.type.equals(Utils.TYPE_GIT)) {
			serviceProvider.getSimplexBaseService().loadGitProject(
					favoriToLoad.adresse, new OperationCallback<String>() {
						@Override
						public void onSuccess(String result) {
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
		// TODO Auto-generated method stub
		
	}

}
