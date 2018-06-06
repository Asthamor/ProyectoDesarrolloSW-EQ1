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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaPagosController implements Initializable,Controlador {
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    @FXML
    private JFXButton btnPagoMaestro;
    @FXML
    private JFXButton btnPagoAlumno;
    @FXML
    private StackPane pnlPagos;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnPagoAlumno.setFocusTraversable(false);
        btnPagoMaestro.setFocusTraversable(false);
        PantallaPrincipalDirectorController.animacionCargarPantalla(pnlPagos);
        pnlPagos.getChildren().add(crearPantalla("/fxml/PantallaRegistrarPagoMaestro.fxml"));
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
    private void ventanaPagoMaestro(ActionEvent event) {
        pnlPagos.getChildren().clear();
        PantallaPrincipalDirectorController.animacionCargarPantalla(pnlPagos);        
        pnlPagos.getChildren().add(crearPantalla("/fxml/PantallaRegistrarPagoMaestro.fxml"));
    }

    @FXML
    private void ventanaPagoAlumnoExterno(ActionEvent event) {
        pnlPagos.getChildren().clear();
        PantallaPrincipalDirectorController.animacionCargarPantalla(pnlPagos);
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/PantallaRegistraPagoAlumnoExterno.fxml"));
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalDirectorController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        PantallaRegistraPagoAlumnoExternoController controlador = loader.getController();
        controlador.setPantallaDividida(pantallaDividida);
        controlador.setPnlPrincipal(pnlPrincipal);
        controlador.setPnlPagos(pnlPagos);
        pnlPagos.getChildren().add(root);
    }
    
    public Parent crearPantalla(String ruta) {
        pnlPagos.getChildren().clear();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource(ruta));
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
    
}
