package com.taxiservice.web.demo;


import com.google.common.base.Function;
import com.taxiservice.model.HasDriveType;
import com.taxiservice.model.PredefinedDataCreator;
import com.taxiservice.model.writer.DriverManagement;
import com.taxiservice.model.writer.UserManagement;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static com.taxiservice.web.demo.SeedDataCreator.*;

@Singleton
@Component
@DependsOn("seedDataCreator")
public class DemoDataCreator {

    private static final List<Long> driveTypes = newArrayList();
    private static boolean isInit = false;
    @Inject
    UserManagement userManagement;
    @Inject
    PredefinedDataCreator initiator;
    @Inject
    DriverManagement driverManagement;

    //TODO: Implement
    @PostConstruct
    public void init() {
        if (isInit) {
            return;
        }
        isInit = true;
        final long ukraine = initiator.createCountry("Украина");
        final long usa = initiator.createCountry("USA");
        final long kharkiv = initiator.createCity("Харьков", ukraine);
        final long kyiv = initiator.createCity("Киев", ukraine);

        final long admin = initiator.createAdmin("Герман", "Замула", "herman.zamula@gmail.com", "pwd");
        final long sergiy = initiator.createUser("Сергей", "Сергиенко", "sergey@gmail.com", "pwd");
        final long dmitry = initiator.createUser("Дмитрий", "Корж", "dmintriy@gmail.com", "pwd");

        createDriveTypes();

        final long bonus = initiator.createDriver("BONUS", BONUS_DESCRIPTION, "bonustaxi.com", kharkiv, RUSALKA_PHONES, getAnyDriveTypes());
        final long русалочка = initiator.createDriver("Русалочка", "", "", kharkiv, RUSALKA_PHONES, getAnyDriveTypes());
        final long diana = initiator.createDriver("Diana", "", "", kharkiv, DIANA_PHONES, getAnyDriveTypes());
        final long гранит = initiator.createDriver("Гранит", "", "", kharkiv, GRANIT_PHONES, getAnyDriveTypes());
        final long driver = initiator.createDriver("Такси-1629", "", "taxi-1629.com.ua", kyiv, SeedDataCreator.KIEV_1629PHONES, getAnyDriveTypes());

        driverManagement.likeDriver(admin, bonus);
        driverManagement.likeDriver(sergiy, diana);
        driverManagement.likeDriver(admin, diana);
        driverManagement.likeDriver(admin, bonus);
        driverManagement.dislikeDriver(sergiy, driver);
        driverManagement.likeDriver(dmitry, гранит);

        userManagement.addUserToPlace(admin, kyiv);
        userManagement.addUserToPlace(sergiy, kyiv);
        userManagement.addUserToPlace(dmitry, kyiv);

        driverManagement.comment(dmitry, bonus, "Cool cool coooooooool");
        driverManagement.comment(sergiy, bonus, "Хорошее такси, мне понравилось");

        createTestDriver(kharkiv);
    }



    private List<HasDriveType> getAnyDriveTypes() {
        return from(driveTypes).transform(new Function<Long, HasDriveType>() {
            @Override
            public HasDriveType apply(Long input) {
                final double minVal = new Random().nextDouble() * 100;
                return new HasDriveType(input, minVal, minVal + 10, "");
            }
        }).toImmutableList();
    }

    private void createDriveTypes() {
        final long l = initiator.driveType("Тариф по городу", "Тариф по городу");
        final long l2 = initiator.driveType("Тариф за городом", "Тариф за городом");
        final long l1 = initiator.driveType("Простой", "Простой машины, в минуту");
        driveTypes.add(l);
        driveTypes.add(l2);
        driveTypes.add(l1);
    }

    private void createTestDriver(final long kharkiv) {
        driverManagement.createDriver(new DriverManagement.DriverInfo(
                "Some Driver", new ArrayList<String>(){{add("(056) 444-66-88");}},
                new ArrayList<Long>(){{ add(kharkiv);}},
                from(driveTypes).transform(new Function<Long, DriverManagement.PriceList>() {
                    @Override
                    public DriverManagement.PriceList apply(Long input) {
                        return new DriverManagement.PriceList(input, 44, 55, "");
                    }
                }).toImmutableList(), "some.com", "", "ololo@gmail.com"
        ));
    }
}
