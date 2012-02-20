package fr.ubourgogne.simplex.webapp.client.activities;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;

import fr.ubourgogne.simplex.webapp.client.activities.main.MainActivity;
import fr.ubourgogne.simplex.webapp.client.places.MainPlace;

/**
 * Classe chargée de renvoyer l'activité qui correspond à la place. Ce grace à
 * la méthode getActivity().
 */
public class AppActivityMapper implements ActivityMapper {
	/**
	 * Interface d'injection assistée.
	 * <p>
	 * Lorsque l'on veut se faire injecter quelquechose, mettons par exemple une
	 * Activité, mais qu'on veuille au cours de cette injection, lui passer un
	 * paramètre (dans le cas présent, ce paramètre est courament le "place"
	 * correspondant à l'activité).
	 * <p>
	 * On dispose alors de deux solutions, la première solution est de passer le
	 * paramètre en question après que l'Activité de soit faite injecter. Ceci
	 * n'est pas très pratique. Si par exemple l'Activité a besoin de cette
	 * place pour bien s'initialiser, alors on est bloqué. De même, le moment
	 * entre l'injection et le passage en paramètre peut etre long, et ceci peut
	 * être source d'erreures.
	 * <p>
	 * Ainsi donc on utilise la deuxieme solution. On crée la factory suivante,
	 * et on se la fait injecter. GIN est alors capable de voir qu'il s'agit
	 * d'une injection assistée, et les paramètres du constructeur qui sont
	 * annottés <code>@assisted</code> sont automatiquement remplis avec les
	 * objets passés en paramètre à la méthode get() de cette factory.
	 * <p>
	 * Pour en savoir plus sur l'injection de dépandances assistée, il suffit
	 * d'aller sur le lien :
	 * http://code.google.com/p/google-guice/wiki/AssistedInject
	 * <p>
	 * /!\ Attention à bien "installer" cette factory dans le module {@Link
	 *  MyWidgetClientModule}. /!\
	 * <p>
	 * Enjoy!
	 */
	public interface AssistedActivitiesFactory {
		MainActivity getMainActivity(MainPlace place);
	}

	@Inject
	private AssistedActivitiesFactory assistedActivitiesFactory;

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof MainPlace) {
			return assistedActivitiesFactory.getMainActivity((MainPlace) place);
		}

		return null;
	}
}