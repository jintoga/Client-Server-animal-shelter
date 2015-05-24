/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shelter.Comraz.svc;

import Shelter.Comraz.core.Animal;
import Shelter.Comraz.core.Expense;
import Shelter.Comraz.core.Owner;
import Shelter.Comraz.core.Profit;
import Shelter.Comraz.core.Shelter;
import Shelter.Comraz.core.Sponsor;
import Shelter.Comraz.core.Staff;
import Shelter.Comraz.core.Support_Type;
import Shelter.Comraz.core.TempOwner;
import Shelter.Comraz.core.Type_Animal;
import Shelter.Comraz.core.Type_AnimalHelper;
import Shelter.Comraz.core.Volunteer;
import Shelter.Comraz.repo.AnimalRepository;
import Shelter.Comraz.repo.ExpenseRepository;
import Shelter.Comraz.repo.OwnerRepository;
import Shelter.Comraz.repo.ProfitRepository;
import Shelter.Comraz.repo.ShelterRepository;
import Shelter.Comraz.repo.SponsorRepository;
import Shelter.Comraz.repo.StaffRepository;
import Shelter.Comraz.repo.SupportTypeRepository;
import Shelter.Comraz.repo.TempOwnerRepository;
import Shelter.Comraz.repo.TypeAnimalRepository;
import Shelter.Comraz.repo.VolunteerRepository;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DAT
 */
@Service
public class MyServiceImp implements MyService {

    @Autowired
    private AnimalRepository animals;

    @Autowired
    private TempOwnerRepository temp_owners;

    @Autowired
    private OwnerRepository owners;

    @Autowired
    private TypeAnimalRepository type_animals;

    @Autowired
    private ShelterRepository shelters;

    @Autowired
    private StaffRepository staves;

    @Autowired
    private VolunteerRepository volunteers;

    @Autowired
    private SponsorRepository sponsors;

    @Autowired
    private ProfitRepository profits;

    @Autowired
    private SupportTypeRepository support_types;

    @Autowired
    private ExpenseRepository expenses;

    protected final Log log = LogFactory.getLog(getClass());

    @Override
    public Animal animal(Long id) {
        return animals.getOne(id);
    }

    @Override
    public Collection<Animal> animalsIsApprovedByAdmin() {

        return animals.findByIsapproved(isApprovedByAdmin);
    }

    @Override
    public Collection<Animal> animalsWaitForApprove() {
        return animals.findByIsapproved(isWaitApprovedByAdmin);
    }

    @Override
    public Collection<Staff> staves() {
        return staves.findAll();
    }

    @Override
    public Staff staff(Long id) {
        return staves.getOne(id);
    }

    @Override
    public Collection<Volunteer> volunteers() {
        return volunteers.findAll();
    }

    @Override
    public Volunteer volunteer(Long id) {
        return volunteers.getOne(id);
    }

    @Override
    public Animal addAnimal(Long pk_type_animal, String name, String type, int gender, String color, String health_status, float weight, String breed, String relationship_with_human, int age, String description, int sterilized, boolean isApproved) {
        Type_Animal type_animal = type_animals.getOne(pk_type_animal);
        Animal animal = new Animal(type_animal, name, type, gender, color, health_status, weight, breed, relationship_with_human, age, description, sterilized);
        if (isApproved) {
            animal.setIs_approved(isApprovedByAdmin);
        }
        return animals.save(animal);
    }

    @Override
    public Collection<Type_Animal> type_animal() {
        return type_animals.findAll();
    }

    @Override
    public Type_Animal findAnimalPK(String type_animal) {
        for (Type_Animal t : type_animals.findAll()) {
            if (type_animal.equals(t.getTitle())) {
                return t;
            }
        }
        return null;
    }

    @Override
    public void filterOrganization() {

        for (Sponsor sponsor : sponsors.findAll()) {
            if (sponsor.getIs_organization() == 1) {
                sponsor.setShowIs_organization("Да");
            } else if (sponsor.getIs_organization() == 2) {
                sponsor.setShowIs_organization("Нет");
            }
        }
    }

    @Override
    public void filterGender() {

        for (Animal animal : animals.findAll()) {
            if (animal.getGender() == 1) {
                animal.setShowGender("Мальчик");
            } else if (animal.getGender() == 2) {
                animal.setShowGender("Девочка");
            }
        }
    }

    @Override
    public void filterSterilized() {
        for (Animal animal : animals.findAll()) {
            if (animal.getSterilized() == 1) {
                animal.setSterilized_status("Да");
            } else if (animal.getSterilized() == 2) {
                animal.setSterilized_status("Нет");
            }
        }
    }

    @Override
    public void deleteAnimal(Long pk_animal) {
        animals.delete(pk_animal);
    }

    @Override
    public void updateAnimal(Long pk_animal, Long pk_type_animal, String name, String type, int gender, String color, String health_status, float weight, String breed, String relationship_with_human, int age, String description, int sterilized) {
        Animal animal = animal(pk_animal);
        Type_Animal type_animal = type_animals.getOne(pk_type_animal);
        animal.updateAnimal(type_animal, name, type, gender, color, health_status, weight, breed, relationship_with_human, age, description, sterilized);
        animals.save(animal);
    }

    @Override
    public Collection<TempOwner> temp_owners() {
        return temp_owners.findAll();
    }

    @Override
    public TempOwner temp_owner(Long id) {
        return temp_owners.getOne(id);
    }

    @Override
    public Collection<Owner> owners() {
        return owners.findAll();
    }

    @Override
    public Owner owner(Long id) {
        return owners.getOne(id);
    }

    @Override
    public TempOwner addTempOwner(String name, int telephone, String address, int amount_of_animal) {
        return temp_owners.save(new TempOwner(name, telephone, address, amount_of_animal));
    }

    @Override
    public Owner addOwner(String name, int telephone, String address, int amount_of_animal) {
        return owners.save(new Owner(name, telephone, address, amount_of_animal));
    }

    @Override
    public void updateOwner(Long pk_owner, String name, int telephone, String address, int amount_of_animal) {
        Owner owner = owner(pk_owner);
        owner.updateOwner(name, telephone, address, amount_of_animal);
        owners.save(owner);
    }

    @Override
    public void updateTempOwner(Long pk_temp_owner, String name, int telephone, String address, int amount_of_animal) {
        TempOwner tOwner = temp_owner(pk_temp_owner);
        tOwner.updateTempOwner(name, telephone, address, amount_of_animal);
        temp_owners.save(tOwner);
    }

    @Override
    public void deleteTempOwner(Long pk_temp_owner) {
        temp_owners.delete(pk_temp_owner);
    }

    @Override
    public void deleteOwner(Long pk_owner) {
        owners.delete(pk_owner);
    }

    @Override
    public Collection<Shelter> shelters() {
        return shelters.findAll();
    }

    @Override
    public Shelter addShelter(String name, int telephone, String address, int seat, int free_seat, String site, String email, String description) {
        return shelters.save(new Shelter(name, telephone, address, seat, free_seat, site, email, description));
    }

    @Override
    public Staff addStaff(String name, String career, int telephone, String date_of_birth, String address, String description) {
        return staves.save(new Staff(name, career, telephone, date_of_birth, address, description));
    }

    @Override
    public Volunteer addVolunteer(String name, String career, int telephone, String date_of_birth, String address, String description) {
        return volunteers.save(new Volunteer(name, career, telephone, date_of_birth, address, description));
    }

    @Override
    public Shelter shelter(Long id) {
        return shelters.getOne(id);
    }

    @Override
    public void deleteShelter(Long pk_shelter) {
        shelters.delete(pk_shelter);
    }

    @Override
    public void deleteStaff(Long pk_staff) {
        staves.delete(pk_staff);
    }

    @Override
    public void deleteVolunteer(Long pk_volunteer) {
        volunteers.delete(pk_volunteer);
    }

    @Override
    public void updateShelter(Long pk_shelter, String name, int telephone, String address, int seat, int free_seat, String site, String email, String description) {
        Shelter shelter = shelters.getOne(pk_shelter);
        shelter.updateShelter(name, telephone, address, seat, free_seat, site, email, description);
        shelters.save(shelter);
    }

    @Override
    public void updateStaff(Long pk_staff, String name, String career, Integer telephone, String date_of_birth, String address, String description) {
        Staff staff = staves.getOne(pk_staff);
        staff.updateStaff(name, career, telephone, date_of_birth, address, description);
        staves.save(staff);
    }

    @Override
    public void updateVolunteer(Long pk_volunteer, String name, String career, Integer telephone, String date_of_birth, String address, String description) {
        Volunteer volunteer = volunteers.getOne(pk_volunteer);
        volunteer.updateVolunteer(name, career, telephone, date_of_birth, address, description);
        volunteers.save(volunteer);
    }

    @Override
    public ArrayList<Type_AnimalHelper> getListHelper(Long v1, Integer v2, Integer v3, Integer isApproved) {
        Type_Animal type_animal = type_animals.getOne(v1);
        ArrayList<Type_AnimalHelper> listHelper = new ArrayList<>();
        Collection<Animal> res;
        if (v1 == 0 && v2 == 0 && v3 == 0) {
            res = animals.findByIsapproved(isApproved);
        } else if (v1 != 0 && v2 == 0 && v3 == 0) {
            res = animals.findByTypeanimalAndIsapproved(type_animal, isApproved);
        } else if (v1 == 0 && v2 != 0 && v3 == 0) {
            res = animals.findByGenderAndIsapproved(v2, isApproved);
        } else if (v1 == 0 && v2 == 0 && v3 != 0) {
            res = animals.findByAgeAndIsapproved(v3, isApproved);
        } else if (v1 == 0 && v2 != 0 && v3 != 0) {
            res = animals.findByGenderAndAgeAndIsapproved(v2, v3, isApproved);
        } else if (v1 != 0 && v2 != 0 && v3 == 0) {
            res = animals.findByTypeanimalAndGenderAndIsapproved(type_animal, v2, isApproved);
        } else if (v1 != 0 && v2 == 0 && v3 != 0) {
            res = animals.findByTypeanimalAndAgeAndIsapproved(type_animal, v3, isApproved);
        } else {
            res = animals.findByTypeanimalAndGenderAndAgeAndIsapproved(type_animal, v2, v3, isApproved);
        }

        for (Animal animal : res) {
            Type_AnimalHelper helper = new Type_AnimalHelper(animal);

            listHelper.add(helper);
        }
        return listHelper;
    }

    @Override
    public void filterAge() {
        for (Animal animal : animals.findAll()) {
            if (animal.getAge() == 1) {
                animal.setShowAge("< 1 Года");
            } else if (animal.getAge() == 2) {
                animal.setShowAge("1 - 3 Лет");
            } else if (animal.getAge() == 3) {
                animal.setShowAge("3 - 5 Лет");
            } else if (animal.getAge() == 4) {
                animal.setShowAge("> 5 Лет");
            }
        }
    }

    @Override
    public Collection<Sponsor> sponsors() {
        return sponsors.findAll();
    }

    @Override
    public Sponsor sponsor(Long id) {
        return sponsors.getOne(id);
    }

    @Override
    public Sponsor addSponsor(String name, int telephone, String address, String site, String email, String description, int is_organization) {
        return sponsors.save(new Sponsor(name, telephone, address, site, email, description, is_organization));
    }

    @Override
    public void deleteSponsor(Long pk_sponsor) {
        sponsors.delete(pk_sponsor);
    }

    @Override
    public void updateSponsor(Long pk_sponsor, String name, int telephone, String address, String site, String email, String description, int is_organization) {
        Sponsor sponsor = sponsors.getOne(pk_sponsor);
        sponsor.updateSponsor(name, telephone, address, site, email, description, is_organization);
        sponsors.save(sponsor);
    }

    @Override
    public Collection<Profit> profits() {
        return profits.findAll();
    }

    @Override
    public Collection<Support_Type> support_types() {
        return support_types.findAll();
    }

    @Override
    public Profit profit(Long id) {
        return profits.getOne(id);
    }

    @Override
    public Support_Type support_type(Long id) {
        return support_types.getOne(id);
    }

    @Override
    public Profit addProfit(Long pk_sponsor, Long pk_support_type, int amount, String description, String date_receive) {
        Sponsor sponsor = sponsors.getOne(pk_sponsor);
        Support_Type support_type = support_types.getOne(pk_support_type);
        return profits.save(new Profit(sponsor, support_type, amount, description, date_receive));

    }

    @Override
    public void deleteProfit(Long pk_profit) {
        profits.delete(pk_profit);
    }

    @Override
    public void updateProfit(Long pk_profit, Long pk_sponsor, Long pk_support_type, int amount, String description, String date_receive) {
        Profit profit = profits.getOne(pk_profit);
        Sponsor sponsor = sponsors.getOne(pk_sponsor);
        Support_Type support_type = support_types.getOne(pk_support_type);
        profit.updateProfit(sponsor, support_type, amount, description, date_receive);
        profits.save(profit);
    }

    @Override
    public Collection<Expense> expenses() {
        return expenses.findAll();
    }

    @Override
    public Expense expense(Long id) {
        return expenses.getOne(id);
    }

    @Override
    public Expense addExpense(String product, int price, String organization, String date_use, String description) {
        return expenses.save(new Expense(product, price, organization, date_use, description));
    }

    @Override
    public void deleteExpense(Long pk_expense) {
        expenses.delete(pk_expense);
    }

    @Override
    public void updateExpense(Long id, String product, int price, String organization, String date_use, String description) {
        Expense expense = expense(id);
        expense.updateExpense(product, price, organization, date_use, description);
        expenses.save(expense);
    }

    @Override
    public Support_Type addSupport_type(String title) {
        return support_types.save(new Support_Type(title));
    }

    @Override
    public void deleteSupport_type(Long pk_support_type) {
        support_types.delete(pk_support_type);
    }

    @Override
    public void updateSupport_type(Long pk_support_type, String title) {
        Support_Type support_type = support_type(pk_support_type);
        support_type.updateSupport_Type(title);
        support_types.save(support_type);
    }

    @Override
    public void approveAnimal(Long id) {
        Animal animal = animal(id);
        animal.setIs_approved(isApprovedByAdmin);
        animals.save(animal);
    }

}
