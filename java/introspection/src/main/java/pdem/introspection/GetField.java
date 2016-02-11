package pdem.introspection;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import fr.incore_systemes.si.mts.model.data.AcquitAlarm;

public class GetField {

  public static List<Field> getAllFields (@SuppressWarnings ("rawtypes") final Class clazz) {
    final List<Field> asList = toList (Arrays.asList (clazz.getDeclaredFields ()));
    Class zuper;
    while ((zuper = clazz.getSuperclass ()) != Object.class) {
      asList.addAll (Arrays.asList (zuper.getDeclaredFields ()));
    }
    return asList;
  }

  public static <T> List<T> toList (final Collection<T> col) {
    final ArrayList<T> list = new ArrayList<T> ();
    list.addAll (col);
    return list;
  }

  public static boolean isStatic (final Member m) {
    return ((m.getModifiers () & Modifier.STATIC) > 0);
  }

  public static Class getGenericType (final Type type) {
    if (!(type instanceof ParameterizedType)) {
      return (Class) type;
    }
    final ParameterizedType pt = (ParameterizedType) type;
    return (Class) pt.getActualTypeArguments ()[0];
  }

  private final Set<Class> processed = new HashSet ();

  @SuppressWarnings ({ "unchecked", "rawtypes" })
  public void getFields (final Class clazz, final int recursion) {
    if (this.processed.contains (clazz)) {
      return;
    }
    this.processed.add (clazz);
    List<Field> fields = getAllFields (clazz);
    // @SuppressWarnings("unchecked")
    // final List<String> names = toList (CollectionUtils.collect (fields,
    // new Transformer ()
    // {
    //
    // public Object transform(final Object input)
    // {
    // return ((Field) input).getName ();
    // }
    // }));
    Collections.sort (fields, new Comparator () {

      public int compare (final Object o1, final Object o2) {
        final Field f1 = (Field) o1;
        final Field f2 = (Field) o2;
        return f1.getName ().compareTo (f2.getName ());
      }

    });
    fields = toList (fields);

    fields = fields.stream ().filter (new Predicate () {

      public boolean test (final Object input) {
        final Field field = (Field) input;
        return field.getAnnotation (ManyToMany.class) != null 
            || field.getAnnotation (ManyToOne.class) != null
            || field.getAnnotation (OneToOne.class) != null
            || field.getAnnotation (OneToMany.class) != null
            ;
        
      }
    }).collect (Collectors.toList ());
    for (final Field field : fields) {
      if (!isStatic (field)) {
        System.out.println ("                       ".substring (0, 20 - recursion) //+ 
            + field.getName () + "\t" //+
            + ((field.getType () == List.class || field.getType () == Set.class) ? "*->" : "1->")
            + getGenericType (field.getGenericType ()).getSimpleName ());
        if (recursion > 0) {
          getFields (getGenericType (field.getGenericType ()), recursion - 1);
        }
      }
    }

  }

  public void start () {
    //final Class<?> clazz = Sheet.class;
    //final Class<?> clazz = Alarm.class;
    final Class<?> clazz = AcquitAlarm.class;
    System.out.println (clazz.getSimpleName ());
    getFields (clazz, 20);
  }

  public static void main (String[] args) {
    new GetField ().start ();
  }

}