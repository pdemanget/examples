package pdem.snippets;
//package fr.incore_systemes.si.mts.monitor.view.component;
//
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.scene.layout.RegionBuilder;
//
///**
// * RackViewBuilder
// * B extends javafx.scene.layout.RegionBuilder<B>
// * B extends javafx.scene.layout.RackViewBuilder<B>
// * implements javafx.util.Builder<javafx.scene.layout.RackView>
// *
// * <p><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
// *
// * @author pdemanget
// * @version 1.0.0, 15 nov. 2016
// */
//@SuppressWarnings ("deprecation")
//public class RackViewBuilder<B extends RackViewBuilder<B>> extends RegionBuilder<B> {
//
//  private final SimpleIntegerProperty countValue = new SimpleIntegerProperty();
//  private int innerWidth;
//  private int innerHeight;
//  private int rectangleHeight;
//
//  public RackViewBuilder(){
//    System.out.println("creation builder");
//  }
//
//
//  @SuppressWarnings ("rawtypes")
//  public static RackViewBuilder<?> create() {
//    return new RackViewBuilder();
//  }
//
//  public RackView build() {
//
//    RackView x = new RackView();
//    if(countValue != null){
//      x.countValueProperty().bind(countValue);
//    }
//
//    applyTo(x);
//    return x;
//}
//
//  public SimpleIntegerProperty getCountValue() {
//    return countValue;
//  }
//
//  public int getInnerWidth() {
//    return innerWidth;
//  }
//
//  public int getInnerHeight() {
//    return innerHeight;
//  }
//
//  public int getRectangleHeight() {
//    return rectangleHeight;
//  }
//
//  public void setInnerWidth(int innerWidth) {
//    this.innerWidth = innerWidth;
//  }
//
//  public void setInnerHeight(int innerHeight) {
//    this.innerHeight = innerHeight;
//  }
//
//  public void setRectangleHeight(int rectangleHeight) {
//    this.rectangleHeight = rectangleHeight;
//  }
//}
