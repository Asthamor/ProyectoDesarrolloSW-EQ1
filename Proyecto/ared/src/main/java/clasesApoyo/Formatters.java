/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesApoyo;

import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;

/**
 *
 * @author mauricio
 */
public class Formatters {
  
  public static TextFormatter getNumFormatter(int size){
    UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (!change.isAdded()) {
                return change;
            } else {
                if (text.matches("[0-9]")) {
                    return change;
                } 
            }
            return null;
        };

        TextFormatter<String> textLimit = new TextFormatter<>(filter);
        return textLimit;
  }
}
