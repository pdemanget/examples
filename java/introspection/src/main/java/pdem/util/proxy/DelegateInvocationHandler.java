package pdem.util.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Permet de prendre la main avant après l'execution (Décorateur).
 *
 * @author pdemanget
 * @version 28 avr. 2016
 */
public class DelegateInvocationHandler implements InvocationHandler {
  protected final InvocationHandler delegate;
  protected final List<InvocationListener> listeners = new ArrayList<>();

  public DelegateInvocationHandler (InvocationHandler delegate) {
    super();
    this.delegate = delegate;
  }

  public Object invoke (Object proxy, Method method, Object[] args) throws Throwable {
    if("getListeners".equals(method.getName())){
      return method.invoke(this,args );
    }
    Object result;
    try {
      result = delegate.invoke(proxy, method, args);
    } catch (Exception e) {

      for(InvocationListener l:listeners){
        l.afterInvoke(proxy, method, args, null, e);
      }
      throw e;
    }

    for(InvocationListener l:listeners){
      try {
        l.afterInvoke(proxy, method, args, result, null);
      } catch (Exception e) {
        throw e;
      }
    }

    return result;
  }

  /**
   * expose liste instead of add/remove
   *
   * @return
   */
  public List<InvocationListener> getListeners(){
    return listeners;
  }

}
