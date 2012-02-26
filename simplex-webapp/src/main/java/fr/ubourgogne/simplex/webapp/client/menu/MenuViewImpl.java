package fr.ubourgogne.simplex.webapp.client.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MenuViewImpl extends Composite implements MenuView {
	public interface MyUiBinder extends UiBinder<Widget, MenuViewImpl> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private Delegate delegate;

	public MenuViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}

	@UiHandler("main")
	public void onMainClick(ClickEvent event) {
		if (this.delegate != null) {
			this.delegate.goToMainPlace();
		} else {
			GWT.log("MenuViewImpl.delegate == null; peut-etre avez vous oublié d'appeller setDelegate().",
					new NullPointerException("MenuViewImpl.delegate == null"));
		}
	}
	
	@UiHandler("exemple")
	public void onExempleClick(ClickEvent event) {
		if (this.delegate != null) {
			this.delegate.goToExemple();
		} else {
			GWT.log("MenuViewImpl.delegate == null; peut-etre avez vous oublié d'appeller setDelegate().",
					new NullPointerException("MenuViewImpl.delegate == null"));
		}
	}
}
