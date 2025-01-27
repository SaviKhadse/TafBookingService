package com.tekarch.flight.TafBookingService.Controller;

import com.tekarch.flight.TafBookingService.Model.Booking;
import com.tekarch.flight.TafBookingService.Service.Interface.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    Logger logger = LogManager.getLogger(BookingController.class);


    @Autowired
    private BookingService bookingService;

    // Endpoint to create a new booking
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestParam("uid") Long uid, @RequestParam("fid") Long fid) {
//        logger.warn("hello");
        try {
//            logger.info("UserId"+ fid.toString());
            Booking booking = bookingService.createBooking(uid, fid);
//            BookingResponseDTO bookingResponseDTO= new BookingResponseDTO(bookingResponseDTO.getId(), bookingResponseDTO.getUser().getId(),
//                    bookingResponseDTO.getFlight().getId() ,bookingResponseDTO.getStatus());

            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    // Endpoint to get booking details by booking ID
    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking != null) {
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to get all bookings for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        if (bookings != null && !bookings.isEmpty()) {
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }



    // Endpoint to cancel a booking (mark as "Cancelled")
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}