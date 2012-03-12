package fr.ubourgogne.simplex.model.java;

import java.util.ArrayList;

import fr.ubourgogne.simplex.model.BasicEntity;

public class JavaProject extends BasicEntity {
	private static final long serialVersionUID = 624109605727189878L;

	private String name;
	private ArrayList<JavaPackage> packages;
	private JavaPackage defaultPackage;

	/**
	 * 
	 */
	public JavaProject() {
		super();
		packages = new ArrayList<JavaPackage>();
		defaultPackage = new JavaPackage("default");
		packages.add(defaultPackage);
	}

	/**
	 * @param name
	 */
	public JavaProject(String name) {
		this();
		this.setName(name);
	}

	/**
	 * @param id
	 * @param version
	 */
	public JavaProject(String id, long version) {
		this();
		setId(id);
		setVersion(version);
	}

	/**
	 * @param id
	 * @param version
	 * @param name
	 */
	public JavaProject(String id, long version, String name) {
		this(id, version);
		this.setName(name);
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

		// a voir un default package...
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

	public void addJavaObject(JavaObject object) {
		if (object.getPackage() == null || object.getPackage().isEmpty()) {
			// Defaultpackage...
			defaultPackage.getObjects().add(object.getCommonInfos());
		} else {
			boolean found = false;
			for (JavaPackage pack : packages) {
				if (object.getPackage().startsWith(pack.getName())) {
					found = true;
					pack.addObject(object);
				}
			}
			if (!found) {
				JavaPackage pack;
				if (object.getPackage().indexOf(".") != -1) {

					pack = new JavaPackage(object.getPackage().substring(0,
							object.getPackage().indexOf(".")));
				} else {
					pack = new JavaPackage(object.getPackage());
				}
				packages.add(pack);
				pack.addObject(object);
			}
		}
	}

	public boolean isPackageInProject(String packageName) {
		if (packageName == null || packageName.isEmpty()) {
			return true;
		}
		for (JavaPackage _package : packages) {
			if (packageName.startsWith(_package.getName())) {
				return true;
			}
		}
		return false;
	}
}
