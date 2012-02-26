package fr.ubourgogne.simplex.webapp.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * Place pour observer le code d'un objet.
 */
public class ObjectPlace extends Place {
	@Prefix("Object")
	public static class Tokenizer implements PlaceTokenizer<ObjectPlace> {

		@Override
		public ObjectPlace getPlace(String token) {
			return new ObjectPlace(token);
		}

		@Override
		public String getToken(ObjectPlace place) {
			return place.getObjectName();
		}
	}

	private final String objectName;

	public ObjectPlace(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectName() {
		return objectName;
	}
}
