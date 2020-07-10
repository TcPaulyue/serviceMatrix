package com.servicematrix.personproxy;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail testQuartz2() {
        return JobBuilder.newJob(PersonService.class).withIdentity("QuartzTask").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger2() {
        return newTrigger().forJob(testQuartz2())
                .withIdentity("QuartzTask")
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withRepeatCount(5))
                //.withSchedule()
//                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
//                .withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?"))
                .build();
    }
}
