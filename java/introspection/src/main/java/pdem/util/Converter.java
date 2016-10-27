package pdem.util;

/**
 *
 *
 * @author pdemanget
 * @version 17 mai 2016
 */
public interface Converter<I,O> {
  public O to(I i);
  public I from(O o);

}
