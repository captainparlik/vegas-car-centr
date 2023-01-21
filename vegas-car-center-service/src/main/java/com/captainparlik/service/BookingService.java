package com.captainparlik.service;

import static com.captainparlik.exceptions.ErrorRegistry.BOOKING_NOT_FOUND;

import java.util.List;


import com.captainparlik.exceptions.IllegalBookingException;
import com.captainparlik.model.entity.Booking;
import com.captainparlik.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public Booking book(Booking booking) {
        if (bookingRepository.existsById(booking.getId())) {
            throw new IllegalBookingException(BOOKING_NOT_FOUND);
        }
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Booking booking) {
        if (bookingRepository.existsById(booking.getId())) {
            throw new IllegalBookingException(BOOKING_NOT_FOUND);
        }
        bookingRepository.delete(booking);
    }

    public Booking changeBooking(Long bookingId, Booking booking) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new IllegalBookingException(BOOKING_NOT_FOUND);
        }
        booking.setId(bookingId);
        return bookingRepository.save(booking);
    }

    public List<Booking> showAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new IllegalBookingException(BOOKING_NOT_FOUND));
    }
}
