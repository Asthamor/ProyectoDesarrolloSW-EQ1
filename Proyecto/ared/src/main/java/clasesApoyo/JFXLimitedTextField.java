package clasesApoyo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import java.util.function.UnaryOperator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextFormatter;

/**
 *
 * @author mauricio
 */
public class JFXLimitedTextField extends JFXTextField {

  public enum InputType {
    NUMBER, ALPHANUMERIC, EMAIL, ANY
  }

  public JFXLimitedTextField() {

  }

  public JFXLimitedTextField(InputType inputType, int size) {
    switch (inputType){
      case NUMBER:
        setNumLimiter(size);
        break;
      case ALPHANUMERIC:
        setAlphanumericLimiter(size);
        break;
      case EMAIL:
        break;
      case ANY:
        setSizeLimiter(size);
        break;
    }
    
  }

  public final void setNumLimiter(int size) {
    textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (newValue.matches("\\d{0," + Integer.toString(size) + "}")) {
          setText(newValue);
        } else {
          setText(oldValue);
        }
      }
    });
    
  }

  public final void setSizeLimiter(int size) {
    textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (newValue.matches(".{0," + Integer.toString(size) + "}")) {
          setText(newValue);
        } else {
          setText(oldValue);
        }
      }
    });

  }
    public final void setAlphanumericLimiter(int size) {
    textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (newValue.matches("[a-zA-Z0-9 ñáéíóú]{0," + Integer.toString(size) + "}")) {
          setText(newValue);
        } else {
          setText(oldValue);
        }
      }
    });

  }

  public void setRequired(boolean isRequired) {
    if (isRequired) {
      ValidatorBase req = new RequiredFieldValidator();
      req.setMessage("Campo requerido");
      setValidators(req);
    }
  }
  public final void setCurrencyFilter(){
    UnaryOperator<TextFormatter.Change> dineroFilter = (change -> {
      String newText = change.getControlNewText();
      if (newText.matches("(\\$[^0\\D][0-9]*\\.?[0-9]{0,2})")) {
        return change;
      } else if (newText.matches("([1-9]+?\\d*\\.?[0-9]{0,2})")){
        change.setText("$" + newText);
        return change;
      } else if (newText.matches("(\\$)")){
        return change;
      }
      return null;
    });
    this.setTextFormatter(new TextFormatter(dineroFilter));
  }
   public final void setIntCurrencyFilter(){
    UnaryOperator<TextFormatter.Change> dineroFilter = (change -> {
      String newText = change.getControlNewText();
      if (newText.matches("(\\$[^0\\D][0-9]{0,10})")) {
        return change;
      } else if (newText.matches("([1-9]+?\\d{0,10}})")){
        change.setText("$" + newText);
        return change;
      } else if (newText.matches("(\\$)")){
        return change;
      }
      return null;
    });
    this.setTextFormatter(new TextFormatter(dineroFilter));
  }
  
}
