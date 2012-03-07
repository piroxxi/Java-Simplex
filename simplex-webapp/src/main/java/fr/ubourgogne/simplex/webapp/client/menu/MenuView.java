package fr.ubourgogne.simplex.webapp.client.menu;

import com.google.inject.ImplementedBy;

import fr.ubourgogne.simplex.webapp.client.utils.view.View;

@ImplementedBy(MenuViewImpl.class)
public interface MenuView extends View<MenuView.Delegate> {

	public interface Delegate extends View.Delegate {
		void goToMainPlace();
	}
}
