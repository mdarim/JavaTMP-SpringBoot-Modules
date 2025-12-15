package com.javatmp.demo.batch;

import org.springframework.batch.core.configuration.annotation.EnableJdbcJobRepository;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
//@EnableJdbcJobRepository(dataSourceRef = "batchDataSource", transactionManagerRef = "batchTransactionManager")
public class BatchConfig {

    @Bean(name = "job1")
    public Job job1(JobRepository jobRepository, Step step1) {
        return new JobBuilder("job1", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }

    @Bean(name = "job2")
    public Job job2(JobRepository jobRepository
            , Step step1, Step step2) {
        return new JobBuilder("job2", jobRepository)
                .incrementer(new RunIdIncrementer())
//                .start(step1)
                .start(step2)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository,
                          PlatformTransactionManager tm) {
        return new StepBuilder("step1", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Hello from step1");
                    return RepeatStatus.FINISHED;
                }, tm)
                .build();
    }

    @Bean
    public Step step2(JobRepository jobRepository,
                      PlatformTransactionManager tm) {
        return new StepBuilder("step2", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Hello from step2");
                    return RepeatStatus.FINISHED;
                }, tm)
                .build();
    }

}