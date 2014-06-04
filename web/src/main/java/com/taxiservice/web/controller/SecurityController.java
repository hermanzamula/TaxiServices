package com.taxiservice.web.controller;

import com.taxiservice.web.response.ValueResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Herman Zamula
 */
@Controller
@RequestMapping("/security")
public class SecurityController {

    @Value("${base.url}")
    private String baseUrl;

    @Value("${location.server.base.url}")
    private String locationServerBaseUrl;

    @RequestMapping(value = "/locationServerBaseUrl", method = RequestMethod.GET)
    @ResponseBody
    public ValueResponse<String> getLocationServerBaseUrl() {
        return new ValueResponse<>(locationServerBaseUrl);
    }


}
