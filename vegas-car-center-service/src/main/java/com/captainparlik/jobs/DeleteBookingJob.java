package com.captainparlik.jobs;

import java.util.Map;

import com.captainparlik.model.entity.Booking;
import com.captainparlik.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteBookingJob implements ScheduledJob {

    private final BookingRepository bookingRepository;
    private Map<String, Object> jobData;

    @Override
    public void execute(final Map<String, Object> jobData) {
        bookingRepository.deleteById((Long) jobData.get("bookingId"));
    }

    public Map<String, Object> getJobData(Booking booking) {
        jobData = Map.of("bookingId",booking.getId());
        return jobData;
    }

    @Override
    public String getGroupName() {
        return "Group";
    }

    @Override
    public String getJobName() {
        return "DeletePastBookings"+ jobData.get("bookingId");
    }
}
