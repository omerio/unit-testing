/**
 * 
 */
package com.omerio.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author omerio
 *
 */
public class Customer implements Serializable {

	private static final long serialVersionUID = -2508579631525354989L;
	
    @Id
    @GeneratedValue
	private Long id;
    
    private String name;
    
    private String emailAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
    

}
