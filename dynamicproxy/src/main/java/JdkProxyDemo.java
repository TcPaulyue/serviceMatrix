import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyDemo {
    interface If {
        void sendPersonInfo(String s);
    }
    static class CoffeeMessage implements If {
        public void sendPersonInfo(String s) {
            System.out.println(s);
        }
    }
    static class Handler implements InvocationHandler {
        private final If original;
        public Handler(If original) {
            this.original = original;
        }

        public Object invoke(Object proxy, Method method, Object[] args)
                throws IllegalAccessException, IllegalArgumentException,
                InvocationTargetException {
            System.out.println("BEFORE");
            method.invoke(original, args);
            System.out.println("AFTER");
            return null;
        }
    }
    public static void main(String[] args){
        CoffeeMessage coffeeMessage = new CoffeeMessage();
        Handler handler = new Handler(coffeeMessage);
        If f = (If) Proxy.newProxyInstance(If.class.getClassLoader(),
                new Class[] { If.class },
                handler);
        f.sendPersonInfo("Hallo");
//        DaoProxy daoProxy = new DaoProxy();
//
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(Dao.class);
//        enhancer.setCallback(daoProxy);
//        Dao dao = (Dao)enhancer.create();
//        dao.update();
//        dao.select();
    }
}