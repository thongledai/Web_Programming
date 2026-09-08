package vn.iotstar.configs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfig {

	private static EntityManagerFactory factory;

	public static EntityManager getEntityManager() {
		if (factory == null || !factory.isOpen()) {
			factory = Persistence.createEntityManagerFactory("WebProgrammingJPA");
		}
		return factory.createEntityManager();
	}

	public static void shutdown() {
		if (factory != null && factory.isOpen()) {
			factory.close();
		}
	}
}
