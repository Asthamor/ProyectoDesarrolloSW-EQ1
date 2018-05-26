/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author mau
 */
public class PantallaRegistrarEgresoController implements Initializable {

  @FXML
  private JFXDatePicker txtDia;
  @FXML
  private ScrollPane scrollHorario;
  @FXML
  private GridPane gridHorario;
  @FXML
  private JFXButton btnGuardar;
  @FXML
  private Label lblFecha;
  @FXML
  private JFXComboBox<?> cmbCliente;
  @FXML
  private JFXTextField txtMonto;
  @FXML
  private Label lblHorarioRenta;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  

  @FXML
  private void mostrarFecha(ActionEvent event) {
  }

  @FXML
  private void guardarRenta(ActionEvent event) {
  }
  
}
