package fr.ubourgogne.simplex.webapp.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.ubourgogne.simplex.model.java.object.JavaClass;

public interface SimplexBaseServiceAsync {
	void welcome(String accountId, AsyncCallback<Void> callback);

	void getClassByName(String className, AsyncCallback<JavaClass> callback);

	void loadGitProject(String type, AsyncCallback<String> callback);

}
