package fr.ubourgogne.simplex.webapp.client.utils.java;

import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import fr.ubourgogne.simplex.model.java.entity.JavaParam;

public class JavaParamPanel extends Composite {

	HorizontalPanel panel = new HorizontalPanel();

	public JavaParamPanel() {
		initWidget(panel);
	}

	public void setParams(List<JavaParam> params) {
		if (params.isEmpty())
			return;
		
		panel.add(new Label("<"));

		for (int i = 0; i < params.size(); i++) {
			Label l = new Label();
			l.getElement().setInnerHTML(
					LigneCodeRenderer.render(params.get(i).print("")));
			panel.add(l);
		}

		panel.add(new Label(">"));
	}

}
