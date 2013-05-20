package com.taxiservice.model.writer;


import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BonusManagement {

    long addBonus(long actor, long driver, Bonus bonus);

    void updateBonus(long actor, long driver, Bonus bonus);

    static class Bonus {
        public final String description;
        public final double value;
        public final long driveType;

        public Bonus(String description, double value, long driveType) {
            this.description = description;
            this.value = value;
            this.driveType = driveType;
        }
    }
}
