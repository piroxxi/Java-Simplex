package fr.ubourgogne.simplex.webapp.client.utils.java;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.ubourgogne.simplex.model.java.JavaEntity;
import fr.ubourgogne.simplex.model.java.entity.JavaParam;
import fr.ubourgogne.simplex.model.java.object.JavaClass;

public class JavaClassPanel extends Composite {
	public interface MyUiBinder extends UiBinder<Widget, JavaClassPanel> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField
	Label clazzDesc;
	@UiField
	VerticalPanel content;

	public JavaClassPanel(JavaClass clazz) {
		initWidget(uiBinder.createAndBindUi(this));
		String clazzDescS = clazz.getModifiers() + " class " + clazz.getName();
		if (!clazz.getParams().isEmpty()) {
			clazzDescS += "<";
			for (int i = 0; i < clazz.getParams().size(); i++) {
				JavaParam c = clazz.getParams().get(i);
				if (c.getName() != null) {
					clazzDescS += c.getName();
					if (c.getExtent() != null) {
						clazzDescS += " extends ";
					}
				}
				if (c.getExtent() != null) {
					clazzDescS += c.getExtent().print();
				}

			}
			clazzDescS += ">";
		}
		clazzDescS = LigneCodeRenderer.render(clazzDescS);
		clazzDesc.getElement().setInnerHTML(clazzDescS);
		for (JavaEntity entity : clazz.getContent()) {
			if (entity instanceof JavaClass) {
				content.add(new JavaClassPanel((JavaClass) entity));
			}
		}
	}

	@UiHandler("clazzDesc")
	public void onClassClick(ClickEvent event) {
		event.getNativeEvent().getEventTarget();
	}
}
