package fr.ubourgogne.simplex.webapp.client.activities.project;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

import fr.ubourgogne.simplex.model.java.JavaObject;
import fr.ubourgogne.simplex.model.java.JavaPackage;
import fr.ubourgogne.simplex.model.java.JavaProject;

public class ProjectHomeViewImpl extends Composite implements ProjectHomeView {

	private Delegate delegate;

	private VerticalPanel panel;

	public ProjectHomeViewImpl() {
		panel = new VerticalPanel();
		initWidget(panel);
	}

	@Override
	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}

	@Override
	public void setProject(JavaProject project) {
		printPackages(project.getPackages());
	}

	private void printPackages(ArrayList<JavaPackage> packages) {
		for (JavaPackage p : packages) {
			for (final JavaObject o : p.getObjects()) {
				Button b = new Button(p.getName() + "." + o.getName());
				b.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						if (delegate != null) {
							delegate.goToObject(o.getId());
						}
					}
				});
				panel.add(b);
			}
			printPackages(p.getPackages());
		}
	}

}
