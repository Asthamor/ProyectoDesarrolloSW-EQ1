/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

/**
 * FXML Controller class
 *
 * @author mau
 */
public class TarjetaPopupUsuarioController implements Initializable {

  @FXML
  private JFXButton btnEditarPerfil;
  @FXML
  private JFXButton btnCerrarSesion;
  private PantallaPrincipalDirectorController controlador;

  
    /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  

  public PantallaPrincipalDirectorController getControlador() {
    return controlador;
  }

  public void setControlador(PantallaPrincipalDirectorController controlador) {
    this.controlador = controlador;
  }

  @FXML
  private void abrirEditarPerfil(ActionEvent event) {
    controlador.abrirEditarPerfil();
  }

  @FXML
  private void logout(ActionEvent event) {
    controlador.cerrarSesion();
    
  }
  
}
