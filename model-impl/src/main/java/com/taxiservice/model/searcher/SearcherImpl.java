package com.taxiservice.model.searcher;


import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.taxiservice.model.Searcher;
import com.taxiservice.model.entity.TaxiDriver;
import com.taxiservice.model.reader.DriverReader;
import com.taxiservice.model.util.Transformers;
import org.hibernate.CacheMode;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.MustJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.TermMatchingContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.concurrent.Future;

import static com.google.common.collect.FluentIterable.from;
import static org.hibernate.search.jpa.Search.getFullTextEntityManager;

@Service
public class SearcherImpl implements Searcher {

    private EntityManager entityManager;

    @PostConstruct
    public void initSearcher() {
        FullTextEntityManager manager = getFullTextEntityManager(entityManager);
        Future<?> start = manager.createIndexer()
                .purgeAllOnStart(true)
                .cacheMode(CacheMode.IGNORE)
                .optimizeAfterPurge(true)
                .optimizeOnFinish(true)
                .batchSizeToLoadObjects(25)
                .threadsToLoadObjects(4)
                .threadsForSubsequentFetching(10).start();
    }

    @Override
    public List<DriverReader.DriverLine> drivers(String query) {
        Iterable<TaxiDriver> drivers = search(TaxiDriver.class, query, ImmutableSet.of("description"));
        return from(drivers).transform(Transformers.DRIVER_LINE_TRANSFORMER).toImmutableList();
    }

    private <T> Iterable<T> search(final Class<T> clazz, String keyword, Iterable<String> additionalFields) {
        final FullTextEntityManager session = Search.getFullTextEntityManager(entityManager);
        QueryBuilder qb = session.getSearchFactory()
                .buildQueryBuilder().forEntity(clazz).get();

        String[] searchTerms = keyword.split(" ");
        final TermMatchingContext context = qb.keyword().wildcard().onField("name");
        for (String field : additionalFields) {
            context.andField(field);
        }

        BooleanJunction<BooleanJunction> bool = qb.bool();
        for (String searchTerm : searchTerms) {
            bool.must(context.matching(String.format("*%s*", searchTerm.toLowerCase())).createQuery());
        }

        MustJunction must = bool.must(bool.createQuery());
        final FullTextQuery fullTextQuery = session.createFullTextQuery(must.createQuery(), clazz);

        //noinspection unchecked
        return Iterables.transform(fullTextQuery.getResultList(), new Function() {
            @Override
            public Object apply(Object input) {
                if (input.getClass().isAssignableFrom(clazz)) return input;
                throw new AssertionError(String.format("Expected: %s. Actual: %s", clazz, input.getClass()));
            }
        });
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
