package fr.ubourgogne.simplex.webapp.client.utils.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface View<D extends View.Delegate> extends IsWidget {
	public void setDelegate(D delegate);

	public interface Delegate {

	}
}
