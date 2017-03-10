package pdem.weld;

import javax.inject.Inject;

/**
 *
 *
 * <b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 *
 * @author pdemanget
 * @version 1.0.0, 15 déc. 2016
 */
public class MyApplicationBean {

  @Inject
  private ExampleBean exampleBean;

  public void callBusinessMethod() {
    System.out.println("IWH"+exampleBean);

  }

}
