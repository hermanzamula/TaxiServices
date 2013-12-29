package com.taxiservice.model.reader;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSortedSet;
import com.taxiservice.model.entity.Comment;
import com.taxiservice.model.entity.Country;
import com.taxiservice.model.repository.CityRepository;
import com.taxiservice.model.repository.CommentRepository;
import com.taxiservice.model.repository.CountryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;
import static com.taxiservice.model.util.Transformers.TO_CITY_LINE;
import static com.taxiservice.model.util.Transformers.TO_FEEDBACK;

@Service
public class AdditionalReaderImpl implements AdditionalReader {

    public static final Function<Country, DriverReader.CountryLine> TO_COUNTRY_LINE = new Function<Country, DriverReader.CountryLine>() {
        @Override
        public DriverReader.CountryLine apply(Country input) {
            return new DriverReader.CountryLine(input.getName(), input.getId());
        }
    };

    public static final Comparator<Comment> COMMENT_COMPARATOR = new Comparator<Comment>() {
        @Override
        public int compare(Comment o1, Comment o2) {
            if (o1.getDate().equals(o2.getDate())) {
                return o1.hashCode() - o2.hashCode();
            }
            return o2.getDate().compareTo(o1.getDate());
        }
    };
    private final CountryRepository countryRepository;
    private final CommentRepository commentRepository;
    private final CityRepository cityRepository;

    @Inject
    public AdditionalReaderImpl(CountryRepository countryRepository, CommentRepository commentRepository, CityRepository cityRepository) {
        this.countryRepository = countryRepository;
        this.commentRepository = commentRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<DriverReader.CityLine> readCities(long country) {
        return from(cityRepository.findByCountry(countryRepository.findOne(country)))
                .transform(TO_CITY_LINE)
                .toList();
    }

    @Override
    public List<DriverReader.Feedback> readLastComments(int size) {
        ImmutableSortedSet.Builder<Comment> builder = ImmutableSortedSet.orderedBy(COMMENT_COMPARATOR);
        return from(builder.addAll(commentRepository.findAll()).build())
                .limit(size)
                .transform(TO_FEEDBACK)
                .toList();
    }

    @Override
    public List<DriverReader.CountryLine> readCountries() {
        return from(countryRepository.findAll())
                .transform(TO_COUNTRY_LINE)
                .toList();
    }

    @Override
    public DriverReader.CityLine readCity(long id) {
        return TO_CITY_LINE.apply(checkNotNull(cityRepository.findOne(id)));
    }
}
