package com.captainparlik.jobs;

import java.util.Map;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
@DisallowConcurrentExecution
public interface ScheduledJob extends Job {

    @Override
    default void execute(JobExecutionContext jobExecutionContext) {
        execute(jobExecutionContext.getJobDetail().getJobDataMap());
    }

    void execute(Map<String, Object> jobData);
    String getGroupName();
    String getJobName();

}
