package pdem.introspection.generator;

import java.lang.reflect.Field;
import java.util.List;

import pdem.introspection.IntrospectorGadget;

/**
 * BeanCopy generates copy instruction from class reflection.
 *
 * <b>© Copyright 2016 - pdemanget@gmail.com - LGPLv3</b>
 *
 * @author pdemanget
 */
public class BeanCopy {
  private String[] args;

  public static void main(String[] args) {
    try {
      new BeanCopy().setArgs(args).run();
    } catch (ClassNotFoundException e) {
      e.printStackTrace(System.err);
    }
  }

  /*
   * template
   * dest.set<Prop>(src.get<Prop>());
   */
  private void run() throws ClassNotFoundException {
    @SuppressWarnings ("rawtypes")
    Class clazz = Class.forName(args[0]);
    List<Field> fields = IntrospectorGadget.getInheritedFields(clazz);
    for (Field field:fields){

      String name = field.getName();
      name = name.substring(0,1).toUpperCase()+name.substring(1);
      System.out.println(  "dest.set"+name+"(src.get"+name+"());");
    }


  }

  public String[] getArgs() {
    return args;
  }

  public BeanCopy setArgs(String[] args) {
    this.args = args;
    return this;
  }

}
