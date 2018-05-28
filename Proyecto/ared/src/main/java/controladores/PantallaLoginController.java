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
import com.jfoenix.validation.RequiredFieldValidator;

import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaLoginController implements Initializable {

  @FXML
  private AnchorPane panelPrincipal;
  @FXML
  private ImageView imgAredEspacio;
  @FXML
  private JFXTextField txtNombreUsuario;
  @FXML
  private JFXPasswordField txtContraseña;
  @FXML
  private JFXButton btnIniciarSesion;
  @FXML
  private Label lblError;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    btnIniciarSesion.setDefaultButton(true);
    ValidatorBase requeridos = new RequiredFieldValidator();
    requeridos.setMessage("Campo Requerido");
    requeridos.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
            .glyph(FontAwesomeIcon.WARNING)
            .size("1em")
            .styleClass("error")
            .build());
    txtNombreUsuario.setValidators(requeridos);
    requeridos = new RequiredFieldValidator();
    requeridos.setMessage("La contraseña no puede estar vacía");
    requeridos.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
            .glyph(FontAwesomeIcon.WARNING)
            .size("1em")
            .styleClass("error")
            .build());
    txtContraseña.getValidators().add(requeridos);
    // TODO

  }

  @FXML
  private void tryLogin(ActionEvent event) {
    if (!camposVacios()) {
      String nombreUsuario = txtNombreUsuario.getText();
      String contraseña = txtContraseña.getText();
      int res = checkUser(nombreUsuario, contraseña);
      Usuario usuario = new Usuario();
      switch (res) {
        case 1:
          usuario = usuario.buscar(nombreUsuario);
          abrirMenuMaestro(usuario);
          break;
        case 0:
          usuario = usuario.buscar(nombreUsuario);
          abrirMenuDirector(usuario);
          break;
        default:
          lblError.setVisible(true);
          txtNombreUsuario.clear();
          txtContraseña.clear();
          break;
      }
    }
  }

  private boolean camposVacios() {
    boolean vacios = false;
    if (!txtContraseña.validate()) {
      vacios = true;
    }
    if (!txtNombreUsuario.validate()) {
      vacios = true;
    }
    return vacios;
  }

  private int checkUser(String nombreUsuario, String contraseña) {
    Usuario usuario = new Usuario();
    int resultado = usuario.autenticar(nombreUsuario, contraseña);
    return resultado;
  }

  private boolean abrirMenuMaestro(Usuario usuario) {
    Stage mainStage = (Stage) txtNombreUsuario.getScene().getWindow();
    System.setProperty("nombreUsuario", usuario.getNombreUsuario());
    mainStage.getProperties().put("nombreUsuario", usuario.getNombreUsuario());
    mainStage.getProperties().put("persona", usuario.getMaestroCollection().toArray()[0]);
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/fxml/PantallaPrincipalMaestro.fxml"));
      mainStage.setScene(new Scene(root));
      mainStage.setResizable(false);

      mainStage.setFullScreen(true);
      mainStage.setFullScreenExitHint(null);
      mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
      mainStage.show();

    } catch (IOException ex) {
      Logger.getLogger(PantallaLoginController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return true;

  }

  private boolean abrirMenuDirector(Usuario usuario) {
    Stage mainStage = (Stage) txtNombreUsuario.getScene().getWindow();
    System.setProperty("nombreUsuario", usuario.getNombreUsuario());
    mainStage.getProperties().put("nombreUsuario", usuario.getNombreUsuario());
    mainStage.getProperties().put("persona", usuario.getMaestroCollection().toArray()[0]);

    try {
      Parent root = FXMLLoader.load(getClass().getResource("/fxml/PantallaPrincipalDirector.fxml"));
      mainStage.setScene(new Scene(root));
      mainStage.setFullScreen(true);
      mainStage.setResizable(false);
      mainStage.setFullScreenExitHint(null);
      mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
      mainStage.show();

    } catch (IOException ex) {
      Logger.getLogger(PantallaLoginController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return true;
  }
}
