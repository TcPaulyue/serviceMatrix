package com.servicematrix.personservice;

import org.quartz.*;
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
                .withRepeatCount(2))
                //.withSchedule()
//                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
//                .withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?"))
                .build();
    }

}