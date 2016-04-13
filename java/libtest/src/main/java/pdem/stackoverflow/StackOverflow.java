package pdem.stackoverflow;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 
 * <br><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 * 
 * @author pdemanget
 * @version 25 févr. 2016
 */
public class StackOverflow {
  public static Stream<Class<? extends CharSequence>> getClasses() {
    Stream<Class<? extends CharSequence>> map1 = Arrays.asList ("java.lang.String", "java.lang.StringBuilder", "Kaboom!").stream ().map (x -> {
      try {
        Class<?> result = Class.forName (x);
        return result == null ? null : result.asSubclass(CharSequence.class);
      } catch (Exception e) {
        e.printStackTrace ();
      }

      return null;
    });
    return map1.filter(x -> x != null);
  }
  
  public static Stream<Class<? extends CharSequence>> getClasses2 () {

    return Arrays.asList ("java.lang.String", "java.lang.StringBuilder", "Kaboom!").stream ().map (x -> {
      try {
        Class<?> result = Class.forName (x);
         return (Class<? extends CharSequence>)( result == null ? null : result.asSubclass(CharSequence.class));
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace ();
      }

      return (Class<? extends CharSequence>)null;
    }).filter(x -> x != null);
  }
  
}
