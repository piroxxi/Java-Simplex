package fr.ubourgogne.simplex.webapp.client.utils;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {
	@Source("plus.png")
	public ImageResource plusImg();

	@Source("moins.png")
	public ImageResource moinsImg();

	public interface Style extends CssResource {

	}
}
