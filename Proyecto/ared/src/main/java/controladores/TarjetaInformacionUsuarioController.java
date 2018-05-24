/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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

    private Persona persona;
    private HBox pantallaDividida;
    private StackPane pnlSecundario = new StackPane();
    private final Background focusBackground = new Background(new BackgroundFill(Color.web("#FFD4FC"), CornerRadii.EMPTY, Insets.EMPTY));
    private final Background unfocusBackground = new Background(new BackgroundFill(Color.web("#ffe6fd"), CornerRadii.EMPTY, Insets.EMPTY));
    @FXML
    private StackPane panelTarjeta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        panelTarjeta.backgroundProperty().bind(Bindings
                .when(panelTarjeta.focusedProperty())
                .then(focusBackground)
                .otherwise(unfocusBackground)
        );
        panelTarjeta.getStyleClass().add("pane");
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
        Image imagen = new Image("file:" + persona.obtenerImagen(), true);
        imgFotoUsuario.setImage(imagen);
        if (persona.getEmail() == null) {
            lblCorreoElectronicoUsuario.setText("");
        } else {
            lblCorreoElectronicoUsuario.setText(persona.getEmail());
        }
    }

    @FXML
    private void cambiarColor(MouseEvent event) {
        panelTarjeta.requestFocus();
    }

    @FXML
    private void editarUsuario(MouseEvent event) {
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
        if (pantallaDividida.getChildren().size() > 1) {
            pantallaDividida.getChildren().remove(1);
        }
        pantallaDividida.getChildren().add(pnlSecundario);
    }

}
