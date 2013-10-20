package com.taxiservice.web.controller;


import com.taxiservice.model.reader.AdditionalReader;
import com.taxiservice.model.reader.DriverReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

@Controller
public class AdditionalController extends BasicSecurityController {

    @Inject
    AdditionalReader additionalReader;

    @RequestMapping(value = "/countries")
    @ResponseBody
    List<DriverReader.CountryLine> getCountries(){
         return  additionalReader.readCountries();
    }

    @RequestMapping(value = "/cities/country/{id}")
    @ResponseBody
    List<DriverReader.CityLine> getCities(@PathVariable long id) {
        return additionalReader.readCities(id);
    }

    @RequestMapping(value = "/comments/top", method = RequestMethod.GET)
    @ResponseBody
    List<DriverReader.Feedback> getTopComments() {
        return additionalReader.readLastComments(10);
    }

    @RequestMapping(value = "/cities/{id}", method = RequestMethod.GET)
    @ResponseBody
    DriverReader.CityLine getCity(@PathVariable long id) {
        return additionalReader.readCity(id);
    }
}
