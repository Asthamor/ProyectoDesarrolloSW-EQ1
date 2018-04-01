package clasesApoyo;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alonso
 */
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

}
