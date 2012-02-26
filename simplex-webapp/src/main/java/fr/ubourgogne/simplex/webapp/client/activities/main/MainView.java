package fr.ubourgogne.simplex.webapp.client.activities.main;

import com.google.inject.ImplementedBy;

import fr.ubourgogne.simplex.webapp.client.utils.view.View;

@ImplementedBy(MainViewImpl.class)
public interface MainView extends View<MainView.Delegate> {

	public interface Delegate extends View.Delegate {

	}
}
