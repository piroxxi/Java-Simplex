package fr.ubourgogne.simplex.webapp.client.activities.main;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.ubourgogne.simplex.webapp.client.utils.Resources;
import fr.ubourgogne.simplex.webapp.client.utils.Resources.Favori;

public class MainViewImpl extends Composite implements MainView {
	public interface MyUiBinder extends UiBinder<Widget, MainViewImpl> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyStyle extends CssResource {
		String note();

		String button();
	}

	@UiField
	MyStyle style;

	@UiField
	VerticalPanel oldDepots;

	@UiField
	RadioButton depotGit;

	@UiField
	RadioButton depotSvn;

	@UiField
	TextBox adresse;

	private Delegate delegate;

	public MainViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}

	@Override
	public void setFavoris(List<Favori> favoris) {
		if (favoris == null || favoris.isEmpty()) {
			Label l = new Label("aucun dépot utilisé récément");
			l.addStyleName(style.note());
			oldDepots.add(l);
			return;
		}

		for (final Favori favori : favoris) {
			Button button = new Button(favori.adresse);
			button.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					goToFavori(favori);
				}
			});
			button.addStyleName(style.button());
			oldDepots.add(button);
		}
	}

	@UiHandler("go")
	public void goToRepository(ClickEvent event) {
		if (this.delegate != null && adresse.getValue() != null
				&& !adresse.getValue().isEmpty()) {
			if (depotGit.getValue()) {
				this.delegate.createAndGoFavori(Resources.TYPE_GIT,
						adresse.getValue());
				return;
			}

			if (depotSvn.getValue()) {
				this.delegate.createAndGoFavori(Resources.TYPE_SVN,
						adresse.getValue());
				return;
			}
		}
	}

	@UiHandler("ourFavori")
	public void goToOurFavori(ClickEvent event) {
		goToFavori(new Favori(Resources.TYPE_GIT,
				"https://code.google.com/p/java-simplex/"));
	}

	private void goToFavori(Favori favori) {
		if (this.delegate != null) {
			this.delegate.goToFavori(favori);
		}
	}
}
