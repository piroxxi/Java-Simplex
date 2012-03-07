package fr.ubourgogne.simplex.webapp.client.activities.loading;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoadingProjectViewImpl extends Composite implements
		LoadingProjectView {

	private VerticalPanel panel = new VerticalPanel();

	public LoadingProjectViewImpl() {
		// TODO(raphae) Mette en place un système de suivi de la récupération du
		// projet. (Et un logo qui tourne, histoire de faire patienter et de
		// montrere que ca travail.
		panel.add(new Label(
				"Le projet est en cours de chargement. Merci de patienter..."));
		initWidget(panel);
	}

	private Delegate delegate;

	@Override
	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}

	@Override
	public void loadingSuccesFull(String result) {
		panel.clear();
		Button arbo = new Button("Afficher l'arborecence du projet");
		arbo.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				afficherArbo(event);
			}
		});
		panel.add(arbo);

		Label l = new Label();
		l.getElement().setInnerHTML(
				SafeHtmlUtils.fromTrustedString(
						result.replaceAll("\\n", "<br />")).asString());
		panel.add(l);
	}

	private void afficherArbo(ClickEvent event) {
		if (this.delegate != null) {
			this.delegate.printArborecence();
		}
	}

}
