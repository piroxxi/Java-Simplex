package fr.ubourgogne.simplex.webapp.client.activities.java;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import fr.ubourgogne.simplex.model.java.JavaObject;
import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.webapp.client.utils.arrow.ArrowGestionnary;
import fr.ubourgogne.simplex.webapp.client.utils.java.JavaClassPanel;

public class ObjectExplorateurViewImpl extends Composite implements
		ObjectExplorateurView {
	public interface MyUiBinder extends UiBinder<Widget, ObjectExplorateurViewImpl> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField
	SimplePanel panel;
	@UiField
	HTMLPanel arrowPanel;

	private ArrowGestionnary gestionnary;

	public ObjectExplorateurViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		gestionnary = new ArrowGestionnary(arrowPanel);
	}

	@Override
	public void setDelegate(Delegate delegate) {

	}

	@Override
	public void printObject(JavaObject result) {
		if (result instanceof JavaClass) {
			panel.setWidget(new JavaClassPanel((JavaClass) result,gestionnary));
		} else {
			GWT.log("ObjectExplorateurViewImpl.printObject => Type non gérér",
					new Throwable("["+result.getClass()+"]"));
		}
	}

}
