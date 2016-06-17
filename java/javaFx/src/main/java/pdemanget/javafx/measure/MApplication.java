package pdemanget.javafx.measure;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manage application bootstrap with fxml css and i18n bundle.
 * @author pdemanget
 */
public class MApplication extends Application {
  public static void main (String[] args) {
    launch (args);
  }

  @Override
  public void start (Stage primaryStage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader();
    Parent root = fxmlLoader.load(getClass().getResource("index.fxml").openStream());
    Scene scene = new Scene(root, 300, 275);
    primaryStage.setMaximized(true);
    primaryStage.setScene(scene);
    primaryStage.show ();
  }
  

}
