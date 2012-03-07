package fr.ubourgogne.simplex.webapp.client.places;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ MainPlace.Tokenizer.class, ObjectPlace.Tokenizer.class,
		LoadProjectPlace.Tokenizer.class, ProjectHomePlace.Tokenizer.class })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
