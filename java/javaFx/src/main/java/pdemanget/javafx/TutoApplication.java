package pdemanget.javafx;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
    
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setResources(bundle);
    Parent root = fxmlLoader.load(getClass().getResource("tuto.fxml").openStream());
    
    Scene scene = new Scene(root, 300, 275);
    primaryStage.setMaximized(true);
    //scene.getStylesheets().add(MonitorApplication.class.getResource("/monitor.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show ();
//    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//      public void handle(WindowEvent we) {
//          System.out.println("Stage is closing");
//
//      }
//    });
        Thread.setDefaultUncaughtExceptionHandler((t,e)->{
          Alert alert = new Alert(AlertType.ERROR);
          alert.setContentText(e.getMessage());
          alert.show();
        });
  }
  
  @Override
  public void stop () {
    // override to close properly all threads to end application (bus, timers etc.)
    System.out.println("Stage is closing");
  }

}
