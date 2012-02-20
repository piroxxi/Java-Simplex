package fr.ubourgogne.simplex.model.java.entity;

import java.util.List;

import fr.ubourgogne.simplex.model.java.JavaEntity;

/**
 * Représente une méthode java.
 */
public class JavaMethod extends JavaEntity {
	private boolean isStatic;
	private String returnTypeId;
	private List<JavaVariable> params;

	/**
	 * @return the isStatic
	 */
	public boolean isStatic() {
		return isStatic;
	}

	/**
	 * @param isStatic
	 *            the isStatic to set
	 */
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
	
	/**
	 * @return the returnTypeId
	 */
	public String getReturnTypeId() {
		return returnTypeId;
	}

	/**
	 * @param returnTypeId
	 *            the returnTypeId to set
	 */
	public void setReturnTypeId(String returnTypeId) {
		this.returnTypeId = returnTypeId;
	}

	/**
	 * @return the params
	 */
	public List<JavaVariable> getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(List<JavaVariable> params) {
		this.params = params;
	}
}
