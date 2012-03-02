package fr.ubourgogne.simplex.webapp.client.activities.loading;

import com.google.inject.ImplementedBy;

import fr.ubourgogne.simplex.webapp.client.utils.view.View;

@ImplementedBy(LoadingProjectViewImpl.class)
public interface LoadingProjectView extends View<LoadingProjectView.Delegate> {
	public interface Delegate extends View.Delegate {
		void printArborecence();
	}

	void loadingSuccesFull(String result);
}