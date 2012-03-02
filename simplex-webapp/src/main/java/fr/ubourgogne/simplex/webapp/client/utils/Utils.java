package fr.ubourgogne.simplex.webapp.client.utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
	/**
	 * <p>
	 * A la suite d'un très mauvais merge, le code suivant c'est perdu... a
	 * modifier dès que possible.
	 * <p>
	 */

	public static final String TYPE_GIT = "git";
	public static final String TYPE_SVN = "svn";

	public static final String FAVORIS = "java-simplex-favoris";

	/**
	 * Class chargée de la sérialisation d'une adresse précédement utilisée.
	 */
	public static class Favori {
		public final String type; // coded on 3 chars
		public final String adresse;

		public Favori(String token) {
			this.type = token.substring(0, 3);
			this.adresse = token.substring(3);
		}

		public Favori(String type, String adresse) {
			this.type = type;
			this.adresse = adresse;
		}

		public static String tokenize(Favori repo) {
			return repo.type + repo.adresse;
		}
	}

	private static String SEPARATOR = "\\$\\$";

	public static String serializeFavoris(List<Favori> favoris) {
		String token = "";
		for (Favori favori : favoris) {
			token += Favori.tokenize(favori) + SEPARATOR;
		}
		return token;
	}

	public static List<Favori> deserializeFavoris(String token) {
		String[] split = token.split(SEPARATOR, -1);
		List<Favori> favoris = new ArrayList<Utils.Favori>();
		for (String s : split) {
			if (s.isEmpty()) {
				continue;
			}
			favoris.add(new Favori(s));
		}
		return favoris;
	}
}
