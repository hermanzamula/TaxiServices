SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `taxiservice` ;
CREATE SCHEMA IF NOT EXISTS `taxiservice` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `taxiservice` ;

-- -----------------------------------------------------
-- Table `taxiservice`.`country`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiservice`.`country` ;

CREATE  TABLE IF NOT EXISTS `taxiservice`.`country` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `idcountry_UNIQUE` (`id` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxiservice`.`city`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiservice`.`city` ;

CREATE  TABLE IF NOT EXISTS `taxiservice`.`city` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `country_id` BIGINT NOT NULL ,
  `name` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_city_country_idx` (`country_id` ASC) ,
  UNIQUE INDEX `idcity_UNIQUE` (`id` ASC) ,
  CONSTRAINT `fk_city_country`
    FOREIGN KEY (`country_id` )
    REFERENCES `taxiservice`.`country` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxiservice`.`taxi_driver`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiservice`.`taxi_driver` ;

CREATE  TABLE IF NOT EXISTS `taxiservice`.`taxi_driver` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `city_id` BIGINT NOT NULL ,
  `name` VARCHAR(256) NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `idtaxidriver_UNIQUE` (`id` ASC) ,
  INDEX `fk_taxidriver_city1_idx` (`city_id` ASC) ,
  CONSTRAINT `fk_taxidriver_city1`
    FOREIGN KEY (`city_id` )
    REFERENCES `taxiservice`.`city` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxiservice`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiservice`.`user` ;

CREATE  TABLE IF NOT EXISTS `taxiservice`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `firstName` VARCHAR(45) NULL ,
  `email` VARCHAR(45) NOT NULL ,
  `passwordHash` VARCHAR(128) NOT NULL ,
  `lastName` VARCHAR(45) NULL ,
  `favourite_id` BIGINT NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `iduser_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxiservice`.`drive_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiservice`.`drive_type` ;

CREATE  TABLE IF NOT EXISTS `taxiservice`.`drive_type` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  `description` VARCHAR(256) NULL ,
  `price` DECIMAL(2) NULL ,
  UNIQUE INDEX `iddrivetype_UNIQUE` (`id` ASC) ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxiservice`.`user_bonus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiservice`.`user_bonus` ;

CREATE  TABLE IF NOT EXISTS `taxiservice`.`user_bonus` (
  `id` BIGINT NOT NULL ,
  `taxi_driver_id` BIGINT NOT NULL ,
  `user_id` BIGINT NOT NULL ,
  `description` VARCHAR(256) NULL ,
  `drive_type_id` BIGINT NOT NULL ,
  `value` DECIMAL(2) NOT NULL ,
  `isAdmin` TINYINT(1) NULL DEFAULT false ,
  INDEX `fk_userbonus_taxidriver1_idx` (`taxi_driver_id` ASC) ,
  INDEX `fk_userbonus_user1_idx` (`user_id` ASC) ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `iduserbonus_UNIQUE` (`id` ASC) ,
  INDEX `fk_userbonus_drivetype1_idx` (`drive_type_id` ASC) ,
  CONSTRAINT `fk_userbonus_taxidriver1`
    FOREIGN KEY (`taxi_driver_id` )
    REFERENCES `taxiservice`.`taxi_driver` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_userbonus_user1`
    FOREIGN KEY (`user_id` )
    REFERENCES `taxiservice`.`user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_userbonus_drivetype1`
    FOREIGN KEY (`drive_type_id` )
    REFERENCES `taxiservice`.`drive_type` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxiservice`.`phone_number`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiservice`.`phone_number` ;

CREATE  TABLE IF NOT EXISTS `taxiservice`.`phone_number` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `taxi_driver_id` BIGINT NOT NULL ,
  `number` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `idphonenumber_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `number_UNIQUE` (`number` ASC) ,
  CONSTRAINT `fk_phonenumber_taxidriver1`
    FOREIGN KEY (`taxi_driver_id` )
    REFERENCES `taxiservice`.`taxi_driver` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxiservice`.`taxi_driver_has_drive_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiservice`.`taxi_driver_has_drive_type` ;

CREATE  TABLE IF NOT EXISTS `taxiservice`.`taxi_driver_has_drive_type` (
  `taxi_driver_id` BIGINT NOT NULL ,
  `drive_type_id` BIGINT NOT NULL ,
  PRIMARY KEY (`taxi_driver_id`, `drive_type_id`) ,
  INDEX `fk_taxi_driver_has_drive_type_drive_type1_idx` (`drive_type_id` ASC) ,
  INDEX `fk_taxi_driver_has_drive_type_taxi_driver1_idx` (`taxi_driver_id` ASC) ,
  CONSTRAINT `fk_taxi_driver_has_drive_type_taxi_driver1`
    FOREIGN KEY (`taxi_driver_id` )
    REFERENCES `taxiservice`.`taxi_driver` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_taxi_driver_has_drive_type_drive_type1`
    FOREIGN KEY (`drive_type_id` )
    REFERENCES `taxiservice`.`drive_type` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxiservice`.`favorite`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiservice`.`favorite` ;

CREATE  TABLE IF NOT EXISTS `taxiservice`.`favorite` (
  `taxi_driver_id` BIGINT NOT NULL ,
  `user_id` BIGINT NOT NULL ,
  PRIMARY KEY (`taxi_driver_id`, `user_id`) ,
  INDEX `fk_taxi_driver_has_user_user1_idx` (`user_id` ASC) ,
  INDEX `fk_taxi_driver_has_user_taxi_driver1_idx` (`taxi_driver_id` ASC) ,
  CONSTRAINT `fk_taxi_driver_has_user_taxi_driver1`
    FOREIGN KEY (`taxi_driver_id` )
    REFERENCES `taxiservice`.`taxi_driver` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_taxi_driver_has_user_user1`
    FOREIGN KEY (`user_id` )
    REFERENCES `taxiservice`.`user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxiservice`.`user_place`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `taxiservice`.`user_place` ;

CREATE  TABLE IF NOT EXISTS `taxiservice`.`user_place` (
  `user_id` BIGINT NOT NULL ,
  `city_id` BIGINT NOT NULL ,
  `lastModification` DATETIME NULL ,
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  INDEX `fk_user_place_user1_idx` (`user_id` ASC) ,
  INDEX `fk_user_place_city1_idx` (`city_id` ASC) ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_user_place_user1`
    FOREIGN KEY (`user_id` )
    REFERENCES `taxiservice`.`user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_place_city1`
    FOREIGN KEY (`city_id` )
    REFERENCES `taxiservice`.`city` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
