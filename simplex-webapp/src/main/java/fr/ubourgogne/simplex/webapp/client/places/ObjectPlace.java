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
			String[] split = token.split(":", -1);
			return new ObjectPlace(split[0], split[1]);
		}

		@Override
		public String getToken(ObjectPlace place) {
			return place.getProjectId() + ":" + place.getObjectId();
		}
	}

	private final String objectId;
	private final String projectId;

	public ObjectPlace(String projectId, String objectName) {
		this.projectId = projectId;
		this.objectId = objectName;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getProjectId() {
		return projectId;
	}
}
