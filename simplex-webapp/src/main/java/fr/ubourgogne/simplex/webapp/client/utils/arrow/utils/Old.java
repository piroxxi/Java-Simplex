package fr.ubourgogne.simplex.webapp.client.utils.arrow.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import fr.ubourgogne.simplex.model.java.meta.JavaReferenceObject;

public class Old {
	public static class UMLItem {
		Point point;
		JavaReferenceObject object;
	}

	public static interface ArrowRefresherCallback {
		public Point getStartCoord();
	}

	public static class ArrowStart {
		int numeroDeDepartSurLaLigne;
		int typeDepart; // 1 = depart horizontal : 2 = depart vertical
		ArrowRefresherCallback callback;

		public ArrowStart(int numeroDeDepartSurLaLigne, int typeDepart,
				ArrowRefresherCallback callback) {
			this.numeroDeDepartSurLaLigne = numeroDeDepartSurLaLigne;
			this.typeDepart = typeDepart;
			this.callback = callback;
		}
	}

	private final Map<String, List<ArrowStart>> links = new HashMap<String, List<ArrowStart>>();
	private final Map<String, UMLItem> objects = new HashMap<String, UMLItem>();

	private int z = 0;
	private final HTMLPanel panel;

	public Old(HTMLPanel panel) {
		this.panel = panel;
	}

	/**
	 * 
	 * @param arrowCallback
	 * @param object
	 */
	public void drawArrowToObject(ArrowRefresherCallback arrowCallback,
			JavaReferenceObject object, int numeroDepartSurLigne, int typeDepart) {
		// first, create the UMLItem if it doesn't exists.
		UMLItem umlItem = objects.get(object.getObjectId());
		if (umlItem == null) {
			umlItem = new UMLItem();
			umlItem.point = new Point(780, 20 + z * 150);
			z++;
			umlItem.object = object;
			objects.put(object.getObjectId(), umlItem);

			Label uml = new Label(object.getObjectName());
			uml.getElement().setAttribute(
					"style",
					"position: absolute; " + "top: " + (umlItem.point.y - 5)
							+ "px; " + "left: " + umlItem.point.x + "px; "
							+ "border: solid 1px " + colors[z % colors.length]
							+ "; " + "border-radius: 4px; " + "padding: 3px; "
							+ "background: #DDD; z-index: 1;");
			panel.add(uml);
		}

		// Creation du lien entre l'element uml (umlItem) et le debut de la
		// fleche.
		List<ArrowStart> refs = links.get(object.getObjectId());
		if (refs == null) {
			refs = new ArrayList<ArrowStart>();
			links.put(object.getObjectId(), refs);
		}
		ArrowStart arrowStart = new ArrowStart(numeroDepartSurLigne,
				typeDepart, arrowCallback);
		refs.add(arrowStart);

		panel.add(createArrowBeetween(arrowStart, umlItem.point, colors[z
				% colors.length]));
	}

	public static final String[] colors = { "rgba(80,150,80,0.6)",
			"rgba(150,80,80,0.6)", "rgba(80,80,150,0.6)" };

	private int numArrow = 700; // oulalalablague.

	private Widget createArrowBeetween(ArrowStart arrowStart, Point end,
			String color) {
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
		Point start = arrowStart.callback.getStartCoord();
		int horizontalWidth1 = numArrow - start.x;
		int horizontalWidth2 = end.x - numArrow + 2;
		numArrow = numArrow + 8;

		Label arrow1 = new Label();
		arrow1.getElement().setAttribute(
				"style",
				"background: " + color + "; height: 1px; width: "
						+ horizontalWidth1 + "px; position: absolute; top: "
						+ start.y + "px; left: " + start.x + "px; z-index: 1;");
		p.add(arrow1);

		Label arrow2 = new Label();
		arrow2.getElement().setAttribute(
				"style",
				"background: " + color + "; width: 1px; height: "
						+ abs(start.y - end.y)
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
}
