package com.stars.persistence.dbaccess;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.logging.Logger;

public class PersistenceManager {
	
	private static Session session = null;
	private Transaction transaction = null;
	private SessionFactory sessionFactory = null;
	private static Logger logger = Logger.getLogger(QuerySpec.class.getName());

	public PersistenceManager(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		if (session == null) {
			session = createSession();
		}
		return session;
	}

	private Session createSession() {
		
		logger.info("Opening a new session");
		Session session = this.sessionFactory.openSession();
		return session;
	}

	public void cleanUp() {
		try {
			logger.info("Hibernate transaction cleaning up.");

			if (this.session != null) {
				this.session.close();
			}

			logger.info("Hibernate transaction cleaning up.");
		} catch (Exception e) {
			logger.info("Failed to close session");
		}
	}

	private boolean isTransactionOpened() {
		if ((transaction != null) && transaction.isActive()) {
			return true;
		} else {
			return false;
		}
	}

	public void beginTransaction() {
		logger.info("Entering beginTransaction()");
		logger.info("Hibernate transaction opened.");
		logger.info("Working with session " + getSession().hashCode());
		transaction = getSession().beginTransaction();
		logger.info("Exiting beginTransaction()");
	}

	public void commitTransaction() {
		if (isTransactionOpened()) {
			transaction.commit();
			logger.info("Hibernate transaction committed.");
		} else {
			logger.info("[PROD_OPS_PAGE]: Commit called without an open transaction");
		}
	}

	public void rollbackTransaction() {
		if (isTransactionOpened()) {
			transaction.rollback();
			logger.info("Hibernate transaction rolled back.");
		}
	}

	public void save(Object o) {
		getSession().save(o);
	}

	public void saveOrUpdate(Object o) {
		getSession().saveOrUpdate(o);
	}

	public void update(Object o) {
		getSession().update(o);
	}
}
