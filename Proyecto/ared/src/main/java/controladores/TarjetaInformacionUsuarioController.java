/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class TarjetaInformacionUsuarioController implements Initializable {

    @FXML
    private ImageView imgFotoUsuario;
    @FXML
    private Label lblNombresUsuario;
    @FXML
    private Label lblApellidosUsuario;
    @FXML
    private Label lblTelefonoUsuario;
    @FXML
    private Label lblCorreoElectronicoUsuario;
    @FXML
    private JFXButton btnEditarUsuario;
    @FXML
    private Tooltip etBtnEditar;
    @FXML
    private FontAwesomeIconView iconoBtn;

    private Persona persona;
    private HBox pantallaDividida;
    private StackPane pnlSecundario = new StackPane();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setPersona(Persona persona) {
        this.persona = persona;
        mostrarInformacion(this.persona);
    }

    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    public void mostrarInformacion(Persona persona) {
        lblNombresUsuario.setText(persona.getNombre());
        lblApellidosUsuario.setText(persona.getApellidos());
        lblTelefonoUsuario.setText(persona.getTelefono());
        Image imagen = new Image("file:" + persona.obtenerImagen(),true);
        imgFotoUsuario.setImage(imagen);
        if (persona.getEmail() == null) {
            lblCorreoElectronicoUsuario.setText("");
        } else {
            lblCorreoElectronicoUsuario.setText(persona.getEmail());
        }
    }

    @FXML
    private void editarUsuario(ActionEvent event) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/PantallaEditarUsuario.fxml"));
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalDirectorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PantallaEditarUsuarioController controlador = loader.getController();
        controlador.setPersona(persona);
        controlador.setPantallaDividida(pantallaDividida);
        controlador.setControlador(this);
        pnlSecundario.getChildren().add(root);
        PantallaPrincipalDirectorController.animacionCargarPantalla(pnlSecundario);
        if(pantallaDividida.getChildren().size() > 1){
            pantallaDividida.getChildren().remove(1);
        }
        pantallaDividida.getChildren().add(pnlSecundario);
    }
    
    
}
