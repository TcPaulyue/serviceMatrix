package com.servicematrix.personservice;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail testQuartz2() {
        return JobBuilder.newJob(PersonService.class).withIdentity("QuartzTask").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger2() {
        return TriggerBuilder.newTrigger().forJob(testQuartz2())
                .withIdentity("QuartzTask")
//                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
                .withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?"))
                .build();
    }

}