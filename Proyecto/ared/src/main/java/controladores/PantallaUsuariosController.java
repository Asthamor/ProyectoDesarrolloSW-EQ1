/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaUsuariosController implements Initializable {

    @FXML
    private ScrollPane scrollUsuarios;
    @FXML
    private AnchorPane pnlUsuarios;
    @FXML
    private Label etNombreUsuario;
    @FXML
    private JFXButton btnRegistrarUsuario;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXTextField txtNombreUsuario;

    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private Persona persona;
    private List<Persona> personas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        scrollUsuarios.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    }

    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
        etNombreUsuario.setText("Nombre del " + this.persona.getTipoUsario() + ":");
        btnRegistrarUsuario.setText("Registrar " + this.persona.getTipoUsario());
        pnlUsuarios.getChildren().add(mostrarUsuarios(persona.obtenerTodos()));
    }

    public GridPane mostrarUsuarios(List<Persona> personas) {
        System.out.println(personas.size());
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        int filas = personas.size() / 2;
        int auxiliar = 0;
        if (personas.size() % 2 != 0) {
            filas = (personas.size() + 1) / 2;
        }
        for (int i = 0; i < filas; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/TarjetaInformacionUsuario.fxml"));
                Parent root = (Parent) loader.load();
                TarjetaInformacionUsuarioController controlador = loader.getController();
                controlador.setPersona(personas.get(auxiliar));
                auxiliar++;
                controlador.setPantallaDividida(pantallaDividida);
                grid.add(root, 0, i);
            } catch (IOException ex) {
                Logger.getLogger(PantallaPrincipalDirectorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (auxiliar < personas.size()) {
                try {
                    FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/TarjetaInformacionUsuario.fxml"));
                    Parent root = (Parent) loader.load();
                    TarjetaInformacionUsuarioController controlador = loader.getController();
                    controlador.setPersona(personas.get(auxiliar));
                    auxiliar++;
                    controlador.setPantallaDividida(pantallaDividida);
                    grid.add(root, 1, i);
                } catch (IOException ex) {
                    Logger.getLogger(PantallaPrincipalDirectorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return grid;
    }

    @FXML
    private void abrirVentanaRegistrarUsuario(ActionEvent event) {
        PantallaPrincipalDirectorController.limpiarPanelPrincipal(pnlPrincipal, pantallaDividida);
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/PantallaRegistrarUsuario.fxml"));
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalDirectorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PantallaRegistrarUsuarioController controlador = loader.getController();
        controlador.setPersona(persona);
        controlador.setPantallaDividida(pantallaDividida);
        controlador.setPnlPrincipal(pnlPrincipal);
        pnlPrincipal.getChildren().add(root);
        pantallaDividida.getChildren().add(pnlPrincipal);
    }

    @FXML
    private void buscarUsuario(ActionEvent event) {
        if (txtNombreUsuario.getText().isEmpty()) {
            Mensajes.mensajeAlert("Ingrese el nombre del "+ persona.getTipoUsario() + " que desea buscar");
        } else {
            pnlUsuarios.getChildren().clear();
            pnlUsuarios.getChildren().add(mostrarUsuarios(persona.buscar(txtNombreUsuario.getText())));
        }

    }

}
