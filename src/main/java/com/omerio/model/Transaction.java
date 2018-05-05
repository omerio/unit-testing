package com.omerio.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author omerio
 *
 */
@Entity
public class Transaction implements Serializable {

    private static final long serialVersionUID = -1982058097348765461L;

    @Id
    @GeneratedValue
    private Long id;

    private String reference;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private BigDecimal amount;

    private BigDecimal serviceFees;

    private BigDecimal tax;

    private BigDecimal deliveryCharge;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Seller seller;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Customer customer;

    /**
     * Calculate the net amount received by the seller
     * @return - the net amount paid to the seller
     */
    public BigDecimal calculateSellerNetAmount() {
        BigDecimal net = BigDecimal.ZERO;

        if(amount != null) {
            net = net.add(amount);
        }

        if(serviceFees != null) {
            net = net.subtract(serviceFees);
        }

        if(tax != null) {
            net = net.subtract(tax);
        }

        if(deliveryCharge != null) {
            net = net.add(deliveryCharge);
        }

        return net.setScale(2, RoundingMode.HALF_UP);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getDate() {
        // http://findbugs.sourceforge.net/bugDescriptions.html#EI_EXPOSE_REP
        if(this.date != null) {
            return new Date(this.date.getTime());
        } else {
            return null;
        }
    }

    public void setDate(Date date) {
     // http://findbugs.sourceforge.net/bugDescriptions.html#EI_EXPOSE_REP
        if(date != null) {
            this.date = new Date(date.getTime());
        } else {
            this.date = null;
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getServiceFees() {
        return serviceFees;
    }

    public void setServiceFees(BigDecimal serviceFees) {
        this.serviceFees = serviceFees;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(BigDecimal deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
