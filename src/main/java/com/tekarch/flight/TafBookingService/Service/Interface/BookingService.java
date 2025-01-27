package com.tekarch.flight.TafBookingService.Service.Interface;

import com.tekarch.flight.TafBookingService.Model.Booking;

import java.util.List;

public interface BookingService {

    // Method to create a new booking
    Booking createBooking(Long userId, Long flightId);

    // Method to retrieve booking details by booking ID
    Booking getBookingById(Long bookingId);

    // Method to retrieve all bookings for a specific user by user ID
    List<Booking> getBookingsByUserId(Long userId);

    // Method to cancel a booking by changing its status to "Cancelled"
    void cancelBooking(Long bookingId);
}