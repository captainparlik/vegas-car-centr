package com.captainparlik.service;

import static com.captainparlik.exceptions.ErrorRegistry.BOOKING_NOT_FOUND;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import com.captainparlik.exceptions.IllegalBookingException;
import com.captainparlik.jobs.DeleteBookingJobScheduler;
import com.captainparlik.model.entity.Booking;
import com.captainparlik.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final DeleteBookingJobScheduler deleteBookingJobScheduler;

    @Transactional
    public Booking book(Booking booking) throws SchedulerException {
        if (Objects.nonNull(booking.getId())) {
            throw new IllegalBookingException(BOOKING_NOT_FOUND);
        }
        bookingRepository.save(booking);
        deleteBookingJobScheduler.scheduleDeletion(booking);
        return booking;
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new IllegalBookingException(BOOKING_NOT_FOUND);
        }
        bookingRepository.deleteById(id);
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
