package com.captainparlik.jobs;

import java.time.ZoneOffset;
import java.util.Date;

import com.captainparlik.model.entity.Booking;
import com.captainparlik.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteBookingJobScheduler {

    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

    private final DeleteBookingJob deleteBookingJob;
    private final SchedulerService schedulerService;

    public void scheduleDeletion(Booking booking) throws SchedulerException {
        Date fireDate = Date.from(booking.getDate().toInstant(ZONE_OFFSET));
        schedulerService.scheduleOnDate(deleteBookingJob, deleteBookingJob.getJobData(booking), fireDate);
    }
}
