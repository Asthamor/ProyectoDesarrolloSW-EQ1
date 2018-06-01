package clasesApoyo;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class Mensajes {


  public static void mensajeAlert(String mensaje) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.show();
  }

  public static void mensajeExitoso(String mensaje) {
    Alert alert = new Alert(Alert.AlertType.NONE);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    ButtonType botonCancelar = new ButtonType("Aceptar", ButtonBar.ButtonData.CANCEL_CLOSE);
    alert.getButtonTypes().setAll(botonCancelar);
    alert.show();
  }
  
  public static ButtonType dialogoConfirmacion(String encabezado, String mensaje){
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setHeaderText(encabezado);
    alert.setContentText(mensaje);
    ButtonType btnOK = new ButtonType("Aceptar", ButtonBar.ButtonData.APPLY);
    ButtonType btnCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
    alert.getButtonTypes().setAll(btnCancel, btnOK);
    alert.showAndWait();
    return alert.getResult();
  }

}
