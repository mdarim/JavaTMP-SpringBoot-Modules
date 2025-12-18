package com.javatmp.demo.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class App {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner springBootMain(
            final JobOperator jobOperator,
            Job job1
    ) throws Exception {

        return args -> {
            log.info("*** Start Spring Boot Batching Project ***");
            log.info("job1 {}", job1);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("runAt", System.currentTimeMillis())
                    .addString("inputDate", "2025‑12‑01")
                    .toJobParameters();

            JobExecution job1Execution = jobOperator.start(job1, jobParameters);

            log.info("job {} status is {}", job1Execution.getJobInstance().getJobName(),
                    job1Execution.getStatus());
        };
    }
}
