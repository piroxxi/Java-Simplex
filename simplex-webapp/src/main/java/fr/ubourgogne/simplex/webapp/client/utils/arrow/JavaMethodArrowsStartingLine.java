package fr.ubourgogne.simplex.webapp.client.utils.arrow;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;

import fr.ubourgogne.simplex.model.java.JavaObject;
import fr.ubourgogne.simplex.model.java.entity.JavaMethod;
import fr.ubourgogne.simplex.model.java.meta.JavaObjectCommonInfos;
import fr.ubourgogne.simplex.model.java.meta.JavaReferenceObject;
import fr.ubourgogne.simplex.webapp.client.utils.java.JavaReferenceObjectPanel;

public class JavaMethodArrowsStartingLine {
	/**
	 * Widget de la ligne (utile lorsque la méthode est collapsée).
	 */
	private Widget lineWidget;

	/**
	 * Dans la ligne déclarative d'une {@link JavaMethod}, il y a plusieurs
	 * {@link JavaObject} qui sont référencés (sous forme de
	 * {@link JavaReferenceObject}).
	 * <p>
	 * Dans notre cas, on ne connait pas tout le {@link JavaObject}, on connait
	 * uniquement le {@link JavaObjectCommonInfos} de cet object.
	 * <p>
	 * Or, toutes les JavaReferenceObject contiennent la méthode.
	 */
	private List<JavaReferenceObjectPanel> widgets = new ArrayList<JavaReferenceObjectPanel>();

	/**
	 * Indique si la méthode est collapsée ou non.
	 */
	private boolean methodCollapsed;

	private String color;

	public JavaMethodArrowsStartingLine() {

	}

	/**
	 * @return the lineWidget
	 */
	public Widget getLineWidget() {
		return lineWidget;
	}

	/**
	 * @param lineWidget
	 *            the lineWidget to set
	 */
	public void setLineWidget(Widget lineWidget) {
		this.lineWidget = lineWidget;
	}

	/**
	 * @return the methodCollapsed
	 */
	public boolean isMethodCollapsed() {
		return methodCollapsed;
	}

	/**
	 * @param methodCollapsed
	 *            the methodCollapsed to set
	 */
	public void setMethodCollapsed(boolean methodCollapsed) {
		this.methodCollapsed = methodCollapsed;
	}

	/**
	 * @return the widgets
	 */
	public List<JavaReferenceObjectPanel> getWidgets() {
		return widgets;
	}

	/**
	 * @param widgets
	 *            the widgets to set
	 */
	public void setWidgets(List<JavaReferenceObjectPanel> widgets) {
		this.widgets = widgets;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
}
