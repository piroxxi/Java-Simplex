package fr.ubourgogne.simplex.webapp.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SimplexBaseServiceAsync {
	void welcome(String accountId, AsyncCallback<Void> callback);

}
