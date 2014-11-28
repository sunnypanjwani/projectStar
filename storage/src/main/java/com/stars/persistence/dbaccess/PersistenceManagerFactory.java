package com.stars.persistence.dbaccess;

import java.io.File;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class PersistenceManagerFactory {

	private static Logger log = Logger
			.getLogger(PersistenceManagerFactory.class.getName());
	private static PersistenceManagerFactory factory = null;
	private static SessionFactory appSessionFactory = null;
	private static SessionFactory auditLogSessionFactory = null;
	public static final String PERSISTENCE_HIBERNATE_CFG_FILE = "/Users/panjwani/Desktop/eclipse/apache-tomcat-7.0.50/webapps/stars_service-0.0.1/WEB-INF/classes/hibernate.cfg.xml";
	private static Configuration config = null;
	private static String hibernateConfigFile = null;

	private PersistenceManagerFactory() {
		config.addAnnotatedClass(com.stars.persistence.dao.Users.class);
	}

	synchronized public static PersistenceManagerFactory getInstance() {
		
		if (config == null) {
			config = getConfiguration();
			log.info("creating new persistence mgr factory");
			factory = new PersistenceManagerFactory();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(config.getProperties()).build();
			appSessionFactory = config.buildSessionFactory(serviceRegistry);
			auditLogSessionFactory = appSessionFactory;
			log.info("persistence mgr factory created successfully");
		}
		return factory;
	}

	static Configuration getConfiguration() {

		hibernateConfigFile = PERSISTENCE_HIBERNATE_CFG_FILE;

		if (hibernateConfigFile != null) {
			File file = new File(hibernateConfigFile);
			if (file.exists() && file.canRead()) {
				return new Configuration().configure(file);
			}
		}

		return new Configuration().configure();
	}

	public PersistenceManager getPersistenceManager() {
		return new PersistenceManager(appSessionFactory);
	}

	public PersistenceManager getAuditLogPersistenceManager() {
		return new PersistenceManager(auditLogSessionFactory);
	}

	/**
	 * @param hibernateConfigFile
	 *            the hibernateConfigFile to set
	 */
	public static final void setHibernateConfigFile(String hibernateConfigFile) {
		PersistenceManagerFactory.hibernateConfigFile = hibernateConfigFile;
	}
}
