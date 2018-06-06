/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.JFXLimitedTextField;
import interfaces.Controlador;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Alumno;
import modelo.Grupo;
import modelo.Maestro;
import modelo.PagoAlumno;
import modelo.PagoAlumnoExterno;
import modelo.Persona;
import org.controlsfx.control.Notifications;
import static controladores.PantallaGruposController.obtenerGruposActivos;

/**
 * FXML Controller class
 *
 * @author raymundo
 */
public class PantallaRegistraPagoAlumnoExternoController implements Initializable, Controlador {

    @FXML
    private JFXListView<String> lstColaboradores;
    @FXML
    private JFXListView<String> lstGrupos;
    @FXML
    private JFXListView<String> lstAlumno;
    @FXML
    private JFXLimitedTextField labelAlumno;
    @FXML
    private Label labelColaborador;
    @FXML
    private Label labelFecha;
    @FXML
    private JFXLimitedTextField labelGrupo;
    @FXML
    private JFXLimitedTextField txtMonto;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private ArrayList<String> nombresGrupos;
    private ArrayList<String> nombresColaboradores;
    private ArrayList<String> nombresAlumnos;
    private List<Alumno> alumnos;
    private List<Grupo> grupos;
    private List<Persona> maestros;
    private Maestro maestro;
    private StackPane pnlPagos;
    @FXML
    private Label labelFechaProximoPago;
    @FXML
    private Label lblFechaProximo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstAlumno.setExpanded(true);
        lstAlumno.depthProperty().set(1);
        lstColaboradores.setExpanded(true);
        lstColaboradores.depthProperty().set(1);
        lstGrupos.setExpanded(true);
        lstGrupos.depthProperty().set(1);
        nombresColaboradores = new ArrayList();
        crearValidaciones();
    }

    public void crearValidaciones() {
        labelAlumno.setRequired(true);
        labelGrupo.setRequired(true);
        txtMonto.setRequired(true);
        txtMonto.setCurrencyFilter();
        txtMonto.setText("$");
    }

    public void setPnlPagos(StackPane pnlPagos) {
        this.pnlPagos = pnlPagos;
    }

    public void mostrarColaboradores() {
        labelFecha.setText(DateFormat.getDateInstance().format(new Date()));
        maestro = new Maestro();
        maestros = maestro.obtenerTodos();
        maestros.forEach((colaborador) -> {
            nombresColaboradores.add(colaborador.getNombre() + " " + colaborador.getApellidos());
        });
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(nombresColaboradores);
        lstColaboradores.setItems(items);
        lstColaboradores.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lstGrupos.setItems(null);
                labelFechaProximoPago.setText("");
                labelColaborador.setText("");
                labelGrupo.setText("");
                labelAlumno.setText("");
                nombresGrupos = new ArrayList();
                Maestro maestro = (Maestro) maestros.get(lstColaboradores.getSelectionModel().getSelectedIndex());
                if (maestro.getGrupoCollection().isEmpty()) {

                } else {
                    grupos = obtenerGruposActivos(new ArrayList(maestro.getGrupoCollection()));
                    grupos.forEach((grupo) -> {
                        nombresGrupos.add(grupo.getNombre());
                    });
                    ObservableList<String> items = FXCollections.observableArrayList();
                    items.addAll(nombresGrupos);
                    lstGrupos.setItems(items);

                    lstGrupos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            if (lstGrupos.getSelectionModel().getSelectedIndex() != -1) {
                                lstAlumno.setItems(null);
                                labelFechaProximoPago.setText("");
                                labelColaborador.setText("");
                                labelGrupo.setText("");
                                labelAlumno.setText("");
                                nombresAlumnos = new ArrayList();
                                Grupo grupo = grupos.get(lstGrupos.getSelectionModel().getSelectedIndex());
                                if (grupo.getAlumnoCollection().isEmpty()) {

                                } else {
                                    alumnos = new ArrayList(grupo.getAlumnoCollection());
                                    alumnos.forEach((alumno) -> {
                                        nombresAlumnos.add(alumno.getNombre() + " " + alumno.getApellidos());
                                    });
                                    ObservableList<String> items = FXCollections.observableArrayList();
                                    items.addAll(nombresAlumnos);
                                    lstAlumno.setItems(items);

                                    lstAlumno.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                                        @Override
                                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                            if (lstAlumno.getSelectionModel().getSelectedIndex() != -1) {
                                                List<PagoAlumno> pagos = new ArrayList<>(alumnos.get(lstAlumno.getSelectionModel().getSelectedIndex()).getPagoAlumnoCollection());
                                                PagoAlumno ultimoPago = null;
                                                PagoAlumno auxiliar;
                                                for (int i = pagos.size() - 1; i >= 0; i--) {
                                                    auxiliar = pagos.get(i);
                                                    if (auxiliar.getGrupo().getGrupoPK().getIdGrupo() == grupo.getGrupoPK().getIdGrupo()) {
                                                        if (!auxiliar.getEsInscripcion()) {
                                                            ultimoPago = pagos.get(i);
                                                            break;
                                                        }
                                                    }
                                                }                                                
                                                if(ultimoPago == null)
                                                    labelFechaProximoPago.setText("Primer pago");
                                                else
                                                    labelFechaProximoPago.setText(DateFormat.getDateInstance().format(ultimoPago.getFechaVencimiento()));
                                                
                                                labelColaborador.setText(maestros.get(lstColaboradores.getSelectionModel().getSelectedIndex()).getNombre() + " " + maestros.get(lstColaboradores.getSelectionModel().getSelectedIndex()).getApellidos());
                                                labelGrupo.setText(grupos.get(lstGrupos.getSelectionModel().getSelectedIndex()).getNombre());
                                                labelAlumno.setText(alumnos.get(lstAlumno.getSelectionModel().getSelectedIndex()).getNombre() + " " + alumnos.get(lstAlumno.getSelectionModel().getSelectedIndex()).getApellidos());
                                            }
                                        }

                                    });
                                }
                            }
                        }

                    });
                }
            }

        });

    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
        mostrarColaboradores();
    }

    @FXML
    private void registrarPago(ActionEvent event) {
        boolean bandera = false;
        if (!existenCamposVacios()) {
            PagoAlumnoExterno pagoAlumno = new PagoAlumnoExterno();
            pagoAlumno.setAlumno(alumnos.get(lstAlumno.getSelectionModel().getSelectedIndex()));
            pagoAlumno.setMaestro((Maestro) maestros.get(lstColaboradores.getSelectionModel().getSelectedIndex()));
            pagoAlumno.setFecha(new Date());
            pagoAlumno.setMonto(Double.valueOf(txtMonto.getText().replace("$", "")));
            if (pagoAlumno.registrarPago()) {
                pnlPagos.getChildren().clear();
                PantallaPrincipalDirectorController.animacionCargarPantalla(pnlPagos);
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/PantallaRegistraPagoAlumnoExterno.fxml"));
                try {
                    root = (Parent) loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(PantallaPrincipalDirectorController.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                PantallaRegistraPagoAlumnoExternoController controlador = loader.getController();
                controlador.setPantallaDividida(pantallaDividida);
                controlador.setPnlPrincipal(pnlPrincipal);
                controlador.setPnlPagos(pnlPagos);
                pnlPagos.getChildren().add(root);
                Notifications.create()
                        .title("¡Exito!")
                        .text("El pago del alumno se registro correctamente")
                        .showInformation();
            }
        }
    }

    public boolean existenCamposVacios() {
        boolean errorDeMonto = false;
        boolean huboErrores = false;

        if (txtMonto.getText().replace("$", "").trim().isEmpty()) {
            txtMonto.setTextFormatter(null);
            txtMonto.setText("");
            errorDeMonto = true;
            huboErrores = true;
        }
        if (!txtMonto.validate()) {
            huboErrores = true;
        }
        if (errorDeMonto) {
            txtMonto.setText("$");
            txtMonto.setCurrencyFilter();
        }
        return huboErrores | !labelAlumno.validate() | !labelGrupo.validate();
    }

    public boolean tamañoInvalidoCaracteres() {
        return txtMonto.getText().length() > 11;
    }

    public boolean existenCamposErroneos() {
        return !txtMonto.validate();
    }

}
