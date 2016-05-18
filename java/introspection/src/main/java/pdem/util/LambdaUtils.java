package pdem.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeItem;
import javafx.util.StringConverter;

public class LambdaUtils {

  
  /**
   * grouper sur 1 niveau
   */
  public static <K, V> Map<K, List<V>> groupByFields (List<V> modelList, Function<? super V, ? extends K> classifier1) {

    Collector<V, ?, Map<K, List<V>>> groupingBy = Collectors.groupingBy(classifier1);
    Map<K, List<V>> collect = modelList.stream().collect(groupingBy);
    return collect;
  }

  /**
   * grouper sur 2 niveaux.
   */
  public static <K, V> Map<K, Map<K, List<V>>> groupByFields2 (List<V> modelList, Function<? super V, ? extends K> classifier1,
      Function<? super V, ? extends K> classifier2) {

    Collector<V, ?, Map<K, List<V>>> groupingBy2 = Collectors.groupingBy(classifier2);
    Collector<V, ?, Map<K, Map<K, List<V>>>> groupingBy = Collectors.groupingBy(classifier1, groupingBy2);
    return modelList.stream().collect(groupingBy);
  }

  /**
   * Converti un arbre de map en un arbre treeitem javaFX. 
   *
   * @param groupByFields Map from a groupBy
   * @return Treeitem tree
   */
  @SuppressWarnings ("unchecked")
  public static <K extends V, V extends Comparable<? super V>> List<TreeItem<V>> mapToTreeItems (Map<K, ?> groupByFields) {
    List<TreeItem<V>> rslt = new ArrayList<>();
    List<K> keys = new ArrayList<>(groupByFields.keySet());
    Collections.sort(keys);
    for (K field : keys) {
      TreeItem<V> node = new TreeItem<V>((V) field);
      node.setExpanded(true);

      rslt.add(node);

      Object value = groupByFields.get(field);
      if (value instanceof Map) {
        node.getChildren().addAll(mapToTreeItems((Map<K, ?>) value));
      }
      else {
        List<V> lvalue = (List<V>) value;
        for (V modelId : lvalue) {
          node.getChildren().add(new TreeItem<V>(modelId));
        }
      }
    }

    return rslt;
  }

  /**
   * Fonction identique x->x avec casting. 
   *
   * @return
   */
  @SuppressWarnings ("unchecked")
  public static <I, R> Function<I, R> castingIdentity () {
    return i -> (R) i;
  }

  public static <T> void copyList (ObservableList<T> dest, ObservableList<T> src) {
    dest.clear();
    dest.addAll(src);
  }

  @SuppressWarnings ("unchecked")
  private static <S, D> void copyProperty (Property<S> src, Property<D> dest) {
    dest.setValue((D) src.getValue());
  }

  /**
   * Loop on a property list and copy values.
   *
   * @param src property list
   * @param dest property list
   */
  public static void copyProperties (List<Property<?>> src, List<Property<?>> dest) {
    for (int i = 0; i < src.size(); i++) {
      copyProperty(src.get(i), dest.get(i));
      // dest.get (i).setValue (src.get (i).getValue ());
    }
  }

  /**
   * permet d'exprimer la lambda à partir de la valeur du bean et de passer
   * directement la Method Reference du bean.
   *
   * @param templateLabel2
   * @param getter
   */
  public static <BEAN, T> void setCellDataValueFactory (TableColumn<BEAN, T> templateLabel2, final Function<BEAN, ObservableValue<T>> getter) {
    templateLabel2.setCellValueFactory(cellData -> getter.apply(cellData.getValue()));
  }

  
  /**
   * Bind les getter setter d'un bean classique wrappé dans une property à une property.
   *
   * @param getter
   * @param setter
   * @param objProp
   * @param property
   */
  public static <B, T> void bindBeanBidirectional (ObjectProperty<B> objProp, Function<B, T> getter, BiConsumer<B, T> setter, Property<T> property) {
    objProp.addListener( (obs, old, value) -> {
      if (value == null) {
        property.setValue(null);
      }
      else {
        property.setValue(getter.apply(objProp.get()));
      }
    });

    property.addListener( (obs, old, value) -> {
      if (objProp.get() == null) {
        return;
      }
      setter.accept(objProp.get(), value);
    });
  }
  
  /**
   * rebind la property du bean à chaque changement de bean 
   *
   * @param objProp
   * @param getter
   * @param property
   */
  public static <B, T> void bindBeanBidirectional (ObjectProperty<B> objProp, Function<B, Property<T>> getter, Property<T> property) {
        objProp.addListener( (obs, old, newValue) -> {
      if (old != null ) {
        property.unbindBidirectional(getter.apply(old));
      }
      if (newValue != null) {
        property.bindBidirectional(getter.apply(newValue));
      }
    });
  }
  
  /**
   * Bind les getter setter d'un bean classique wrappé dans une property contenant des TreeItem.
   * permet de binder des type hétérogènes.
   * @param getter
   * @param setter
   * @param objProp
   * @param textProperty
   */
  public static <B, T> void bindTreeBeanBidirectional (Class<B> clazz, ObjectProperty<? extends TreeItem<?>> objProp, Function<B, T> getter, BiConsumer<B, T> setter, Property<T> textProperty) {
    bindTreeBeanBidirectional(clazz,objProp,getter,setter,textProperty,null);
  }

  @SuppressWarnings ("unchecked")
  public static <B, T, U> void bindTreeBeanBidirectional (Class<B> clazz, ObjectProperty<? extends TreeItem<?>> objProp, Function<B, T> getter, BiConsumer<B, T> setter, Property<U> textProperty, StringConverter<T> converter) {
    // when bean has changed, reset all the form and use the getter to fill it again
    objProp.addListener( (obs, old, value) -> {
      if (value == null) {
        textProperty.setValue(null);
      }
      else {
        Object wrappedValue = objProp.get().getValue();
        if (clazz.isAssignableFrom(wrappedValue.getClass())){
          T getterValue = getter.apply((B)wrappedValue);
          U textValue =(U)( (converter==null)?getterValue:converter.toString(getterValue));
          textProperty.setValue(textValue);
        }
      }
    });
    //when user input has changed, call the bean's setter.
    textProperty.addListener( (obs, old, value) -> {
      if (objProp.get() == null) {
        return;
      }
      Object wrappedValue = objProp.get().getValue();
      if (clazz.isAssignableFrom(wrappedValue.getClass())){
        T setterValue = (converter==null)?(T)value:converter.fromString((String)value);
        setter.accept((B)wrappedValue, setterValue);
      }
    });
  }


  
  public static <B, T> void bindBeanGetter (ObjectProperty<B> objProp, Function<B, T> getter, Property<T> textProperty) {
    objProp.addListener( (obs, old, value) -> {
      if (value == null) {
        textProperty.setValue(null);
      }
      else {
        textProperty.setValue(getter.apply(objProp.get()));
      }
    });
  }
  
  public static <B, T> void bindBeanGetter (ObjectProperty<B> objProp, Function<B, T> getter, Property<String> textProperty, StringConverter<T> converter) {
    objProp.addListener( (obs, old, value) -> {
      if (value == null) {
        textProperty.setValue(null);
      }
      else {
        textProperty.setValue(converter.toString( getter.apply(objProp.get())));
      }
    });
  }
  
  public static <T,R> void bindProperties( ObjectProperty<T> src , ObjectProperty<R> dest, Function<T,R> converter){
    src.addListener((obs, old, value)->{
      dest.set(converter.apply(value));
    });
  }
  
  
  /**
   * Bind 2 properties ensemble avec un converter de type générique.
   *  
   * @param src
   * @param dest
   * @param converter
   */
  public static <T,R> void bindPropertiesBiDir( Property<T> src , Property<R> dest, Converter<T,R> converter){
    src.addListener((obs, old, value)->{
      dest.setValue(converter.to(value));
    });
    
    dest.addListener((obs, old, value)->{
      src.setValue(converter.from(value));
    });
  }
  
  /**
   * Wrappe un property dans un auter property bindée. 
   *
   */
  public static <T,R> Property<R> wrapProperty(Property<T> src , Converter<T,R> converter){
    Property<R> dest = new SimpleObjectProperty();
    bindPropertiesBiDir(src,dest,converter);
    return dest;
  }
  
  public static <T> Function<T,T> trapException(Function<T,T> fun){
    return (T t)->{
      try {
        return fun.apply(t);
      } catch (Exception e) {
        
        e.printStackTrace();
        return t;
      }
    };
  }

}