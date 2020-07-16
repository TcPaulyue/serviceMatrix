package com.servicematrix.person.dynamicLoad;

import java.io.*;

public class DynamicClassLoader extends ClassLoader {


    private String name;
    //类加载路径
    private String path;

    public DynamicClassLoader(ClassLoader parent, String name, String path) {
        super(parent); //父类加载器
        this.name = name;
        this.path = path;
    }
    /**
     * 重写父类的loadClass方法
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> clazz = null;
        //判断是不是本类
        if(this.name.equals(name)) {
            //查找是否已经被加载过了
            clazz = this.findLoadedClass(name);
            if(clazz == null) {
                //如果没找到则继续去查找
                clazz = this.findClass(name);
            }
        }
        return super.loadClass(name);
    }

    /**
     * 重写findClass方法，自定义规则
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //转成二进制字节流，因为JVM只认识二进制不认识字符串
        byte[] b = readFileToByteArray(name);
        return this.defineClass(this.name, b, 0, b.length);
    }

    /**
     * 将包名转换成全路径名，比如
     *
     * temp.a.com.dn.Demo -> D:/temp/a/com/dn/Demo.class
     *
     * @param name
     * @return
     */
    private byte[] readFileToByteArray(String name) {
        InputStream is = null;
        byte[] rtnData = null;
        //转换
        name = name.replaceAll("\\.", "/");
        //拼接
        String filePath = this.path + name + ".class";
        File file = new File(filePath);

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            is = new FileInputStream(file);
            int tmp = 0;
            while((tmp = is.read()) != -1) {
                os.write(tmp);
            }

            rtnData = os.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return rtnData;
    }

    public Class<?> loadClass() throws ClassNotFoundException {
        return loadClass(this.name);
    }

}