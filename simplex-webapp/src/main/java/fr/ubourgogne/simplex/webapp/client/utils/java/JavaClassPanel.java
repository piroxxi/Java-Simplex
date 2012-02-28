package fr.ubourgogne.simplex.webapp.client.utils.java;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.ubourgogne.simplex.model.java.JavaEntity;
import fr.ubourgogne.simplex.model.java.object.JavaClass;

public class JavaClassPanel extends Composite {
	public interface MyUiBinder extends UiBinder<Widget, JavaClassPanel> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField(provided = true)
	JavaClassDeclarationPanel clazzDesc;

	@UiField
	VerticalPanel content;
	
	public JavaClassPanel(JavaClass clazz) {
		clazzDesc = new JavaClassDeclarationPanel(clazz);
		initWidget(uiBinder.createAndBindUi(this));

		for (JavaEntity entity : clazz.getContent()) {
			if (entity instanceof JavaClass) {
				content.add(new JavaClassPanel((JavaClass) entity));
			}
		}
	}
}
