package pdem.introspection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <br><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 * 
 * TODO Introspector language:
 * 
 * exp expression
 * C Constant
 * F Field
 * M Method
 * 
 * exp->C | exp.F | exp.M
 * M->id(exp[,exp])
 * C->id
 * F->id
 * 
 * @author  pdemanget
 * @version 30 juin 2016
 */
public class IntrospectorGadget {
  
  
  
  @SuppressWarnings ("unchecked")
  public static void main (Class<?> clazz, String... args) {
    List<String> mainArgs = new ArrayList<>();
    for (String arg : args) {
      if (arg.startsWith("-D")) {
        String[] split = arg.split("=");
        System.setProperty(split[0].substring(2), split[1]);
      }
      else {
        mainArgs.add(arg);
      }
    }
    new Thread( () -> {

      try {

        clazz.getDeclaredMethod("main", String[].class).invoke(null, new Object [] { mainArgs.toArray(new String [0]) });
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
        e.printStackTrace();
      }
    }).start();
  }

  public static Object getField (Object bean, String fieldName) {
    if (bean == null) {
      return null;
    }
    Object value;
    try {
      Field field = bean.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      value = field.get(bean);
      if (value != null) {
        return value;
      }
    } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
      //ignore les erreurs
      System.out.println( e);
    }
    return null;
  }


}
