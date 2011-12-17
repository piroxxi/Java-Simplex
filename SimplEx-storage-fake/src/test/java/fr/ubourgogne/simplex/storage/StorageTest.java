package fr.ubourgogne.simplex.storage;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.ubourgogne.simplex.storage.exceptions.EntityIsObsoleteException;

public class StorageTest {

	private class TestingEntity extends BasicEntity {

	}

	private Storage storage;

	/**
	 * Avant de lancer chaque fonction de test, on lance à chaque fois la
	 * fonction before() qui s'occupera de créer un nouveau Storage.
	 */
	@Before
	public void before() {
		storage = new StorageImpl();
	}

	/**
	 * Verifie que le cycle stockage/récupération fonctionne.
	 * 
	 * @throws EntityIsObsoleteException
	 */
	@Test
	public void verifyGetting() throws EntityIsObsoleteException {
		// G(iven), étant donné une entité sauvegardée en base avec une id
		// donnée
		String id = UUID.randomUUID().toString();
		TestingEntity entity = new TestingEntity();
		entity.setId(id);
		storage.store(TestingEntity.class, entity);

		// W(hen), lorsque l'on récupère une entité ayant cette même id
		TestingEntity retrievedEntity = storage.get(TestingEntity.class, id);

		// T(hen), alors on veut que l'on puisse récupérer cette même entité
		Assert.assertNotNull("Aucune entité en base ne porte l'ID " + id
				+ ", alors qu'il devrait y en avoir une.", retrievedEntity);
		Assert.assertEquals("On a bien recupéré une entité ayant l'id " + id
				+ ", mais il ne s'agit pas de la bonne entité", entity,
				retrievedEntity);
	}

	/**
	 * On verifie que l'ID d'une entité est bien crée lorsqu'on sauve l'entité
	 * pour la première fois.
	 * 
	 * @throws EntityIsObsoleteException
	 */
	@Test
	public void verifyIDCompletion() throws EntityIsObsoleteException {
		TestingEntity entity = new TestingEntity();

		Assert.assertNull(
				"l'id de l'entité n'est pas null, alors qu'il s'agit d'un nouvelle entité",
				entity.getId());

		storage.store(TestingEntity.class, entity);

		Assert.assertNotNull(
				"l'id de l'entité n'a pas étée définie pendant la sauvegarde",
				entity.getId());
	}

}
