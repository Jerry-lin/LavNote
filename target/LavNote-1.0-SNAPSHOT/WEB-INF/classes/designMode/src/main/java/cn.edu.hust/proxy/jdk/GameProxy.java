package cn.edu.hust.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class GameProxy {
    public static  GameTask  invoke(ClassLoader classLoader, Class<?>[] interfaces, final Object object)
    {
        return (GameTask) Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                System.out.println("使用动态代理");
                return method.invoke(object,args);
            }
        });
    }
}
