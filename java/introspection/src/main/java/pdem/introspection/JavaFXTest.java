package pdem.introspection;

import static pdem.introspection.IntrospectorGadget.getField;

import java.lang.reflect.Method;
import java.util.Set;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;

/**
 * 
 */
public class JavaFXTest {
  
  public static void clickButton (Parent root, String action) throws InterruptedException {
    Node button = lookupOnAction(root, "Button", action);
    Platform.runLater(((Button) button)::fire);
  }


  public static Node lookupOnAction (Parent root, String selector, String action) {
    Set<Node> nodes = root.lookupAll(selector);
    for (Node node : nodes) {
      if (node instanceof ButtonBase) {
        @SuppressWarnings ("unused")
        EventHandler<ActionEvent> onAction = ((ButtonBase) node).getOnAction();
        Object method = getField(getField(onAction, "handler"), "method");
        if (method != null && (method instanceof Method) && ((Method) method).getName().equals(action)) {
          return node;
        }
      }
    }
    return null;
  }


}
