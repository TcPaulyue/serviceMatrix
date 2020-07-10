package com.servicematrix.personproxy;

import com.servicematrix.personproxy.domain.PersonInfo;
import com.servicematrix.personproxy.domain.PersonLocation;
import net.sf.cglib.proxy.Enhancer;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

@Service
public class PersonService extends QuartzJobBean {

    private CoffeeMessagePublisher coffeeMessagePublisher;

    @Autowired
    public PersonService(CoffeeMessagePublisher coffeeMessagePublisher){
        this.coffeeMessagePublisher = coffeeMessagePublisher;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        BuyCoffeeProxy buyCoffeeProxy = new BuyCoffeeProxy(coffeeMessagePublisher);

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PersonInfo.class);
        enhancer.setCallback(buyCoffeeProxy);
        PersonInfo personInfo = (PersonInfo) enhancer.create();
        personInfo.sendInfo("abc",new PersonLocation(1.00,1.22));
    }
}
