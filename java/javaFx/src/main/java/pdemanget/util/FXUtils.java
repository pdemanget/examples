package pdemanget.util;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.Validator;

import com.airhacks.afterburner.injection.Injector;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 *
 * <br>
 * <b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 *
 * @author pdemanget
 * @version 18 mars 2016
 */
public class FXUtils {
  /**
   * create FXML View for custom components.
   *
   * @param root:
   *          the root node to inherit
   * @param view:
   *          full fmlx view name
   * @param bundle:
   *          translation bundle
   * @return the Controller
   */
  @SuppressWarnings ("unchecked")
  public static <T> T openFXML (Node root, ResourceBundle bundle) {
    FXMLLoader fxmlLoader = new FXMLLoader ();
    fxmlLoader.setResources (bundle);
    fxmlLoader.setRoot (root);
    try {
      fxmlLoader.load (root.getClass ().getResourceAsStream (root.getClass ().getSimpleName () + ".fxml"));
    } catch (IOException e) {
      throw new IllegalArgumentException("Can't open FXML view " + root.getClass ().getSimpleName () + ".fxml", e);
    }
    return (T) fxmlLoader.getController ();
  }

  public static void registerInjection (Object o) {
    Injector.registerExistingAndInject (o);
    Injector.setModelOrService (o.getClass (), o);
  }

  /**
   * Définit les option de chargement de notre FXMLLoader. l'option ControllerFactory pourrait utiliser l'injection.
   *
   * @param url
   * @param bundle
   * @return le Node instancié en FXML
   */
  public static FXMLLoader load (URL url, ResourceBundle bundle, Object controller) {
    FXMLLoader fxmlLoader = new FXMLLoader ();
    if (bundle != null) {
      fxmlLoader.setResources (bundle);
    }
    if (controller != null) {
      fxmlLoader.setController (controller);
    }
    // fxmlLoader.setControllerFactory(clazz->controller);
    // Thread.currentThread().getContextClassLoader().getResource(url)
    fxmlLoader.setLocation (url);
    try {
      fxmlLoader.load ();
      return fxmlLoader;
    } catch (IOException e) {
      throw new IllegalArgumentException ("Can't open FXML view", e);
    }
    // return (T)fxmlLoader.getController();
  }

  public static FXMLLoader load (URL url, ResourceBundle bundle) {
    return load (url, bundle, null);
  }

  public static <ROW, T extends Temporal> Callback<TableColumn<ROW, T>, TableCell<ROW, T>> getDateCell (DateTimeFormatter format) {
    return column -> {
      return new TableCell<ROW, T> () {
        @Override
        protected void updateItem (T item, boolean empty) {
          super.updateItem (item, empty);
          if (item == null || empty) {
            setText (null);
          }
          else {
            setText (format.format (item));
          }
        }
      };
    };
  }

  public static <ROW, T extends Temporal> Callback<TreeTableColumn<ROW, T>, TreeTableCell<ROW, T>> getDateTreeCell (DateTimeFormatter format) {
    return column -> {
      return new TreeTableCell<ROW, T> () {
        @Override
        protected void updateItem (T item, boolean empty) {
          super.updateItem (item, empty);
          if (item == null || empty) {
            setText (null);
          }
          else {
            setText (format.format (item));
          }
        }
      };
    };
  }

  /**
   * ouvre une image
   */
  public static Image openImage (String path) {
    Image image = new Image (path);
    return image;
  }

  /**
   * ouvre une image
   */
  public static Node openImageView (String path) {
    Image image = FXUtils.openImage (path);
    ImageView imageView = new ImageView (image);
    return imageView;
  }

  /**
   * ouvre une image
   */
  public static ImageView openImageView (Image image) {
    ImageView imageView = new ImageView (image);
    return imageView;
  }

  /**
   *
   * Permet de rendre visible/invisible une list de composants en prenan compte les déplacements des autres composants de l'IHM
   *
   * @param active
   *          {boolean}
   * @param tabNodes
   *          {Node[]...}
   */
  public static void setVisibleComponent (boolean active, Node[]... tabNodes) {
    if (tabNodes == null) {
      return;
    }
    for (Node[] nodes : tabNodes) {
      FXUtils.setVisibleComponent (active, nodes);
    }
  }

  /**
   *
   * Permet de rendre visible/invisible une list de composants en prenan compte les déplacements des autres composants de l'IHM
   *
   * @param active
   *          {boolean}
   * @param nodes
   *          {Node...}
   */
  public static void setVisibleComponent (boolean active, Node... nodes) {
    if (nodes == null) {
      return;
    }
    for (Node node : nodes) {
      if (node == null) {
        continue;
      }
      if (node.isVisible () != active) {
        node.setVisible (active);
      }
      if (node.isManaged () != active) {
        node.setManaged (active);
      }
    }
  }

  /**
   * Formatter pour les textfields de type double
   * restriction sur les caractères: [0-9.,]
   * @return TextFormatter<String>
   */
  public static TextFormatter<String> getDoubleFormatter () {
    return new TextFormatter<String> (getFilter ("[0-9.,]"));
  }

  /**
   * Formatter pour les textfields de type Integer
   * restriction sur les caractères: [0-9]
   *
   * @return TextFormatter<String>
   */
  public static TextFormatter<String> getIntegerFormatter () {
    return new TextFormatter<String> (getFilter ("[0-9]"));
  }

  private static UnaryOperator<Change> getFilter (String regex) {
    return change -> {
      String text = change.getText ();
      if (text.equals ("") || text.matches (regex)) {
        if (regex.contains (",")) {
          change.setText (text.replaceAll (",", "."));
        }
        return change;
      }
      return null;
    };
  }

  /**
   * Validation d'un entier non vide
   * @param msgErreurObligatoire
   * @param msgErreurInteger
   * @return
   */
  public static Validator<String> getValidatorIntegerRequired (String msgErreurObligatoire, String msgErreurInteger) {
    return Validator.combine (Validator.createEmptyValidator (msgErreurObligatoire),
        Validator.createRegexValidator (msgErreurInteger, "\\d*", Severity.ERROR));
  }

  /**
   * Validation d'un décimal non vide
   * @param msgErreurObligatoire
   * @param msgErreurDecimal
   * @return
   */
  public static Validator<String> getValidatorDecimalRequired (String msgErreurObligatoire, String msgErreurDecimal) {
    return Validator.combine (Validator.createEmptyValidator (msgErreurObligatoire),
        Validator.createRegexValidator (msgErreurDecimal, "^(\\d+(?:[\\.\\,]\\d{2})?)$", Severity.ERROR));
  }

  /**
   * Validation d'un texte (via regex) non vide
   * @param msgErreurObligatoire
   * @param msgErreurRegex
   * @return
   */
  public static Validator<String> getValidatorRegexRequired (String msgErreurObligatoire, String msgErreurRegex, String regex) {
    if(regex.trim ().length () > 0){
      return Validator.combine (Validator.createEmptyValidator (msgErreurObligatoire),
          Validator.createRegexValidator (msgErreurRegex, regex, Severity.ERROR));
    } else{
      return Validator.createEmptyValidator (msgErreurObligatoire);
    }
  }

  public static <T> String getFXML (Class<T> controller) {
    String name = controller.getName();
    
    return "/"+name.substring(0,name.length()-"Controller".length()).replace('.','/') +".fxml";
  }

}
