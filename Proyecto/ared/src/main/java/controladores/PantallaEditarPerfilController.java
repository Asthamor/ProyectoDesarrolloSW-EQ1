/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author mauricio
 */
public class PantallaEditarPerfilController implements Initializable {

  @FXML
  private JFXTextField txtTelefono;
  @FXML
  private JFXTextField txtEmail;
  @FXML
  private JFXTextField txtContraseña;
  @FXML
  private JFXTextField txtConfirmarContraseña;
  @FXML
  private JFXButton btnEditarImagen;
  @FXML
  private ImageView imgUsuario;
  @FXML
  private JFXButton btnGuardar;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  
  
}
