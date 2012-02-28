package fr.ubourgogne.simplex.webapp.client.utils.java;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import fr.ubourgogne.simplex.model.java.object.JavaClass;

public class JavaClassDeclarationPanel extends Composite {

	private HorizontalPanel panel = new HorizontalPanel();

	public JavaClassDeclarationPanel(JavaClass clazz) {
		initWidget(panel);
		if (!clazz.getModifiers().isEmpty()) {
			Label l = new Label(clazz.getModifiers() + " ");
			l.addStyleName("java_motR");
			l.addStyleName("rightPadding");
			panel.add(l);
		}
		Label name = new Label(clazz.getName());
		name.addStyleName("rightPadding");
		panel.add(name);
		if (clazz.getParams() != null && !clazz.getParams().isEmpty()) {
			panel.add(new Label("<"));
			for (int i = 0; i < clazz.getParams().size(); i++) {
				panel.add(new JavaParamPanel(clazz.getParams().get(i)));
				if (i < clazz.getParams().size() - 1) {
					panel.add(new Label(", "));
				}
			}
			Label l = new Label(">");
			l.addStyleName("rightPadding");
			panel.add(l);
		}
		if (clazz.getSuperClass() != null) {
			Label l = new Label("extends");
			l.addStyleName("java_motR");
			l.addStyleName("rightPadding");
			panel.add(l);
			panel.add(new JavaReferenceObjectPanel(clazz.getSuperClass()));
		}
		panel.add(new Label("{"));
	}

}
