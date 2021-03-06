package fr.ubourgogne.simplex.webapp.client.utils.java;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.ubourgogne.simplex.model.java.entity.JavaMethod;
import fr.ubourgogne.simplex.webapp.client.utils.ObjectLinkDelegate;
import fr.ubourgogne.simplex.webapp.client.utils.Resources;
import fr.ubourgogne.simplex.webapp.client.utils.arrow.ArrowGestionnary;
import fr.ubourgogne.simplex.webapp.client.utils.arrow.JavaMethodArrowsStartingLine;

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

	boolean isExpanded = true;
	private JavaMethodArrowsStartingLine methodArrowsStartingLine;

	Resources res = GWT.create(Resources.class);
	JavaMethod method;

	private final ArrowGestionnary gestionnary;

	public JavaMethodPanel(final JavaMethod method,
			final ArrowGestionnary gestionnary, ObjectLinkDelegate delegate) {
		this.gestionnary = gestionnary;
		this.methodArrowsStartingLine = new JavaMethodArrowsStartingLine();
		this.method = method;

		methodDesc = new JavaMethodDeclarationPanel(method,
				methodArrowsStartingLine, delegate);
		initWidget(uiBinder.createAndBindUi(this));

		for (String line : method.getLines()) {
			content.add(new Label(line));
		}

		if (!method.getModifiers().contains("abstract")) {
			img.addClickHandler(this);
			img.setUrl(res.plusImg().getSafeUri());
			img.addStyleName(".plus");
		} else {
			img.setVisible(false);
		}

		Timer t = new Timer() {
			@Override
			public void run() {
				gestionnary.PrintArrows(methodArrowsStartingLine);
			}
		};
		t.schedule(150);
		onClick(null);
	}

	/*
	 * L'affichage doit se faire pour chaque JavaReferenceObject. et non pas au
	 * niveau de la méthode. Sauf si celle ci est dépliée.
	 */

	@Override
	public void onClick(ClickEvent event) {
		isExpanded = !isExpanded;
		if (!isExpanded) {
			img.setUrl(res.plusImg().getSafeUri());
			content.setVisible(false);
			accoladeFermante.setText("");
			methodDesc.setExpanded(isExpanded);
		} else {
			img.setUrl(res.moinsImg().getSafeUri());
			content.setVisible(true);
			accoladeFermante.setText("}");
			methodDesc.setExpanded(isExpanded);
		}
		this.methodArrowsStartingLine.setMethodCollapsed(!isExpanded);
		this.gestionnary.refreshArrows();
	}
}
