package pdem.util.proxy.transaction;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pdem.util.proxy.ProxyFactory;



public class AbstractTransactedService implements HasProxy{
  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  protected EntityManagerService entityManagerService;

  protected EntityManager entityManager;

  protected final Object proxy;


  /**
   * Crée un proxy transactionné et l'injecte dans le moteur d'injection.
   *
   */
  public AbstractTransactedService(){
//    Injector.injectMembers(this.getClass(), this);

    Class<?> interfaz = this.getClass().getInterfaces()[0];

    entityManager = entityManagerService.getEntityManager();

    proxy = ProxyFactory.getProxy(this, interfaz,new TransactionListener(entityManager));

    // Injector.setModelOrService(interfaz,proxy);


  }


  public Object getProxy () {
    return proxy;
  }

}
