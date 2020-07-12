package com.servicematrix.personproxy;

import com.servicematrix.personproxy.domain.PersonInfo;
import net.sf.cglib.proxy.Enhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
public class PersonService implements CommandLineRunner{

    private CoffeeMessagePublisher coffeeMessagePublisher;

    @Autowired
    public PersonService(CoffeeMessagePublisher coffeeMessagePublisher){
        this.coffeeMessagePublisher = coffeeMessagePublisher;
    }

    @Override
    public void run(String... args) throws Exception{
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("a");
        personInfo.setXloc(1.00);
        personInfo.setYloc(1.00);
        personInfo.updatePersonInfo("b",1.01,2.00);
        Thread.sleep(1000);

        BuyCoffeeProxy buyCoffeeProxy = new BuyCoffeeProxy(coffeeMessagePublisher);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PersonInfo.class);
        enhancer.setCallback(buyCoffeeProxy);
        personInfo = (PersonInfo) enhancer.create();
        personInfo.setPersonInfo("c",1.00,1.01);

        Thread.sleep(1000);

        //personInfo.select();
        System.out.println(personInfo.updatePersonInfo("b",1.01,2.00));

        Thread.sleep(10000);
    }
}
