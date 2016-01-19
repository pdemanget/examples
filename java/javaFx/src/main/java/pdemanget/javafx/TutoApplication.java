package pdemanget.javafx;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Manage application bootstrap with fxml css and i18n bundle.
 * @author pdemanget
 */
public class TutoApplication extends Application {
  public static void main (String[] args) {
    launch (args);
  }

  @Override
  public void start (Stage primaryStage) throws Exception {
    ResourceBundle bundle = ResourceBundle.getBundle("translation", Locale.getDefault ());
    
    primaryStage.setTitle (bundle.getString ("title"));
    
    primaryStage.getIcons().add(new Image("/logo.png"));
    
    Parent root = FXMLLoader.load(getClass().getResource("tuto.fxml"), bundle);
    Scene scene = new Scene(root, 300, 275);
    primaryStage.setMaximized(true);
    //scene.getStylesheets().add(MonitorApplication.class.getResource("/monitor.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show ();
    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      public void handle(WindowEvent we) {
          System.out.println("Stage is closing");

      }
    });
  }

}
