/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
public class PantallaEditarUsuarioController implements Initializable {

    @FXML
    private Label lblInfoNecesaria;
    @FXML
    private Label lblNombresUsuario;
    @FXML
    private Label lblApellidosUsuario;
    @FXML
    private Label lblTelefonoUsuario;
    @FXML
    private JFXTextField txtNombresUsuario;
    @FXML
    private JFXTextField txtApellidosUsuario;
    @FXML
    private JFXTextField txtTelefonoUsuario;
    @FXML
    private Label lblInfoOpcional;
    @FXML
    private Label lblFotoUsuario;
    @FXML
    private ImageView imgFotoUsuario;
    @FXML
    private Label lblCorreoElectronico;
    @FXML
    private JFXTextField txtCorreoElectronico;
    @FXML
    private JFXButton btnSeleccionarFoto;
    @FXML
    private JFXButton btnGuardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
