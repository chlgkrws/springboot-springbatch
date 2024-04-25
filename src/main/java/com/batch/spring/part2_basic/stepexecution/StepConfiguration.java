package com.batch.spring.part2_basic.stepexecution;

import com.batch.spring.part2_basic.step.CustomTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@RequiredArgsConstructor
public class StepConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /**
     * - Step에 대한 한번의 시도를 의미하는 객체로서 Step 실행 중에 발생한 정보들을 저장하고 있는 객체
     * - Job이 재시작 하더라도 이미 성공적으로 완료된 Step은 재실행되지 않고 실패한 Step만 실행
     * - Step이 실행되지 않았다면 StepExecution을 생성하지 않는다. Step이 실제로 시작됐을 때만  StepExecution을 생성한다
     */
    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(" >> Step1 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    /**
     * 성공 케이스인 경우 Step Execution은 step1, 2, 3 총 3개가 생성되지만
     * 실패 케이스인 경우 Step Execution은 step1, 2(실패) 총 2개가 생성된다.
     *  - 재실행되면 실패했던 Step부터 재실행되어 step2, 3이 실행되며 재실행에 대한 StepExecution이 총 2개 생성된다.
     * @return
     */
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    // 성공 케이스
                    System.out.println(" >> Step2 has executed");
                    return RepeatStatus.FINISHED;

                    // 실패 케이스
//                    throw new RuntimeException(" >> Step2 has failed");
                })
                .build();
    }

    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(" >> Step3 has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
