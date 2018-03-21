/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaInscribirAlumnoController implements Initializable {

    @FXML
    private JFXComboBox<?> comboAlumnos;
    @FXML
    private JFXTextField txtMonto;
    @FXML
    private JFXComboBox<?> comboPromocion;
    @FXML
    private Label labelMontoFinal;
    @FXML
    private JFXButton btnInscribir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
