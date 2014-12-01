package com.stars.persistence.dao;

import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.stars.persistence.dbaccess.PersistenceManagerFactory;

@Entity
@Cacheable(false)
@Table(name = "profiles")
public class Profiles {

	private static Logger logger = Logger.getLogger(Profiles.class.getName());
	private Long profileId;
	private Talents talent;
	private Date created;
	private Date modified;
	private String name;

	public Profiles(){
	}
	
	@Id
	@Column(name = "profile_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getProfileId() {
		return profileId;
	}
	
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "talents_talent_id")
	public Talents getTalent() {
		return talent;
	}

	public void setTalent(Talents talent) {
		this.talent = talent;
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

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void save() throws Exception {

		this.setModified(new Date());
		PersistenceManagerFactory.getInstance().getPersistenceManager()
				.saveOrUpdate(this);
	}
}
