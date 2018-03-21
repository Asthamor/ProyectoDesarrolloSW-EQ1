/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
