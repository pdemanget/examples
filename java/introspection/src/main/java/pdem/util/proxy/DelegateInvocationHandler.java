package pdem.util.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Permet de prendre la main avant après l'execution (Décorateur).
 * <br><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
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
    listeners.stream().forEach(l->l.beforeInvoke(proxy, method, args));
    Object result = delegate.invoke(proxy, method, args);
    listeners.stream().forEach(l->l.afterInvoke(proxy, method, args, result));
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
