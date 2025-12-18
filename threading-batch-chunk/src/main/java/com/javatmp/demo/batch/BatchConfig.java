package com.javatmp.demo.batch;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.support.ListItemReader;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

@Configuration
public class BatchConfig {

    @Bean(name = "job1")
    public Job job1(JobRepository jobRepository, Step step1, Step step2) {
        return new JobBuilder("job1", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .next(step2)
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
                .listener(new StepLoggerListener()) // attach listener
                .build();
    }

    @Bean
    public Step step2(JobRepository jobRepository,
                      PlatformTransactionManager tm,
                      ItemReader<String> reader,
                      ItemProcessor<String, String> processor,
                      ItemWriter<String> writer) {
        return new StepBuilder("step2", jobRepository)
                .<String, String>chunk(3)   // chunk size 3
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new StepLoggerListener())
                .build();
    }

    @Bean
    public ItemReader<String> reader() {
        List<String> items = Arrays.asList("spring", "batch", "example", "gradle", "chunk");
        return new ListItemReader<>(items);
    }

    @Bean
    public ItemProcessor<String, String> processor() {
        return item -> item.toUpperCase(); // simple transform
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> items.forEach(System.out::println);
    }

}