/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import interfaces.Controlador;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import modelo.Maestro;
import modelo.Persona;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author mauricio
 */
public class PantallaEditarPerfilController implements Initializable, Controlador {

  @FXML
  private JFXTextField txtTelefono;
  @FXML
  private JFXTextField txtEmail;
  @FXML
  private JFXPasswordField txtContraseña;
  @FXML
  private JFXButton btnEditarImagen;
  @FXML
  private ImageView imgUsuario;
  @FXML
  private JFXButton btnGuardar;
  @FXML
  private Label lblNombreUsuario;
  @FXML
  private JFXPasswordField txtConfContraseña;

  private HBox pantallaDividida;
  private StackPane pnlPrincipal;

  private Persona persona;
  private Usuario usuario;
  private Image img;
  private String imgPath;
  private boolean imgFlag = false;
  
  @FXML
  private Label lblError;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    setUserData();
    lblNombreUsuario.setText(persona.getNombre() + " " + persona.getApellidos());
    txtTelefono.setText(persona.getTelefono());
    txtEmail.setText(persona.getEmail());
    imgPath = "file:" + persona.obtenerImagen();
    System.out.println(imgPath);
    img = new Image(imgPath, true);
    imgUsuario.setImage(img);
    btnEditarImagen.toFront();
  }

  public boolean setUserData() {
    String nombreUsuario = System.getProperty("nombreUsuario");
    usuario = new Usuario();
    usuario = usuario.buscar(nombreUsuario);
    if (usuario.getTipoUsuario().equals("maestro")) {
      this.persona = (Maestro) usuario.getMaestroCollection().toArray()[0];
    }
    if (usuario.getTipoUsuario().equals("director")) {
      this.persona = (Maestro) usuario.getMaestroCollection().toArray()[0];
    }
    return true;
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
  private void openImgExplorer(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Imagenes", "*.jpg", "*.png")
    );
    File file = fileChooser.showOpenDialog(null);
    if (file != null) {
      Image imagen = new Image("file:" + file.getAbsolutePath());
      imgPath = file.getAbsolutePath();
      imgUsuario.setImage(imagen);
      imgFlag = true;
    }
  }

  @FXML
  private void guardarCambios(ActionEvent event) {
    persona.setTelefono(txtTelefono.getText());
    persona.setEmail(txtEmail.getText());
    if (imgFlag){
      persona.setImgFoto(imgPath);
    }
    
    int passControl = checkPassword();
    if (passControl == 1){
      usuario.editarPassword(txtContraseña.getText());
      persona.actualizarDatos(imgFlag);
      Mensajes.mensajeExitoso("Los cambios se han guardado");
    } else if (passControl == -1){
      persona.actualizarDatos();
      Mensajes.mensajeExitoso("Los cambios se han guardado");
    }
    
  }
  
  private int checkPassword(){
    if (!txtContraseña.getText().trim().equals("") && !txtConfContraseña.getText().trim().equals("")){
      if(txtContraseña.getText().equals(txtConfContraseña.getText())){
        lblError.setVisible(false);
        return 1; 
      } else {
      lblError.setText("Las contraseñas deben coincidir");
      lblError.setVisible(true);
      return 0;
      }
    }
    lblError.setVisible(false);
    return -1;
  }

}
