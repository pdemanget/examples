package pdem.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 *
 * @author pdemanget
 * @version 31 mars 2016
 */
public class CollectionUtils {

  public static Map<String, String> createMap (String... values) {
    Map<String, String> result = new HashMap<>();

    for (int i = 0; i < values.length; i += 2) {
      result.put(values[i], values[i + 1]);
    }
    return result;
  }

  /**
   * Manage lazy insert in Map.
   *
   * @param key
   * @param map
   * @param func
   * @return
   */
  public static <K, V> V lazyCreate (K key, Map<K, V> map, Function<K, V> func) {
    V value = map.get(key);
    if (value == null) {
      value = func.apply(key);
      map.put(key, value);
    }
    return value;
  }

  public static <T> T pop (List<T> list) {
    if (!list.isEmpty()) {
      T rslt = list.get(list.size() - 1);
      list.remove(list.size() - 1);
      return rslt;
    }
    return null;
  }

  public static <T> T peek (List<T> list) {
    if (!list.isEmpty()) {
      T rslt = list.get(list.size() - 1);
      return rslt;
    }
    return null;
  }


  public static <T,U> List<U> map(List<T> list, Function<T,U> fun){
    return list.stream().map(fun).collect(Collectors.toList());
  }

  public static <T> T find(List<T> list, Predicate<T> fun){
    return list.stream().filter(fun).findFirst().orElse(null);
  }

  /**
   * Contains giving predicate instead of classic equals method
   *
   * @param list
   * @param fun
   * @return
   */
  public static <T> boolean containsStream(List<T> list, Predicate<T> fun){
    return list.stream().anyMatch(fun);
  }
  public static <T> boolean contains2(List<T> list, Predicate<T> fun){
    for(T t:list){
      if (fun.test(t)){
        return true;
      }
    }
    return false;
  }

  public static <T> boolean contains(List<T> list, Predicate<T> fun){
    return containsStream(list,fun);
  }
  // perf problem, see http://stackoverflow.com/questions/22658322/java-8-performance-of-streams-vs-collections
  public static void main (String[] args) {
    List<String> list = new ArrayList<>();
    for(int i=0;i<1000;i++){
      list.add("test"+i);
    }
    //warm up
    for(int i=0;i<1000;i++){
      contains(list,s->"test".equals(s));
    }

    long start=System.currentTimeMillis();
    for(int i=0;i<100_000;i++){
      contains(list,s->"test".equals(s));
    }
    long end=System.currentTimeMillis();
    System.out.printf("Exec: %d ms",end-start);
  }

}
