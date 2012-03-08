package fr.ubourgogne.simplex.webapp.client.activities.java;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import fr.ubourgogne.simplex.model.java.JavaObject;
import fr.ubourgogne.simplex.model.java.JavaProject;
import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.webapp.client.utils.arrow.ArrowGestionnary;
import fr.ubourgogne.simplex.webapp.client.utils.java.JavaClassPanel;
import fr.ubourgogne.simplex.webapp.client.utils.java.ObjectLinkDelegate;

public class ObjectExplorateurViewImpl extends Composite implements
		ObjectExplorateurView, ObjectLinkDelegate {
	public interface MyUiBinder extends
			UiBinder<Widget, ObjectExplorateurViewImpl> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField
	SimplePanel panel;
	@UiField
	HTMLPanel arrowPanel;

	private ArrowGestionnary gestionnary;

	private Delegate delegate;

	public ObjectExplorateurViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		gestionnary = new ArrowGestionnary(arrowPanel);
	}

	@Override
	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}

	@Override
	public void printObject(JavaObject result) {
		if (result.getType() == JavaObject.CLASS) {
			panel.setWidget(new JavaClassPanel((JavaClass) result, gestionnary,
					this));
		} else {
			GWT.log("ObjectExplorateurViewImpl.printObject => Type non géré",
					new Throwable("[" + result.getClass() + "]"));
		}
	}

	@Override
	public void goToObject(int type, String name) {
		if (this.delegate != null) {
			this.delegate.goToObject(type, name);
		} else {
			GWT.log("ObjectExplorateurViewImpl.delegate == null; peut-etre avez vous oublié d'appeller setDelegate().",
					new NullPointerException(
							"ObjectExplorateurViewImpl.delegate == null"));
		}
	}

	@Override
	public void setProject(JavaProject project) {
		gestionnary.setProject(project);
	}

	@UiHandler("color")
	public void colorButton(ClickEvent event) {
		this.gestionnary.setColorOfUML(!this.gestionnary.isColorOfUML());
		this.gestionnary.refreshArrows();
	}

	@UiHandler("packages")
	public void packagesButton(ClickEvent event) {
		this.gestionnary.setOnlyProjectClasses(!this.gestionnary
				.isOnlyProjectClasses());
		this.gestionnary.refreshArrows();
	}
}
