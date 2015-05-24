/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shelter.Comraz.repo;

import Shelter.Comraz.core.Animal;
import Shelter.Comraz.core.Type_Animal;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author DAT
 */
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Collection<Animal> findByGenderAndIsapproved(Integer gender,Integer isapproved);

    Collection<Animal> findByAgeAndIsapproved(Integer age,Integer isapproved);

    Collection<Animal> findByTypeanimalAndIsapproved(Type_Animal typeanimal,Integer isapproved);

    Collection<Animal> findByGenderAndAgeAndIsapproved(Integer gender, Integer age,Integer isapproved);

    Collection<Animal> findByTypeanimalAndGenderAndIsapproved(Type_Animal typeanimal, Integer gender,Integer isapproved);

    Collection<Animal> findByTypeanimalAndAgeAndIsapproved(Type_Animal typeanimal, Integer age,Integer isapproved);

    Collection<Animal> findByTypeanimalAndGenderAndAgeAndIsapproved(Type_Animal typeanimal, Integer gender, Integer age,Integer isapproved);
    
    Collection<Animal> findByIsapproved(Integer isapproved);
}
