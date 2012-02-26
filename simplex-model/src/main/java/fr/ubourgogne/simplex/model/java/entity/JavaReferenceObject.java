package fr.ubourgogne.simplex.model.java.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.ubourgogne.simplex.model.java.JavaObject;

public class JavaReferenceObject implements Serializable {
	private static final long serialVersionUID = -7354348310792167308L;

	private String objectId;
	private String objectClazz;
	private List<String> paramsIds = new ArrayList<String>();

	private String serializedString;

	public JavaReferenceObject() {
	}

	public JavaReferenceObject(String serializedString) {
		this.serializedString = serializedString;
	}

	public JavaReferenceObject(JavaObject object) {
		this(object, (JavaParam[]) null);
	}

	public JavaReferenceObject(JavaObject object, JavaParam... params) {
		objectId = object.getId();
		objectClazz = object.getClass().getName();
		if (params != null && params.length > 0) {
			for (JavaParam p : params) {
				paramsIds.add(p.getId());
			}
		}
		this.serializedString = serialize(object, params);
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectClazz() {
		return objectClazz;
	}

	public void setObjectClazz(String objectClazz) {
		this.objectClazz = objectClazz;
	}

	public List<String> getParamsIds() {
		return paramsIds;
	}

	public void setParamsIds(List<String> paramsIds) {
		this.paramsIds = paramsIds;
	}

	public String getSerializedString() {
		return serializedString;
	}

	public void setSerializedString(String serializedString) {
		this.serializedString = serializedString;
	}

	public String print() {
		return serializedString;
	}

	private String serialize(JavaObject object, JavaParam[] params) {
		String ret = object.getName();
		if (params != null && params.length > 0) {
			ret += "<";
			for (int i = 0; i < params.length; i++) {
				ret += params[i].print("");
				if (i < params.length - 1) {
					ret += ", ";
				}
			}
			ret += ">";
		}
		return ret;
	}
}
