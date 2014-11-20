package com.stars.persistence.dbaccess;

import java.io.File;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class PersistenceManagerFactory {
	private static Logger log = Logger.getLogger(PersistenceManagerFactory.class.getName());
	  private static PersistenceManagerFactory factory = null;
	  private static SessionFactory appSessionFactory = null;
	  private static SessionFactory auditLogSessionFactory = null;
	  public static final String PERSISTENCE_HIBERNATE_CFG_FILE = "/Library/Tomcat/webapps/StarsServer-0.0.1/WEB-INF/classes/hibernate.cfg.xml";
	  private static Configuration config = null;
	  private static String hibernateConfigFile = null;

	  private PersistenceManagerFactory()
	  {
		 config.addAnnotatedClass(com.stars.persistence.dao.Users.class);
	  }

	  @SuppressWarnings("deprecation")
	synchronized public static PersistenceManagerFactory getInstance()
	  {
	    if (config == null)
	    {
	    	log.info("Getting config");
	      config = getConfiguration();
	      factory = new PersistenceManagerFactory();
	      log.info("Got factory");
	      appSessionFactory = config.buildSessionFactory();
	      auditLogSessionFactory = config.buildSessionFactory();
	    }
	    log.info("returning factory");
	    return factory;
	  }

	  static Configuration getConfiguration()
	  {
	    if (hibernateConfigFile == null)
	    {
	      hibernateConfigFile = System.getProperty(PERSISTENCE_HIBERNATE_CFG_FILE);
	    }

	    if (hibernateConfigFile != null)
	    {
	      File file = new File(hibernateConfigFile);
	      if (file.exists() && file.canRead())
	      {
	        return new Configuration().configure(file);
	      }
	    }

	    return new Configuration().configure();
	  }

	  public PersistenceManager getPersistenceManager()
	  {
	    return new PersistenceManager(appSessionFactory);
	  }

	  public PersistenceManager getAuditLogPersistenceManager()
	  {
	    return new PersistenceManager(auditLogSessionFactory);
	  }

	  /**
	   * @param hibernateConfigFile
	   *          the hibernateConfigFile to set
	   */
	  public static final void setHibernateConfigFile(String hibernateConfigFile)
	  {
	    PersistenceManagerFactory.hibernateConfigFile = hibernateConfigFile;
	  }
}
