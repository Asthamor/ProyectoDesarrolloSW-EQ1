/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
}
