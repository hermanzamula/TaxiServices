package com.taxidriver.search;

import java.awt.*;
import java.util.Date;

/**
 * @author Herman Zamula
 */
public class Drive {

    private String name;
    private DriveParams params;
    private NormalizedDriverCriterias normalizedParams;

    private Float driveScore;

    public Drive(String driverName, DriveParams params, NormalizedDriverCriterias normalizedParams) {
        this.name = driverName;
        this.params = params;
        this.normalizedParams = normalizedParams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DriveParams getParams() {
        return params;
    }

    public void setParams(DriveParams params) {
        this.params = params;
    }

    public NormalizedDriverCriterias getNormalizedParams() {
        return normalizedParams;
    }

    public void setNormalizedParams(NormalizedDriverCriterias normalizedParams) {
        this.normalizedParams = normalizedParams;
    }

    public Float getDriveScore() {
        return driveScore;
    }

    public void setDriveScore(Float driveScore) {
        this.driveScore = driveScore;
    }


    public static class DriveParams {
        private long cost;
        private long raiting;
        private Point place;
        private Date time;

        public DriveParams(long cost, long raiting, Point placeStart, Date timeStart) {
            this.cost = cost;
            this.raiting = raiting;
            this.place = placeStart;
            this.time = timeStart;
        }

        public long getCost() {
            return cost;
        }

        public void setCost(long cost) {
            this.cost = cost;
        }

        public long getRaiting() {
            return raiting;
        }

        public void setRaiting(long raiting) {
            this.raiting = raiting;
        }

        public Point getPlace() {
            return place;
        }

        public void setPlace(Point place) {
            this.place = place;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "DriveParams{" +
                    "cost=" + cost +
                    ",\traiting=" + raiting +
                    ",\tplace=" + place +
                    ",\ttime=" + time +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Drive{" +
                "name='" + name + '\'' +
                ",\tparams=" + params +
                //", normalizedParams=" + normalizedParams +
                '}';
    }

    /**
     * @author Herman Zamula
     */
    public static class NormalizedDriverCriterias {

        private float cost;
        private float rating;
        private float closenessPlace;
        private float closenessTime;
        private float interests;

        public NormalizedDriverCriterias(float cost, float rating, float closenessPlace, float closenessTime, float interests) {
            this.cost = cost;
            this.rating = rating;
            this.closenessPlace = closenessPlace;
            this.closenessTime = closenessTime;
            this.interests = interests;
        }

        public float getCost() {
            return cost;
        }

        public float getRating() {
            return rating;
        }

        public float getClosenessPlace() {
            return closenessPlace;
        }

        public float getClosenessTime() {
            return closenessTime;
        }

        public float getInterests() {
            return interests;
        }

        public float criterionValue(Criterion criterion) {
            switch (criterion) {
                case COST:
                    return getCost();
                case RATING:
                    return getRating();
                case PLACE_CLOSENESS:
                    return getClosenessPlace();
                case TIME_CLOSENESS:
                    return getClosenessTime();
                case INTERESTS:
                    return getInterests();
                default:
                    throw new AssertionError(criterion);
            }

        }

        @Override
        public String toString() {
            return "NormalizedDriverCriterias{" +
                    "cost=" + cost +
                    ", rating=" + rating +
                    ", closenessPlace=" + closenessPlace +
                    ", closenessTime=" + closenessTime +
                    ", interests=" + interests +
                    '}';
        }
    }
}
