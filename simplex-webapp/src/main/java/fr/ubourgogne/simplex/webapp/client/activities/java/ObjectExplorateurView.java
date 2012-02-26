package fr.ubourgogne.simplex.webapp.client.activities.java;

import com.google.inject.ImplementedBy;

import fr.ubourgogne.simplex.model.java.JavaObject;
import fr.ubourgogne.simplex.webapp.client.utils.view.View;

@ImplementedBy(ObjectExplorateurViewImpl.class)
public interface ObjectExplorateurView extends
		View<ObjectExplorateurView.Delegate> {

	void printObject(JavaObject result);

	public interface Delegate extends View.Delegate {

	}
}
