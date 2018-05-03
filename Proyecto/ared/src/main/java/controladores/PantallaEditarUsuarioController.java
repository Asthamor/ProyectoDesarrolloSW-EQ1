/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.JFXLimitedTextField;
import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
  private JFXLimitedTextField txtNombresUsuario;
  @FXML
  private JFXLimitedTextField txtApellidosUsuario;
  @FXML
  private JFXLimitedTextField txtTelefonoUsuario;
  @FXML
  private Label lblInfoOpcional;
  @FXML
  private Label lblFotoUsuario;
  @FXML
  private ImageView imgFotoUsuario;
  @FXML
  private Label lblCorreoElectronico;
  @FXML
  private JFXLimitedTextField txtCorreoElectronico;
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
    txtCorreoElectronico.setSizeLimiter(100);
    txtNombresUsuario.setAlphanumericLimiter(80);
    txtApellidosUsuario.setAlphanumericLimiter(45);
    txtTelefonoUsuario.setNumLimiter(10);

    txtNombresUsuario.setRequired(true);
    txtApellidosUsuario.setRequired(true);
    txtTelefonoUsuario.setRequired(true);

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
    if (!existenCamposVacios()) {
      persona.setNombre(txtNombresUsuario.getText());
      persona.setApellidos(txtApellidosUsuario.getText());
      persona.setTelefono(txtTelefonoUsuario.getText());
      persona.setEmail(txtCorreoElectronico.getText());

      if (rutaFoto != null) {
        persona.setImgFoto(rutaFoto);
        if (persona.actualizarDatos(true)) {
          controlador.mostrarInformacion(persona);
          pantallaDividida.getChildren().remove(1);
          Mensajes.mensajeExitoso("La información se actualizó correctamente");
        }
      } else {
        if (persona.actualizarDatos(false)) {
          controlador.mostrarInformacion(persona);
          pantallaDividida.getChildren().remove(1);
          Mensajes.mensajeExitoso("La información se actualizó correctamente");
        }

      }
    }

  }

  public boolean existenCamposVacios() {
    boolean vacios = true;
    txtNombresUsuario.setText(txtNombresUsuario.getText().trim());
    txtApellidosUsuario.setText(txtApellidosUsuario.getText().trim());
    txtTelefonoUsuario.setText(txtTelefonoUsuario.getText().trim());

    boolean valNombre = txtNombresUsuario.validate();
    boolean valApellidos = txtApellidosUsuario.validate();
    boolean valTel = txtTelefonoUsuario.validate();

    if (valNombre && valApellidos && valTel) {
      vacios = false;
    }
    return vacios;
  }

}
