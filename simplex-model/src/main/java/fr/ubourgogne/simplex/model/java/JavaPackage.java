package fr.ubourgogne.simplex.model.java;

import java.util.ArrayList;

import fr.ubourgogne.simplex.model.BasicEntity;

public class JavaPackage extends BasicEntity {
	private static final long serialVersionUID = 3896038364534678085L;

	private String name;
	private ArrayList<JavaPackage> packages;
	private ArrayList<JavaObject> objects;

	/**
	 * 
	 */
	public JavaPackage() {
		super();
		packages = new ArrayList<JavaPackage>();
		objects = new ArrayList<JavaObject>();
	}

	/**
	 * @param name
	 */
	public JavaPackage(String name) {
		super();
		this.setName(name);
		packages = new ArrayList<JavaPackage>();
		objects = new ArrayList<JavaObject>();
	}

	/**
	 * @param id
	 * @param version
	 */
	public JavaPackage(String id, long version) {
		super(id, version);
		packages = new ArrayList<JavaPackage>();
		objects = new ArrayList<JavaObject>();
	}

	/**
	 * @param id
	 * @param version
	 * @param name
	 */
	public JavaPackage(String id, long version, String name) {
		super(id, version);
		this.setName(name);
		packages = new ArrayList<JavaPackage>();
		objects = new ArrayList<JavaObject>();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<JavaPackage> getPackages() {
		return packages;
	}

	public void setPackages(ArrayList<JavaPackage> packages) {
		this.packages = packages;
	}

	public ArrayList<JavaObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<JavaObject> objects) {
		this.objects = objects;
	}

	public JavaPackage getPackageByFullName(String name) {
		if (name.equals(""))
			return this;
		
		String directChild = name;
		String otherChildren = "";
		if (name.indexOf(".") != -1) {
			directChild = name.substring(0, name.indexOf("."));
			otherChildren = name.substring(name.indexOf(".") + 1);
		}
		for (JavaPackage jp : packages) {
			if (jp.getName().equals(directChild))
				return jp.getPackageByFullName(otherChildren);
		}

		// TODO gaffe au constructeur du coup...
		return new JavaPackage(name);

		// return storage.getByName(JavaPackage.class,name);
	}
	
	

}
