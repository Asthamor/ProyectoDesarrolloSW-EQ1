/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import interfaces.Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author mau
 */
public class PantallaFinanzas implements Initializable, Controlador {

  @FXML
  private JFXButton btnIngresos;
  @FXML
  private JFXButton btnEgresos;
  @FXML
  private JFXButton btnEstadisticas;
  @FXML
  private StackPane pnlFinanzas;
  private HBox pantallaDividida;
  private StackPane pnlPrincipal;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    PantallaPrincipalDirectorController.animacionCargarPantalla(pnlFinanzas);
    pnlFinanzas.getChildren().add(crearPantalla("/fxml/PantallaConsultarIngresos.fxml"));
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
  private void abrirIngresos(ActionEvent event) {
    PantallaPrincipalDirectorController.animacionCargarPantalla(pnlFinanzas);
    pnlFinanzas.getChildren().add(crearPantalla("/fxml/PantallaConsultarIngresos.fxml"));
  }

  @FXML
  private void abrirEgresos(ActionEvent event) {
    PantallaPrincipalDirectorController.animacionCargarPantalla(pnlFinanzas);
    pnlFinanzas.getChildren().add(crearPantalla("/fxml/PantallaEgresos.fxml"));

  }

  @FXML
  private void abrirEstadisticas(ActionEvent event) {
    PantallaPrincipalDirectorController.animacionCargarPantalla(pnlFinanzas);
        pnlFinanzas.getChildren().add(crearPantalla("/fxml/PantallaEstadisticas.fxml"));
  }

  public Parent crearPantalla(String ruta) {
    pnlFinanzas.getChildren().clear();
    Parent root = null;
    FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource(ruta));
    try {
      root = (Parent) loader.load();
    } catch (IOException ex) {
      Logger.getLogger(PantallaPrincipalDirectorController.class.getName())
              .log(Level.SEVERE, null, ex);
    }
    Controlador controlador = loader.getController();
    controlador.setPantallaDividida(pantallaDividida);
    controlador.setPnlPrincipal(pnlPrincipal);
    return root;
  }
}
