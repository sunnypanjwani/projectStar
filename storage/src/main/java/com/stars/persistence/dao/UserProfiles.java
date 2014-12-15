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
import javax.persistence.ManyToOne;
import javax.persistence.NoResultException;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.Query;

import com.stars.persistence.dbaccess.PersistenceManager;
import com.stars.persistence.dbaccess.PersistenceManagerFactory;

@Entity
@Cacheable(false)
@Table(name = "user_profiles")
public class UserProfiles {

	private static Logger logger = Logger.getLogger(UserProfiles.class
			.getName());
	private Long userProfileId;
	private Users user;
	private Profiles profile;
	private Date created;
	private Date modified;

	public UserProfiles() {
	}

	@Id
	@Column(name = "user_profile_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_user_id")
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profiles_profile_id")
	public Profiles getProfile() {
		return profile;
	}

	public void setProfile(Profiles profile) {
		this.profile = profile;
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

	public void save() throws Exception {

		this.setModified(new Date());
		PersistenceManagerFactory.getInstance().getPersistenceManager()
				.saveOrUpdate(this);
	}

	public static UserProfiles load(Long usersProfileId) throws Exception {

		PersistenceManager persistMgr = PersistenceManagerFactory.getInstance()
				.getPersistenceManager();

		Query query = persistMgr
				.getSession()
				.createSQLQuery(
						"select * from user_profiles where user_profile_id = :usersProfileId")
				.addEntity(UserProfiles.class);
		query.setParameter("usersProfileId", usersProfileId);
		logger.info("Executing Query : " + query.getQueryString());

		@SuppressWarnings("unchecked")
		List<UserProfiles> list = query.list();

		if (list == null || list.size() != 1) {

			throw new NoResultException(
					"No result or more than one result found for userProfileId:"
							+ usersProfileId);
		}

		return list.get(0);
	}

	public static List<UserProfiles> loadWithUserId(Long userId)
			throws Exception {

		PersistenceManager persistMgr = PersistenceManagerFactory.getInstance()
				.getPersistenceManager();

		Query query = persistMgr
				.getSession()
				.createSQLQuery(
						"select * from user_profiles where users_user_id = :userId")
				.addEntity(UserProfiles.class);
		query.setParameter("userId", userId);
		logger.info("Executing Query : " + query.getQueryString());

		@SuppressWarnings("unchecked")
		List<UserProfiles> list = query.list();

		if (list == null) {

			throw new NoResultException("No result found for userProfileId:"
					+ userId);
		}

		return list;
	}
}
