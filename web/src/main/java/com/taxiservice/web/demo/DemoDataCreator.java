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
    public static final String DESCRIPTION_1629 = "Заказывайте такси представительского класса на любое время и в любую точку города! Гибкая система скидок. Тариф называет оператор.Наличный и безналичный расчет.";
    private static boolean isInit = false;
    @Inject
    UserManagement userManagement;
    @Inject
    PredefinedDataCreator initiator;
    @Inject
    DriverManagement driverManagement;

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
        createMoreCities(ukraine);

        final long admin = initiator.createAdmin("Герман", "Замула", "herman.zamula@gmail.com", "pwd");
        final long sergiy = initiator.createUser("Сергей", "Сергиенко", "sergey@gmail.com", "pwd");
        final long dmitry = initiator.createUser("Дмитрий", "Корж", "dmintriy@gmail.com", "pwd");

        createDriveTypes();

        final long bonus = initiator.createDriver("BONUS", BONUS_DESCRIPTION, "bonustaxi.com", kharkiv, RUSALKA_PHONES, getAnyDriveTypes());
        final long русалочка = initiator.createDriver("Русалочка", "", "", kharkiv, RUSALKA_PHONES, getAnyDriveTypes());
        final long diana = initiator.createDriver("Diana", "", "", kharkiv, DIANA_PHONES, getAnyDriveTypes());
        final long гранит = initiator.createDriver("Гранит", "", "", kharkiv, GRANIT_PHONES, getAnyDriveTypes());
        final long driver = initiator.createDriver("Такси-1629", DESCRIPTION_1629, "taxi-1629.com.ua", kyiv, SeedDataCreator.KIEV_1629PHONES, getAnyDriveTypes());

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
        driverManagement.comment(sergiy, bonus, "Плохое обслуживание, все плохо. Беспредел и безысходность.");

        driverManagement.comment(admin, driver, "Одно из худших такси.\n" +
                "\n" +
                "Операторы неадекватны, т.к. перезванивают, говорят \"Алло\" и ждут, что же ты им скажешь.\n" +
                "\n" +
                "Понятно ведь что не пиццу я у них хочу заказать.\n" +
                "\n" +
                "Ладно, говорю \"хочу заказать такси\".\n" +
                "\n" +
                "подумали-подумали, спросили куда подать, куда едем, т.е. стандарт.\n" +
                "\n" +
                "Цена была в полтора раза выше чем у других такси.\n" +
                "\n" +
                "Согласился.");

        driverManagement.comment(admin, driver, "Что могу сказать...Подают машинки вовремя, тарифы радуют, водители вменяемые...авто в основном класса эконом, но это небольшой минус!!!! Так держать!!! Забил в телефон их номер и поубирал остальные номера такси :)!!!!");

        driverManagement.comment(sergiy, diana, "согласна! всегда езжу Эталоном. удобно - присылают  смску с номерами и веренем прибытия на место. дешево. всегда есть машины!! ну, разве что в киеве какая-то беда дорожная. было только один раз  такое, что не было машин весь вечер. но тогда и в народном не было, и  в алло! так что почапала ппешком и с десятью пересадками. \n" +
                "\n" +
                "что еще важно - не хамят по телефону. как-то было даже так, что я ждала, а машины не было и не было. я перезвонила, мне дали моего оператора и она со мной и водителем общалась параллельно - он перепутал и приехал не туда чуть-чуть. очень хорошее такси, всем советуюю. ");


        driverManagement.comment(dmitry, diana, "Классная служба! Вежливые диспетчера, это редкий случай)) Всегда хорошие машины, и цены радуют!");

        driverManagement.comment(admin, гранит, "Сегодня по дороге на работу видел в потоке чёрную Кэмри с номером 777, засмотрелся, понравилась фишка на крыше, вежливый водитель пропустил. Запомнил номер 222... нашел сайт, оказалось Президент Такси. Обязательно в следующий раз позвоню, закажу.");

        driverManagement.comment(dmitry, bonus, "В воскресенье поехал с семьей отдыхать, заказал такси \"повышенной комфортности\". Сначала пообещали Ауди А-6, но приехала старенькая Вольво (я так понимаю, для них все что не \"дэу-ланос\" - уже повышенной комфортности считается). При этом сначала назвали цену 140 гривен, потом перезвонили и сказали что будет 171 (стандартная цена - около 90 гривен).\n" +
                "\n" +
                "И да, машина опоздала на 10 минут, хотя я заранее заказывал.\n" +
                "\n" +
                "Ладно, сели, поехали.\n" +
                "\n" +
                "В дороге выяснилось, что кондиционер не работает. Водитель предложил нам открыть все окна. Говорит, что кондиционер \"работает, но только с пылью\". А я как раз с двумя детьми именно заказывал машину с кондиционером, чтобы с \"повышенной комфортностью\" проехать по 28-градусной жаре из одного конца города в другой.\n" +
                "\n" +
                "Когда приехали, у водителя не оказалось сдачи с 200 гривен. Еще минут 10 он бегал искал сдачу и потом высокомерно предложил мне скидку в 1 гривну. На мой отказ от такой издевательской скидки, водитель начал хамить и высказывать мне - кто я такой и какой из меня клиент.\n" +
                "\n" +
                "В общем, получили отличный сервис за двойную цену. Отличная машина, отличный водитель и отличные операторы. И хоть бы кто-нибудь извинился или попытался как-то исправить ситуацию.\n" +
                "\n" +
                "Как-то плохо уживаются реальные таксисты и рекламные обещания компании \"элитное такси в Киеве, предоставляющее услуги премиум-класса. Для нас Вы всегда VIP! Наши клиенты – это неизменно люди, которые привыкли выбирать услуги наивысшего уровня\". Фигня полная.\n" +
                "\n");


        driverManagement.comment(admin, bonus, "А вот это такси мне понравилось. И мужички нормальные работают, и машина приходит без опозданий, и везут аккуратно. Да и машинки ничего - хотя конечно, для меня это не критерий, ведь и на дешевых машинах люди тоже хотят заработать.\n" +
                "\n" +
                "Может, тут не самая низкая цена. Но зато оправданная, если точно знаешь, что платишь за комфорт, там более, если садишь в такси жену или родственницу с ребенком. Хочется ведь, чтобы их довезли куда надо без приключений и не нахамили по дороге.");


        createTestDriver(kharkiv);
    }

    private void createMoreCities(long ukraine) {
        initiator.createCity("Луганск", ukraine);
        initiator.createCity("Днепропетровск", ukraine);
        initiator.createCity("Кировоград", ukraine);
        initiator.createCity("Львов", ukraine);
        initiator.createCity("Чернигов", ukraine);
        initiator.createCity("Суммы", ukraine);
        initiator.createCity("Полтава", ukraine);
        initiator.createCity("Черновцы", ukraine);
        initiator.createCity("Донецк", ukraine);
    }


    private List<HasDriveType> getAnyDriveTypes() {
        return from(driveTypes).transform(new Function<Long, HasDriveType>() {
            @Override
            public HasDriveType apply(Long input) {
                final double minVal = new Random().nextDouble() * 100;
                return new HasDriveType(input, minVal, minVal + 10, "");
            }
        }).toList();
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
                }).toList(), "some.com", "", "ololo@gmail.com"
        ));
    }
}
