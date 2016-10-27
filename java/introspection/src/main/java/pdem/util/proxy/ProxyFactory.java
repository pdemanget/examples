package pdem.util.proxy;

import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 *
 *
 * @author pdemanget
 * @version 28 avr. 2016
 */
public class ProxyFactory {

  @SuppressWarnings ("unchecked")
  public static <I,T > I getProxy(T target, Class<I> interfaz, InvocationListener... listeners ){
    DelegateInvocationHandler dih = new DelegateInvocationHandler( new PassthroughInvocationHandler(target));
    dih.getListeners().addAll(Arrays.asList(listeners));
    return (I)  Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{interfaz}, dih);
  }

}
