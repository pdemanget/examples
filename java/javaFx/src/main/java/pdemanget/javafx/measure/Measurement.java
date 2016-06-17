package pdemanget.javafx.measure;

import javafx.stage.Screen;

/**
 * Trick top insert value in javaFX
 * http://stackoverflow.com/questions/23705654/bind-font-size-in-javafx
 */
public class Measurement {
  private double dp;
  
  public Measurement(){
    Screen primary = Screen.getPrimary();
    dp=primary.getBounds().getWidth()/1920;
  }
  
  /** 
   * Equivalent of 1 px in 1920. 
   */
  public double getDp(){
    return dp;
  }
  
  public void setDp (double dp) {
    this.dp = dp;
  }

}