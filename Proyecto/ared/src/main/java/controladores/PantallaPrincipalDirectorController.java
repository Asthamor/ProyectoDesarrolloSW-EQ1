/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import interfaces.Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

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
    
    private HBox pantallaDividida;
    private StackPane pnlPrincipal = new StackPane();
    private ImageView imagen = new ImageView("/imagenes/aredEspacioCompleto.png");


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        contenedor.getChildren().addAll(pantallaDividida,imagen);
        contenedor.setAlignment(imagen,Pos.CENTER);
        
    }

    @FXML
    private void abrirVentanaAnterior(ActionEvent event) {
    }

    @FXML
    private void abrirVentanaMisGrupos(ActionEvent event) {
    }

    @FXML
    private void abrirVentanaInscripciones(ActionEvent event) {
    }

    @FXML
    private void abrirVentanaPromociones(ActionEvent event) {
    }

    @FXML
    private void abrirVentanaRentas(ActionEvent event) {
    }

    @FXML
    private void abrirVentanaPagos(ActionEvent event) {
    }

    @FXML
    private void abrirVentanaFinanzas(ActionEvent event) {
    }

    @FXML
    private void abrirVentanaAnuncios(ActionEvent event) {
    }

    @FXML
    private void abrirVentanaAlumnos(ActionEvent event) {
        imagen.setVisible(false);
        limpiarPanelPrincipal(this.pnlPrincipal, this.pantallaDividida);
        pnlPrincipal.getChildren().add(crearPantallaUsuarios("alumno"));
        pantallaDividida.getChildren().add(pnlPrincipal);

    }

    @FXML
    private void abrirVentanaGrupos(ActionEvent event) {
        imagen.setVisible(false);
        limpiarPanelPrincipal(this.pnlPrincipal, this.pantallaDividida);
        pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaGrupos.fxml", this.pnlPrincipal, this.pantallaDividida));
        pantallaDividida.getChildren().add(pnlPrincipal);

    }

    @FXML
    private void abrirVentanaMaestros(ActionEvent event) {
        imagen.setVisible(false);
        limpiarPanelPrincipal(this.pnlPrincipal, this.pantallaDividida);
        pnlPrincipal.getChildren().add(crearPantallaUsuarios("maestro"));
        pantallaDividida.getChildren().add(pnlPrincipal);
    }

    @FXML
    private void abrirVentanaClientes(ActionEvent event) {
        imagen.setVisible(false);
        limpiarPanelPrincipal(this.pnlPrincipal, this.pantallaDividida);
        pnlPrincipal.getChildren().add(crearPantallaUsuarios("cliente"));
        pantallaDividida.getChildren().add(pnlPrincipal);
    }

    @FXML
    private void abrirVentanaNotificaciones(ActionEvent event) {
    }

    public static void limpiarPanelPrincipal(StackPane pnlPrincipal, HBox pantallaDividida) {
        
        pnlPrincipal.getChildren().clear();
        pantallaDividida.getChildren().clear();
        animacionCargarPantalla(pnlPrincipal);
    }

    public Parent crearPantallaUsuarios(String tipoUsuario) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/PantallaUsuarios.fxml"));
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalDirectorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PantallaUsuariosController controlador = loader.getController();
        controlador.setPantallaDividida(pantallaDividida);
        controlador.setPnlPrincipal(pnlPrincipal);
        controlador.setTipoUsuario(tipoUsuario);
        return root;
    }

    public static Parent crearPantalla(String rutaPantalla, StackPane pnlPrincipal, HBox pantallaDividida) {
        limpiarPanelPrincipal(pnlPrincipal, pantallaDividida);
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource(rutaPantalla));
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalDirectorController.class.getName()).log(Level.SEVERE, null, ex);
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
    

        

    

}
