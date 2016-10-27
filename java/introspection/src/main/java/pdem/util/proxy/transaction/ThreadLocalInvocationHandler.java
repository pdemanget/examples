package pdem.util.proxy.transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public class ThreadLocalInvocationHandler implements InvocationHandler {
  protected final ThreadLocal delegate= new ThreadLocal();

  private Supplier factory;

  public ThreadLocalInvocationHandler (Supplier factory) {
    super();
    this.factory=factory;
  }

  public Object invoke (Object proxy, Method method, Object[] args) throws Throwable {
    Object result = null;
    if(delegate.get()==null){
      delegate.set(factory.get());
    }
    result = method.invoke(delegate.get(), args);
    return result;
  }
}