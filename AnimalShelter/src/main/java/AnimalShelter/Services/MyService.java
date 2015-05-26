/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnimalShelter.Services;

import AnimalShelter.Core.Animal;
import AnimalShelter.Core.Expense;
import AnimalShelter.Core.Owner;
import AnimalShelter.Core.Profit;
import AnimalShelter.Core.Shelter;
import AnimalShelter.Core.Sponsor;
import AnimalShelter.Core.Staff;
import AnimalShelter.Core.Support_Type;
import AnimalShelter.Core.TempOwner;
import AnimalShelter.Core.Type_Animal;
import AnimalShelter.Core.Type_AnimalHelper;
import AnimalShelter.Core.Volunteer;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author DAT
 */
public interface MyService {

    public static final int isApprovedByAdmin = 2;
    public static final int isWaitApprovedByAdmin = 0;

    Collection<Animal> animalsIsApprovedByAdmin();

    Collection<Animal> animalsWaitForApprove();

    Collection<Type_Animal> type_animal();

    Collection<TempOwner> temp_owners();

    Collection<Owner> owners();

    Collection<Shelter> shelters();

    Collection<Staff> staves();

    Collection<Volunteer> volunteers();

    Collection<Sponsor> sponsors();

    Collection<Profit> profits();

    Collection<Support_Type> support_types();

    Collection<Expense> expenses();

    Animal animal(Long id);

    TempOwner temp_owner(Long id);

    Owner owner(Long id);

    Shelter shelter(Long id);

    Staff staff(Long id);

    Volunteer volunteer(Long id);

    Sponsor sponsor(Long id);

    Profit profit(Long id);

    Support_Type support_type(Long id);

    Expense expense(Long id);

    public Expense addExpense(String product, int price, String organization, String date_use, String description);

    public Animal addAnimal(Long pk_type_animal, String name, String type, int gender, float weight, String breed, int age, String description, int sterilized, boolean isApproved);

    public Type_Animal findAnimalPK(String type_animal);

    public void filterGender();

    public void filterSterilized();

    public void filterAge();

    public void filterOrganization();

    void deleteAnimal(Long pk_animal);

    void deleteExpense(Long pk_expense);

    void updateAnimal(Long id, Long pk_type_animaln, String name, String type, int gender, float weight, String breed, int age, String description, int sterilized);

    void updateExpense(Long id, String product, int price, String organization, String date_use, String description);

    public TempOwner addTempOwner(String name, int telephone, String address, int amount_of_animal);

    public Owner addOwner(String name, int telephone, String address, int amount_of_animal);

    void deleteTempOwner(Long pk_temp_owner);

    void deleteOwner(Long pk_owner);

    public void updateTempOwner(Long pk_temp_owner, String name, int telephone, String address, int amount_of_animal);

    public void updateOwner(Long pk_owner, String name, int telephone, String address, int amount_of_animal);

    public Shelter addShelter(String name, int telephone, String address, int seat, int free_seat, String site, String email, String description);

    public Staff addStaff(String name, String career, int telephone, String date_of_birth, String address, String description);

    public Volunteer addVolunteer(String name, String career, int telephone, String date_of_birth, String address, String description);

    public Sponsor addSponsor(String name, int telephone, String address, String site, String email, String description, int is_organization);

    void deleteShelter(Long pk_shelter);

    void deleteStaff(Long pk_staff);

    void deleteVolunteer(Long pk_volunteer);

    void deleteSponsor(Long pk_sponsor);

    void deleteProfit(Long pk_profit);

    public void updateShelter(Long pk_shelter, String name, int telephone, String address, int seat, int free_seat, String site, String email, String description);

    public void updateStaff(Long pk_staff, String name, String career, Integer telephone, String date_of_birth, String address, String description);

    public void updateVolunteer(Long pk_volunteer, String name, String career, Integer telephone, String date_of_birth, String address, String description);

    public void updateSponsor(Long pk_sponsor, String name, int telephone, String address, String site, String email, String description, int is_organization);

    public ArrayList<Type_AnimalHelper> getListHelper(Long v1, Integer v2, Integer v3, Integer isApproved);

    public Profit addProfit(Long pk_sponsor, Long pk_support_type, int amount, String description, String date_receive);

    public void updateProfit(Long pk_profit, Long pk_sponsor, Long pk_support_type, int amount, String description, String date_receive);

    public Support_Type addSupport_type(String title);

    public void deleteSupport_type(Long pk_support_type);

    public void updateSupport_type(Long pk_support_type, String title);

    void approveAnimal(Long id);

}
