package com.taxiservice.web.controller;


import com.taxiservice.model.reader.CarpoolReader;
import com.taxiservice.model.reader.DetailsReader;
import com.taxiservice.model.writer.CarpoolManagement;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/drivers")
public class DriverController extends BasicSecurityController {

    @Inject
    private CarpoolReader<Long> carpoolReader;
    @Inject
    private CarpoolManagement carpoolManagement;
    @Inject
    private DetailsReader detailsReader;

    @RequestMapping(value = "/city/{city}", method = RequestMethod.GET)
    @ResponseBody
    public List  getDriversByCity(@PathVariable long city) {
        return Collections.emptyList();
    }

    @RequestMapping(value = "/favourite/{token}")
    @ResponseBody
    public List  getFavouriteDrivers(@PathVariable String token) {
        return Collections.emptyList();
    }

    @RequestMapping(value = "/favourite/city/{token}")
    @ResponseBody
    public List getFavouritesByCity(@PathVariable String token) {
        return Collections.emptyList();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List getAll() {
        return Collections.emptyList();
    }

    @RequestMapping(value = "/city/{city}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public List getByDriveType(@PathVariable long city, @PathVariable long type) {
        return Collections.emptyList();
    }

    @RequestMapping(value = "/like", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void likeDriver(@RequestBody LikeRequest likeRequest) {
//        driverManagement.likeDriver(getUserId(likeRequest.token), likeRequest.driver);
    }

    @RequestMapping(value = "/comments/{driver}", method = RequestMethod.GET)
    @ResponseBody
    public Set<CarpoolReader.Feedback> getComments(@PathVariable long driver) {
        return carpoolReader.readComments(driver);
    }

    @RequestMapping(value = "/dislike", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void dislikeDriver(@RequestBody LikeRequest likeRequest) {
//        driverManagement.dislikeDriver(getUserId(likeRequest.token), likeRequest.driver);
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void commentDriver(@RequestBody CommentRequest request) {
//        driverManagement.comment(getUserId(request.token),  request.driver, request.message);
    }

    @RequestMapping(value = "/city/{cityId}/short", method = RequestMethod.GET)
    @ResponseBody
    public List<CarpoolReader.DriverLine> driverLine(@PathVariable long cityId) {
//        return carpoolReader.readShortInfo(cityId);
        return Collections.emptyList();
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DetailsReader.DriverItem details(@PathVariable long id) {
        return detailsReader.readDriver(0, id);
    }


    public static class LikeRequest {
        public long driver;
        public String token;
    }

    public static class CommentRequest extends LikeRequest {
        public String message;
    }
}
