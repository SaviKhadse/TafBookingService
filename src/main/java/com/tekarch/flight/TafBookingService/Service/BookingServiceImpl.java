package com.tekarch.flight.TafBookingService.Service;

//import com.tekarch.flight.TafBookingService.Controller.BookingController;
import com.tekarch.flight.TafBookingService.Model.Booking;
import com.tekarch.flight.TafBookingService.Model.Flight;
import com.tekarch.flight.TafBookingService.Model.User;
import com.tekarch.flight.TafBookingService.Service.Interface.BookingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    Logger logger = LogManager.getLogger(BookingServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${booking.ms.url}")
    String bookingssurl;

    @Value("${flights.ms.url}")
    String flightsurl;

    @Value("${users.ms.url}")
    String usersurl;

//    private static final String DATASOURCE_URL = "http://localhost:8081/bookings";
//    private static final String FLIGHT_URL = "http://localhost:8081/flights/";
//    private static final String USER_DATASOURCE_URL ="http://localhost:8081/users/";

    @Override
    public Booking createBooking(Long userId, Long flightId) {
        // Check available seats
//        System.out.println(userId);
        logger.info("userId"+ userId);

        Flight flight = restTemplate.getForObject(flightsurl +"/"+ flightId, Flight.class);
        User user= restTemplate.getForObject(usersurl +"/"+ userId, User.class);
        if (flight == null) {
            throw new IllegalArgumentException("Flight not available");
        }

        if (flight.getAvailableSeats() <= 0) {
            throw new IllegalArgumentException("No available seats");
        }

        // Create a booking if seats are available
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setFlight(flight);
        booking.setStatus("Booked");
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());

        logger.info("Booking - "+booking.getUser().getId());
        logger.info("Booking - "+ booking.getFlight().getId());

        // Reduce available seats after booking
        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        restTemplate.put(flightsurl +"/"+ flightId, flight);  // Update the available seats in flight
        logger.info("Booking object"+booking.toString());
        Booking savedBooking = restTemplate.postForObject(bookingssurl, booking, Booking.class);

        logger.info("saved booking"+savedBooking);

        // Create the booking in the datastore service
        return savedBooking;
        
    }

    @Override
    public Booking getBookingById(Long bookingId) {
        // Get booking by ID
        String getBookingurl= bookingssurl + "/" + bookingId;
        return restTemplate.getForObject(getBookingurl, Booking.class);
    }
    // Get bookings by user ID
    @Override
    public List<Booking> getBookingsByUserId(Long userId) {
        String  getBookingbyUserId = bookingssurl + "/user/" + userId;
        Booking[] bookings= restTemplate.getForObject(getBookingbyUserId, Booking[].class);
        return Arrays.asList(bookings);
    }

    @Override
    public void cancelBooking(Long bookingId) {
        // Cancel booking and update status
        String deleteBookingUrl= bookingssurl + "/" + bookingId;
        Booking booking = restTemplate.getForObject(deleteBookingUrl, Booking.class);

        if (booking != null) {
//            booking.setStatus("Cancelled");
//            booking.setUpdatedAt(LocalDateTime.now());

            // Restore available seat count
            Flight flight = restTemplate.getForObject(flightsurl +"/"+ booking.getFlight().getId(), Flight.class);
            if (flight != null) {
                flight.setAvailableSeats(flight.getAvailableSeats() + 1);
                restTemplate.put(flightsurl +"/" + booking.getFlight().getId(), flight);
            }

            // Update booking status in the datastore service
//            String deleteBookingUrl= DATASOURCE_URL + "/" + bookingId;
            logger.info("delete "+ deleteBookingUrl);
            restTemplate.delete(deleteBookingUrl);
        }
    }
}