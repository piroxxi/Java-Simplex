package fr.ubourgogne.simplex.webapp.client.activities.java;

import com.google.inject.ImplementedBy;

import fr.ubourgogne.simplex.model.java.JavaObject;
<<<<<<< HEAD
import fr.ubourgogne.simplex.model.java.JavaProject;
import fr.ubourgogne.simplex.webapp.client.utils.ObjectLinkDelegate;
=======
import fr.ubourgogne.simplex.webapp.client.utils.java.ObjectLinkDelegate;
>>>>>>> parent of 6d5433c... mise au gout du jour de l'affichage des classes
import fr.ubourgogne.simplex.webapp.client.utils.view.View;

@ImplementedBy(ObjectExplorateurViewImpl.class)
public interface ObjectExplorateurView extends
		View<ObjectExplorateurView.Delegate> {

	void printObject(JavaObject result);

	public interface Delegate extends View.Delegate, ObjectLinkDelegate {

	}
}
