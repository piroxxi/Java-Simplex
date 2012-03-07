package fr.ubourgogne.simplex.model.java;

import java.util.ArrayList;

import fr.ubourgogne.simplex.model.BasicEntity;
import fr.ubourgogne.simplex.model.java.meta.JavaObjectCommonInfos;

public class JavaPackage extends BasicEntity {
	private static final long serialVersionUID = 3896038364534678085L;

	private String name;
	private ArrayList<JavaPackage> subPackages;
	private ArrayList<JavaObjectCommonInfos> objects;

	/**
	 * 
	 */
	public JavaPackage() {
		super();
		subPackages = new ArrayList<JavaPackage>();
		objects = new ArrayList<JavaObjectCommonInfos>();
	}

	/**
	 * @param name
	 */
	public JavaPackage(String name) {
		super();
		this.setName(name);
		subPackages = new ArrayList<JavaPackage>();
		objects = new ArrayList<JavaObjectCommonInfos>();
	}

	/**
	 * @param id
	 * @param version
	 */
	public JavaPackage(String id, long version) {
		super(id, version);
		subPackages = new ArrayList<JavaPackage>();
		objects = new ArrayList<JavaObjectCommonInfos>();
	}

	/**
	 * @param id
	 * @param version
	 * @param name
	 */
	public JavaPackage(String id, long version, String name) {
		super(id, version);
		this.setName(name);
		subPackages = new ArrayList<JavaPackage>();
		objects = new ArrayList<JavaObjectCommonInfos>();
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
		return subPackages;
	}

	public void setPackages(ArrayList<JavaPackage> packages) {
		this.subPackages = packages;
	}

	public ArrayList<JavaObjectCommonInfos> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<JavaObjectCommonInfos> objects) {
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
		for (JavaPackage jp : subPackages) {
			if (jp.getName().equals(directChild))
				return jp.getPackageByFullName(otherChildren);
		}

		// TODO gaffe au constructeur du coup...
		return new JavaPackage(name);

		// return storage.getByName(JavaPackage.class,name);
	}

	public void addObject(JavaObject object) {
		if (object.getPackage().equals(name)) {
			objects.add(object.getCommonInfos());
			return;
		}

		boolean found = false;
		for (JavaPackage pack : subPackages) {
			if (object.getPackage().startsWith(pack.getName())) {
				found = true;
				pack.addObject(object);
			}
		}
		if (!found) {
			String packageName = object.getPackage();
			packageName = packageName.substring(name.length() + 1);
			if (packageName.indexOf(".") != -1) {
				packageName = packageName
						.substring(0, packageName.indexOf("."));
			}

			JavaPackage pack = new JavaPackage(name + "." + packageName);
			subPackages.add(pack);
			pack.addObject(object);
		}
	}

}
