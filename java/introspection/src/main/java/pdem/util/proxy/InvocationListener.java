package pdem.util.proxy;

import java.lang.reflect.Method;

/**
 * 
 * <br><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 * 
 * @author pdemanget
 * @version 28 avr. 2016
 */
public interface InvocationListener {

  void beforeInvoke(Object proxy, Method method, Object[] args);
  void afterInvoke(Object proxy, Method method, Object[] args, Object result);
}
