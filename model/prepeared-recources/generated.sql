SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `taxiservice` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `taxiservice` ;

-- -----------------------------------------------------
-- Table `taxiservice`.`country`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `taxiservice`.`country` (
  `idcountry` INT NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idcountry`) ,
  UNIQUE INDEX `idcountry_UNIQUE` (`idcountry` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxiservice`.`city`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `taxiservice`.`city` (
  `idcity` INT NOT NULL ,
  `country_idcountry` INT NOT NULL ,
  `name` VARCHAR(45) NULL ,
  PRIMARY KEY (`idcity`) ,
  INDEX `fk_city_country_idx` (`country_idcountry` ASC) ,
  UNIQUE INDEX `idcity_UNIQUE` (`idcity` ASC) ,
  CONSTRAINT `fk_city_country`
    FOREIGN KEY (`country_idcountry` )
    REFERENCES `taxiservice`.`country` (`idcountry` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxiservice`.`drivetype`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `taxiservice`.`drivetype` (
  `iddrivetype` INT NOT NULL ,
  `name` VARCHAR(45) NULL ,
  `description` VARCHAR(256) NULL ,
  `price` DECIMAL(2) NULL ,
  UNIQUE INDEX `iddrivetype_UNIQUE` (`iddrivetype` ASC) ,
  PRIMARY KEY (`iddrivetype`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxiservice`.`taxidriver`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `taxiservice`.`taxidriver` (
  `idtaxidriver` INT NOT NULL ,
  `city_idcity` INT NOT NULL ,
  `name` VARCHAR(256) NULL ,
  `drivetype_iddrivetype` INT NOT NULL ,
  PRIMARY KEY (`idtaxidriver`) ,
  UNIQUE INDEX `idtaxidriver_UNIQUE` (`idtaxidriver` ASC) ,
  INDEX `fk_taxidriver_city1_idx` (`city_idcity` ASC) ,
  INDEX `fk_taxidriver_drivetype1_idx` (`drivetype_iddrivetype` ASC) ,
  CONSTRAINT `fk_taxidriver_city1`
    FOREIGN KEY (`city_idcity` )
    REFERENCES `taxiservice`.`city` (`idcity` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_taxidriver_drivetype1`
    FOREIGN KEY (`drivetype_iddrivetype` )
    REFERENCES `taxiservice`.`drivetype` (`iddrivetype` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxiservice`.`user`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `taxiservice`.`user` (
  `iduser` INT NOT NULL ,
  `firstName` VARCHAR(45) NULL ,
  `email` VARCHAR(45) NOT NULL ,
  `passwordHash` VARCHAR(128) NOT NULL ,
  `lastName` VARCHAR(45) NULL ,
  PRIMARY KEY (`iduser`) ,
  UNIQUE INDEX `iduser_UNIQUE` (`iduser` ASC) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxiservice`.`userbonus`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `taxiservice`.`userbonus` (
  `iduserbonus` INT NOT NULL ,
  `taxidriver_idtaxidriver` INT NOT NULL ,
  `user_iduser` INT NOT NULL ,
  `description` VARCHAR(256) NULL ,
  `drivetype_iddrivetype` INT NOT NULL ,
  `value` DECIMAL(2) NOT NULL ,
  INDEX `fk_userbonus_taxidriver1_idx` (`taxidriver_idtaxidriver` ASC) ,
  INDEX `fk_userbonus_user1_idx` (`user_iduser` ASC) ,
  PRIMARY KEY (`iduserbonus`) ,
  UNIQUE INDEX `iduserbonus_UNIQUE` (`iduserbonus` ASC) ,
  INDEX `fk_userbonus_drivetype1_idx` (`drivetype_iddrivetype` ASC) ,
  CONSTRAINT `fk_userbonus_taxidriver1`
    FOREIGN KEY (`taxidriver_idtaxidriver` )
    REFERENCES `taxiservice`.`taxidriver` (`idtaxidriver` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_userbonus_user1`
    FOREIGN KEY (`user_iduser` )
    REFERENCES `taxiservice`.`user` (`iduser` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_userbonus_drivetype1`
    FOREIGN KEY (`drivetype_iddrivetype` )
    REFERENCES `taxiservice`.`drivetype` (`iddrivetype` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxiservice`.`phonenumber`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `taxiservice`.`phonenumber` (
  `idphonenumber` INT NOT NULL ,
  `taxidriver_idtaxidriver` INT NOT NULL ,
  `number` VARCHAR(45) NULL ,
  PRIMARY KEY (`idphonenumber`) ,
  UNIQUE INDEX `idphonenumber_UNIQUE` (`idphonenumber` ASC) ,
  UNIQUE INDEX `number_UNIQUE` (`number` ASC) ,
  CONSTRAINT `fk_phonenumber_taxidriver1`
    FOREIGN KEY (`taxidriver_idtaxidriver` )
    REFERENCES `taxiservice`.`taxidriver` (`idtaxidriver` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `taxiservice` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
