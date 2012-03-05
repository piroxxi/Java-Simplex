package fr.ubourgogne.simplex.model.java;

import java.util.ArrayList;

import fr.ubourgogne.simplex.model.BasicEntity;

public class JavaProject extends BasicEntity {
	private static final long serialVersionUID = 624109605727189878L;

	private String name;
	private ArrayList<JavaPackage> packages;

	/**
	 * 
	 */
	public JavaProject() {
		super();
		packages = new ArrayList<JavaPackage>();
	}

	/**
	 * @param name
	 */
	public JavaProject(String name) {
		super();
		this.setName(name);
		packages = new ArrayList<JavaPackage>();
	}

	/**
	 * @param id
	 * @param version
	 */
	public JavaProject(String id, long version) {
		super(id, version);
		packages = new ArrayList<JavaPackage>();
	}

	/**
	 * @param id
	 * @param version
	 * @param name
	 */
	public JavaProject(String id, long version, String name) {
		super(id, version);
		this.setName(name);
		packages = new ArrayList<JavaPackage>();
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

	public JavaPackage getPackageByFullName(String name) {
		if (name == null)
			return null;
		
		//a voir un default package...
		if (name.equals(""))
			return null;
		
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

		JavaPackage jp = new JavaPackage(name);
		packages.add(jp);
		return jp.getPackageByFullName(otherChildren);

		// return storage.getByName(JavaPackage.class,name);
	}
}
