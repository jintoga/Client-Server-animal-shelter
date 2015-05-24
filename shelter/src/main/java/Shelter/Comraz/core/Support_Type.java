/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shelter.Comraz.core;

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
@Table(name = "support_type")
public class Support_Type implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long pk_support_type;

    @Column(nullable = false, unique = false)
    private String title;

    public Support_Type() {
    }

    public Support_Type(String title) {
        this.title = title;
    }
    
    public void updateSupport_Type(String title) {
        this.title = title;
    }

    public Long getPk_support_type() {
        return pk_support_type;
    }

    public void setPk_support_type(Long pk_support_type) {
        this.pk_support_type = pk_support_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
