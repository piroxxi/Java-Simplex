package fr.ubourgogne.simplex.webapp.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.ubourgogne.simplex.webapp.shared.security.SimplexSecurityException;

@RemoteServiceRelativePath("base")
public interface SimplexBaseService extends RemoteService {
	void welcome(String accountId) throws SimplexSecurityException;
	
}
