package fr.ubourgogne.simplex.webapp.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ProjectHomePlace extends Place {
	@Prefix("ProjectHome")
	public static class Tokenizer implements PlaceTokenizer<ProjectHomePlace> {

		@Override
		public ProjectHomePlace getPlace(String token) {
			return new ProjectHomePlace(token);
		}

		@Override
		public String getToken(ProjectHomePlace place) {
			return place.getProjectId();
		}
	}

	private final String projectName;

	public ProjectHomePlace(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectId() {
		return projectName;
	}
}