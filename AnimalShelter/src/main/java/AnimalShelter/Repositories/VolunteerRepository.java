/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnimalShelter.Repositories;
 
import AnimalShelter.Core.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author DAT
 */
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

}
