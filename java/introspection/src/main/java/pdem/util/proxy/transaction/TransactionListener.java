package pdem.util.proxy.transaction;

import java.lang.reflect.Method;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

import pdem.util.proxy.InvocationListener;

public class TransactionListener implements InvocationListener {

  EntityManager entityManager;

  EntityTransaction tx;

  public TransactionListener (EntityManager entityManager) {
    super();
    this.entityManager = entityManager;
  }

  @Override
  public void beforeInvoke (Object proxy, Method method, Object[] args) {

    if (method.getAnnotation(Transactional.class) != null) {
      tx = entityManager.getTransaction();
      try {
        tx.begin();
      } catch (IllegalStateException e) {
        tx.rollback();
        tx = entityManager.getTransaction();
      }
    } else {
      tx=null;
    }

  }

  @Override
  public void afterInvoke (Object proxy, Method method, Object[] args, Object result, Throwable exception) {
    if (tx != null) {
      if(exception != null){
        tx.rollback();
      }else{
        tx.commit();
      }
    }
  }
}