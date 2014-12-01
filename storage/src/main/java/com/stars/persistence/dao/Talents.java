package com.stars.persistence.dao;

import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.stars.persistence.dbaccess.PersistenceManagerFactory;

@Entity
@Cacheable(false)
@Table(name = "talents")
public class Talents {

	private static Logger logger = Logger.getLogger(Talents.class.getName());
	private Long talentId;
	private String type;
	private Date created;

	public Talents(){}
	
	@Id
	@Column(name = "talent_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getTalentId() {
		return talentId;
	}

	public void setTalentId(Long talentId) {
		this.talentId = talentId;
	}
	
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void save() throws Exception {
		PersistenceManagerFactory.getInstance().getPersistenceManager()
				.saveOrUpdate(this);
	}
}
