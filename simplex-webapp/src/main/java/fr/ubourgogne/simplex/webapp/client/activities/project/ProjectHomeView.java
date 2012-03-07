package fr.ubourgogne.simplex.webapp.client.activities.project;

import com.google.inject.ImplementedBy;

import fr.ubourgogne.simplex.model.java.JavaProject;
import fr.ubourgogne.simplex.webapp.client.utils.view.View;

@ImplementedBy(ProjectHomeViewImpl.class)
public interface ProjectHomeView extends View<ProjectHomeView.Delegate> {

	void setProject(JavaProject project);

	public interface Delegate extends View.Delegate {
		
		public void goToObject(String objectId);
		
	}
}
