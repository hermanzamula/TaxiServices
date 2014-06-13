package com.taxiservice.web.controller;

import com.taxiservice.model.Location;
import com.taxiservice.model.reader.CarpoolReader;
import com.taxiservice.model.reader.DetailsReader;
import com.taxiservice.model.writer.CarpoolManagement;
import com.taxiservice.web.request.DriverCreationRequest;
import com.taxiservice.web.request.LocationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Set;

/**
 * @author Herman Zamula
 */
@Controller
@RequestMapping("/drivers")
public class DriversController extends BasicSecurityController {

    @Inject
    private CarpoolReader<Long> carpoolReader;
    @Inject
    private CarpoolManagement<Long> carpoolManagement;
    @Inject
    private DetailsReader<Long> detailsReader;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Set<CarpoolReader.DriverLine> getAll(LocationRequest request, @RequestParam String token) {
        return carpoolReader.readDriversNear(getUserId(token), new Location(request.getLng(), request.getLat()), request.getRadius());
    }

    @RequestMapping(value = "/rate", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void likeDriver(@RequestBody RateRequest rateRequest) {
        carpoolManagement.rateDriver(getUserId(rateRequest.token), rateRequest.driver, rateRequest.rate);
    }

    @RequestMapping(value = "/comments/{driver}", method = RequestMethod.GET)
    @ResponseBody
    public Set<CarpoolReader.Feedback> getComments(@PathVariable long driver) {
        return carpoolReader.readComments(driver);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void save(DriverCreationRequest request, @RequestParam String token) {
        if (request.id == null) {
            carpoolManagement.createDriver(getUserId(token), request.driverInfo);
        }
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void commentDriver(@RequestBody CommentRequest request) {
        carpoolManagement.leaveComment(getUserId(request.token), request.driver, request.message);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DetailsReader.DriverItem details(@PathVariable long id, @RequestParam String token) {
        return detailsReader.readDriver(getUserId(token), id);
    }

    public static class RateRequest {
        public int rate;
        public long driver;
        public String token;
    }

    public static class CommentRequest extends RateRequest {
        public String message;
    }
}
