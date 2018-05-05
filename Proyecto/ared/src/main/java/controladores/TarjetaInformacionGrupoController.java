/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import modelo.Alumno;
import modelo.Grupo;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class TarjetaInformacionGrupoController implements Initializable {

    @FXML
    private Label lblNombreGrupo;
    @FXML
    private Label lblTipoDanza;
    @FXML
    private JFXTextArea txtHorario;

    private Grupo grupo;
    private Color colorGrupo;

    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private boolean editarGrupo;
    private List<Alumno> alumnos;

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
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
        mostrarInformacion(grupo);
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public void setEditarGrupo(boolean editarGrupo) {
        this.editarGrupo = editarGrupo;
    }

    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    public void agregarHorario(String horario) {
        txtHorario.setText(horario);
    }

    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public void mostrarInformacion(Grupo grupo) {
        lblNombreGrupo.setText(grupo.getNombre());
        lblTipoDanza.setText(grupo.getTipoDanza());
    }

    public void setColorGrupo(Color colorGrupo) {
        this.colorGrupo = colorGrupo;
    }

    @FXML
    private void cambiarColor(MouseEvent event) {
        panelTarjeta.requestFocus();
    }

    @FXML
    private void editarGrupo(MouseEvent event) {
        Parent root = null;
        if (editarGrupo) {
            FXMLLoader loader = new FXMLLoader(TarjetaInformacionGrupoController.class.getResource("/fxml/PantallaEditarGrupo.fxml"));
            try {
                root = (Parent) loader.load();
                PantallaEditarGrupoController controlador = loader.getController();
                controlador.setGrupo(grupo);
                controlador.setPantallaDividida(pantallaDividida);
                controlador.setPnlPrincipal(pnlPrincipal);
                controlador.setControlador(this);
                controlador.agregarHorario(txtHorario.getText());
                controlador.setColorGrupo(colorGrupo);
                pnlSecundario.getChildren().add(root);
                PantallaPrincipalDirectorController.animacionCargarPantalla(pnlSecundario);
                if (pantallaDividida.getChildren().size() > 1) {
                    pantallaDividida.getChildren().remove(1);
                }
                pantallaDividida.getChildren().add(pnlSecundario);
            } catch (IOException ex) {
                Logger.getLogger(TarjetaInformacionGrupoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            FXMLLoader loader = new FXMLLoader(TarjetaInformacionGrupoController.class.getResource("/fxml/PantallaListaAlumnos.fxml"));
            try {
                root = (Parent) loader.load();
                PantallaListaAlumnosController controlador = loader.getController();
                controlador.setPantallaDividida(pantallaDividida);
                controlador.setPnlPrincipal(pnlPrincipal);
                controlador.setAlumnos(alumnos);
                pnlSecundario.getChildren().add(root);
                PantallaPrincipalDirectorController.animacionCargarPantalla(pnlSecundario);
                if (pantallaDividida.getChildren().size() > 1) {
                    pantallaDividida.getChildren().remove(1);
                }
                pantallaDividida.getChildren().add(pnlSecundario);
            } catch (IOException ex) {
                Logger.getLogger(TarjetaInformacionGrupoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
