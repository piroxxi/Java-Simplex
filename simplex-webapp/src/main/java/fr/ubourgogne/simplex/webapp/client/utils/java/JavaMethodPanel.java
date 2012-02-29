package fr.ubourgogne.simplex.webapp.client.utils.java;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.thirdparty.guava.common.io.Resources;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.ubourgogne.simplex.model.java.entity.JavaMethod;

public class JavaMethodPanel extends Composite implements ClickHandler {
	public interface MyUiBinder extends UiBinder<Widget, JavaMethodPanel> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField(provided = true)
	JavaMethodDeclarationPanel methodDesc;

	@UiField
	VerticalPanel content;
	
	@UiField
	Label accoladeFermante;
	
	@UiField
	Image img;

	public JavaMethodPanel(JavaMethod method) {
		methodDesc = new JavaMethodDeclarationPanel(method);
		initWidget(uiBinder.createAndBindUi(this));
		
		for (String line : method.getLines()) {
			content.add(new Label(line));
		}
		
		if (!method.getModifiers().contains("abstract")) {
			accoladeFermante.setText("}");
		}
		
		
		img.addClickHandler(this);
		Resources res = GWT.create(Resources.class);
		//TODO
		//img.setResource(res.plusImg());
	}

	@Override
	public void onClick(ClickEvent event) {
		
	}

}
