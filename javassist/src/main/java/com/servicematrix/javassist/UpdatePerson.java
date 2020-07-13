package com.servicematrix.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.*;

import java.lang.reflect.Method;

public class UpdatePerson {

    public static void update() throws Exception {
        ClassPool pool = ClassPool.getDefault();


        //将BuyCoffee方法转成字节码
        CtClass bc = pool.get("com.servicematrix.javassist.BuyCoffee");
        CtMethod buyCoffee = bc.getDeclaredMethod("orderCoffeeService");
        MethodInfo buyCoffeeMethodInfo = buyCoffee.getMethodInfo();

        //在PersonService中加入BuyCoffee方法
        ClassFile cf = pool.get("com.servicematrix.javassist.PersonService").getClassFile();
        cf.addMethod(buyCoffeeMethodInfo);


        CtClass cc = pool.get("com.servicematrix.javassist.PersonService");


        //修改方法
        CtMethod personFly = cc.getDeclaredMethod("personFly");
        personFly.insertBefore("System.out.println(\"起飞之前准备降落伞\");");
        personFly.insertAfter("System.out.println(\"成功落地。。。。\");");


        //新增一个方法

        CtMethod ctMethod = new CtMethod(CtClass.voidType, "personRun", new CtClass[]{}, cc);
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody("{System.out.println(\"oh my god,I can run!!\");}");
        cc.addMethod(ctMethod);


        Object person = cc.toClass().newInstance();
        // 调用 personFly 方法
        System.out.println("=========personFly=============");
        Method personFlyMethod = person.getClass().getMethod("personFly");
        personFlyMethod.invoke(person);

        //调用 personRun 方法
        System.out.println("=========personFly=============");
        Method personRunMethod = person.getClass().getMethod("personRun");
        personRunMethod.invoke(person);

        //调用 drinkCoffee 方法
        System.out.println("=========personDrinkCoffee=============");
        Method drinkCoffeeMethod = person.getClass().getMethod("orderCoffeeService");
        drinkCoffeeMethod.invoke(person);

       // cc.writeFile("/Users/tangcong/Desktop/serviceMatrix/javassist/src/main/java/com/servicematrix/javassist");
    }

    public static void main(String[] args) {
        try {
            update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
