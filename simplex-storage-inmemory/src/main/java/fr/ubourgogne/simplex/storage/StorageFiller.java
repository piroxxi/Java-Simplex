package fr.ubourgogne.simplex.storage;

import fr.ubourgogne.simplex.model.java.entity.JavaMethod;
import fr.ubourgogne.simplex.model.java.entity.JavaParam;
import fr.ubourgogne.simplex.model.java.entity.JavaReferenceClass;
import fr.ubourgogne.simplex.model.java.entity.JavaReferenceInterface;
import fr.ubourgogne.simplex.model.java.entity.JavaReferenceObject;
import fr.ubourgogne.simplex.model.java.entity.JavaSimpleType;
import fr.ubourgogne.simplex.model.java.entity.JavaVariable;
import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.model.java.object.JavaInterface;
import fr.ubourgogne.simplex.storage.exceptions.StorageException;

public class StorageFiller {
	public static void fillStorage(Storage storage) throws StorageException {
		JavaClass string = new JavaClass("public", "String");
		storage.store(string);

		JavaClass class2 = new JavaClass("public", "Class2");
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

		JavaClass superClass = new JavaClass("public", "SuperClass");
		superClass.getParams().add(
				new JavaParam("V", new JavaReferenceClass(class2,
						new JavaParam("", new JavaReferenceClass(string)))));
		storage.store(superClass);

		JavaInterface interface1 = new JavaInterface("Interface1");
		storage.store(interface1);

		JavaClass class1 = new JavaClass("public", "Class1");
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
				"", new JavaReferenceClass(string))));
		class1.getImplementedInterfaces().add(
				new JavaReferenceInterface(interface1));
		class1.getParams().add(
				new JavaParam("T", new JavaReferenceClass(class2,
						new JavaParam(new JavaReferenceObject(new JavaClass("",
								"List"), new JavaParam(new JavaReferenceObject(
								string)))))));
		storage.store(class1);

		JavaClass class3 = new JavaClass("public", "Class3");
		storage.store(class3);

		/*
		 * 
		 */
		JavaMethod methode1 = new JavaMethod("public static",
				new JavaReferenceClass(class3), "methode1");
		{
			// Ajout d'une variable "parametre"
			JavaVariable var1methode1 = new JavaVariable("parametre",
					new JavaReferenceClass(class2));
			storage.store(var1methode1);

			methode1.getParams().add(var1methode1);
			methode1.setRawCode(
					"System.out.println(\"Appel à la méthode \\\"methode1\\\" .\");",
					"parametre.fonction(\"toto\");", "return null;");
		}
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
		JavaMethod methode4 = new JavaMethod("public abstrac",
				JavaSimpleType.SHORT, "methodeAbstraite");
		methode4.setHasCode(false);
		storage.store(methode4);

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
		storage.store(class1);
		storage.store(class1);
		storage.store(class1);
		storage.store(class1);
	}
}
