package com.api.services.residence;

import com.api.model.adress.Adress;
import com.api.model.adress.City;
import com.api.model.adress.Country;

import java.util.List;

public class AdressBuilder {
    public static List<?> generateResidence(Country country, City city, Adress adress, String[] names) {
        if (country == null) {
            country = new Country();
            country.setName(names[0]);
        }
        if (city == null) {
            city = new City();
            city.setName(names[1]);
            city.setCountry(country);
        }
        if (adress == null) {
            adress = new Adress();
            adress.setAdress(names[2]);
            adress.setCity(city);
        }

        return List.of(country, adress, city);
    }
}
