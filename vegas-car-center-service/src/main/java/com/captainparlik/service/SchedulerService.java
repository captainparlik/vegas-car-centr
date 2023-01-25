package com.captainparlik.service;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import com.captainparlik.jobs.ScheduledJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final Scheduler quartzScheduler;

    public void scheduleJob(final ScheduledJob job, final Map<String, Object> jobData,
                            final long period) throws SchedulerException {
        if (checkIfJobExists(getJobKey(job))) {
            rescheduleJob(job, period);
            return;
        }
        quartzScheduler.scheduleJob(buildJobDetail(job.getClass(), jobData, getJobKey(job)),
                                    buildPeriodicalTrigger(getTriggerKey(job), period));
    }

    public void scheduleOnDate(final ScheduledJob job,
                               final Map<String, Object> jobData,
                               final Date date) throws SchedulerException {
        if (checkIfJobExists(getJobKey(job))) {
            return;
        }

        quartzScheduler.scheduleJob(buildJobDetail(job.getClass(), jobData, getJobKey(job)),
                                    buildOneTrigger(getTriggerKey(job), date));
    }

    private Trigger buildOneTrigger(final TriggerKey triggerKey,
                                    final Date date) {
        return newTrigger()
                .withIdentity(triggerKey)
                .startAt(date)
                .build();
    }

    private Trigger buildPeriodicalTrigger(final TriggerKey triggerKey,
                                           final long period) {
        return newTrigger()
                .withIdentity(triggerKey)
                .startAt(Date.from(Instant.now().plusSeconds(period)))
                .withSchedule(simpleSchedule().withIntervalInMilliseconds(period).repeatForever())
                .build();
    }

    private JobDetail buildJobDetail(final Class<? extends Job> jobType,
                                     final Map<String, Object> jobData,
                                     final JobKey jobKey) {
        return newJob(jobType)
                .usingJobData(Objects.nonNull(jobData)
                                      ? new JobDataMap(jobData)
                                      : new JobDataMap())
                .withIdentity(jobKey)
                .requestRecovery()
                .build();
    }

    private static JobKey getJobKey(final ScheduledJob job) {
        return JobKey.jobKey(job.getJobName(), job.getGroupName());
    }

    private static TriggerKey getTriggerKey(final ScheduledJob job) {
        return TriggerKey.triggerKey(job.getJobName(), job.getGroupName());
    }

    private boolean checkIfJobExists(final JobKey jobKey) throws SchedulerException {
        return Objects.nonNull(quartzScheduler.getJobDetail(jobKey));
    }

    private void rescheduleJob(final ScheduledJob job, final long period) throws SchedulerException {
        final var trigger = (SimpleTrigger) quartzScheduler.getTrigger(getTriggerKey(job));
        final var triggerInterval = trigger.getRepeatInterval();

        if (triggerInterval != period) {
            final var triggerKey = getTriggerKey(job);
            quartzScheduler.rescheduleJob(getTriggerKey(job), buildPeriodicalTrigger(triggerKey, period));
        }
    }
}
