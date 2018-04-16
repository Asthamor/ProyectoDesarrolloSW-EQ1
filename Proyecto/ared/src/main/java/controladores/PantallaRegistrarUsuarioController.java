/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import static controladores.PantallaPrincipalDirectorController.crearPantallaUsuarios;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    private JFXTextField txtNombresUsuario;
    @FXML
    private Label lblApellidosUsuario;
    @FXML
    private Label lblTelefonoUsuario;
    @FXML
    private JFXTextField txtApellidosUsuario;
    @FXML
    private JFXTextField txtTelefonoUsuario;
    @FXML
    private Label lblFotografiaUsuario;
    @FXML
    private ImageView imgFotoUsuario;
    @FXML
    private JFXButton btnSeleccionarFoto;
    @FXML
    private Label lblCorreoElectronicoUsuario;
    @FXML
    private JFXTextField txtCorreoElectronicoUsuario;
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
        UnaryOperator<Change> filter = change -> {
            String text = change.getText();
            if (!change.isAdded()) {
                return change;
            } else {
                if (text.matches("^[0-9]${0,10}")) {
                    return change;
                }
            }

            return null;
        };

        TextFormatter<String> textLimit = new TextFormatter<>(filter);

        txtCorreoElectronicoUsuario.setText("");
        rutaFoto = null;
        txtApellidosUsuario.setText("");
        txtNombresUsuario.setText("");
        txtTelefonoUsuario.setText("");
        txtTelefonoUsuario.setTextFormatter(textLimit);

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
        colorEtiquetas();
        if (existenCamposVacios()) {
            Mensajes.mensajeAlert("Algunos campos estan vacíos");
        } else {
            if (!tamañoValidoCaracteres()) {
                Mensajes.mensajeAlert("Algunos campos sobre pasan el limite de caracteres");
            } else {
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
            }
        }
    }

    public boolean existenCamposVacios() {
        return txtNombresUsuario.getText().equals("") && txtApellidosUsuario.getText().equals("")
            && txtTelefonoUsuario.getText().equals("");
    }

    @FXML
    private void limitarCaracteresEmail(KeyEvent event) {
        int limiteCaracteres = 100;
        if (txtCorreoElectronicoUsuario.getText().length() >= limiteCaracteres) {
            event.consume();
        }
    }

    @FXML
    private void limitarCaracteresNombre(KeyEvent event) {
        int limiteCaracteres = 80;
        if (txtNombresUsuario.getText().length() >= limiteCaracteres) {
            event.consume();
        }
    }

    @FXML
    private void limitarCaracteresApellido(KeyEvent event) {
        int limiteCaracteres = 45;
        if (txtApellidosUsuario.getText().length() >= limiteCaracteres) {
            event.consume();
        }
    }

    @FXML
    private void limitarCaracteresTelefono(KeyEvent event) {
//        int limiteCaracteres = 10;
//        if (txtTelefonoUsuario.getText().length() >= limiteCaracteres) {
//            event.consume();
//        }
    }

    public boolean tamañoValidoCaracteres() {
        boolean tamañoValido = true;
        if (txtCorreoElectronicoUsuario.getText().length() > 100) {
            tamañoValido = false;
            lblCorreoElectronicoUsuario.setTextFill(Color.web("#EC7063"));
        }

        if (txtNombresUsuario.getText().length() > 80) {
            tamañoValido = false;
            lblNombresUsuario.setTextFill(Color.web("#EC7063"));
        }

        if (txtApellidosUsuario.getText().length() > 45) {
            tamañoValido = false;
            lblApellidosUsuario.setTextFill(Color.web("#EC7063"));
        }

        if (txtTelefonoUsuario.getText().length() > 10) {
            tamañoValido = false;
            lblTelefonoUsuario.setTextFill(Color.web("#EC7063"));
        }
        return tamañoValido;
    }

    public void colorEtiquetas() {
        lblCorreoElectronicoUsuario.setTextFill(Color.web("#000000"));
        lblNombresUsuario.setTextFill(Color.web("#000000"));
        lblApellidosUsuario.setTextFill(Color.web("#000000"));
        lblTelefonoUsuario.setTextFill(Color.web("#000000"));
    }

    @FXML
    private void limitarCaracteresTel(InputMethodEvent event) {
        int limiteCaracteres = 10;
        if (txtTelefonoUsuario.getText().length() > limiteCaracteres) {
            txtTelefonoUsuario.setText(
                txtTelefonoUsuario.getText().substring(0, 9));
        }
    }

}
