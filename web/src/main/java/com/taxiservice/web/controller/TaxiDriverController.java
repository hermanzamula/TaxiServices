package com.taxiservice.web.controller;


import com.taxiservice.model.reader.DriverReader;
import com.taxiservice.model.writer.DriverManagement;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping(value = "/drivers")
public class TaxiDriverController extends BasicSecurityController {

    @Inject
    private DriverReader driverReader;
    @Inject
    private DriverManagement driverManagement;

    @RequestMapping(value = "/city/{city}", method = RequestMethod.GET)
    @ResponseBody
    public List<DriverManagement.DriverDetails> getDriversByCity(@PathVariable long city) {
        return driverReader.readDriversByCity(city);
    }

    @RequestMapping(value = "/favourite/{token}")
    @ResponseBody
    public List<DriverManagement.DriverDetails> getFavouriteDrivers(@PathVariable String token) {
        return driverReader.readFavourites(getUserId(token));
    }

    @RequestMapping(value = "/favourite/city/{token}")
    @ResponseBody
    public List<DriverManagement.DriverDetails> getFavouritesByCity(@PathVariable String token) {
        return driverReader.readCurrentCityFavourites(getUserId(token));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<DriverManagement.DriverDetails> getAll() {
        return driverReader.readAll();
    }

    @RequestMapping(value = "/city/{city}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public List<DriverManagement.DriverDetails> getByDriveType(@PathVariable long city, @PathVariable long type) {
        return driverReader.readDriversByDriveType(type, city);
    }

    @RequestMapping(value = "/like", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void likeDriver(@RequestBody LikeRequest likeRequest) {
        driverManagement.likeDriver(getUserId(likeRequest.token), likeRequest.driver);
    }

    @RequestMapping(value = "/comment/{driver}", method = RequestMethod.GET)
    @ResponseBody
    public List<DriverReader.Feedback> getComments(@PathVariable long driver) {
        return driverReader.readComments(driver);
    }

    @RequestMapping(value = "/dislike", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void dislikeDriver(@RequestBody LikeRequest likeRequest) {
        driverManagement.dislikeDriver(getUserId(likeRequest.token), likeRequest.driver);
    }

    @RequestMapping(value = "/comment", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void commentDriver(@RequestBody CommentRequest request) {
        driverManagement.comment(getUserId(request.token),  request.driver, request.message);
    }

    private class LikeRequest {
        public final long driver;
        public final String token;

        private LikeRequest(long driver, String token) {
            this.driver = driver;
            this.token = token;
        }
    }

    private class CommentRequest extends LikeRequest{
        public final String message;

        private CommentRequest(long driver, String token, String message) {
            super(driver, token);
            this.message = message;
        }
    }
}
