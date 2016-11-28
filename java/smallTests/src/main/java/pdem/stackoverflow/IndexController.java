package pdem.stackoverflow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

    /**
     * Main Controller
     */
    public class IndexController {


      @FXML
      private BorderPane root;

      @FXML
      private Button buttonOpen;

      private ObjectProperty<Object> selectedProperty;

      public FXMLLoader loadFXML(URL url, ResourceBundle resources){
        FXMLLoader fxmlLoader = new FXMLLoader ();
        fxmlLoader.setLocation (url);
        fxmlLoader.setResources(resources);
        try {
          fxmlLoader.load ();
          return fxmlLoader;
        } catch (IOException e) {
          e.printStackTrace(System.err);
          throw new IllegalStateException(e);
        }
      }

      public void button1(){
        FXMLLoader loadFXML = loadFXML(getClass().getResource("View1.fxml"),null);
        root.setCenter(loadFXML.getRoot());
        ICenterController controller = (ICenterController) loadFXML.getController();
        selectedProperty = controller.selectedProperty();
        buttonOpen.disableProperty().bind(selectedProperty.isNull());
      }

      public void open(){
        //TODO open in a view
        System.out.println("open the object"+selectedProperty.get());
       }

    }
