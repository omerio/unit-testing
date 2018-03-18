/**
 * 
 */
package com.omerio.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author omerio
 *
 */
@Entity
public class Seller implements Serializable {
	
	private static final long serialVersionUID = 6227575431859385950L;

    @Id
    @GeneratedValue
	private Long id;
	
	private String name;
	
	private String emailAddress;
	
	private BigDecimal deliveryFees;

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

	public BigDecimal getDeliveryFees() {
		return deliveryFees;
	}

	public void setDeliveryFees(BigDecimal deliveryFees) {
		this.deliveryFees = deliveryFees;
	}

	
}
