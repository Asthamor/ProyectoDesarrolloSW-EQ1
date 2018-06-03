/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import interfaces.Controlador;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXListView;
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Alumno;
import modelo.Grupo;
import modelo.Maestro;
import modelo.Persona;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author mauricio
 */
public class PantallaBajaInscripcionController implements Initializable, Controlador {

    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    @FXML
    private JFXListView<String> lstAlumnos;
    private ArrayList<String> nombresAlumnos;
    private List<Alumno> alumnos;
    @FXML
    private JFXListView<String> lstGrupo;
    private ArrayList<String> nombresGrupos;
    private List<Grupo> grupos;

    private Persona persona;
    private Usuario usuario;
    @FXML
    private ImageView imgAlumno;
    @FXML
    private Label lblAlumno;
    @FXML
    private Label lblTelefono;
    @FXML
    private Label lblCorreo;
    @FXML
    private JFXButton btnDarBaja;

    public boolean setUserData() {
        String nombreUsuario = System.getProperty("nombreUsuario");
        usuario = new Usuario();
        usuario = usuario.buscar(nombreUsuario);
        if (usuario.getTipoUsuario().equals("maestro")) {
            this.persona = (Maestro) usuario.getMaestroCollection().toArray()[0];
        }
        if (usuario.getTipoUsuario().equals("director")) {
            this.persona = (Maestro) usuario.getMaestroCollection().toArray()[0];
        }
        return true;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUserData();
        setListGrupos();
        lstGrupo.setExpanded(true);
        lstGrupo.depthProperty().set(1);
        lstAlumnos.setExpanded(true);
        lstAlumnos.depthProperty().set(1);
    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    private boolean setListGrupos() {
        Grupo g = new Grupo();
        nombresGrupos = new ArrayList();
        grupos = g.obtenerTodosLosGrupos();
        grupos.forEach((grupo) -> {
            nombresGrupos.add(grupo.getNombre());
        });
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(nombresGrupos);
        lstGrupo.setItems(items);
        lstGrupo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lstAlumnos.setItems(null);
                nombresAlumnos = new ArrayList();
                int grpIndex = lstGrupo.getSelectionModel().getSelectedIndex();
                if (grpIndex != -1) {
                    alumnos = new ArrayList(grupos.get(grpIndex).getAlumnoCollection());
                    alumnos.forEach((alumno) -> {
                        nombresAlumnos.add(alumno.getNombre() + " " + alumno.getApellidos());
                    });
                    ObservableList<String> items = FXCollections.observableArrayList();
                    items.addAll(nombresAlumnos);
                    lstAlumnos.setItems(items);
                    lstAlumnos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            if (lstAlumnos.getSelectionModel().getSelectedIndex() != -1) {
                                Alumno alumn = alumnos.get(lstAlumnos.getSelectionModel().getSelectedIndex());
                                lblAlumno.setText(alumn.getNombre() + " " + alumn.getApellidos());
                                lblCorreo.setText(alumn.getEmail());
                                lblTelefono.setText(alumn.getTelefono());

                                imgAlumno.setImage(new Image("file:" + alumn.obtenerImagen(), true));

                            }
                        }

                    });

                }
            }
        });
        return true;
    }

    @FXML
    private void darBaja(ActionEvent event) {
        int grpIndex = lstGrupo.getSelectionModel().getSelectedIndex();
        int almIndex = lstAlumnos.getSelectionModel().getSelectedIndex();
        if (almIndex != -1 && grpIndex != -1) {
            Grupo grupo = grupos.get(grpIndex);
            Alumno alumn = alumnos.get(almIndex);
            grupo.getAlumnoCollection().remove(alumn);
            grupo.actualizarDatosGrupo(grupo);
            setListGrupos();
            Mensajes.mensajeExitoso(alumn.getNombre() + " " + alumn.getApellidos()
                    + " se ha eliminado del grupo " + grupo.getNombre());
        } else if (almIndex == -1) {
            Mensajes.mensajeAlert("Selecciona un alumno");
        } else if (grpIndex == -1) {
            Mensajes.mensajeAlert("Selecciona un grupo");
        }
    }
}
