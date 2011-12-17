package fr.ubourgogne.simplex.application.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CodePanel extends Composite {
	interface MyUiBinder extends UiBinder<Widget, CodePanel> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public CodePanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
