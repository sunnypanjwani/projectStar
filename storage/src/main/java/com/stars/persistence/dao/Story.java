package com.stars.persistence.dao;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
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
@Table(name = "story")
public class Story implements java.io.Serializable {	
	private static Logger logger = Logger.getLogger(Users.class.getName());
	private static final long serialVersionUID = 1454644747L;
	
	private Long storyId;
	private Date created;
	private Date modified;	
	private Users user;
	private String description;

	
	public Story(){	
	}
	
	@Id
	@Column(name = "story_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getStoryId() {
		return storyId;
	}
	
	public void setStoryId(Long storyId) {
		this.storyId = storyId;
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


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_user_id")
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void save() throws Exception
	{
		this.setModified(new Date());
		PersistenceManagerFactory.getInstance().getPersistenceManager().saveOrUpdate(this);
	}
}