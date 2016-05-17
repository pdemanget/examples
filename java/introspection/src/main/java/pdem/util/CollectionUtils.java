package pdem.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 
 * <br><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
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

}
