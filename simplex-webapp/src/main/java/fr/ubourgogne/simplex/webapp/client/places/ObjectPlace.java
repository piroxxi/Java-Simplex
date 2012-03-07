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
			return new ObjectPlace(split[0], Integer.parseInt(split[1]),
					split[2]);
		}

		@Override
		public String getToken(ObjectPlace place) {
			return place.getProjectId() + ":" + place.getObjectType() + ":"
					+ place.getObjectId();
		}
	}

	private final String projectId;
	private final int objectType;
	private final String objectId;

	public ObjectPlace(String projectId, int objectType, String objectName) {
		this.objectType = objectType;
		this.projectId = projectId;
		this.objectId = objectName;
	}

	public String getProjectId() {
		return projectId;
	}

	public int getObjectType() {
		return objectType;
	}

	public String getObjectId() {
		return objectId;
	}
}
