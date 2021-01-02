package com.lambdaschool.countries.controllers;

import com.lambdaschool.countries.models.Country;
import com.lambdaschool.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController
{
    @Autowired
    CountryRepository countryrepos;

    // Sorted by name
    // http://localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> getAlCountries()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> c1.getName()
            .compareToIgnoreCase(c2.getName()));

        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    // List of all counties that begin with a certain letter
    // http://localhost:2019/names/start/u
    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> getCountriesWithStartLetter(@PathVariable char letter)
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);

        List<Country> rtnList = HelperFunctions.findCountries(myList, c -> c.getName()
            .toUpperCase()
            .charAt(0) == Character.toUpperCase(letter));

        rtnList.sort((c1, c2) -> c1.getName()
            .compareToIgnoreCase(c2.getName()));

        return new ResponseEntity<>(rtnList,
            HttpStatus.OK);
    }

    // Total population
    // http://localhost:2019/population/total
    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> findLargerPopulations()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);

        long total = 0;

        for (Country c : myList)
        {
            total = total + c.getPopulation();
        }

        return new ResponseEntity<>("The total population is " + total, HttpStatus.OK);
    }

    // Smallest population
    // http://localhost:2019/population/min
    @GetMapping(value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> findMinPopulation()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> (int) (c1.getPopulation() - c2.getPopulation()));

        Country rtnCountry = myList.get(0);

        return new ResponseEntity<>(rtnCountry, HttpStatus.OK);
    }

    // Largest population
    // http://localhost:2019/population/max
    @GetMapping(value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> findMaxPopulation()
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> (int) (c2.getPopulation() - c1.getPopulation()));

        Country rtnCountry = myList.get(0);
        return new ResponseEntity<>(rtnCountry, HttpStatus.OK);
    }
}
