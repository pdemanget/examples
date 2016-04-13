package pdemanget.javafx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * 
 * <br><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 * 
 * @author pdemanget
 * @version 6 avr. 2016
 */
public class Main2 extends Application {
  private static void testPopup () {
    Stage stage = new Stage();
    StackPane p = new StackPane();
    p.setPrefSize(700, 700);// set a default size for your stackpane
    Label label = new Label("Hello world");
    p.getChildren().add(label);
    StackPane.setAlignment(label, Pos.CENTER_LEFT);// set it to the Center Left(by default it's on the center)
    stage.setScene(new Scene(p));
    stage.show();

  }

  public static void main (String[] args) {
    launch(args);
  }

  @Override
  public void start (Stage primaryStage) throws Exception {
    testPopup();
    
  }

}
