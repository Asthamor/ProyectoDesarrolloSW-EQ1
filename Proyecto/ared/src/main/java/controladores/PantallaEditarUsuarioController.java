/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import static controladores.PantallaPrincipalDirectorController.crearPantallaUsuarios;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaEditarUsuarioController implements Initializable {

  @FXML
  private Label lblInfoNecesaria;
  @FXML
  private Label lblNombresUsuario;
  @FXML
  private Label lblApellidosUsuario;
  @FXML
  private Label lblTelefonoUsuario;
  @FXML
  private JFXTextField txtNombresUsuario;
  @FXML
  private JFXTextField txtApellidosUsuario;
  @FXML
  private JFXTextField txtTelefonoUsuario;
  @FXML
  private Label lblInfoOpcional;
  @FXML
  private Label lblFotoUsuario;
  @FXML
  private ImageView imgFotoUsuario;
  @FXML
  private Label lblCorreoElectronico;
  @FXML
  private JFXTextField txtCorreoElectronico;
  @FXML
  private JFXButton btnSeleccionarFoto;
  @FXML
  private JFXButton btnGuardar;

  private String rutaFoto = null;
  private Persona persona;
  private HBox pantallaDividida;
  private TarjetaInformacionUsuarioController controlador;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {

  }

  public void setPersona(Persona persona) {
    this.persona = persona;
    mostrarInformacion();
  }

  public void setPantallaDividida(HBox pantallaDividida) {
    this.pantallaDividida = pantallaDividida;
  }

  public void setControlador(TarjetaInformacionUsuarioController controlador) {
    this.controlador = controlador;
  }

  public void mostrarInformacion() {
    txtNombresUsuario.setText(persona.getNombre());
    txtApellidosUsuario.setText(persona.getApellidos());
    txtTelefonoUsuario.setText(persona.getTelefono());

    Image imagen = new Image("file:" + persona.obtenerImagen());
    
    imgFotoUsuario.setImage(imagen);
    if (persona.getEmail() == null) {
      txtCorreoElectronico.setText("");
    } else {
      txtCorreoElectronico.setText(persona.getEmail());
    }
  }

  @FXML
  private void seleccionarFoto(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Imagenes", "*.jpg", "*.png")
    );
    File file = fileChooser.showOpenDialog(null);
    if (file != null) {
      Image imagen = new Image("file:" + file.getAbsolutePath());

      rutaFoto = file.getAbsolutePath();
      imgFotoUsuario.setImage(imagen);
    }
  }

  @FXML
  private void actualizarInformacion(ActionEvent event) {
    colorEtiquetas();
    if (existenCamposVacios()) {
      Mensajes.mensajeAlert("Algunos campos estan vacíos");
    } else {
      if (!tamañoValidoCaracteres()) {
        Mensajes.mensajeAlert("Algunos campos sobre pasan el limite de caracteres");
      } else {
        persona.setNombre(txtNombresUsuario.getText());
        persona.setApellidos(txtApellidosUsuario.getText());
        persona.setTelefono(txtTelefonoUsuario.getText());
        persona.setImgFoto(rutaFoto);
        persona.setEmail(txtCorreoElectronico.getText());
        if (persona.actualizarDatos()) {
          controlador.mostrarInformacion(persona);
          pantallaDividida.getChildren().remove(1);
          Mensajes.mensajeExitoso("La información se actualizó correctamente");
        }
      }

    }
  }

  public boolean existenCamposVacios() {
    return txtNombresUsuario.getText().equals("") && txtApellidosUsuario.getText().equals("")
        && txtTelefonoUsuario.getText().equals("");
  }

  @FXML
  private void limitarCaracteresEmail(KeyEvent event) {
    int limiteCaracteres = 100;
    if (txtCorreoElectronico.getText().length() >= limiteCaracteres) {
      event.consume();
    }
  }

  @FXML
  private void limitarCaracteresNombre(KeyEvent event) {
    int limiteCaracteres = 80;
    if (txtNombresUsuario.getText().length() >= limiteCaracteres) {
      event.consume();
    }
  }

  @FXML
  private void limitarCaracteresApellido(KeyEvent event) {
    int limiteCaracteres = 45;
    if (txtApellidosUsuario.getText().length() >= limiteCaracteres) {
      event.consume();
    }
  }

  @FXML
  private void limitarCaracteresTelefono(KeyEvent event) {
    int limiteCaracteres = 10;
    if (txtTelefonoUsuario.getText().length() >= limiteCaracteres) {
      event.consume();
    }
  }

  public boolean tamañoValidoCaracteres() {
    boolean tamañoValido = true;
    if (txtCorreoElectronico.getText().length() >= 100) {
      tamañoValido = false;
      lblCorreoElectronico.setTextFill(Color.web("#EC7063"));
    }

    if (txtNombresUsuario.getText().length() >= 80) {
      tamañoValido = false;
      lblNombresUsuario.setTextFill(Color.web("#EC7063"));
    }

    if (txtApellidosUsuario.getText().length() >= 45) {
      tamañoValido = false;
      lblApellidosUsuario.setTextFill(Color.web("#EC7063"));
    }

    if (txtTelefonoUsuario.getText().length() >= 10) {
      tamañoValido = false;
      lblTelefonoUsuario.setTextFill(Color.web("#EC7063"));
    }
    return tamañoValido;
  }

  public void colorEtiquetas() {
    lblCorreoElectronico.setTextFill(Color.web("#000000"));
    lblNombresUsuario.setTextFill(Color.web("#000000"));
    lblApellidosUsuario.setTextFill(Color.web("#000000"));
    lblTelefonoUsuario.setTextFill(Color.web("#000000"));
  }
}
