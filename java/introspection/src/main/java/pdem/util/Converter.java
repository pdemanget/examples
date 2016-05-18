package pdem.util;

/**
 * 
 * <br><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 * 
 * @author pdemanget
 * @version 17 mai 2016
 */
public interface Converter<I,O> {
  public O to(I i);
  public I from(O o);

}
