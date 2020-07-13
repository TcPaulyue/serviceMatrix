package com.servicematrix.javassist.Person;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

public class Person {
    private String name = "xiaoming";

    public void setName(String var1) {
        this.name = var1;
    }

    public String getName() {
        return this.name;
    }

    public Person() {
        this.name = "xiaohong";
    }

    public Person(String var1) {
        this.name = var1;
    }

    public void printName() {
        System.out.println(this.name);
    }

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException {
        ClassPool pool = ClassPool.getDefault();
        // 获取接口
        CtClass codeClassI = pool.get("com.servicematrix.javassist.Person.PersonI");
        // 获取上面生成的类
        CtClass ctClass = pool.get("com.servicematrix.javassist.Person.Person");
// 使代码生成的类，实现 PersonI 接口
        ctClass.setInterfaces(new CtClass[]{codeClassI});

// 以下通过接口直接调用 强转
        PersonI person = (PersonI)ctClass.toClass().newInstance();
        System.out.println(person.getName());
        person.setName("xiaolv");
        person.printName();
    }
}