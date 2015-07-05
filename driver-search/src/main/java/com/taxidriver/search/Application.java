package com.taxidriver.search;

import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.Date;
import java.util.Random;

/**
 * @author Herman Zamula
 */
public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class);

    public static void main(String[] args) {
        LOGGER.info("Application started");

        final DriverSearcher driverSearcher = new DriverSearcher();

        int driversCount = 10;
        final ImmutableList<Drive> drivesData = createTestDrivesData(driversCount);

        drivesData.forEach((drive) -> LOGGER.info("Created driver: " + drive));

        LOGGER.info("Start searching...");

        final ImmutableList<Drive> sortedDrives = driverSearcher.doSearch(drivesData,
                new SearchParams(
                        null,
                        0.5f,
                        Criterion.RATING
                ));

        LOGGER.info("Search was end.");

        sortedDrives.forEach((drive -> LOGGER.info(String.format("Score: %f, Result: %s", drive.getDriveScore(), drive))));


    }

    private static ImmutableList<Drive> createTestDrivesData(int driversCount) {

        final ImmutableList<String> testNames = ImmutableList.of("Mike", "Nate", "Alex", "Sam", "Joe", "Kate", "Leo", "Jack", "Olga", "Serg");

        final ImmutableList.Builder<Drive> builder = ImmutableList.builder();

        final Date today = new Date();

        final Random random = new Random(100);
        final int tomorrowOffset = 1000 * 60 * 60 * 24;

        for (int i = 0; i < driversCount; i++) {


            final Drive.DriveParams params = new Drive.DriveParams(
                    (int) (random.nextDouble() * 1000),
                    (int) (random.nextDouble() * 10),
                    new Point((int) (random.nextDouble() * 90), (int) (random.nextDouble() * 90)),
                    new Date((long) (today.getTime() + (random.nextDouble() * tomorrowOffset)))
            );


            builder.add(new Drive(testNames.get(i % 10), params, null));

        }


        final ImmutableList<Drive> build = builder.build();

        build.forEach((drive -> {

            final Drive.DriveParams params = drive.getParams();

            final Drive.NormalizedDriverCriterias normalizedDriverCriterias = new Drive.NormalizedDriverCriterias(
                    calcFQ(params.getCost(), getMaxCost(build), getMinCost(build)),
                    calcFQ(params.getRaiting(), getMinRating(build), getMaxRating(build)),
                    random.nextFloat(),
                    calcFQ((float)params.getTime().getTime(), getMaxTime(build), getMinTime(build)),
                    random.nextFloat()
            );

            drive.setNormalizedParams(normalizedDriverCriterias);

        }));

        build.forEach((drive -> {

            final Drive.NormalizedDriverCriterias params = drive.getNormalizedParams();

            final Drive.NormalizedDriverCriterias normalizedDriverCriterias = new Drive.NormalizedDriverCriterias(
                    params.getCost(),
                    params.getRating(),
                    calcFQ(params.getClosenessPlace(), getMaxPlace(build), getMinPlace(build)),
                    params.getClosenessTime(),
                    calcFQ(params.getInterests(), getMinInterests(build), getMaxInterests(build))
            );

            drive.setNormalizedParams(normalizedDriverCriterias);

        }));

        return build;
    }

    private static float getMinCost(ImmutableList<Drive> build) {
        return (float) build.stream()
                .mapToDouble((drive) -> drive.getParams().getCost())
                .min()
                .getAsDouble();
    }

    private static float getMaxRating(ImmutableList<Drive> build) {
        return (float) build.stream()
                .mapToDouble((drive) -> drive.getParams().getRaiting())
                .max()
                .getAsDouble();
    }

    private static float getMinRating(ImmutableList<Drive> build) {
        return (float) build.stream()
                .mapToDouble((drive) -> drive.getParams().getRaiting())
                .min()
                .getAsDouble();
    }

    private static float getMaxTime(ImmutableList<Drive> build) {
        return (float) build.stream()
                .mapToDouble((drive) -> drive.getParams().getTime().getTime())
                .max()
                .getAsDouble();
    }

    private static float getMinTime(ImmutableList<Drive> build) {
        return (float) build.stream()
                .mapToDouble((drive) -> drive.getParams().getTime().getTime())
                .min()
                .getAsDouble();
    }

    private static float getMaxCost(ImmutableList<Drive> build) {
        return (float) build.stream()
                .mapToDouble((drive) -> drive.getParams().getCost())
                .max()
                .getAsDouble();
    }

    private static float getMaxPlace(ImmutableList<Drive> build) {
        return (float) build.stream()
                .mapToDouble((drive) -> drive.getNormalizedParams().getClosenessPlace())
                .max()
                .getAsDouble();
    }

    private static float getMinPlace(ImmutableList<Drive> build) {
        return (float) build.stream()
                .mapToDouble((drive) -> drive.getNormalizedParams().getClosenessPlace())
                .min()
                .getAsDouble();
    }

    private static float getMinInterests(ImmutableList<Drive> build) {
        return (float) build.stream()
                .mapToDouble((drive) -> drive.getNormalizedParams().getInterests())
                .min()
                .getAsDouble();
    }

    private static float getMaxInterests(ImmutableList<Drive> build) {
        return (float) build.stream()
                .mapToDouble((drive) -> drive.getNormalizedParams().getInterests())
                .max()
                .getAsDouble();
    }



    static float calcFQ(float current, float bad, float best) {
        return (current - bad) / (best - bad);
    }
}
