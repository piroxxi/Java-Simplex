package fr.ubourgogne.simplex.webapp.client.utils.arrow.utils;

public class Point {
	public static final int UNDEFINED = -1;
	public static final int VERTICAL = 0;
	public static final int HORIZONTAL = 1;

	public int x;
	public int y;

	public int typeFleche = UNDEFINED;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(int x, int y, int typeFleche) {
		this.x = x;
		this.y = y;
		this.typeFleche = typeFleche;
	}
}