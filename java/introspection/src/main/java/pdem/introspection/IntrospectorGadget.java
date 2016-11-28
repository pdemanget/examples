package pdem.introspection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
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

  /**
   * Return public inherited method
   *
   * @param clazz
   * @param name
   * @return
   */
  public static List<Method> getPublicMethod(Class clazz, String name){
    return Arrays.asList(clazz.getMethods()).stream().filter(m->name.equals(m.getName())).collect(Collectors.toList());
  }

  public static Object invoke(Object o,String name,Object... args){
    List<Method> methods = getPublicMethod(o.getClass(),name);
    if(methods.size()>0){
      try {
        return methods.get(0).invoke(o,args);
      } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
        throw new IllegalArgumentException("Method call error "+o.getClass().getSimpleName()+"."+name,e);
      }
    }
    throw new IllegalArgumentException("Method not found "+o.getClass().getSimpleName()+"."+name,null);
  }




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

  public static List<Field> getInheritedFields(@SuppressWarnings ("rawtypes") Class clazz){
    List<Field> result = new ArrayList<Field>();

    while (clazz != null){
      //TODO whould we remove super private or overriden fields?
      result.addAll(Arrays.asList(clazz.getDeclaredFields()));
      clazz=clazz.getSuperclass();
    }
    return result;
  }


}
