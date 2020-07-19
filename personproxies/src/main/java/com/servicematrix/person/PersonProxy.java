package com.servicematrix.person;

import com.rabbitmq.client.Channel;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.ClassFile;
import javassist.bytecode.MethodInfo;
import net.openhft.compiler.CompilerUtils;

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
        ClassFile cf = pool.get("com.servicematrix.person.PersonSender").getClassFile();
        cf.addMethod(buyCoffeeMethodInfo);


        CtClass cc = pool.get("com.servicematrix.person.PersonSender");

        //修改方法
        CtMethod personFly = cc.getDeclaredMethod("sendMessage");
        personFly.insertBefore("System.out.println(\"before send message\");");
        personFly.insertAfter("System.out.println(\"after send message\");");

        //新增一个方法

        CtMethod ctMethod = new CtMethod(CtClass.voidType, "personRun", new CtClass[]{}, cc);
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody("{System.out.println(\"oh my god,I can run!!\");}");
        cc.addMethod(ctMethod);

        PersonSender personSender = (PersonSender) cc.toClass().newInstance();
        personSender.init("messageCenter",1.00,2.00);

        System.out.println("=========sendMessage==========");
        String message = "send a message";
        Method sendMessageMethod = personSender.getClass().getMethod("sendMessage", String.class);
        sendMessageMethod.invoke(personSender,message);

        //调用 drinkCoffee 方法
        System.out.println("=========personDrinkCoffee=============");
        Method drinkCoffeeMethod = personSender.getClass().getMethod("orderCoffee", Channel.class,String.class,String.class
                ,Map.class);
        drinkCoffeeMethod.invoke(personSender, personSender.getChannel(), personSender.getEXCHANGE_NAME(), personSender.routingKey, personSender.getHeaders());

//        for(Method method:personSender.getClass().getMethods()){
//            System.out.println(method.getName());
//        }
        // cc.writeFile("/Users/tangcong/Desktop/serviceMatrix/javassist/src/main/java/com/servicematrix/javassist");
    }

    public static void main(String[] args) throws Exception {
        try {
            update();

//            String className = "com.servicematrix.person.MyClass";
//            String javaCode = "package com.servicematrix.person;\n" +
//                    "public class MyClass implements OrderCoffee {\n" +
//                    "    public void print() {\n" +
//                    "        System.out.println(\"Hello World\");\n" +
//                    "    }\n" +
//                    "}\n";
//            Class aClass = CompilerUtils.CACHED_COMPILER.loadFromJava(className, javaCode);
//            OrderCoffee runner = (OrderCoffee) aClass.newInstance();
//            runner.print();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
