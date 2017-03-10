package pdem.weld;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 *
 *
 * <b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 *
 * @author pdemanget
 * @version 1.0.0, 15 déc. 2016
 */
public class WeldExample {
  public static void main(String[] args) {

    Weld weld = new Weld();

    try (WeldContainer container = weld.initialize()) {

      container.select(MyApplicationBean.class).get().callBusinessMethod();

    }
  }
}
