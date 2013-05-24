package com.taxiservice.web.demo;


import com.google.common.base.Function;
import com.taxiservice.model.HasDriveType;
import com.taxiservice.model.PredefinedDataCreator;
import com.taxiservice.model.writer.UserManagement;
import org.apache.log4j.Logger;
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

@Singleton
@Component
@DependsOn("seedDataCreator")
public class DemoDataCreator {

    public static final Logger LOGGER = Logger.getLogger(DemoDataCreator.class);
    public static final String BONUS_DESCRIPTION = "ИДС Bonus существует с 2004 года - это надежная служба заказа такси по разумным ценам.\n" +
            "Круглосуточный вызов такси в любую точку Киева и ближний загород, встречи на ЖД-вокзалах";
    public static final List<String> BONUS_PHONES = new ArrayList<String>() {{
        add("099-444-999-22");
        add("(050) 633-04-43");
        add("(097) 623-33-55");
    }};
    private static final List<Long> driveTypes = newArrayList();
    @Inject
    UserManagement userManagement;
    @Inject
    PredefinedDataCreator initiator;

    private static boolean isInit = false;
    //TODO: Implement
    @PostConstruct
    public void init() {
        if(isInit) {
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

        initiator.createDriver("BONUS", BONUS_DESCRIPTION, "bonustaxi.com", kharkiv, BONUS_PHONES, getAnyDriveTypes());

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
}
