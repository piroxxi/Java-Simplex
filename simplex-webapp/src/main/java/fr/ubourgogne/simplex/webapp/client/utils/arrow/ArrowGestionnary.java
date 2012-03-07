package fr.ubourgogne.simplex.webapp.client.utils.arrow;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;

import fr.ubourgogne.simplex.model.java.meta.JavaObjectCommonInfos;
import fr.ubourgogne.simplex.model.java.meta.JavaReferenceObject;
import fr.ubourgogne.simplex.webapp.client.utils.arrow.utils.Point;
import fr.ubourgogne.simplex.webapp.client.utils.java.JavaReferenceObjectPanel;

public class ArrowGestionnary {
	public static class UMLItem {
		Point point;
		JavaObjectCommonInfos object;
		Label graphicalItem;
		String color;
	}

	public static final String[] colors = { "rgba(150 , 60 , 60 , 0.8)",
			"rgba(150 , 150 , 60 , 0.8)", "rgba(60 , 150 , 60 , 0.8)",
			"rgba(60 , 150 , 150 , 0.8)", "rgba(60 , 60 , 150 , 0.8)",
			"rgba(150 , 60 , 150 , 0.8)" };

	private final HTMLPanel panel;
	private ArrayList<JavaMethodArrowsStartingLine> starts = new ArrayList<JavaMethodArrowsStartingLine>();
	private HashMap<String, UMLItem> umlObjects = new HashMap<String, UMLItem>();

	private int nbUMLItem = 0;
	private int nbArrowsCrossing = 0;

	public ArrowGestionnary(HTMLPanel panel) {
		this.panel = panel;
	}

	public void PrintArrows(
			JavaMethodArrowsStartingLine methodArrowsStartingLine) {
		starts.add(methodArrowsStartingLine);

		// First, find all entities and see if they are already on the diagram.
		for (JavaReferenceObjectPanel widget : methodArrowsStartingLine
				.getWidgets()) {
			UMLItem umlItem = getOrCreateUMLItem(widget.getObject());
			HTMLPanel arrow = drawArrow(methodArrowsStartingLine, widget,
					umlItem);
			methodArrowsStartingLine.getArrows().add(arrow);
			panel.add(arrow);
		}
	}

	private UMLItem getOrCreateUMLItem(JavaReferenceObject object) {
		UMLItem item = umlObjects.get(object.getObjectId());
		if (item == null) {
			item = new UMLItem();
			// TODO(change those black magic numbers)
			item.point = new Point(950, 20 + nbUMLItem * 40);

			item.object = object.getObjectInfos();
			item.color = colors[nbUMLItem % colors.length];

			item.graphicalItem = new Label(object.getObjectName());
			item.graphicalItem.getElement().setAttribute(
					"style",
					"position: absolute; " + "top: " + (item.point.y - 5)
							+ "px; " + "left: " + item.point.x + "px; "
							+ "border: solid 1px " + item.color + "; "
							+ "border-radius: 4px; " + "padding: 3px; "
							+ "background: #DDD; z-index: 1;");

			panel.add(item.graphicalItem);
			umlObjects.put(object.getObjectId(), item);

			nbUMLItem++;
		}
		return item;
	}

	private HTMLPanel drawArrow(
			JavaMethodArrowsStartingLine methodArrowsStartingLine,
			JavaReferenceObjectPanel widget, UMLItem umlItem) {
		int offset = panel.getAbsoluteTop();

		HTMLPanel p = new HTMLPanel("");
		p.getElement().setAttribute("style",
				"position: absolute; width: 0px; height: 0px;");

		/**
		 * ..........+---(3)---+<br/>
		 * ..........|..........<br/>
		 * .........(2).........<br/>
		 * ..........|..........<br/>
		 * +---(1)---+..........<br/>
		 */
		/*
		 * La partie chargée d'afficher le code ne dois pas faire plus de 700 de
		 * large. TODO : voir plus tard si on peut pas modifier ca de maniere a
		 * avoir une largeur plus "dynamique".
		 */
		Point start = null;
		Point end = umlItem.point;
		if (methodArrowsStartingLine.isMethodCollapsed()) {
			start = new Point(
					methodArrowsStartingLine.getLineWidget().getAbsoluteLeft()
							+ methodArrowsStartingLine.getLineWidget()
									.getOffsetWidth(), methodArrowsStartingLine
							.getLineWidget().getAbsoluteTop() - offset,
					Point.HORIZONTAL);
		} else {
			// TODO(when will you stopp with f!!!ing magic numbers?)
			start = new Point(widget.getAbsoluteLeft(), widget.getAbsoluteTop()
					- offset, Point.VERTICAL);
			widget.getElement().setAttribute("style",
					"border-top: 1px solid " + umlItem.color + ";");
		}
		int horizontalWidth1 = (700 + nbArrowsCrossing * 13) - start.x;
		int horizontalWidth2 = end.x - (700 + nbArrowsCrossing * 13) + 2;
		nbArrowsCrossing++;

		// TODO(put a f!!ing button)
		String color = umlItem.color;

		int decalageStartTop = (methodArrowsStartingLine.getWidgets().size() - 1 - methodArrowsStartingLine
				.getWidgets().indexOf(widget)) * 5 + 3;

		if (start.typeFleche == Point.VERTICAL) {
			Label arrow0 = new Label();
			arrow0.getElement().setAttribute(
					"style",
					"background: " + color + "; height: " + decalageStartTop
							+ "px; width: 1px; position: absolute; top: "
							+ (start.y - decalageStartTop) + "px; left: "
							+ start.x + "px; z-index: 1;");
			p.add(arrow0);
		}

		/*
		 * 
		 */
		Label arrow1 = new Label();
		arrow1.getElement().setAttribute(
				"style",
				"background: " + color + "; height: 1px; width: "
						+ horizontalWidth1 + "px; position: absolute; top: "
						+ (start.y - decalageStartTop) + "px; left: " + start.x
						+ "px; z-index: 1;");
		p.add(arrow1);

		Label arrow2 = new Label();
		arrow2.getElement().setAttribute(
				"style",
				"background: " + color + "; width: 1px; height: "
						+ (abs(start.y - end.y) - decalageStartTop)
						+ "px; position: absolute; top: " + min(end.y, start.y)
						+ "px; left: " + (start.x + horizontalWidth1 - 1)
						+ "px; z-index: 1;");
		p.add(arrow2);

		Label arrow3 = new Label();
		arrow3.getElement().setAttribute(
				"style",
				"background: " + color + "; height: 1px; width: "
						+ horizontalWidth2 + "px; position: absolute; top: "
						+ end.y + "px; left: "
						+ (start.x + horizontalWidth1 - 1) + "px; z-index: 1;");
		p.add(arrow3);

		return p;
	}

	private int min(int a, int b) {
		return (a > b) ? b : a;
	}

	private int abs(int x) {
		if (x > 0)
			return x;
		else
			return -x;
	}

	public void refreshArrows() {
		nbArrowsCrossing = 0;
		for (JavaMethodArrowsStartingLine start : starts) {
			for (HTMLPanel p : start.getArrows()) {
				p.setVisible(false);
				panel.remove(p);
			}

			for (JavaReferenceObjectPanel widget : start.getWidgets()) {
				UMLItem umlItem = getOrCreateUMLItem(widget.getObject());
				HTMLPanel arrow = drawArrow(start, widget, umlItem);
				start.getArrows().add(arrow);
				panel.add(arrow);
			}
		}
	}
}
