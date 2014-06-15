package com.taxiservice.web.test.json;

import com.google.common.io.Resources;
import com.taxiservice.model.writer.CarpoolManagement;
import com.taxiservice.web.json.Mapper;
import com.taxiservice.web.request.CreatePassengerRequest;
import com.taxiservice.web.request.DriverCreationRequest;
import com.taxiservice.web.request.TripCreationRequest;
import com.taxiservice.web.request.UserCreationRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 * @author Herman Zamula
 */
@RunWith(Theories.class)
public class MapperTest {

    private MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

    @Before
    public void setUpConverter() {
        converter.setObjectMapper(new Mapper());
    }

    @DataPoints
    public static Class[] getClassesToCheck() {
        return new Class[]{
                CarpoolManagement.CarInfo.class,
                CarpoolManagement.DriverInfo.class,
                CarpoolManagement.PassengerInfo.class,
                CarpoolManagement.TripInfo.class
        };
    }

    @Theory
    public void testCouldBeDeserialized(Class classToCheck) {
        assertThat("Can read " + classToCheck, converter.canRead(classToCheck, MediaType.APPLICATION_JSON), is(true));
    }

    @Test
    public void testCreateAccount() throws IOException {
        UserCreationRequest result = readJson("json/user-creation.json", UserCreationRequest.class);
        assertNotNull(result);
        assertEquals("Герман", result.firstName);
        assertEquals("Замула", result.lastName);
        assertEquals("herman.zamula@gmail.com", result.email);
        assertEquals("pwd", result.password);
    }

    @SuppressWarnings("unchecked")
    private <T> T readJson(String resourceName, Class<? extends T> clazz) throws IOException {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        byte[] json = Resources.toByteArray(Resources.getResource(/*MapperTest.class, */resourceName));
        servletRequest.setContent(json);
        return (T) converter.read(clazz,
                new ServletServerHttpRequest(servletRequest));
    }

    @Test
    public void testCreateDriver() throws Exception {
        DriverCreationRequest request = readJson("json/driver-creation.json", DriverCreationRequest.class);
        assertThat(request.driverInfo.description, is("Опыт вождения - более 10 лет"));
        assertThat(request.driverInfo.phones.size(), is(2));
        assertThat(request.driverInfo.cars.size(), is(1));
    }

    @Test
    public void testCreatePassenger() throws Exception {
        CreatePassengerRequest passenger = readJson("json/passenger-creation.json", CreatePassengerRequest.class);
        assertThat(passenger.passengerInfo.phones.size(), is(1));
    }

    @Test
    public void testCreateTrip() throws Exception {
        TripCreationRequest trip = readJson("json/trip-creation.json", TripCreationRequest.class);
        assertThat(trip.car, is(1l));
        assertNull(trip.id);
        assertNotNull(trip.tripInfo);
        assertThat(trip.tripInfo.passengersLimit, is(3));
        assertThat(trip.tripInfo.start.lat, is(50.0045805f));
    }

}
