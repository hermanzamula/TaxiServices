package com.taxiservice.model.reader;

import com.taxiservice.model.repository.CommentRepository;
import com.taxiservice.model.repository.CountryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.FluentIterable.from;
import static com.taxiservice.model.util.Transformers.TO_CITY_LINE;
import static com.taxiservice.model.util.Transformers.TO_FEEDBACK;

@Service
public class AdditionalReaderImpl implements AdditionalReader {

    private final CountryRepository countryRepository;
    private final CommentRepository  commentRepository;

    @Inject
    public AdditionalReaderImpl(CountryRepository countryRepository, CommentRepository commentRepository) {
        this.countryRepository = countryRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<DriverReader.CityLine> readCities(long country) {
        return from(countryRepository.findOne(country).getCities())
                .transform(TO_CITY_LINE)
                .toImmutableList();
    }

    @Override
    public List<DriverReader.Feedback> readLastComments(int size) {
        return from(commentRepository.findAll())
                .limit(size)
                .transform(TO_FEEDBACK)
                .toImmutableList();
    }
}
