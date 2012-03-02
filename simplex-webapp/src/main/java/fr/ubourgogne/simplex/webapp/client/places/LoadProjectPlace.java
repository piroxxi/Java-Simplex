package fr.ubourgogne.simplex.webapp.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

import fr.ubourgogne.simplex.webapp.client.utils.Resources.Favori;

/**
 * Il suffit d'aller sur cette place pour lancer le chargement d'un projet.
 * <p>
 * /!\ ATTENTION /!\ le fait de lancer cette méthode recharge tout le code coté
 * serveur. Pour acceder au code sans recharger le projet, utiliser la place
 * {@link ObjectPlace}.
 */
public class LoadProjectPlace extends Place {
	@Prefix("LoadProject")
	public static class Tokenizer implements PlaceTokenizer<LoadProjectPlace> {

		@Override
		public LoadProjectPlace getPlace(String token) {
			return new LoadProjectPlace(new Favori(token));
		}

		@Override
		public String getToken(LoadProjectPlace place) {
			return Favori.tokenize(place.getFavori());
		}
	}

	private final Favori favori;

	public LoadProjectPlace(Favori favori) {
		this.favori = favori;
	}

	/**
	 * @return the favori
	 */
	public Favori getFavori() {
		return favori;
	}
}