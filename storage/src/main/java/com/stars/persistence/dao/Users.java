package com.stars.persistence.dao;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.Query;

import com.stars.persistence.dbaccess.PersistenceManager;
import com.stars.persistence.dbaccess.PersistenceManagerFactory;

@Entity
@Cacheable(false)
@Table(name = "users")
public class Users implements java.io.Serializable {	
	private static Logger logger = Logger.getLogger(Users.class.getName());
	private static final long serialVersionUID = 1454644747L;
	
	private long userId;
	private String screenName;
	private Date created;
	private Date modified;
	private String firstName;
	private String lastName;
	private String email;
	private String sex;
	
	public Users(){
		
	}
	
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "screen_name")
	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	@Column(name = "sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void save() throws Exception
	{
		this.setModified(new Date());
		PersistenceManagerFactory.getInstance().getPersistenceManager().saveOrUpdate(this);
	}
	
	public static Users loadUserByUserId(Long userId) throws Exception {

		PersistenceManager persistMgr = PersistenceManagerFactory.getInstance().getPersistenceManager();

		Query query = persistMgr.getSession().createSQLQuery("select * from users where user_id = :userId").addEntity(Users.class);
		query.setParameter("userId", userId);
		logger.info("Executing Query : " + query.getQueryString());

		@SuppressWarnings("unchecked")
		List<Users> list = query.list();

		if (list == null || list.size() != 1){

			throw new NoResultException("No result found in users for userId:  " + userId);
		}

		return list.get(0);
	}
	
	public static List<Users> loadUserByScreenNameOrEmail(String screenName,
			String email) {
		
		PersistenceManager persistMgr = PersistenceManagerFactory.getInstance().getPersistenceManager();
		Query query = persistMgr.getSession().createSQLQuery("(select * from users where screen_name = :screenName) "
				+ "union (select * from users where email = :email)").addEntity(Users.class);
		query.setParameter("screenName", screenName);
		query.setParameter("email", email);
		
		@SuppressWarnings("unchecked")
		List<Users> list = query.list();
		
		return list;
	}
	
	public static Users loadUserByScreenName(String screenName) throws Exception {

		PersistenceManager persistMgr = PersistenceManagerFactory.getInstance().getPersistenceManager();

		Query query = persistMgr.getSession().createSQLQuery("select * from users where screen_name = :screenName").addEntity(Users.class);
		query.setParameter("screenName", screenName);
		logger.info("Executing Query : " + query.getQueryString());

		@SuppressWarnings("unchecked")
		List<Users> list = query.list();

		if (list == null || list.size() != 1){

			throw new NoResultException("No result found in users for screenName:  " + screenName);
		}

		return list.get(0);
	}

	
}