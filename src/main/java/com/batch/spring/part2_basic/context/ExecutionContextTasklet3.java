package com.batch.spring.part2_basic.context;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class ExecutionContextTasklet3 implements Tasklet {

    /**
     * 처음 Job을 실행할 때 name2라는 값은 JobExecutionContext에 존재하지 않음.
     * Job이 실패하여 다시 실행할 때는 실패했던 Step3 부터 재실행 되며,
     * DB JobExecutionContext 저장되어있던 name2가 로드되어 해당 Step은 정상 종료 됨.
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("step3 was executed");

        Object name = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("name2");

        if (name == null) {
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("name2", "user1");
            throw new RuntimeException("no name2");
        }

        return RepeatStatus.FINISHED;
    }
}
