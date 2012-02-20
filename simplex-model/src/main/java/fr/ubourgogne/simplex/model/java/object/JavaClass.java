package fr.ubourgogne.simplex.model.java.object;

import java.util.List;

import fr.ubourgogne.simplex.model.java.JavaObject;

public class JavaClass extends JavaObject {
	private String superClassId;
	private List<String> implementedInterfacesIds;
	
	/**
	 * @return the superClassId
	 */
	public String getSuperClassId() {
		return superClassId;
	}

	/**
	 * @param superClassId
	 *            the superClassId to set
	 */
	public void setSuperClassId(String superClassId) {
		this.superClassId = superClassId;
	}

	/**
	 * @return the implementedInterfacesIds
	 */
	public List<String> getImplementedInterfacesIds() {
		return implementedInterfacesIds;
	}

	/**
	 * @param implementedInterfacesIds
	 *            the implementedInterfacesIds to set
	 */
	public void setImplementedInterfacesIds(
			List<String> implementedInterfacesIds) {
		this.implementedInterfacesIds = implementedInterfacesIds;
	}
}
