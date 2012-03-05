package fr.ubourgogne.simplex.model.sample;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.ubourgogne.simplex.model.sample.other.pack.TotalyUselessClassButNotInTheSamePackage;
import fr.ubourgogne.simplex.model.sample.other.pack.TotalyUselessClassButNotInTheSamePackage2;

/**
 * documentation.
 * 
 * @author cheloo
 * 
 */
@SampleAnnotation
public class SampleClass<F extends HashMap<Integer,List<String>>> extends SampleParentClass<String> implements
		SampleInterface<String> {

	/**
	 * And I said to myself... what a fak!
	 * 
	 * @author Chloé
	 * 
	 */

	public class T {
		/**
		 * This is sparta! (comment pour la méthode methode() de la sous class
		 * T).
		 */
		public void methode() {
			new TotalyUselessClassButNotInTheSamePackage();
		}

		/**
		 * Simple comment for methode2();
		 * 
		 * @param t
		 */
		@Annotation2("value of the annotation")
		@SampleAnnotation
		public void methode2(String t) {
			new TotalyUselessClassButNotInTheSamePackage2();
			new TG().ouiBonCaArriveRarementMaisQuandMeme();
		}

		@Annotation2("value of the annotation for methode 3")
		@SampleAnnotation
		public void methode3(String t) {
			new TotalyUselessClassButNotInTheSamePackage2();
			new TG().ouiBonCaArriveRarementMaisQuandMeme();
		}

		public class TG extends T implements SampleInterface<Integer>{
			/**
			 * Commentaire pour ouiBonCaArriveRarementMaisQuandMeme() ... pasque
			 * c'est vrai qu'une sous classe qui redéfinit une classe interne
			 * c'est pas fréquent.
			 */
			private void ouiBonCaArriveRarementMaisQuandMeme() {

			}

			@Override
			public Integer inheritedMethodeWitoutCommentButAStringReturnType() {
				return 3959;
			}
		}

		public final void qsf(String toto) {

		}
	}

	public enum SampleEnum {
		QSFLKj, QSFqsk, fqf
	}

	/**
	 * Cac 'est de la javadoc pour le field tata.
	 */
	private final String fieldToto = "";
	private String justAnotherField;

	public static final String encoreUnAutreField = "encoreUnAutreField";

	private String fieldTiti;

	/* eh ouais, ca c'est du com normal, pas de la java doc
	 * et en plus il tient sur plusieurs lignes
	 * spas magnifique ?
	 */
	
	public SampleClass() {

	}

	public SampleClass(String fqsf) {
		new List();
		for (String s : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
			System.out.println(" " + s + " =>");
		}
	}

	public SampleClass(String fqsf, String qsf) {
		new ArrayList<String>();
		for (int i = 0; i < 200; i++) {
			System.out.println("" + Math.random()
					+ "qsfmlqskfjqmlskfjqmslfkjqsmf => " + justAnotherField);
		}
	}

	/**
	 * Simple comment for methodeWithCommentAndThatIsStatic();
	 */
	public static void methodeWithCommentAndThatIsStatic() {
		System.out.println("test");
		Map<String, String> map = new HashMap<String, String>();
		System.out.println(map);
	}

	public void methodeWitoutComment(String pp, String t) {
		System.out.println("qfglmkqjsf");
		System.out.println(fieldTiti);
	}

	@Override
	public String inheritedMethodeWitoutCommentButAStringReturnType() {
		String toto = fieldToto;
		justAnotherField = "";
		System.out.println(toto);
		return "";
	}
}
