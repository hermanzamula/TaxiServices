package com.taxiservice.model.reader;

import com.taxiservice.model.repository.CountryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.FluentIterable.from;
import static com.taxiservice.model.util.Transformers.TO_CITY_LINE;

@Service
public class AdditionalReaderImpl implements AdditionalReader {

    private final CountryRepository countryRepository;

    @Inject
    public AdditionalReaderImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<DriverReader.CityLine> readCities(long country) {
        return from(countryRepository.findOne(country).getCities())
                .transform(TO_CITY_LINE)
                .toImmutableList();
    }
}
