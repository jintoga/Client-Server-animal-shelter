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
@Table(name = "animal")

public class Animal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long pk_animal;

    @Column(nullable = true, unique = false)
    private String name;
    private String type;
    private String color;
    private String health_status;
    private float weight;
    private String breed;
    private String relationship_with_human;
    private int age;

    private String description;
    private Boolean is_were_owner;

    private int gender;
    @Column(nullable = true, unique = false)
    private int sterilized;
    private int isapproved;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pk_type_animal")
    private Type_Animal typeanimal;

    private String showGender;
    private String sterilized_status;
    private String showAge;

    public String getShowAge() {
        return showAge;
    }

    public void setShowAge(String showAge) {
        this.showAge = showAge;
    }

    public String getShowGender() {
        return showGender;
    }

    public void setShowGender(String showGender) {
        this.showGender = showGender;
    }

    public void setSterilized_status(String sterilized_status) {
        this.sterilized_status = sterilized_status;
    }

    public String getSterilized_status() {
        return sterilized_status;
    }

    public Animal() {
    }

    public Animal(Type_Animal type_animal, String name, String type, int gender, String color, String health_status, float weight, String breed, String relationship_with_human, int age, String description, int sterilized) {
        this.typeanimal = type_animal;
        this.name = name;
        this.type = type;
        this.gender = gender;
        this.color = color;
        this.health_status = health_status;
        this.weight = weight;
        this.breed = breed;
        this.relationship_with_human = relationship_with_human;
        this.age = age;

        this.description = description;
        this.sterilized = sterilized;
        // this.is_were_owner = is_were_owner;
    }

    public Long getId() {
        return pk_animal;
    }

    public Long getPk_animal() {
        return pk_animal;
    }

    public String getName() {
        return name;
    }

    public Type_Animal getType_animal() {
        return typeanimal;
    }

    public int getGender() {
        return gender;
    }

    public String getColor() {
        return color;
    }

    public String getHealth_status() {
        return health_status;
    }

    public float getWeight() {
        return weight;
    }

    public String getBreed() {
        return breed;
    }

    public String getRelationship_with_human() {
        return relationship_with_human;
    }

    public int getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIs_were_owner() {
        return is_were_owner;
    }

    public String getType() {
        return type;
    }

    public void updateAnimal(Type_Animal type_animal, String name, String type, int gender, String color, String health_status, float weight, String breed, String relationship_with_human, int age, String description, int sterilized) {
        this.typeanimal = type_animal;
        this.name = name;
        this.type = type;
        this.gender = gender;
        this.color = color;
        this.health_status = health_status;
        this.weight = weight;
        this.breed = breed;
        this.relationship_with_human = relationship_with_human;
        this.age = age;

        this.description = description;
        this.sterilized = sterilized;

    }

    public int getSterilized() {
        return sterilized;
    }

    public int getIsapproved() {
        return isapproved;
    }

    public void setIs_approved(int is_approved) {
        this.isapproved = is_approved;
    }
}
