package com.java.travel.repositories;

import com.java.travel.entities.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Definiot for CountryRepository methods
 */
@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {
    Optional<Country> findByName(final String name);
}
