package fr.ubourgogne.simplex.webapp.client.events;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Evenement d'exemple qui sert à rien.
 */
public class ExempleEvent extends GwtEvent<ExempleEvent.Handler> {
	public interface Handler extends EventHandler {
		void onExemplEvent(ExempleEvent event);
	}

	public static final Type<ExempleEvent.Handler> TYPE = GWT
			.create(Type.class);

	public ExempleEvent() {
	}

	@Override
	public Type<ExempleEvent.Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ExempleEvent.Handler handler) {
		handler.onExemplEvent(this);
	}
}