/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import interfaces.Controlador;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Publicidad;

/**
 * FXML Controller class
 *
 * @author mau
 */
public class PantallaEditarAnuncioController implements Initializable, Controlador {

  @FXML
  private Label lblMain;
  @FXML
  private JFXTextField txtMonto;
  @FXML
  private JFXDatePicker startPicker;
  @FXML
  private JFXDatePicker endPicker;
  @FXML
  private JFXListView<?> maestroList;
  @FXML
  private JFXTextArea txtDescripcion;
  @FXML
  private JFXTextField txtURL;
  @FXML
  private JFXTextField txtBuscar;
  @FXML
  private JFXButton btnGuardar;
  
  private Publicidad publicidad;
  private PantallaAnunciosController controlador;
  
   private HBox pantallaDividida;
  private StackPane pnlPrincipal;
  
   @Override
  public void setPantallaDividida(HBox pantallaDividida) {
    this.pantallaDividida = pantallaDividida;
  }

  @Override
  public void setPnlPrincipal(StackPane pnlPrincipal) {
    this.pnlPrincipal = pnlPrincipal;
  }

  public PantallaAnunciosController getControlador() {
    return controlador;
  }

  public void setControlador(PantallaAnunciosController controlador) {
    this.controlador = controlador;
  }
  
  public Publicidad getPublicidad() {
    return publicidad;
  }

  public void setPublicidad(Publicidad publicidad) {
    this.publicidad = publicidad;
  }

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  

  @FXML
  private void actualizarPublicidad(ActionEvent event) {
  }
  
}
