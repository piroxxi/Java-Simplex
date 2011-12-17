package fr.ubourgogne.simplex.application.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SimplEx implements EntryPoint {

	private SimplePanel left = new SimplePanel();
	private SimplePanel right = new SimplePanel();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel.get("gwt-left-panel").add(left);
		left.add(new CodePanel());

		RootPanel.get("gwt-right-panel").add(right);
		right.add(new CodePanel());
	}
}
