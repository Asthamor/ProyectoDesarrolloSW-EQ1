/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

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
    
}
