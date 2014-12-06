package com.stars.persistence.dao;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NoResultException;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.Query;

import com.stars.persistence.dbaccess.PersistenceManager;
import com.stars.persistence.dbaccess.PersistenceManagerFactory;

@Entity
@Cacheable(false)
@Table(name = "passwords")
public class Passwords implements java.io.Serializable {	
	private static Logger logger = Logger.getLogger(Passwords.class.getName());
	private static final long serialVersionUID = 1454644747L;
	
	private long passwordId;
	private Users user;
	private Date created;
	private Date modified;
	private String passwordHash;
	private String passwordSalt;
	private String changeRequired;
	
	public Passwords(){
		
	}
	
	@Id
	@Column(name = "password_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getPasswordId() {
		return passwordId;
	}

	public void setPasswordId(long passwordId) {
		this.passwordId = passwordId;
	}

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_user_id")
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "modified")
	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Column(name = "password_hash")
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Column(name = "password_salt")
	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	@Column(name = "change_required")
	public String getChangeRequired() {
		return changeRequired;
	}

	public void setChangeRequired(String changeRequired) {
		this.changeRequired = changeRequired;
	}
	
	public void save() throws Exception
	{
		this.setModified(new Date());
		PersistenceManagerFactory.getInstance().getPersistenceManager().saveOrUpdate(this);
	}
	
	public static Passwords loadPasswordByUserId(Long userId) throws Exception {

		PersistenceManager persistMgr = PersistenceManagerFactory.getInstance().getPersistenceManager();

		Query query = persistMgr.getSession().createSQLQuery("select * from passwords where users_user_id = :userId").addEntity(Passwords.class);
		query.setParameter("userId", userId);
		logger.info("Executing Query : " + query.getQueryString());

		@SuppressWarnings("unchecked")
		List<Passwords> list = query.list();

		if (list == null || list.size() != 1){

			throw new NoResultException("No result found in passwords for userId:  " + userId);
		}

		return list.get(0);
	}
	
}