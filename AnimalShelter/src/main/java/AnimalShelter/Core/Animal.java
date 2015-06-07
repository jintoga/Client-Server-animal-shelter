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
    private float weight;
    private String breed;
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
    private String dateReceived;
    private String phoneNumber;

    private String last_date_seen;
    private String last_location;

    private String latitude;
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLast_date_seen() {
        return last_date_seen;
    }

    public void setLast_date_seen(String last_date_seen) {
        this.last_date_seen = last_date_seen;
    }

    public String getLast_location() {
        return last_location;
    }

    public void setLast_location(String last_location) {
        this.last_location = last_location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

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

    public Animal(Type_Animal type_animal, String name, String type, int gender, float weight, String breed, int age, String description, int sterilized) {
        this.typeanimal = type_animal;
        this.name = name;
        this.type = type;
        this.gender = gender;
        this.weight = weight;
        this.breed = breed;
        this.age = age;

        this.description = description;
        this.sterilized = sterilized;
        // this.is_were_owner = is_were_owner;
    }

    public Animal(Type_Animal type_animal, String type, String description) {
        this.typeanimal = type_animal;

        this.type = type;

        this.description = description;
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

    public float getWeight() {
        return weight;
    }

    public String getBreed() {
        return breed;
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

    public void setPk_animal(Long pk_animal) {
        this.pk_animal = pk_animal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIs_were_owner(Boolean is_were_owner) {
        this.is_were_owner = is_were_owner;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setSterilized(int sterilized) {
        this.sterilized = sterilized;
    }

    public void setIsapproved(int isapproved) {
        this.isapproved = isapproved;
    }

    public void updateAnimal(Type_Animal type_animal, String name, String type, int gender, float weight, String breed, int age, String description, int sterilized) {
        this.typeanimal = type_animal;
        this.name = name;
        this.type = type;
        this.gender = gender;
        this.weight = weight;
        this.breed = breed;
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

    public Type_Animal getTypeanimal() {
        return typeanimal;
    }

    public void setTypeanimal(Type_Animal typeanimal) {
        this.typeanimal = typeanimal;
    }

}
