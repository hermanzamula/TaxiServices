package com.taxidriver.search;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Floats;
import org.apache.log4j.Logger;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Maps.newHashMap;

/**
 * @author Herman Zamula
 */
public class DriverSearcher {

    private static final Logger LOGGER = Logger.getLogger(DriverSearcher.class);

    private final ImmutableMap<Criterion, Float> INITIAL_CRITERION_WEIGHTS = ImmutableMap.of(
            Criterion.COST, 0.3f,
            Criterion.RATING, 0.2f,
            Criterion.PLACE_CLOSENESS, 0.15f,
            Criterion.TIME_CLOSENESS, 0.2f,
            Criterion.INTERESTS, 0.15f
    );

    private final Map<Criterion, Float> CRITERION_WEiGHTS = newHashMap(INITIAL_CRITERION_WEIGHTS);

    public ImmutableList<Drive> doSearch(List<Drive> drives, SearchParams params) {

        LOGGER.info("Start searching with params: " + params);
        if (params.getUpdateWeightsCriterion() != null) {
            updateWeights(params);
        }

        final Criterion mainCriterion = params.getMainCriterion();

        if(mainCriterion != null) {
            return from(drives).toSortedList(getMainCriterionComparator(mainCriterion)).reverse();
        }

        return from(drives).toSortedList(getEntropyMethodComparator()).reverse();
    }

    private Comparator<Drive> getEntropyMethodComparator() {

        return (o1, o2) -> {

            final EnumSet<Criterion> criterions = EnumSet.allOf(Criterion.class);

            float leftValue = 0;
            float rightValue = 0;

            for (Criterion criterion : criterions) {
                leftValue += caluclateEntropyElement(o1, criterion);
                rightValue += caluclateEntropyElement(o2, criterion);
            }

            o1.setDriveScore(leftValue);
            o2.setDriveScore(rightValue);

            return Floats.compare(leftValue, rightValue);

        };

    }

    private float caluclateEntropyElement(Drive drive, Criterion criterion) {
        final Drive.NormalizedDriverCriterias params = drive.getNormalizedParams();
        final Float lambda = CRITERION_WEiGHTS.get(criterion);
        final float criteriaValue = params.criterionValue(criterion);
        return lambda * (float)Math.pow(criteriaValue, lambda);
    }

    private Comparator<Drive> getMainCriterionComparator(final Criterion mainCriterion) {
        return (o1, o2) -> {
            final float leftValue = o1.getNormalizedParams().criterionValue(mainCriterion);
            final float rightValue = o2.getNormalizedParams().criterionValue(mainCriterion);
            return Floats.compare(leftValue, rightValue);
        };
    }

    private void updateWeights(SearchParams params) {

        final Criterion updateWeightsCriterion = params.getUpdateWeightsCriterion();
        final Float oldCriterionValue = CRITERION_WEiGHTS.get(updateWeightsCriterion);
        final float updatedCriterionValue = (1.f / params.getUpdateWeightsValue()) * oldCriterionValue;
        CRITERION_WEiGHTS.put(updateWeightsCriterion, updatedCriterionValue);

        final EnumSet<Criterion> criterionsToUpdate = EnumSet.complementOf(EnumSet.of(updateWeightsCriterion));

        criterionsToUpdate.forEach((criterion) -> {
            final Float oldValue = CRITERION_WEiGHTS.get(criterion);
            final float newValue = oldValue - (updatedCriterionValue - oldCriterionValue) / (criterionsToUpdate.size() * 1.f);
            CRITERION_WEiGHTS.put(criterion, newValue);
        });

        LOGGER.info("New weights: " + CRITERION_WEiGHTS);
        LOGGER.info("Sum is: " + CRITERION_WEiGHTS.values().stream().reduce((aFloat, aFloat2) -> aFloat + aFloat2));


    }

}
