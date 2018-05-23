/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.Notificaciones;
import com.jfoenix.controls.JFXButton;
import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAtras.setFocusTraversable(false);
        btnMisGrupos.setFocusTraversable(false);
        btnInscripciones.setFocusTraversable(false);
        btnPromociones.setFocusTraversable(false);
        btnNotificaciones.setFocusTraversable(false);   
        btnSesionUsuario.setFocusTraversable(false);       
        pantallaDividida = new HBox();
        contenedor.getChildren().addAll(pantallaDividida,imagen);
        contenedor.setAlignment(imagen,Pos.CENTER);
        btnSesionUsuario.setText(System.getProperty("nombreUsuario"));
        
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
    
}
