package com.captainparlik.endpoints;

import java.util.List;

import com.captainparlik.model.entity.Booking;
import com.captainparlik.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingEndpoint {

    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking book(@RequestBody Booking booking) throws SchedulerException {
        return bookingService.book(booking);
    }

    @DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
    }

    @PutMapping("/{bookingId}")
    public Booking changeBooking(@PathVariable Long bookingId, @RequestBody Booking booking) {
        return bookingService.changeBooking(bookingId, booking);
    }

    @GetMapping
    public List<Booking> showAllBookings() {
        return bookingService.showAllBookings();
    }
    @GetMapping("/{bookingId}")
    public Booking findById(@PathVariable Long bookingId) {
        return bookingService.findById(bookingId);
    }
}
