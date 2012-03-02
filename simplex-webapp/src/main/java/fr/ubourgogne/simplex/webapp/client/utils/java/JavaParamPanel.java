package fr.ubourgogne.simplex.webapp.client.utils.java;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import fr.ubourgogne.simplex.model.java.entity.JavaParam;
import fr.ubourgogne.simplex.webapp.client.utils.arrow.JavaMethodArrowsStartingLine;

public class JavaParamPanel extends Composite {

	HorizontalPanel panel = new HorizontalPanel();

	public JavaParamPanel(JavaParam params,
			JavaMethodArrowsStartingLine methodArrowsStartingLine) {
		initWidget(panel);
		if (params.getName() != null && !params.getName().isEmpty()) {
			panel.add(new Label(params.getName()));
			if (params.getExtent() != null) {
				Label l = new Label("extends");
				l.addStyleName("java_motR");
				l.addStyleName("rightPadding");
				l.addStyleName("leftPadding");
				panel.add(l);
			}
		}
		if (params.getExtent() != null) {
			panel.add(new JavaReferenceObjectPanel(params.getExtent(),
					methodArrowsStartingLine));
		}
	}
}
