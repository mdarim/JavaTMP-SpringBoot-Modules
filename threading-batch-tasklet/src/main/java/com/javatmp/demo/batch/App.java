package com.javatmp.demo.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
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
            @Qualifier("job1") Job job1,
            @Qualifier("job2") Job job2
    ) throws Exception {

        return args -> {
            log.info("*** Start Spring Boot Batching Project ***");
            log.info("job1 {}, job2 {}", job1, job2);
            JobExecution job2Execution = jobOperator.start(job2, new JobParametersBuilder()
                    .addLong("run.id", System.currentTimeMillis())
                    .toJobParameters());
            log.info("job2 started {}", job2Execution.getJobInstanceId());

            TimeUnit.SECONDS.sleep(1);

            job2Execution = jobOperator.restart(job2Execution);
            log.info("job2 restarted {}", job2Execution.getJobInstanceId());
            // start job manually instead spring.batch.job.enabled: true
//            JobExecution job1Execution = jobOperator.start(job1, new JobParametersBuilder()
//                    .addLong("run.id", System.currentTimeMillis())
//                    .toJobParameters());
//            log.info("job1 started {}", job1Execution.getJobInstanceId());

//            IntStream.range(7, 17).forEach(s -> {
//                JobExecution job = jobOperator.startNextInstance(helloJob);
//                log.info("job started {}", job.getJobInstanceId());
//            });
        };
    }
}
