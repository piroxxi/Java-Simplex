package fr.ubourgogne.simplex.webapp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import fr.ubourgogne.simplex.webapp.shared.SimplexBaseService;
import fr.ubourgogne.simplex.webapp.shared.security.SimplexSecurityException;

@SuppressWarnings("serial")
@Singleton
public class SimplexBaseServiceImpl extends RemoteServiceServlet implements
		SimplexBaseService {

	@Inject
	public SimplexBaseServiceImpl() {
		System.out.println("[SERVER] creation du SimplexBaseServiceImpl(" + this + ")");
	}

	@Override
	public void welcome(String accountId) throws SimplexSecurityException {
		System.out.println("[SERVER] you called welcome");
	}
}
