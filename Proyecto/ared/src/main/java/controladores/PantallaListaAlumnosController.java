/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import static controladores.PantallaPrincipalDirectorController.limpiarPanelPrincipal;
import interfaces.Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import com.jfoenix.controls.JFXListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Alumno;
import modelo.Grupo;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaListaAlumnosController implements Initializable, Controlador {

    @FXML
    private JFXListView<String> tbAlumnos;
    private List<Alumno> alumnos;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private Grupo grupo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbAlumnos.setExpanded(true);
        tbAlumnos.depthProperty().set(1);
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
        mostrarAlumnos();
    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void mostrarAlumnos() {
        ArrayList<String> nombresAlumnos = new ArrayList();
        for (Alumno alumno : alumnos) {
            nombresAlumnos.add(alumno.getNombre() + " " + alumno.getApellidos());
        }
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(nombresAlumnos);
        tbAlumnos.setItems(items);
    }

    @FXML
    private void realizarPago(ActionEvent event) {
        limpiarPanelPrincipal(pnlPrincipal, pantallaDividida);
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaListaAlumnosController.class.getResource("/fxml/PantallaRegistrarPagoAlumno.fxml"));
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PantallaListaAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PantallaRegistrarPagoAlumnoController controlador = loader.getController();
        controlador.setGrupo(grupo);
        controlador.setPantallaDividida(pantallaDividida);
        controlador.setPnlPrincipal(pnlPrincipal);

        pnlPrincipal.getChildren().add(root);
        pantallaDividida.getChildren().add(pnlPrincipal);
    }

    @FXML
    private void tomarAsistencia(ActionEvent event) {
        limpiarPanelPrincipal(pnlPrincipal, pantallaDividida);
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaListaAlumnosController.class.getResource("/fxml/PantallaAdministrarAsistencia.fxml"));
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PantallaListaAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PantallaAdministrarAsistenciaController controlador = loader.getController();
        controlador.setGrupo(grupo);
        controlador.setPantallaDividida(pantallaDividida);
        controlador.setPnlPrincipal(pnlPrincipal);

        pnlPrincipal.getChildren().add(root);
        pantallaDividida.getChildren().add(pnlPrincipal);
    }
}
