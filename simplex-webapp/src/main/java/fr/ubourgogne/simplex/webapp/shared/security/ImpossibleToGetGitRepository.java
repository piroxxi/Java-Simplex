package fr.ubourgogne.simplex.webapp.shared.security;

public class ImpossibleToGetGitRepository extends SimplexSecurityException {
	private static final long serialVersionUID = 6985510308211685635L;

	public ImpossibleToGetGitRepository() {
		super();
	}

	public ImpossibleToGetGitRepository(String message) {
		super(message);
	}
}
