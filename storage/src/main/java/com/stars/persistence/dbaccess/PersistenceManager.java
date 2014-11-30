package com.stars.persistence.dbaccess;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.logging.Logger;
import org.hibernate.context.ManagedSessionContext;

public class PersistenceManager {
	  private Session session = null;
	  private Transaction transaction = null;
	  private SessionFactory sessionFactory = null;

	  private static Logger logger = Logger.getLogger(PersistenceManager.class.getName());

	  public PersistenceManager(final SessionFactory sessionFactory)
	  {
	    this.sessionFactory = sessionFactory;
	  }

	  public Session getSession()
	  {
	    if (session == null)
	      if (ManagedSessionContext.hasBind(sessionFactory))
	        session = sessionFactory.getCurrentSession();
	      else
	      {
	        logger.info("Creating new session");
	        session = createSession();
	      }
	    return session;
	  }

	  private Session createSession()
	  {
		  	final Session session = sessionFactory.openSession();
		    ManagedSessionContext.bind((org.hibernate.classic.Session) session);
		    return session;
	  }

	  public void cleanUp()
	  {
		  try
		    {
		      logger.info("Hibernate transaction cleaning up.");

		      if (ManagedSessionContext.hasBind(sessionFactory))
		        ManagedSessionContext.unbind(sessionFactory);

		      if (session != null)
		        session.close();

		      logger.info("Hibernate transaction cleaning up.");
		    } catch (final Exception e)
		    {
		      logger.info("Failed to close session" + e);
		    }

	  }

	  private boolean isTransactionOpened()
	  {
	    if ((transaction != null) && transaction.isActive())
	    {
	      return true;
	    } else
	    {
	      return false;
	    }
	  }

	  public void beginTransaction()
	  {
	    logger.info("Working with session " + getSession().hashCode());
	    transaction = getSession().beginTransaction();
	    logger.info("Exiting beginTransaction()");
	  }

	  public void commitTransaction()
	  {
	    if (isTransactionOpened())
	    {
	      transaction.commit();
	      logger.info("Hibernate transaction committed.");
	    } else
	    {
	      logger.info("[PROD_OPS_PAGE]: Commit called without an open transaction");
	    }
	  }

	  public void rollbackTransaction()
	  {
	    if (isTransactionOpened())
	    {
	      transaction.rollback();
	      logger.info("Hibernate transaction rolled back.");
	    }
	  }

	  public void save(Object o)
	  {
	    getSession().save(o);
	  }

	  public void saveOrUpdate(Object o)
	  {
	    getSession().saveOrUpdate(o);
	  }

	  public void update(Object o)
	  {
	    getSession().update(o);
	  }
}
