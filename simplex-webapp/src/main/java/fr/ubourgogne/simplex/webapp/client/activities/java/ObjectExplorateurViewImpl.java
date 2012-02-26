package fr.ubourgogne.simplex.webapp.client.activities.java;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

import fr.ubourgogne.simplex.model.java.JavaObject;
import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.webapp.client.utils.java.JavaClassPanel;

public class ObjectExplorateurViewImpl extends Composite implements
		ObjectExplorateurView {

	private SimplePanel panel = new SimplePanel();

	public ObjectExplorateurViewImpl() {
		initWidget(panel);
	}

	@Override
	public void setDelegate(Delegate delegate) {

	}

	@Override
	public void printObject(JavaObject result) {
		if (result instanceof JavaClass) {
			panel.setWidget(new JavaClassPanel((JavaClass) result));
		} else {
			GWT.log("ObjectExplorateurViewImpl.printObject => Type non gérér",
					new Throwable("["+result.getClass()+"]"));
		}
	}

}
