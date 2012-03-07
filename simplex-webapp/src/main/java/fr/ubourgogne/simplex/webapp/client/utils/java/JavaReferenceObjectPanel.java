package fr.ubourgogne.simplex.webapp.client.utils.java;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import fr.ubourgogne.simplex.model.java.entity.JavaSimpleType;
import fr.ubourgogne.simplex.model.java.meta.JavaReferenceObject;
import fr.ubourgogne.simplex.webapp.client.utils.arrow.JavaMethodArrowsStartingLine;

public class JavaReferenceObjectPanel extends Composite {

	private HorizontalPanel panel = new HorizontalPanel();
	private final JavaReferenceObject object;

	public JavaReferenceObjectPanel(final JavaReferenceObject object,
			JavaMethodArrowsStartingLine methodArrowsStartingLine, ObjectLinkDelegate delegate) {
		this.object = object;
		initWidget(panel);
		if (object instanceof JavaSimpleType) {
			Label l = new Label(object.getObjectName());
			l.addStyleName("java_motR");
			panel.add(l);
		} else {
			if (object != null) {
				methodArrowsStartingLine.getWidgets().add(this);
				
				panel.add(new JavaClassLinkButton(object,delegate));
				if (object.getParams() != null && !object.getParams().isEmpty()) {
					panel.add(new Label("<"));
					for (int i = 0; i < object.getParams().size(); i++) {
						panel.add(new JavaParamPanel(object.getParams().get(i),
								methodArrowsStartingLine, delegate));
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
	}

	/**
	 * @return the object
	 */
	public JavaReferenceObject getObject() {
		return object;
	}

}
