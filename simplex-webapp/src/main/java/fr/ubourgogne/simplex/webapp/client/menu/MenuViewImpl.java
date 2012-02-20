package fr.ubourgogne.simplex.webapp.client.menu;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class MenuViewImpl extends Composite implements MenuView {
	@SuppressWarnings("unused")
	private Delegate delegate;

	public MenuViewImpl() {
		initWidget(new Label("menu widget"));
	}

	@Override
	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}
}
