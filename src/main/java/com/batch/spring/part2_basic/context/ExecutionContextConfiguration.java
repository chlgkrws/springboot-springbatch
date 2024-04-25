package com.batch.spring.part2_basic.context;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExecutionContextConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("contextJob")
                .start(step1())
                .next(step2())
                .next(step3())
                .next(step4())
                .build();
    }

    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(new ExecutionContextTasklet1())
                .build();
    }

    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(new ExecutionContextTasklet2())
                .build();
    }

    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet(new ExecutionContextTasklet3())
                .build();
    }

    public Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet(new ExecutionContextTasklet4())
                .build();
    }
}
