/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaInformacionInscripcionAlumnoController implements Initializable {

    @FXML
    private ImageView imgAlumno;
    @FXML
    private Label labelAlumno;
    @FXML
    private Label labelTelefono;
    @FXML
    private Label labelCorreo;
    @FXML
    private JFXButton btnReinscribir;
    @FXML
    private JFXButton btnDarBaja;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
