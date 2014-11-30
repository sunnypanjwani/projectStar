package com.stars.persistence.dbaccess;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import com.stars.persistence.dbaccess.PersistenceManager;
import java.util.logging.Logger;


public class QuerySpec {
	private static Logger log = Logger.getLogger(QuerySpec.class.getName());
    private String query;
	private Map namedParameters;
	private Class transformer;
    private Map<String, Type> columnAliases;
    private int maxResults = 0;

    public QuerySpec (Class transformer, String query) {
        this.transformer=transformer;
        this.query = query;
    }
    
    public String getQuery() {
        return query;
    }


    public Map getNamedParameters() {
        return namedParameters;
    }

    public void setNamedParameters(Map namedParameters) {
        this.namedParameters = namedParameters;
    }

    public void setMaxResults(final int max) {
        this.maxResults = max;
    }

    public List execute() throws Exception {
        Session session = getReadHandle();
        log.info("filter query = " + getQuery());
        SQLQuery query = session.createSQLQuery(getQuery());
        if (this.maxResults > 0) {
            query.setMaxResults(maxResults);
        }
        if (getNamedParameters() != null) {
            for (Iterator iter = getNamedParameters().entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                List<Object> valList = (List<Object>) entry.getValue();
                if (valList.size() > 1) {
                    query.setParameterList((String) entry.getKey(), valList);
                } else {
                    query.setParameter((String) entry.getKey(), valList.get(0));
                }
            }
        }
        if (columnAliases != null) {
            for (Iterator iter = columnAliases.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry) iter.next();
                query.addScalar((String)entry.getKey(), (Type) entry.getValue());
            }
        }
        return query
                .setResultTransformer(Transformers.aliasToBean(transformer))
                .list();
    }

    private Session getReadHandle() throws Exception {
        return PersistenceManagerFactory.getInstance().getPersistenceManager().getSession();
    }

    public void setColumnAliases(final Map<String, Type> aliases) {
        columnAliases = aliases;
    }

}
