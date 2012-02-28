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
		panel.add(name);
		
		if (clazz.getParams() != null && !clazz.getParams().isEmpty()) {
			panel.add(new Label("<"));
			for (int i = 0; i < clazz.getParams().size(); i++) {
				panel.add(new JavaParamPanel(clazz.getParams().get(i)));
				if (i < clazz.getParams().size() - 1) {
					Label l = new Label(",");
					l.addStyleName("rightPadding");
					panel.add(l);
				}
			}
			Label l = new Label(">");
			l.addStyleName("rightPadding");
			panel.add(l);
		} else {
			name.addStyleName("rightPadding");
		}
		
		if (clazz.getSuperClass() != null) {
			Label l = new Label("extends");
			l.addStyleName("java_motR");
			l.addStyleName("rightPadding");
			panel.add(l);
			JavaReferenceObjectPanel q = new JavaReferenceObjectPanel(
					clazz.getSuperClass());
			panel.add(q);
			if (clazz.getImplementedInterfaces() == null
					|| clazz.getImplementedInterfaces().isEmpty()) {
				q.addStyleName("rightPadding");
			}
		}
		if (clazz.getImplementedInterfaces() != null
				&& !clazz.getImplementedInterfaces().isEmpty()) {
			Label l = new Label("implements");
			l.addStyleName("java_motR");
			l.addStyleName("rightPadding");
			if (clazz.getSuperClass() != null) {
				l.addStyleName("leftPadding");
			}
			panel.add(l);
			for (int i = 0; i < clazz.getImplementedInterfaces().size(); i++) {
				JavaReferenceObjectPanel q = new JavaReferenceObjectPanel(clazz
						.getImplementedInterfaces().get(i));
				panel.add(q);
				if (i < clazz.getImplementedInterfaces().size() - 1) {
					Label ll = new Label(",");
					ll.addStyleName("rightPadding");
					panel.add(ll);
				} else {
					q.addStyleName("rightPadding");
				}
			}
		}
		// je le met là, pasque dans le ui.xml, ca fais pas beau :/ (pas sur la
		// même ligne)
		panel.add(new Label("{"));
	}
}
