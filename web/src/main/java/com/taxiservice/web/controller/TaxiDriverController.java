package com.taxiservice.web.controller;


import com.taxiservice.model.reader.DriverReader;
import com.taxiservice.model.writer.DriverManagement;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import java.util.List;

@Controller
@RequestMapping(value = "/drivers")
public class TaxiDriverController extends BasicSecurityController {

    @Inject
    private DriverReader driverReader;

    @RequestMapping(value = "/city/{country}/{city}", method = RequestMethod.GET)
    @ResponseBody
    public List<DriverManagement.DriverInfo> getDriversByCity(@PathVariable long country, @PathVariable long city){
        return driverReader.readDriversByCity(city, 0, country);
    }

    @RequestMapping(value = "/favourite/{token}")
    @ResponseBody
    public List<DriverManagement.DriverInfo> getFavouriteDrivers(@PathVariable String token){
        return driverReader.readFavourites(getUserId(token));
    }

    @RequestMapping(value = "/favourite/city/{token}")
    @ResponseBody
    public List<DriverManagement.DriverInfo> getFavouritesByCity(@PathVariable String token){
        return driverReader.readCityFavourites(getUserId(token));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<DriverManagement.DriverInfo> getAll(){
        return driverReader.readAll();
    }

    @RequestMapping(value = "/city/{country}/{city}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public List<DriverManagement.DriverInfo> getByDriveType(@PathVariable long country, @PathVariable long city, @PathVariable long type) {
        return driverReader.readDriversByDriveType(type, city, 0, country);
    }
}
