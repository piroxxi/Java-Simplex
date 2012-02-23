package fr.ubourgogne.simplex.storage;

import java.util.ArrayList;

import fr.ubourgogne.simplex.model.java.JavaEntity;
import fr.ubourgogne.simplex.model.java.entity.JavaMethod;
import fr.ubourgogne.simplex.model.java.entity.JavaParam;
import fr.ubourgogne.simplex.model.java.entity.JavaReferenceObject;
import fr.ubourgogne.simplex.model.java.entity.JavaSimpleType;
import fr.ubourgogne.simplex.model.java.entity.JavaVariable;
import fr.ubourgogne.simplex.model.java.object.JavaClass;
import fr.ubourgogne.simplex.model.java.object.JavaInterface;
import fr.ubourgogne.simplex.storage.exceptions.StorageException;

public class ExempleStorage {

	public static void main(String[] args) {
		Storage storage = new StorageImpl();
		try {
			fillStorage(storage);
		} catch (StorageException e) {
			e.printStackTrace();
		}
	}

	public static void fillStorage(Storage storage) throws StorageException {
		System.out.println("TEST => Remplissage du storage");

		JavaClass string = new JavaClass("String");
		storage.store(string);

		JavaClass class2 = new JavaClass("Class2");
		storage.store(class2);

		JavaClass superClass = new JavaClass("SuperClass");
		superClass.getParams().add(
				new JavaParam("V", new JavaReferenceObject<JavaClass>(class2,
						new JavaParam("", new JavaReferenceObject<JavaClass>(
								string)))));
		storage.store(superClass);

		JavaInterface interface1 = new JavaInterface("Interface1");
		storage.store(interface1);

		JavaClass class1 = new JavaClass("Class1");
		class1.setSuperClass(new JavaReferenceObject<JavaClass>(superClass,
				new JavaParam("", new JavaReferenceObject<JavaClass>(string))));
		class1.getImplementedInterfaces().add(
				new JavaReferenceObject<JavaInterface>(interface1));
		class1.getParams().add(
				new JavaParam("T", new JavaReferenceObject<JavaClass>(class2)));
		storage.store(class1);

		JavaClass class3 = new JavaClass("Class3");
		storage.store(class3);

		/*
		 * 
		 */
		JavaMethod methode1 = new JavaMethod("public static",
				new JavaReferenceObject<JavaClass>(class3), "methode1");
		{
			// Ajout d'une variable "parametre"
			JavaVariable var1methode1 = new JavaVariable("parametre", new JavaReferenceObject<JavaClass>(class2));
			storage.store(var1methode1);

			methode1.setParams(new ArrayList<JavaVariable>());
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
				"CONSTANTE_FIELD", new JavaReferenceObject<JavaClass>(class2));
		field1.setAllocation("new Class2()");
		JavaVariable field2 = new JavaVariable("private", "field2", new JavaReferenceObject<JavaClass>(class2));

		/*
		 * 
		 */
		JavaMethod methode2 = new JavaMethod("public static",
				new JavaReferenceObject<JavaClass>(class2), "methode2");
		methode2.setRawCode(
				"System.out.println(\"Appel à la méthode \\\"methode2\\\" .\");",
				"return new Class2();");
		storage.store(methode2);

		/*
		 * 
		 */
		JavaMethod methode3 = new JavaMethod("public static", JavaSimpleType.INT, "methode3");
		methode3.setRawCode(
				"System.out.println(\"Appel à la méthode \\\"methode3\\\" .\");",
				"return new Class2();");
		storage.store(methode3);
		
		/*
		 * 
		 */
		JavaMethod methode4 = new JavaMethod("public abstrac", JavaSimpleType.SHORT, "methodeAbstraite");
		methode4.setHasCode(false);
		storage.store(methode4);
		
		/*
		 * 
		 */
		class1.setContent(new ArrayList<JavaEntity>());
		class1.getContent().add(methode1);
		class1.getContent().add(field1);
		class1.getContent().add(field2);
		class1.getContent().add(methode2);
		class1.getContent().add(methode3);
		class1.getContent().add(methode4);
		storage.store(class1);

		System.out.println();
		System.out.println(class1.print("\\> "));
		// class1.get
	}
}
