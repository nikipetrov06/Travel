package com.java.travel.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "countries")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "euro_to_currency")
    private Double euroToCurrency;

    @Column(name = "name_of_currency")
    private String currencyName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "countries_has_countries",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "neighbour_id")
    )
    private List<Country> neighbourCountries;
    @ManyToMany(mappedBy = "neighbourCountries",cascade = CascadeType.ALL)
    private List<Country> neighbourToCountries;
}
