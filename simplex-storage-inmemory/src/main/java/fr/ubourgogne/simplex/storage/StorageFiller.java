package fr.ubourgogne.simplex.storage;

import java.util.UUID;

import fr.ubourgogne.simplex.model.java.JavaProject;
import fr.ubourgogne.simplex.model.java.entity.JavaMethod;
import fr.ubourgogne.simplex.model.java.entity.JavaParam;
import fr.ubourgogne.simplex.model.java.entity.JavaSimpleType;
import fr.ubourgogne.simplex.model.java.entity.JavaVariable;
import fr.ubourgogne.simplex.model.java.meta.JavaReferenceClass;
import fr.ubourgogne.simplex.model.java.meta.JavaReferenceInterface;
import fr.ubourgogne.simplex.model.java.meta.JavaReferenceObject;
import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.model.java.object.JavaInterface;
import fr.ubourgogne.simplex.storage.exceptions.StorageException;

public class StorageFiller {
	public static void fillStorage(Storage storage, EntityFactory entityFactory)
			throws StorageException {
		JavaProject project = new JavaProject(UUID.randomUUID().toString());
		JavaClass class2 = entityFactory.getJavaClass(project, "Class2");
		class2.setModifiers("public");
		{
			JavaMethod methodeD = new JavaMethod("public static",
					JavaSimpleType.INT, "methodeD");
			methodeD.setRawCode(
					"System.out.println(\"Appel à la méthode \\\"methodeD\\\" .\");",
					"return new Class2();");
			class2.getContent().add(methodeD);
			storage.store(methodeD);
		}
		storage.store(class2);

		JavaClass superClass = entityFactory
				.getJavaClass(project, "SuperClass");
		superClass.setModifiers("public");
		superClass.getParams().add(
				new JavaParam("V", new JavaReferenceClass(class2,
						new JavaParam("", new JavaReferenceClass(entityFactory
								.getJavaClass(project, "String"))))));
		storage.store(superClass);

		JavaInterface interface1 = entityFactory.getJavaInterface(project,
				"Interface1");
		storage.store(interface1);

		JavaClass class1 = entityFactory.getJavaClass(project, "Class1");
		class1.setModifiers("public");
		class1.setPackage("fr.ubourgogne.simplex.model.java.entity.Class1");
		class1.getImports().add("java.util.List");
		class1.getImports().add(
				"fr.ubourgogne.simplex.model.java.entity.JavaSimpleType");
		class1.getImports().add(
				"fr.ubourgogne.simplex.model.java.entity.JavaVariable");
		class1.getImports().add(
				"fr.ubourgogne.simplex.model.java.object.JavaClass");
		class1.getImports().add(
				"fr.ubourgogne.simplex.model.java.object.JavaInterface");
		class1.setSuperClass(new JavaReferenceClass(superClass, new JavaParam(
				"", new JavaReferenceClass(entityFactory.getJavaClass(project,
						"String")))));
		class1.getImplementedInterfaces().add(
				new JavaReferenceInterface(interface1));
		class1.getParams().add(
				new JavaParam("T", new JavaReferenceClass(class2,
						new JavaParam(new JavaReferenceObject(entityFactory
								.getJavaClass(project, "List"), new JavaParam(
								new JavaReferenceObject(entityFactory
										.getJavaClass(project, "String"))))))));
		storage.store(class1);

		JavaClass class3 = entityFactory.getJavaClass(project, "Class3");
		class3.setModifiers("public");
		storage.store(class3);

		/*
		 * 
		 */
		JavaMethod methode1 = new JavaMethod("public static",
				new JavaReferenceClass(class3), "methode1");
		methode1.getVarParams().add(
				new JavaVariable("par1", new JavaReferenceClass(class2)));
		methode1.getVarParams().add(
				new JavaVariable("par2", new JavaReferenceClass(class1)));
		methode1.getVarParams().add(
				new JavaVariable("id", new JavaReferenceClass(entityFactory
						.getJavaClass(project, "List"), new JavaParam(
						new JavaReferenceObject(entityFactory.getJavaClass(
								project, "String"))))));
		methode1.setRawCode(
				"System.out.println(\"Appel à la méthode \\\"methode1\\\" .\");",
				"parametre.fonction(\"toto\");", "return null;");
		storage.store(methode1);

		/*
		 * 
		 */
		JavaVariable field1 = new JavaVariable("public static final",
				"CONSTANTE_FIELD", new JavaReferenceClass(class2));
		field1.setAllocation("new Class2()");
		JavaVariable field2 = new JavaVariable("private", "field2",
				new JavaReferenceClass(class2));

		/*
		 * 
		 */
		JavaMethod methode2 = new JavaMethod("public static",
				new JavaReferenceClass(class2), "methode2");
		methode2.setRawCode(
				"System.out.println(\"Appel à la méthode \\\"methode2\\\" .\");",
				"return new Class2();");
		storage.store(methode2);

		/*
		 * 
		 */
		JavaMethod methode3 = new JavaMethod("public static",
				JavaSimpleType.INT, "methode3");
		methode3.setRawCode(
				"System.out.println(\"Appel à la méthode \\\"methode3\\\" .\");",
				"return new Class2();");
		storage.store(methode3);

		/*
		 * 
		 */
		JavaMethod methode4 = new JavaMethod("public abstract",
				JavaSimpleType.SHORT, "methodeAbstraite");
		methode4.setHasCode(false);
		storage.store(methode4);

		/*
		 * 
		 */
		JavaMethod methode5 = new JavaMethod("public", JavaSimpleType.SHORT,
				"getClass1");
		methode5.getVarParams().add(
				new JavaVariable("id", new JavaReferenceObject(entityFactory
						.getJavaClass(project, "String"))));
		methode5.setReturnType(new JavaReferenceObject(class1));
		methode5.setRawCode("return new Class1(id);");
		storage.store(methode5);

		/*
		 * 
		 */
		class1.getContent().add(class2);
		class1.getContent().add(methode1);
		class1.getContent().add(field1);
		class1.getContent().add(field2);
		class1.getContent().add(methode2);
		class1.getContent().add(methode3);
		class1.getContent().add(methode4);
		class1.getContent().add(methode5);

		class1.setJavaDoc("Class1 est une classe très utile, avec beaucoups de javadoc...");

		storage.store(class1);
	}
}
