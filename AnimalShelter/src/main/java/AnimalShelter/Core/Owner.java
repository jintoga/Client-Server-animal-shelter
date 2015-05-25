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
@Table(name = "owner")
public class Owner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long pk_owner;

    @Column(nullable = false, unique = false)
    private String name;
    private int telephone;
    private String address;
    private int amount_of_animal;

    public Owner() {
    }

    public Owner(String name, int telephone, String address, int amount_of_animal) {

        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.amount_of_animal = amount_of_animal;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPk_owner() {
        return pk_owner;
    }

    public void setPk_owner(Long pk_owner) {
        this.pk_owner = pk_owner;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAmount_of_animal() {
        return amount_of_animal;
    }

    public void setAmount_of_animal(int amount_of_animal) {
        this.amount_of_animal = amount_of_animal;
    }

    public void updateOwner(String name, int telephone, String address, int amount_of_animal) {
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.amount_of_animal = amount_of_animal;

    }
}
