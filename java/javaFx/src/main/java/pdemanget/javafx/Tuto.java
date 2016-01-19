package pdemanget.javafx;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Tuto {
  @FXML
  private Text actiontarget;
  
  @FXML
  public void handleSubmitButtonAction(){
        actiontarget.setFill(Color.FIREBRICK);
        actiontarget.setText("Sign in button pressed");
  }

}
