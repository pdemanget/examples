package pdem.util.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PassthroughInvocationHandler implements InvocationHandler {

    private final Object target;

    public PassthroughInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(target, args);
    }
}