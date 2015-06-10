/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnimalShelter.Core;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author DAT
 */
@Entity
@Table(name = "profit")
public class Profit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long pk_profit;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pk_sponsor")
    private Sponsor sponsor;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pk_support_type")
    private Support_Type supporttype;

    @Column(nullable = false, unique = false)
    private int amount;
    private String description;
    private String date_receive;

    private String yearreceive;
    private String monthreceive;

    public String getYearreceive() {
        return yearreceive;
    }

    public void setYearreceive(String yearreceive) {
        this.yearreceive = yearreceive;
    }

    public String getMonthreceive() {
        return monthreceive;
    }

    public void setMonthreceive(String monthreceive) {
        this.monthreceive = monthreceive;
    }

    public Profit() {
    }

    public Profit(Sponsor sponsor, Support_Type supporttype, int amount, String description, String date_receive, String yearreceive, String monthreceive) {
        this.sponsor = sponsor;
        this.supporttype = supporttype;
        this.amount = amount;
        this.description = description;
        this.date_receive = date_receive;

        this.yearreceive = yearreceive;
        this.monthreceive = monthreceive;
    }

    public void updateProfit(Sponsor sponsor, Support_Type supporttype, int amount, String description, String date_receive, String yearreceive, String monthreceive) {
        this.sponsor = sponsor;
        this.supporttype = supporttype;
        this.amount = amount;
        this.description = description;
        this.date_receive = date_receive;

        this.yearreceive = yearreceive;
        this.monthreceive = monthreceive;
    }

    public Long getPk_profit() {
        return pk_profit;
    }

    public void setPk_profit(Long pk_profit) {
        this.pk_profit = pk_profit;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public Support_Type getSupporttype() {
        return supporttype;
    }

    public void setSupporttype(Support_Type supporttype) {
        this.supporttype = supporttype;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_receive() {
        return date_receive;
    }

    public void setDate_receive(String date_receive) {
        this.date_receive = date_receive;
    }

}
