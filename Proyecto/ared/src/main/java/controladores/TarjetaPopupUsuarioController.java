/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import interfaces.Controlador;
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
  private PantallaPrincipalDirectorController controladorDirector;
  private PantallaPrincipalMaestroController controladorMaestro;

  private boolean esDirector = false;
  
  
  public PantallaPrincipalMaestroController getControladorMaestro() {
    return controladorMaestro;
  }

  public void setControladorMaestro(PantallaPrincipalMaestroController controladorMaestro) {
    this.controladorMaestro = controladorMaestro;
    esDirector = false;
  }


  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  public PantallaPrincipalDirectorController getControlador() {
    return controladorDirector;
  }

  public void setControlador(PantallaPrincipalDirectorController controlador) {
    this.controladorDirector = controlador;
    esDirector = true;
  }

  @FXML
  private void abrirEditarPerfil(ActionEvent event) {
    if (esDirector) {
      controladorDirector.abrirEditarPerfil();
    } else {
      controladorMaestro.abrirEditarPerfil();
    }
  }

  @FXML
  private void logout(ActionEvent event) {
    if (esDirector) {
      controladorDirector.cerrarSesion();
    } else {
      controladorMaestro.cerrarSesion();
    }

  }

}
