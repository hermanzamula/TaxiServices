package com.taxiservice.web.controller;

import com.taxiservice.model.reader.DetailsReader;
import com.taxiservice.model.writer.CarpoolManagement;
import com.taxiservice.web.request.CreatePassengerRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * @author Herman Zamula
 */
@Controller
@RequestMapping("/passengers")
public class PassengerController extends BasicSecurityController {

    @Inject
    private CarpoolManagement<Long> carpoolManagement;
    @Inject
    private DetailsReader<Long> detailsReader;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void save(@RequestBody CreatePassengerRequest request, @RequestParam String token) {
        if (request.id == null) {
            carpoolManagement.createPassenger(getUserId(token), request.passengerInfo);
        }
    }

    @RequestMapping(value = "/rate", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void rate(@RequestBody RateRequest rateRequest, @RequestParam String token) {
        carpoolManagement.ratePassenger(getUserId(token), rateRequest.passenger, rateRequest.rate);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DetailsReader.PassengerItem details(@PathVariable long id, @RequestParam String token) {
        return detailsReader.readPassenger(getUserId(token), id);
    }


    private class RateRequest {
        public int rate;
        public Long passenger;
    }
}
