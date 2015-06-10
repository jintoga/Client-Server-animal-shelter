/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnimalShelter.Core;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author DAT
 */
@Entity
@Table(name = "sponsor")
public class Sponsor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long pk_sponsor;

    @Column(nullable = false, unique = false)
    private String name;
    private String telephone;
    private String address;
    private String site;
    private String email;
    private String description;

    private int is_organization;

    private String showIs_organization;

    public String getShowIs_organization() {
        return showIs_organization;
    }

    public void setShowIs_organization(String showIs_organization) {
        this.showIs_organization = showIs_organization;
    }

    public Sponsor() {
    }

    public Sponsor(String name, String telephone, String address, String site, String email, String description, int is_organization) {
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.site = site;
        this.email = email;
        this.description = description;

        this.is_organization = is_organization;
    }

    public void updateSponsor(String name, String telephone, String address, String site, String email, String description, int is_organization) {
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.site = site;
        this.email = email;
        this.description = description;

        this.is_organization = is_organization;
    }

    public Long getPk_sponsor() {
        return pk_sponsor;
    }

    public void setPk_sponsor(Long pk_sponsor) {
        this.pk_sponsor = pk_sponsor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIs_organization() {
        return is_organization;
    }

    public void setIs_organization(int is_organization) {
        this.is_organization = is_organization;
    }

}
