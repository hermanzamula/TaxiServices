package com.taxidriver.search;

/**
 * @author Herman Zamula
 */
public class SearchParams {

    private Criterion mainCriterion;
    private float updateWeightsValue;
    private Criterion updateWeightsCriterion;

    public SearchParams(Criterion mainCriterion, float updateWeightsValue, Criterion updateWeightsCriterion) {
        this.mainCriterion = mainCriterion;
        this.updateWeightsValue = updateWeightsValue;
        this.updateWeightsCriterion = updateWeightsCriterion;
    }

    public Criterion getMainCriterion() {
        return mainCriterion;
    }

    public float getUpdateWeightsValue() {
        return updateWeightsValue;
    }

    public Criterion getUpdateWeightsCriterion() {
        return updateWeightsCriterion;
    }

    @Override
    public String toString() {
        return "SearchParams{" +
                "mainCriterion=" + mainCriterion +
                ", updateWeightsValue=" + updateWeightsValue +
                ", updateWeightsCriterion=" + updateWeightsCriterion +
                '}';
    }
}
