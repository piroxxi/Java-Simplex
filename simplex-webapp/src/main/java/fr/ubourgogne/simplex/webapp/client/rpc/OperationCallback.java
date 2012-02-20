package fr.ubourgogne.simplex.webapp.client.rpc;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class OperationCallback<T> implements AsyncCallback<T> {

	private String message;

	public OperationCallback() {

	}

	public OperationCallback(String message) {
		this.message = message;
	}

	@Override
	public void onFailure(Throwable caught) {
		if (message == null) {
			Window.alert("Un erreur est survenu. Si cela vous semble pertinant, merci de bien vouloir prendre quelques secondes afin de soumettre cette erreure.  > "
					+ caught);
		} else {
			Window.alert(message + caught);
		}
		caught.printStackTrace();
	}

	@Override
	public abstract void onSuccess(T result);

}
