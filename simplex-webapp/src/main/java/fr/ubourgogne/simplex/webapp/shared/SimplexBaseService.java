package fr.ubourgogne.simplex.webapp.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.ubourgogne.simplex.model.java.JavaProject;
import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.webapp.shared.security.SimplexSecurityException;

@RemoteServiceRelativePath("base")
public interface SimplexBaseService extends RemoteService {
	void welcome(String accountId) throws SimplexSecurityException;

	/**
	 * This methode returne the following code : "projectId:logs"
	 * 
	 */
	String loadGitProject(String type) throws SimplexSecurityException;

	JavaProject getProject(String projectId) throws SimplexSecurityException;

	JavaClass getJavaClass(String classId) throws SimplexSecurityException;
}
