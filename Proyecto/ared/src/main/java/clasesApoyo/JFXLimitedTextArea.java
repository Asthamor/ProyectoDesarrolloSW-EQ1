/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesApoyo;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
/**
 *
 * @author alonso
 */
public class JFXLimitedTextArea extends JFXTextArea{
    public enum InputType {
    NUMBER, ALPHANUMERIC, EMAIL, ANY
  }

  public JFXLimitedTextArea() {

  }

  public JFXLimitedTextArea(InputType inputType, int size) {
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
}
