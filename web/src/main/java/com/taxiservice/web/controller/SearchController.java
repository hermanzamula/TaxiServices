package com.taxiservice.web.controller;

import com.taxiservice.model.Searcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Inject
    private Searcher searcher;

    @RequestMapping(value = "/drivers")
    @ResponseBody
    public List<Searcher.DriverDetails> drivers(@RequestParam("query") String query) {
        return searcher.drivers(query);
    }
}
