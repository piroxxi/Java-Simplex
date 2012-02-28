package fr.ubourgogne.simplex.webapp.client.utils.java;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

import fr.ubourgogne.simplex.model.java.entity.JavaReferenceObject;

public class JavaReferenceObjectPanel extends Composite {

	private HorizontalPanel panel = new HorizontalPanel();
	
	public JavaReferenceObjectPanel(final JavaReferenceObject object) {
		initWidget(panel);
		JavaClassLinkButton link = new JavaClassLinkButton(object);
		panel.add(link);
	}

}
