package fr.ubourgogne.simplex.webapp.client.utils.java;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import fr.ubourgogne.simplex.model.java.entity.JavaReferenceObject;
import fr.ubourgogne.simplex.model.java.entity.JavaSimpleType;

public class JavaReferenceObjectPanel extends Composite {

	private HorizontalPanel panel = new HorizontalPanel();

	public JavaReferenceObjectPanel(final JavaReferenceObject object) {
		initWidget(panel);
		if(object instanceof JavaSimpleType){
			Label l = new Label("");
			l.addStyleName("java_motR");
			panel.add(l);
		}
		panel.add(new JavaClassLinkButton(object));
		if (object.getParams() != null && !object.getParams().isEmpty()) {
			panel.add(new Label("<"));
			for (int i = 0; i < object.getParams().size(); i++) {
				panel.add(new JavaParamPanel(object.getParams().get(i)));
				if (i < object.getParams().size() - 1) {
					Label l = new Label(",");
					l.addStyleName("rightPadding");
					panel.add(l);
				}
			}
			panel.add(new Label(">"));
		}
	}

}
