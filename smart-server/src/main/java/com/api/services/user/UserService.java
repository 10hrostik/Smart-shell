package com.api.services.user;

import com.api.model.adress.Adress;
import com.api.model.adress.City;
import com.api.model.adress.Country;
import com.api.model.user.User;
import com.api.repositories.ResidenseRepository;
import com.api.services.residence.AdressBuilder;
import com.api.services.user.utils.EditPasswordDto;
import com.api.services.user.utils.EditUserDto;
import com.api.services.user.utils.ResponseUserDto;
import com.api.services.user.utils.UserDtoBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService extends GeneralUserService {
    @Autowired
    private ResidenseRepository<Country> countryRepository;

    @Autowired
    private ResidenseRepository<City> cityRepository;

    @Autowired
    private ResidenseRepository<Adress> adressRepository;

    @Transactional
    public ResponseUserDto edit(EditUserDto userDto) {
        User user = userRepository.getUserByUsernameOrEmail(userDto.getUsername());
        editResidense(userDto.getAdress(), userDto.getCity(), userDto.getCountry(), user);
        ResponseUserDto response = UserDtoBuilder.editEntity(userDto, user);
        userRepository.update(user);

        return response;
    }

    @Transactional
    public void editPassword(EditPasswordDto userDto) {
        User user = userRepository.getUserByUsernameOrEmail(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        userRepository.update(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }

    private void editResidense(String adress, String city, String country, User user) {
        List<?> list = AdressBuilder.generateResidence(countryRepository.findByDetails(country),
                cityRepository.findByDetails(country, city),
                adressRepository.findByDetails(city, country, adress),
                new String[] {adress, city, country});

        list.forEach(el -> {
            if (el instanceof Country) {
                countryRepository.persist((Country) el);
                user.setCountry((Country) el);
            }
            else if (el instanceof City) {
                cityRepository.persist((City) el);
                user.setCity((City) el);
            }
            else if (el instanceof Adress) {
                adressRepository.persist((Adress) el);
                user.setAdress((Adress) el);
            }
        });
    }
}
