package com.taxiservice.web.controller;

import com.taxiservice.model.Location;
import com.taxiservice.model.reader.CarpoolReader;
import com.taxiservice.model.reader.DetailsReader;
import com.taxiservice.model.writer.CarpoolManagement;
import com.taxiservice.web.request.LocationRequest;
import com.taxiservice.web.request.RangeLocationRequest;
import com.taxiservice.web.request.TripCreationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Set;

/**
 * @author Herman Zamula
 */
@Controller
@RequestMapping("/trips")
public class TripsController extends BasicSecurityController {

    @Inject
    private CarpoolReader<Long> reader;

    @Inject
    private CarpoolManagement<Long> management;

    @Inject
    private DetailsReader<Long> detailsReader;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Set<CarpoolReader.TripLine> getTrips(RangeLocationRequest request, @RequestParam String token) {
        return reader.readTrips(getUserId(token), new Location(request.getStartLng(), request.getStartLat()), new Location(request.getEndLng(), request.getEndLat()), request.getRadius());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void saveTrip(@RequestBody TripCreationRequest request, @RequestParam String token) {
        if (request.id == null)
            management.createTrip(getUserId(token), request.car, request.tripInfo);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DetailsReader.TripItem details(@PathVariable long id, @RequestParam String token) {
        return detailsReader.readTrip(getUserId(token), id);
    }

    @RequestMapping(value = "/subscribe/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void subscribe(@RequestParam String token, @PathVariable long id) {
        management.subscribe(getUserId(token), id);
    }

    @RequestMapping(value = "/unsubscribe/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void unsubscribe(@RequestParam String token, @PathVariable long id) {
        management.removeSubscriber(getUserId(token), id, getUserId(token));
    }

    @RequestMapping(value = "/subscribers/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Set<CarpoolReader.PassengerLine> subscribers(@PathVariable long id, @RequestParam String token) {
        return reader.readPassengers(getUserId(token), id);
    }

    @RequestMapping(value = "/near", method = RequestMethod.GET)
    @ResponseBody
    public Set<CarpoolReader.TripLine> getTripsNear(@RequestParam String token, LocationRequest request) {
        return reader.readTripsNear(getUserId(token), new Location(request.getLng(), request.getLat()), request.getRadius());
    }

}
