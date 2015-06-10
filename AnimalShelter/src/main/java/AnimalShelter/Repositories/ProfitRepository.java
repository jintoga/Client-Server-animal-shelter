/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnimalShelter.Repositories;

import AnimalShelter.Core.Profit;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author DAT
 */
public interface ProfitRepository extends JpaRepository<Profit, Long> {

    Collection<Profit> findByYearreceive(String yearreceive);

    Collection<Profit> findByMonthreceive(String monthreceive);

    Collection<Profit> findByYearreceiveAndMonthreceive(String yearreceive, String monthreceive);
}
