package com.captainparlik.endpoints;

import java.util.List;

import com.captainparlik.model.entity.Booking;
import com.captainparlik.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public Booking book(Booking booking) {
        return bookingService.book(booking);
    }

    @DeleteMapping
    public void deleteBooking(Booking booking) {
        bookingService.deleteBooking(booking);
    }

    @PutMapping("/{bookingId}")
    public Booking changeBooking(@PathVariable final Long bookingId, Booking booking) {
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
