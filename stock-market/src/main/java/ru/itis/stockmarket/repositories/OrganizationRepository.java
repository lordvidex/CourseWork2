package ru.itis.stockmarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.stockmarket.models.Country;
import ru.itis.stockmarket.models.Organization;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA
 * Date: 14.05.2022
 * Time: 1:34 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
    Optional<Organization> findByAddressAndNameAndCountry(String address, String name, Country country);
    List<Organization> findByCountry_Code(String code);
}
