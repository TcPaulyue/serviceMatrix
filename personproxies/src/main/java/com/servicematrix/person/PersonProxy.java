package com.servicematrix.person;

import com.rabbitmq.client.Channel;
import com.servicematrix.person.dynamicLoad.DynamicClassLoader;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.ClassFile;
import javassist.bytecode.MethodInfo;

import java.lang.reflect.Method;
import java.util.Map;

public class PersonProxy {
    public static void update() throws Exception {
        ClassPool pool = ClassPool.getDefault();

        //将BuyCoffee方法转成字节码
        CtClass bc = pool.get("com.servicematrix.person.OrderCoffeeService");
        CtMethod buyCoffee = bc.getDeclaredMethod("orderCoffee");
        MethodInfo buyCoffeeMethodInfo = buyCoffee.getMethodInfo();

        //在PersonService中加入BuyCoffee方法
        ClassFile cf = pool.get("com.servicematrix.person.Person").getClassFile();
        cf.addMethod(buyCoffeeMethodInfo);


        CtClass cc = pool.get("com.servicematrix.person.Person");

        //修改方法
        CtMethod personFly = cc.getDeclaredMethod("sendMessage");
        personFly.insertBefore("System.out.println(\"before send message\");");
        personFly.insertAfter("System.out.println(\"after send message\");");

        //新增一个方法

        CtMethod ctMethod = new CtMethod(CtClass.voidType, "personRun", new CtClass[]{}, cc);
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody("{System.out.println(\"oh my god,I can run!!\");}");
        cc.addMethod(ctMethod);

        Person person = (Person) cc.toClass().newInstance();
        person.setRoutingKey("kern.name");
        person.updateLocation("chris",1.00,2.00);
        System.out.println("=========sendMessage==========");
        String message = "send a message";
        Method sendMessageMethod = person.getClass().getMethod("sendMessage", String.class);
        sendMessageMethod.invoke(person,message);

        //调用 drinkCoffee 方法
        System.out.println("=========personDrinkCoffee=============");
        Method drinkCoffeeMethod = person.getClass().getMethod("orderCoffee", Channel.class,String.class,String.class
                ,Map.class);
        drinkCoffeeMethod.invoke(person,person.getChannel(),"cms",person.routingKey,person.getHeaders());

//        for(Method method:person.getClass().getMethods()){
//            System.out.println(method.getName());
//        }
        // cc.writeFile("/Users/tangcong/Desktop/serviceMatrix/javassist/src/main/java/com/servicematrix/javassist");
    }

    public static void main(String[] args) throws Exception {
        try {
            update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
