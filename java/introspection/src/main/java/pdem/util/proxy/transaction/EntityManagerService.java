package pdem.util.proxy.transaction;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.DerbyTenSevenDialect;
import org.hibernate.dialect.MySQLDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityManagerService {
  public Logger logger = LoggerFactory.getLogger(this.getClass());
  public static String PERSISTENCE_UNIT_NAME = "rec";
  EntityManagerFactory entityManagerFactory;
  List<EntityManager> entityManagers = new ArrayList<>();

  public EntityManagerService(){

  }


  public void init (DbConfig db) {
    final Properties persistenceProperties = new Properties();
    if(!db.isEnable()){
      logger.warn("DATABASE is configure to be DISABLED. see gui_configuration.xml");
      db.setDriver("org.apache.derby.jdbc.EmbeddedDriver");
      db.setUrl("jdbc:derby:memory:demo;create=true");
      persistenceProperties.setProperty(AvailableSettings.HBM2DDL_AUTO, "create");
      //persistenceProperties.setProperty(AvailableSettings.HBM2DDL_DATABASE_ACTION, "create");
      persistenceProperties.setProperty("hibernate.dialect", DerbyTenSevenDialect.class.getName());

    } else {
      persistenceProperties.setProperty("hibernate.dialect", MySQLDialect.class.getName());

    }


    if ("***".equals(db.getPassword())) {
      throw new RuntimeException("Please configure database password "+db,null);
    }

//    persistenceProperties.setProperty(AvailableSettings.JPA_JDBC_DRIVER, db.getDriver());
//
//    persistenceProperties.setProperty(AvailableSettings.JPA_JDBC_URL, db.getUrl());
//    if(db.getUser() != null)
//      persistenceProperties.setProperty(AvailableSettings.JPA_JDBC_USER, db.getUser());
//    if(db.getUser() != null)
//      persistenceProperties.setProperty(AvailableSettings.JPA_JDBC_PASSWORD, db.getPassword() );

//    persistenceProperties.setProperty("hibernate.physical_naming_strategy", PhysicalNamingStrategyImpl.class.getName());
//    persistenceProperties.setProperty("hibernate.implicit_naming_strategy", ForeignKeyNamingStrategy.class.getName());

    persistenceProperties.setProperty("hibernate.current_session_context_class", "thread");

    // setC3P0(persistenceProperties);


    persistenceProperties.setProperty("hibernate.generate_statistics","true");
    persistenceProperties.setProperty("hibernate.cache.region.factory_class","org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
    persistenceProperties.setProperty("hibernate.cache.use_second_level_cache","true");
    persistenceProperties.setProperty("hibernate.cache.use_query_cache","true");


    entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, persistenceProperties);


    //Injector.setModelOrService(EntityManager.class ,this.entityManager);
//    Injector.setModelOrService(EntityManagerFactory.class ,this.entityManagerFactory);

    createEntityManager();
  }

  private void setC3P0(final Properties persistenceProperties) {
    persistenceProperties.setProperty("hibernate.connection.provider_class","org.hibernate.connection.C3P0ConnectionProvider");
    persistenceProperties.setProperty("hibernate.c3p0.max_size", "1");
    persistenceProperties.setProperty("hibernate.c3p0.min_size", "1");
    persistenceProperties.setProperty("hibernate.c3p0.acquire_increment", "1");
    persistenceProperties.setProperty("hibernate.c3p0.idle_test_period", "30");
    persistenceProperties.setProperty("hibernate.c3p0.max_statements", "1");
    persistenceProperties.setProperty("hibernate.c3p0.timeout", "1000");

    persistenceProperties.setProperty("hibernate.c3p0.testConnectionOnCheckout", "true");
    persistenceProperties.setProperty("hibernate.c3p0.testConnectionOnCheckin", "false");
//    persistenceProperties.setProperty("hibernate.c3p0.acquireRetryAttempts", "0");
    persistenceProperties.setProperty("hibernate.c3p0.acquire_increment", "3");
  }

  public void close(){
    for(EntityManager entityManager:entityManagers){
      entityManager.close();
    }
  }

  public EntityManager getEntityManager () {
    if(entityManagerFactory == null){
      //throw new LineaError("Bean not initialized",null);
      return null;
    }
    //EntityManager entityManager = createEntityManager();
    //ThreadLocalInvocationHandler
    ThreadLocalInvocationHandler invocationHandler = new ThreadLocalInvocationHandler(this::createEntityManager);
    EntityManager entityManager =
        (EntityManager) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{EntityManager.class}, invocationHandler);
    return entityManager;
  }

  private EntityManager createEntityManager () {
    if(!entityManagers.isEmpty()){
      return entityManagers.get(0);
    }

    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManagers.add(entityManager);
    return entityManager;
  }
}
