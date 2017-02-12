package net.tobebetter.proxy.sun;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by 0513 on 2017/1/8.
 */
public class BusinessProcessorHandler implements InvocationHandler  {

    private Object target = null;

    BusinessProcessorHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("You can do something here before process your business");
        Object result = method.invoke(target, args);
        System.out.println("You can do something here after process your business");
        return result;
    }

}