package fr.ubourgogne.simplex.webapp.client.utils.java;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import fr.ubourgogne.simplex.model.java.entity.JavaReferenceObject;

public class JavaClassLinkButton extends Composite {
	public interface MyUiBinder extends UiBinder<Widget, JavaClassLinkButton> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public static final int DELAY = 700; 
	
	@UiField
	Button link;
	@UiField
	Label javadoc;

	private final JavaReferenceObject object;
	private boolean hover = false;

	public JavaClassLinkButton(JavaReferenceObject object) {
		this.object = object;
		initWidget(uiBinder.createAndBindUi(this));
		this.link.setText(object.getObjectName());
		this.javadoc.setText(object.getObjectJavaDoc());
	}

	@UiHandler("link")
	public void onClick(ClickEvent event) {
		System.out.println("qsf => " + object);
	}

	@UiHandler({"link", "javadoc"})
	public void onMouseOver(MouseOverEvent event) {
		hover = true;
		Timer t = new Timer() {
			@Override
			public void run() {
				if(hover){
					javadoc.getElement().setAttribute("style", "display: block;");
				}
			}
		};
		t.schedule(DELAY);
	}
	
	@UiHandler({"link", "javadoc"})
	public void onMouseOut(MouseOutEvent event) {
		hover = false;
		Timer t = new Timer() {
			@Override
			public void run() {
				if(!hover){
					javadoc.getElement().setAttribute("style", "display: none;");
				}
			}
		};
		t.schedule(200);
	}
}
