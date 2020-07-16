package com.servicematrix.person.dynamicLoad;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    //需要热部署的那个类的全路径名称，不一定必须在项目中，可以在任意位置
    private static final String name = "com.servicematrix.person.dynamicLoad.MessageImpl";
    //需要热部署的类的classes目录
    private static final String path = "/Users/tangcong/Desktop/serviceMatrix/personproxies/target/classes/";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, InterruptedException {
         Message message = null;

        while(true) {
            //1、实例化自己的类加载器，并将当前线程的类加载器作为父类加载器，并将name和path传进去
            DynamicClassLoader loader = new DynamicClassLoader(Thread.currentThread().getContextClassLoader(), name, path);
            //2、调用本来的loadClass方法
            Class<?> clazz = loader.loadClass();
            //实例化上面path+name+".class"
            message = (Message)clazz.newInstance();
            //调用方法
            message.send();
            //每隔3s执行一次，检查class是否有变化
            Thread.sleep(5000);
        }
    }
}