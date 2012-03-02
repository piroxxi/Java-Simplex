package fr.ubourgogne.simplex.model.java.meta;

import java.util.HashMap;

import fr.ubourgogne.simplex.model.java.JavaObject;

public class JavaObjectCommonInfosProvider {
	private JavaObjectCommonInfosProvider() {

	}

	private static HashMap<String, JavaObjectCommonInfos> commonInfos = new HashMap<String, JavaObjectCommonInfos>();

	public static JavaObjectCommonInfos getInfosForObject(JavaObject object) {
		JavaObjectCommonInfos objectInfos = commonInfos.get(object.getId());
		if (objectInfos == null) {
			objectInfos = new JavaObjectCommonInfos(object);
			commonInfos.put(object.getId(), objectInfos);
		}
		return objectInfos;
	}

	public static JavaObjectCommonInfos getInfosForObject(String id) {
		return commonInfos.get(id);
	}
}
