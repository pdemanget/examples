package pdem.stackoverflow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

    /**
     * App launch
     */
    public class SOFx extends Application{

      public static void main(String[] args) {
        launch(args);
      }

      @Override
      public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle ("SO test");
        //static loading for index, don't need the controller.
        Parent root = FXMLLoader.load(getClass().getResource("Index.fxml"),null);
        Scene scene = new Scene(root);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show ();
      }
    }
