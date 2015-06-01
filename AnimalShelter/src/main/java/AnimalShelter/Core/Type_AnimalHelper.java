/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnimalShelter.Core;

/**
 *
 * @author DAT
 */
public class Type_AnimalHelper {

    private Long pk_animal;

    private String name;
    private String type;
    private float weight;
    private String breed;
    private int age;

    private String description;
    private Boolean is_were_owner;

    private int gender;
    private int sterilized;

    private String typeanimal;

    private String showGender;
    private String sterilized_status;

    private String showAge;
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Type_AnimalHelper() {

    }

    public Type_AnimalHelper(Animal animal) {
        this.setPk_animal(animal.getPk_animal());
        this.setName(animal.getName());
        this.setType(animal.getType());
        this.setAge(animal.getAge());
        this.setBreed(animal.getBreed());
        this.setGender(animal.getGender());
        this.setSterilized(animal.getSterilized());
        this.setWeight(animal.getWeight());
        this.setDescription(animal.getDescription());
        this.setTypeanimal(animal.getType_animal().getTitle());
    }

    public Type_AnimalHelper(String type_animal, String name, String type, int gender, float weight, String breed, int age, String description, int sterilized) {
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

    public Long getId() {
        return pk_animal;
    }

    public Long getPk_animal() {
        return pk_animal;
    }

    public String getName() {
        return name;
    }

    public String getType_animal() {
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

    public void updateAnimal(String type_animal, String name, String type, int gender, float weight, String breed, int age, String description, int sterilized) {
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

    public void setTypeanimal(String typeanimal) {
        this.typeanimal = typeanimal;
    }

}
