package fr.ubourgogne.simplex.webapp.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * Place d'acceuil pour l'utilisateur.
 */
public class MainPlace extends Place {
	@Prefix("MainPlace")
	public static class Tokenizer implements PlaceTokenizer<MainPlace> {

		@Override
		public MainPlace getPlace(String token) {
			return new MainPlace();
		}

		@Override
		public String getToken(MainPlace place) {
			return "";
		}
	}
}
