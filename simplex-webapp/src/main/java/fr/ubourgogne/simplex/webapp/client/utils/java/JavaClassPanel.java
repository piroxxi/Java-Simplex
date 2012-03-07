package fr.ubourgogne.simplex.webapp.client.utils.java;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.ubourgogne.simplex.model.java.JavaEntity;
import fr.ubourgogne.simplex.model.java.entity.JavaMethod;
import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.webapp.client.utils.arrow.ArrowGestionnary;
import fr.ubourgogne.simplex.webapp.client.utils.arrow.JavaMethodArrowsStartingLine;

public class JavaClassPanel extends Composite {
	public interface MyUiBinder extends UiBinder<Widget, JavaClassPanel> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField
	HorizontalPanel pckageP;
	@UiField
	Label pckage;
	@UiField
	VerticalPanel imports;
	@UiField(provided = true)
	JavaClassDeclarationPanel clazzDesc;

	@UiField
	VerticalPanel content;

	private JavaMethodArrowsStartingLine methodArrowsStartingLine;

	public JavaClassPanel(JavaClass clazz, ArrowGestionnary gestionnary,
			ObjectLinkDelegate delegate) {
		methodArrowsStartingLine = new JavaMethodArrowsStartingLine();
		clazzDesc = new JavaClassDeclarationPanel(clazz,
				methodArrowsStartingLine, delegate);

		initWidget(uiBinder.createAndBindUi(this));

		if (clazz.getPackage() != null && !clazz.getPackage().isEmpty()) {
			pckage.setText(clazz.getPackage());
			pckage.addStyleName("bottomPadding");
		} else {
			pckageP.setVisible(false);
		}

		if (clazz.getImports() != null && !clazz.getImports().isEmpty()) {
			for (int i = 0; i < clazz.getImports().size(); i++) {
				HorizontalPanel p = new HorizontalPanel();
				Label imp = new Label("import");
				imp.addStyleName("rightPadding");
				imp.addStyleName("java_motR");
				p.add(imp);
				Label l = new Label(clazz.getImports().get(i) + ";");
				p.add(l);
				imports.add(p);
				if (i == (clazz.getImports().size() - 1)) {
					l.addStyleName("bottomPadding");
				}
			}
		}

		for (JavaEntity entity : clazz.getContent()) {
			if (entity instanceof JavaClass) {
				content.add(new JavaClassPanel((JavaClass) entity, gestionnary,
						delegate));
			}
			if (entity instanceof JavaMethod) {
				content.add(new JavaMethodPanel((JavaMethod) entity,
						gestionnary, delegate));
			}
		}

		// Si jamais on veut afficher La javadoc lors de la déclaration d'une
		// classe, il faut décommenter les lignes suivante :

		// Timer t = new Timer() {
		// @Override
		// public void run() {
		// JavaMethodPanel.this.methodArrowsStartingLine.refreshGraph();
		// }
		// };
		// t.schedule(150);
	}
}
