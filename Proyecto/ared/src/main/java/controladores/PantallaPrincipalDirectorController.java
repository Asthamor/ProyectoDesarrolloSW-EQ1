/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.Notificaciones;
import com.jfoenix.controls.JFXButton;
import interfaces.Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.Alumno;
import modelo.Cliente;
import modelo.Maestro;
import modelo.Persona;
import modelo.Usuario;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaPrincipalDirectorController implements Initializable {

    @FXML
    private JFXButton btnAtras;
    @FXML
    private JFXButton btnMisGrupos;
    @FXML
    private JFXButton btnInscripciones;
    @FXML
    private JFXButton btnPromociones;
    @FXML
    private JFXButton btnRentas;
    @FXML
    private JFXButton btnPagos;
    @FXML
    private JFXButton btnFinanzas;
    @FXML
    private JFXButton btnAnuncios;
    @FXML
    private JFXButton btnAlumnos;
    @FXML
    private JFXButton btnGrupos;
    @FXML
    private JFXButton btnMaestros;
    @FXML
    private JFXButton btnClientes;
    @FXML
    private JFXButton btnNotificaciones;
    @FXML
    private Pane pnlUsuario;
    @FXML
    private JFXButton btnSesionUsuario;
    @FXML
    private StackPane contenedor;

    private Stage mainStage;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal = new StackPane();
    private ImageView imagen = new ImageView("/imagenes/aredEspacioCompleto.png");
    private List<StackPane> notificaciones;
    private PopOver popOver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        popOver = new PopOver();
        popOver.setAnimated(true);
        btnAtras.setFocusTraversable(false);
        btnMisGrupos.setFocusTraversable(false);
        btnInscripciones.setFocusTraversable(false);
        btnPromociones.setFocusTraversable(false);
        btnRentas.setFocusTraversable(false);
        btnPagos.setFocusTraversable(false);
        btnFinanzas.setFocusTraversable(false);
        btnAnuncios.setFocusTraversable(false);
        btnAlumnos.setFocusTraversable(false);
        btnGrupos.setFocusTraversable(false);
        btnMaestros.setFocusTraversable(false);
        btnClientes.setFocusTraversable(false);
        btnNotificaciones.setFocusTraversable(false);
        btnSesionUsuario.setFocusTraversable(false);
        pantallaDividida = new HBox();
        contenedor.getChildren().addAll(pantallaDividida, imagen);
        contenedor.setAlignment(imagen, Pos.CENTER);
        btnSesionUsuario.setText(System.getProperty("nombreUsuario"));

    }

    @FXML
    private void abrirVentanaAnterior(ActionEvent event) {
    }

    @FXML
    private void abrirVentanaMisGrupos(ActionEvent event) {
        imagen.setVisible(false);
        pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaMisGrupos.fxml",
                this.pnlPrincipal, this.pantallaDividida));
        pantallaDividida.getChildren().add(pnlPrincipal);
    }

    @FXML
    private void abrirVentanaInscripciones(ActionEvent event) {
        imagen.setVisible(false);
        pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaInscripciones.fxml",
                this.pnlPrincipal, this.pantallaDividida));
        pantallaDividida.getChildren().add(pnlPrincipal);
    }

    @FXML
    private void abrirVentanaPromociones(ActionEvent event) {
        imagen.setVisible(false);
        pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaPromociones.fxml",
                this.pnlPrincipal, this.pantallaDividida));
        pantallaDividida.getChildren().add(pnlPrincipal);
    }

    @FXML
    private void abrirVentanaRentas(ActionEvent event) {
        imagen.setVisible(false);
        pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaRentas.fxml",
                this.pnlPrincipal, this.pantallaDividida));
        pantallaDividida.getChildren().add(pnlPrincipal);
    }

    @FXML
    private void abrirVentanaPagos(ActionEvent event) {
        imagen.setVisible(false);
        pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaPagos.fxml",
                this.pnlPrincipal, this.pantallaDividida));
        pantallaDividida.getChildren().add(pnlPrincipal);
    }

    @FXML
    private void abrirVentanaFinanzas(ActionEvent event) {
        imagen.setVisible(false);
        pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaConsultarIngresos.fxml",
                this.pnlPrincipal, this.pantallaDividida));
        pantallaDividida.getChildren().add(pnlPrincipal);
    }

    @FXML
    private void abrirVentanaAnuncios(ActionEvent event) {
    }

    @FXML
    private void abrirVentanaAlumnos(ActionEvent event) {
        imagen.setVisible(false);
        pnlPrincipal.getChildren().add(crearPantallaUsuarios(new Alumno(),
                this.pnlPrincipal, this.pantallaDividida));
        pantallaDividida.getChildren().add(pnlPrincipal);

    }

    @FXML
    private void abrirVentanaGrupos(ActionEvent event) {
        imagen.setVisible(false);
        pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaGrupos.fxml",
                this.pnlPrincipal, this.pantallaDividida));
        pantallaDividida.getChildren().add(pnlPrincipal);

    }

    @FXML
    private void abrirVentanaMaestros(ActionEvent event) {
        imagen.setVisible(false);
        pnlPrincipal.getChildren().add(crearPantallaUsuarios(new Maestro(),
                this.pnlPrincipal, this.pantallaDividida));
        pantallaDividida.getChildren().add(pnlPrincipal);
    }

    @FXML
    private void abrirVentanaClientes(ActionEvent event) {
        imagen.setVisible(false);
        pnlPrincipal.getChildren().add(crearPantallaUsuarios(new Cliente(),
                this.pnlPrincipal, this.pantallaDividida));
        pantallaDividida.getChildren().add(pnlPrincipal);
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
            Notificaciones notificaciones = new Notificaciones(1, true);
            notificaciones.setMaestro(maestro);
            this.notificaciones = notificaciones.buscarNotificaciones();
            vBox.getChildren().addAll(this.notificaciones);
        } else {
            vBox.getChildren().addAll(this.notificaciones);
        }

        popOver.setContentNode(vBox);
        popOver.show(btnNotificaciones);

    }

    public static void limpiarPanelPrincipal(StackPane pnlPrincipal, HBox pantallaDividida) {
        pnlPrincipal.getChildren().clear();
        pantallaDividida.getChildren().clear();
        animacionCargarPantalla(pnlPrincipal);
    }

    public static Parent crearPantallaUsuarios(Persona persona,
            StackPane pnlPrincipal, HBox pantallaDividida) {

        limpiarPanelPrincipal(pnlPrincipal, pantallaDividida);
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(
                PantallaPrincipalDirectorController.class.getResource("/fxml/PantallaUsuarios.fxml"));
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalDirectorController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        PantallaUsuariosController controlador = loader.getController();
        controlador.setPantallaDividida(pantallaDividida);
        controlador.setPnlPrincipal(pnlPrincipal);
        controlador.setPersona(persona);
        return root;
    }

    public static Parent crearPantalla(String rutaPantalla, StackPane pnlPrincipal,
            HBox pantallaDividida) {
        limpiarPanelPrincipal(pnlPrincipal, pantallaDividida);
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource(rutaPantalla));
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

    public static void animacionCargarPantalla(StackPane pnlPrincipal) {
        FadeTransition transicion = new FadeTransition();
        transicion.setDuration(Duration.millis(800));
        transicion.setNode(pnlPrincipal);
        transicion.setFromValue(0);
        transicion.setToValue(1);
        transicion.play();
    }

    @FXML
    private void usuarioMenuToggle(ActionEvent event) {
        imagen.setVisible(false);
        pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaEditarPerfil.fxml",
                this.pnlPrincipal, this.pantallaDividida));
        pantallaDividida.getChildren().add(pnlPrincipal);
    }
}
