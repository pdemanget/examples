package pdemanget.util;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Permet l'affichage d'une Popin avec un fxml passé en paramètre. <br>
 * <b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 * 
 * cf. http://fxexperience.com/controlsfx/
 * http://controlsfx.bitbucket.org/
 * 
 * @author pdemanget
 * @version 24 févr. 2016
 */
public class PopinService {
  private Parent parent;
  private List<Window> nodeStack = new ArrayList<>();
  private ResourceBundle bundle;
  private String baseViewPath;
  
  protected class Window{
    private final Node parent;
    //private final Node background;
    private final Node node;
    
    public Window (Node parent, Node node) {
      super();
      this.parent = parent;
      //this.background = background;
      this.node = node;
    }
    public Node getParent () {
      return parent;
    }
    
    public Node getNode () {
      return node;
    }
    
    
  }

  /**
   * 
   *
   * @param baseViewPath path in classpath example: 
   * @param bundle
   */
  public void init ( Parent parent, String baseViewPath, ResourceBundle bundle) {
    this.parent=parent;
    this.baseViewPath = baseViewPath;
    this.bundle = bundle;
  }

  /**
   * décalage des fenêtres empilées.
   */
  double DELTA = 100;
  double currentDelta = -2*DELTA;
  /**
   * réglages tailles
   */
  double animation = 200;
  double margin = 10;
  double width = 400;
  double height = 200;

//  public <T> T popinAndWait (String page, Scene scene) {
//    Platform.runLater( () -> {
//
//    });
//    AbstractPopinController<T> controller = popin(page, scene);
//    return controller.getValue();
//  }
  public Object popin (String fxmlPage) {
    return popin(fxmlPage, parent, false);
  }
  
  public Object popin (String page, Node caller) {
    if(caller == null){
      return popin(page, parent, false);  
    }
    return popin(page, caller, true);
  }
  
  public <T> T popin (Class<T> controller, Node caller, boolean modal) {
    return (T) popin(FXUtils.getFXML(controller),caller,modal);
  }

  public Object popin (String page, Node caller, boolean modal) {
    if(caller == null){
      caller = parent;
    }
    Scene scene=caller.getScene();

    try {
      // Node load = fxmlLoader.load ();
      // 1. get component

      // Node message = FXMLLoader.load(getClass().getResource(baseViewPath+page), bundle);

      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setResources(bundle);
      if(!page.startsWith("/")){
        page=baseViewPath +page;
      }
      Node popin = fxmlLoader.load(getClass().getResource( page).openStream());

      Object controller = fxmlLoader.getController();

      // 2. compute position

      // Scene scene = this.getScene ();

      double sWidth = scene.getWidth();
      double sHeight = scene.getHeight();

      double x = (sWidth - width - margin) / 2 + currentDelta;
      double y = (sHeight - height - margin) / 2 + currentDelta;

      currentDelta += DELTA;
      if(currentDelta > sHeight/2){
        currentDelta = margin - (sHeight - height - margin) / 2;
      }

      popin.setLayoutX(x);
      popin.setLayoutY(y);

      // 3. display
      
      addPopin(caller, popin, modal);

      popin.setOnKeyPressed(event -> {
        if (event.getCode().equals(KeyCode.ESCAPE)) {
          removeLastPopin();
        }
      });
      makeMoveable(popin);

      // 4. manage animations
      FadeTransition ft = new FadeTransition(Duration.millis(animation), popin);
      ft.setFromValue(0.2);
      ft.setToValue(1);

      ft.setOnFinished(new EventHandler<ActionEvent>() {

        public void handle (ActionEvent event) {
          // removeFromScene (parent, message);
        }

      });

      SequentialTransition seq = new SequentialTransition(ft);
      seq.play();
      return controller;
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }

  }

  /**
   * Make the node moveable with mouse drag.
   *
   * @param message
   */
  private void makeMoveable (Node message) {
    final double[] position = { 0, 0 };

    double xMax = message.getScene().getWidth() - width;
    double yMax = message.getScene().getHeight() - height;

    message.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle (MouseEvent event) {
        // System.out.println("pressed " + event.getSceneX() + " " + event.getSceneY());
        position[0] = event.getX();
        position[1] = event.getY();
      }
    });

    message.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle (MouseEvent event) {
        // System.out.println("dragged " + event.getSceneX() + " " + event.getSceneY());
        message.setLayoutX(max(0, min(event.getSceneX() - position[0], xMax)));
        message.setLayoutY(max(0, min(event.getSceneY() - position[1], yMax)));
        message.setCursor(Cursor.MOVE);
      }
    });

    message.setOnMouseReleased(event -> {
      message.setCursor(Cursor.DEFAULT);
    });

  }

  private void  addPopin (Node caller, Node popin, boolean modal) {
    Scene scene = caller.getScene();
    double sWidth = scene.getWidth();
    double sHeight = scene.getHeight();
    
    Rectangle rectangle = new Rectangle(sWidth, sHeight, Color.rgb(128, 128, 128, 0.5));
    Group group = new Group(rectangle,popin);
//    group.minWidth(sWidth);
//    group.maxWidth(sWidth);
    addToScene(caller.getScene(), group);
    //addToScene(caller.getScene(), rectangle);
    //addToScene(caller.getScene(),popin);
   if (modal){
     caller.disableProperty().set(true);
   }
    nodeStack.add(new Window(caller,group));
  }
  
  private Parent addToScene (Scene scene, Node... nodes) {
    
    parent = scene.getRoot();

    if (parent instanceof Group) {
      Group group = (Group) parent;
      for (Node node : nodes) {
        group.getChildren().add(node);
      }
    }
    if (parent instanceof Pane) {
      Pane group = (Pane) parent;
      for (Node node : nodes) {
        group.getChildren().add(node);
      }
    }
    return parent;
  }

  private void removeFromScene (Node... nodes) {
    Parent p = this.parent;
    if (p instanceof Group) {
      Group group = (Group) p;
      for (Node node : nodes) {
        group.getChildren().remove(node);
      }
    }
    if (p instanceof Pane) {
      Pane group = (Pane) p;
      for (Node node : nodes) {
        group.getChildren().remove(node);
      }
    }
    
  }

  public void removeLastPopin () {
    Window window = nodeStack.get(nodeStack.size() - 1);
    removeWindow(window);
  }
  
  public void removePopin (Node node) {
    Window window = nodeStack.stream().filter(w->w.getNode()== node).findFirst().orElse(null);
    if (window != null){
      removeWindow(window);
    }
  }

  private void removeWindow (Window window) {
    currentDelta -= DELTA;
    removeFromScene(window.getNode());
    //removeFromScene(window.getBackground());
    window.getParent().disableProperty().set(false);
    nodeStack.remove(window);
  }

  public Parent getParent () {
    return parent;
  }

  public void setParent (Parent parent) {
    this.parent = parent;
  }
  
}
