package com.taxiservice.model;


import com.taxiservice.model.reader.DriverReader;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface Searcher {

    List<DriverReader.DriverLine> drivers(String query);

}
