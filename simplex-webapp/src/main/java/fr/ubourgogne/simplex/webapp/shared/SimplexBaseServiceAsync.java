package fr.ubourgogne.simplex.webapp.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.ubourgogne.simplex.model.java.JavaProject;
import fr.ubourgogne.simplex.model.java.object.JavaClass;

public interface SimplexBaseServiceAsync {
	void welcome(String accountId, AsyncCallback<Void> callback);

	void getJavaClass(String classId, AsyncCallback<JavaClass> operationCallback);

	void loadGitProject(String type, AsyncCallback<String> callback);

	void getProject(String projectId, AsyncCallback<JavaProject> callback);

}
