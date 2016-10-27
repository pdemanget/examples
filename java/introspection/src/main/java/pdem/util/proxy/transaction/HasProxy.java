package pdem.util.proxy.transaction;

/**
 * Permet de renvoyer le proxy d'un service,  quand il y en a un.
 *
 *
 * @author pdemanget
 * @version 1.0.0, 23 sept. 2016
 */
public interface HasProxy {
  Object getProxy();
}
