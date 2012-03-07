package fr.ubourgogne.simplex.webapp.client.activities.project;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import fr.ubourgogne.simplex.model.java.JavaProject;
import fr.ubourgogne.simplex.model.java.meta.JavaObjectCommonInfos;

public class ProjectHomeViewImpl extends Composite implements ProjectHomeView,
		PackageTree.Delegate {

	private Delegate delegate;

	private VerticalPanel panel;

	public ProjectHomeViewImpl() {
		panel = new VerticalPanel();
		initWidget(panel);

		panel.add(new Label("chargement du projet ..."));
	}

	@Override
	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}

	@Override
	public void setProject(JavaProject project) {
		panel.clear();
		panel.add(new PackageTree(project.getPackages(), this));
	}

	@Override
	public void onObjectSelected(JavaObjectCommonInfos object) {
		if (this.delegate != null) {
			this.delegate.goToObject(object.getObjectType(),
					object.getObjectId());
		}
	}
}
