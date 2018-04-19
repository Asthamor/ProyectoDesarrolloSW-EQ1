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
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author mauricio
 */
public class PantallaInscripcion implements Initializable, Controlador {

  @FXML
  private JFXButton btnInscripcion;
  @FXML
  private JFXButton btnBaja;
  @FXML
  private Label lbRegistrarPago;
  private HBox pantallaDividida;
  private StackPane pnlPrincipal;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  
  @Override
  public void setPantallaDividida(HBox pantallaDividida) {
    this.pantallaDividida = pantallaDividida;
  }

  @Override
  public void setPnlPrincipal(StackPane pnlPrincipal) {
    this.pnlPrincipal = pnlPrincipal;
  }

  @FXML
  private void abrirPantallaInscripcion(ActionEvent event) {
      pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaInscribirAlumno.fxml", this.pnlPrincipal, this.pantallaDividida));
      pantallaDividida.getChildren().add(pnlPrincipal);
  }

  @FXML
  private void abrirPantallaBajas(ActionEvent event) {
    pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaBajaInscripcion.fxml", this.pnlPrincipal, this.pantallaDividida));
      pantallaDividida.getChildren().add(pnlPrincipal);
  }
  
}
