package pdem.stackoverflow;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

    /**
     * Child controller
     */
    public class View1Controller implements ICenterController {

      private static final ObjectProperty<Object> selectedProperty = new SimpleObjectProperty();

      @Override
      public ObjectProperty<Object> selectedProperty() {
        return selectedProperty;
      }

      /**
       * simulate TableView selection/deselection
       */
      public void select(){
        selectedProperty.set(new Object());
      }

      public void deselect(){
        selectedProperty.set(null);
      }

    }
