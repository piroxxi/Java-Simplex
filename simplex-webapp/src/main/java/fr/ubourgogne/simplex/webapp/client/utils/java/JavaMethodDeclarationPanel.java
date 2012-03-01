package fr.ubourgogne.simplex.webapp.client.utils.java;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import fr.ubourgogne.simplex.model.java.entity.JavaMethod;

public class JavaMethodDeclarationPanel extends Composite {
	
	private HorizontalPanel panel = new HorizontalPanel();
	private JavaMethod method;

	public JavaMethodDeclarationPanel(JavaMethod method) {
		this.method = method;
		initWidget(panel);
		if (!method.getModifiers().isEmpty()) {
			Label l = new Label(method.getModifiers() + " ");
			l.addStyleName("java_motR");
			l.addStyleName("rightPadding");
			panel.add(l);
		}
		
		if (method.getParams() != null && !method.getParams().isEmpty()) {
			panel.add(new Label("<"));
			for (int i = 0; i < method.getParams().size(); i++) {
				panel.add(new JavaParamPanel(method.getParams().get(i)));
				if (i < method.getParams().size() - 1) {
					Label l = new Label(",");
					l.addStyleName("rightPadding");
					panel.add(l);
				}
			}
			Label l = new Label(">");
			l.addStyleName("rightPadding");
			panel.add(l);
		}
		
		JavaReferenceObjectPanel returnType = new JavaReferenceObjectPanel(method.getReturnType());
		returnType.addStyleName("rightPadding");
		panel.add(returnType);
		panel.add(new Label(method.getName()));
		
		panel.add(new Label("("));
		if (method.getVarParams() != null && !method.getVarParams().isEmpty()) {	
			for (int i = 0; i < method.getVarParams().size(); i++) {
				JavaReferenceObjectPanel type = new JavaReferenceObjectPanel(method.getVarParams().get(i).getType());
				type.addStyleName("rightPadding");
				panel.add(type);
				panel.add(new Label(method.getVarParams().get(i).getName()));
				
				if (i < method.getVarParams().size() - 1) {
					Label l = new Label(",");
					l.addStyleName("rightPadding");
					panel.add(l);
				}
			}			
		}
		Label l = new Label(")");
		l.addStyleName("rightPadding");
		panel.add(l);

		// je le met là, pasque dans le ui.xml, ca fais pas beau :/ (pas sur la
		// même ligne)
		if (method.getModifiers().contains("abstract")) {
			panel.add(new Label(";"));
		} else {
			panel.add(new Label("{...}"));
		}
	}

	public void setExpanded(boolean expanded) {
		if (method.getModifiers().contains("abstract"))
			return;
		
		Label l = (Label)panel.getWidget(panel.getWidgetCount()-1);
		
		if (expanded) {
			l.setText("{");
		} else {
			l.setText("{...}");
		}
	}

}
