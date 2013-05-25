package com.taxiservice.web.demo;


import com.taxiservice.model.PredefinedDataCreator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Component
public class SeedDataCreator {

    public static final String BONUS_DESCRIPTION = "ИДС Bonus существует с 2004 года - это надежная служба заказа такси по разумным ценам.\n" +
            "Круглосуточный вызов такси в любую точку Киева и ближний загород, встречи на ЖД-вокзалах";
    public static final List<String> BONUS_PHONES = new ArrayList<String>() {{
        add("099-444-999-22");
        add("(050) 633-04-43");
        add("(097) 623-33-55");
    }};
    public static final List<String> RUSALKA_PHONES = new ArrayList<String>() {{
        add("(057) 28-70-70");
        add("716-16-00");
    }};
    public static final List<String> DIANA_PHONES = new ArrayList<String>() {{
        add("(057) 719-33-33");
    }};
    public static final List<String> GRANIT_PHONES = new ArrayList<String>() {{
        add("(057) 75-88-777");
        add("(050) 302-7777");
        add("570-77-37");
        add("(093) 391-8777");
    }};
    public static final List<String> KIEV_1629PHONES = new ArrayList<String>() {{
        add("(044)499-16-29\n");
        add("(067)499-16-29");
        add("(068)499-16-29");
        add("(099)633-99-99");
        add("(063)973-99-99");
        add("(093)557-99-99");
        add("(093)558-99-99");
    }};

    private static final Logger LOGGER = Logger.getLogger(SeedDataCreator.class);

    @PostConstruct
    public void create() {
        LOGGER.log(Level.INFO, "Seed data creator init");

    }
}
