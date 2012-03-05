package fr.ubourgogne.simplex.storage;

import fr.ubourgogne.simplex.model.java.JavaProject;
import fr.ubourgogne.simplex.model.java.object.JavaAnnotation;
import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.model.java.object.JavaEnum;
import fr.ubourgogne.simplex.model.java.object.JavaInterface;
import fr.ubourgogne.simplex.storage.exceptions.StorageException;

public class EntityFactoryImpl implements EntityFactory{

	private Storage storage;

	public EntityFactoryImpl(Storage storage) {
		this.storage = storage;
	}

	@Override
	public JavaClass getJavaClass(JavaProject project, String name) {
		JavaClass clazz = storage.getByName(JavaClass.class, name);
		if (clazz == null) {
			clazz = new JavaClass();
			clazz.setName(name);
			storage.store(clazz);
		}
		return clazz;
	}

	@Override
	public JavaInterface getJavaInterface(JavaProject project, String name) {
		JavaInterface interfaze = storage.getByName(JavaInterface.class, name);
		if (interfaze == null) {
			interfaze = new JavaInterface();
			interfaze.setName(name);
			storage.store(interfaze);
		}
		return interfaze;
	}

	@Override
	public JavaAnnotation getJavaAnnotation(JavaProject project, String name)
			throws StorageException {

		JavaAnnotation annotation = storage.getByName(JavaAnnotation.class, name);
		if (annotation == null) {
			annotation = new JavaAnnotation();
			annotation.setName(name);
			storage.store(annotation);
		}
		return annotation;
	}

	@Override
	public JavaEnum getJavaEnum(JavaProject project, String name) {
		JavaEnum enumz = storage.getByName(JavaEnum.class, name);
		if (enumz == null) {
			enumz = new JavaEnum();
			enumz.setName(name);
			storage.store(enumz);
		}
		return enumz;
	}
}
