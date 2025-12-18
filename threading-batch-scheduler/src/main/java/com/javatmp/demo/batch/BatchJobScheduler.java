package com.javatmp.demo.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchJobScheduler {

    @Autowired
    private JobOperator jobOperator;
    @Autowired
    private Job job1;

//    @Scheduled(cron = "0 * * * * *") // every minute at zero seconds
    @Scheduled(fixedDelay = 5000)
    public void runJob() throws Exception {

        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis()) // unique param for every run
                .toJobParameters();
        JobExecution job1Execution = jobOperator.start(job1, params);

        log.info("job {} status is {}", job1Execution.getJobInstance().getJobName(),
                job1Execution.getStatus());

        job1Execution.setStatus(BatchStatus.STOPPED);
        log.info("job parameters are {}", job1Execution.getJobParameters());
    }
}
