package com.batch.spring.part2_basic.jobparameter;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

//@Component
@RequiredArgsConstructor
public class JobParameterTest implements ApplicationRunner {

    private final JobLauncher jobLauncher;

    private final Job job;

    /**
     * jar 파일로 실행 시 Job Parameter를 지정하는 법
     * 1. gradle build
     * 2. spring.batch.job.enabled=true로 변경
     * 3. java -jar springboot-springbatch-0.0.1-SNAPSHOT.jar name=user1 seq\(long\)=4L date\(date\)=2024/01/01 age\(double\)=1.4
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user1")
                .addLong("seq", 2L)
                .addDate("date", new Date())
                .addDouble("age", 16.5)
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}
