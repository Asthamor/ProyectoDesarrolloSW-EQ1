/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.JFXLimitedTextField;
import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.validation.base.ValidatorBase;
import static controladores.PantallaPrincipalDirectorController.crearPantallaUsuarios;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaRegistrarUsuarioController implements Initializable {

    @FXML
    private Label lblInfoNecesaria;
    @FXML
    private Label lblInfoOpcional;
    @FXML
    private Label lblNombresUsuario;
    @FXML
    private JFXLimitedTextField txtNombresUsuario;
    @FXML
    private Label lblApellidosUsuario;
    @FXML
    private Label lblTelefonoUsuario;
    @FXML
    private JFXLimitedTextField txtApellidosUsuario;
    @FXML
    private JFXLimitedTextField txtTelefonoUsuario;
    @FXML
    private Label lblFotografiaUsuario;
    @FXML
    private ImageView imgFotoUsuario;
    @FXML
    private JFXButton btnSeleccionarFoto;
    @FXML
    private Label lblCorreoElectronicoUsuario;
    @FXML
    private JFXLimitedTextField txtCorreoElectronicoUsuario;
    @FXML
    private JFXButton btnGuardarUsuario;
    @FXML
    private Label lblNuevoUsuario;

    private Persona persona;
    private String rutaFoto;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtCorreoElectronicoUsuario.setSizeLimiter(100);
        txtCorreoElectronicoUsuario.setText("");
        rutaFoto = null;
        txtApellidosUsuario.setAlphanumericLimiter(50);
        txtApellidosUsuario.setRequired(true);
        txtApellidosUsuario.setText("");
        txtNombresUsuario.setAlphanumericLimiter(50);
        txtNombresUsuario.setRequired(true);
        txtNombresUsuario.setText("");
        txtTelefonoUsuario.setNumLimiter(10);
        txtTelefonoUsuario.setRequired(true);
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
        lblNuevoUsuario.setText("Nuevo " + this.persona.getTipoUsario());
    }

    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    @FXML
    private void seleccionarFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagenes", "*.jpg", "*.png")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image imagen = new Image("file:" + file.getAbsolutePath());
            rutaFoto = file.getAbsolutePath();
            imgFotoUsuario.setImage(imagen);
        }
    }

    @FXML
    private void guardarUsuario(ActionEvent event) {
        if (!existenCamposVacios()) {
            persona.setNombre(txtNombresUsuario.getText());
            persona.setApellidos(txtApellidosUsuario.getText());
            persona.setTelefono(txtTelefonoUsuario.getText());
            persona.setImgFoto(rutaFoto);
            persona.setEmail(txtCorreoElectronicoUsuario.getText());
            if (persona.registrar(persona)) {
                pnlPrincipal.getChildren().add(crearPantallaUsuarios(persona, this.pnlPrincipal, this.pantallaDividida));
                pantallaDividida.getChildren().add(pnlPrincipal);
                Mensajes.mensajeExitoso("El " + persona.getTipoUsario() + " se registro correctamente");
            }
        } else {
            Mensajes.mensajeAlert("Error");
        }
    }

    public boolean existenCamposVacios() {
        boolean vacios = true;
        txtNombresUsuario.setText(txtNombresUsuario.getText().trim());
        txtApellidosUsuario.setText(txtApellidosUsuario.getText().trim());
        
        boolean valNombre = txtNombresUsuario.validate();
        boolean valApellidos = txtApellidosUsuario.validate();
        boolean valTel = txtTelefonoUsuario.validate();
        
        if ((valNombre && valApellidos) && valTel){ 
            vacios = false;
        }
        return vacios;
    }

}