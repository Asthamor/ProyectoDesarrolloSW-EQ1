/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.Mensajes;
import clasesApoyo.Notificaciones;
import com.jfoenix.controls.JFXButton;
import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modelo.Maestro;
import modelo.Usuario;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaPrincipalMaestroController implements Initializable {

  @FXML
  private JFXButton btnAtras;
  @FXML
  private JFXButton btnMisGrupos;
  @FXML
  private JFXButton btnInscripciones;
  @FXML
  private JFXButton btnNotificaciones;
  @FXML
  private JFXButton btnPromociones;
  @FXML
  private JFXButton btnSesionUsuario;

  private HBox pantallaDividida;
  private StackPane pnlPrincipal = new StackPane();
  private ImageView imagen = new ImageView("/imagenes/aredEspacioCompleto.png");
  @FXML
  private StackPane contenedor;
  private List<StackPane> notificaciones;
  private PopOver popOver;
  private PopOver tarjetaUsuarioPop;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    btnAtras.setVisible(false);
    btnMisGrupos.setFocusTraversable(false);
    btnInscripciones.setFocusTraversable(false);
    btnPromociones.setFocusTraversable(false);
    btnNotificaciones.setFocusTraversable(false);
    btnSesionUsuario.setFocusTraversable(false);
    pantallaDividida = new HBox();
    popOver = new PopOver();
    contenedor.getChildren().addAll(pantallaDividida, imagen);
    contenedor.setAlignment(imagen, Pos.CENTER);
    btnSesionUsuario.setText(System.getProperty("nombreUsuario"));
    tarjetaUsuarioPop = new PopOver();
    tarjetaUsuarioPop.setAnimated(true);
    tarjetaUsuarioPop.setArrowLocation(PopOver.ArrowLocation.TOP_RIGHT);

  }

  @FXML
  private void abrirVentanaMisGrupos(ActionEvent event) {
    imagen.setVisible(false);
    pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaMisGrupos.fxml", this.pnlPrincipal, this.pantallaDividida));
    pantallaDividida.getChildren().add(pnlPrincipal);
  }

  @FXML
  private void abrirVentanaInscripciones(ActionEvent event) {
    imagen.setVisible(false);
    pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaInscripciones.fxml", this.pnlPrincipal, this.pantallaDividida));
    pantallaDividida.getChildren().add(pnlPrincipal);
  }

  @FXML
  private void abrirVentanaPromociones(ActionEvent event) {
    imagen.setVisible(false);
    pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaPromociones.fxml", this.pnlPrincipal, this.pantallaDividida));
    pantallaDividida.getChildren().add(pnlPrincipal);
  }

  @FXML
  private void usuarioMenuToggle(ActionEvent event) {
    Parent root = null;
    FXMLLoader loader = new FXMLLoader(
            TarjetaPopupUsuarioController.class.getResource("/fxml/TarjetaPopupUsuario.fxml"));
    try {
      root = (Parent) loader.load();
    } catch (IOException ex) {
      Logger.getLogger(TarjetaPopupUsuarioController.class.getName())
              .log(Level.SEVERE, null, ex);
    }
    TarjetaPopupUsuarioController controlador = loader.getController();

    controlador.setControladorMaestro(this);

    tarjetaUsuarioPop.setContentNode(root);
    tarjetaUsuarioPop.show(btnSesionUsuario);
  }

  @FXML
  private void abrirVentanaNotificaciones(ActionEvent event) {
    VBox vBox = new VBox();
    vBox.setBackground(new Background(new BackgroundFill(Color.web("#ffe6fd"), CornerRadii.EMPTY, Insets.EMPTY)));
    if (notificaciones == null) {
      String nombreUsuario = System.getProperty("nombreUsuario");
      Usuario usuario = new Usuario();
      usuario = usuario.buscar(nombreUsuario);
      Maestro maestro = (Maestro) usuario.getMaestroCollection().toArray()[0];
      Notificaciones notificaciones = new Notificaciones(1, false);
      notificaciones.setMaestro(maestro);
      this.notificaciones = notificaciones.buscarNotificaciones();
      vBox.getChildren().addAll(this.notificaciones);
    } else {
      vBox.getChildren().addAll(this.notificaciones);
    }

    popOver.setContentNode(vBox);
    popOver.show(btnNotificaciones);
  }
  
  
  public void abrirEditarPerfil() {
    imagen.setVisible(false);
    pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaEditarPerfil.fxml",
            this.pnlPrincipal, this.pantallaDividida));
    pantallaDividida.getChildren().add(pnlPrincipal);
  }

  public void cerrarSesion() {
    ButtonType respuesta = Mensajes.dialogoConfirmacion(
            "Cerrar Sesion",
            "¿Está seguro que desea cerrar la sesión actual?"
    );
    if (respuesta.getButtonData() == ButtonType.APPLY.getButtonData()) {
      System.clearProperty("nombreUsuario");

      Stage stage = (Stage) imagen.getScene().getWindow();
      stage.getProperties().clear();
      Parent root = null;
      FXMLLoader loader = new FXMLLoader(
              PantallaLoginController.class.getResource("/fxml/PantallaLogin.fxml"));
      try {
        root = (Parent) loader.load();
      } catch (IOException ex) {
        Logger.getLogger(PantallaPrincipalDirectorController.class.getName())
                .log(Level.SEVERE, null, ex);
      }
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.setTitle("Ared espacio");
      stage.setFullScreen(true);
      stage.setResizable(false);
      stage.setFullScreenExitHint("");
      stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }

  }


}
