/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.JFXLimitedTextField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import interfaces.Controlador;
import java.net.URL;
import java.time.LocalDate;
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
import java.text.DateFormat;
import java.time.ZoneId;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import static jdk.nashorn.tools.ShellFunctions.input;
import modelo.Alumno;
import modelo.Grupo;
import modelo.Maestro;
import modelo.PagoAlumno;
import modelo.Promocion;
import modelo.Usuario;
import org.controlsfx.control.Notifications;

/**
 *
 * @author alonso
 */
public class PantallaReinscripcionController implements Initializable, Controlador {

    @FXML
    private JFXListView<String> lstGrupo;
    @FXML
    private JFXListView<String> lstAlumnos;
    @FXML
    private JFXLimitedTextField txtMonto;
    @FXML
    private JFXComboBox<String> comboPromocion;
    @FXML
    private Label labelMontoFinal;
    @FXML
    private JFXLimitedTextField lblNombreAlumno;
    @FXML
    private JFXButton btnReinscribir;

    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private Maestro maestro;
    private Usuario usuario;
    private ArrayList<String> nombresAlumnos;
    private List<Alumno> alumnos;
    private ArrayList<String> nombresGrupos;
    private List<Grupo> grupos;
    private ArrayList<Promocion> promociones;
    private ArrayList<String> nombrePromociones;
    private int montoFinal;

    public boolean setUserData() {
        String nombreUsuario = System.getProperty("nombreUsuario");
        usuario = new Usuario();
        usuario = usuario.buscar(nombreUsuario);
        if (usuario.getTipoUsuario().equals("maestro")) {
            this.maestro = (Maestro) usuario.getMaestroCollection().toArray()[0];
        }
        if (usuario.getTipoUsuario().equals("director")) {
            this.maestro = (Maestro) usuario.getMaestroCollection().toArray()[0];
        }
        return true;
    }

    public void obtenerPromociones() {
        promociones = new ArrayList();
        nombrePromociones = new ArrayList();
        Promocion promocionAux = new Promocion();
        List<Promocion> promocionesTotales = promocionAux.obtenerPromociones();
        List<Promocion> promocionesAux = new ArrayList(maestro.getPromocionCollection());
        for (Promocion promocion : promocionesAux) {
            if (promocion.getParaInscripcion() == 1) {
                if (promocion.getPromocionPK().getIdPromocion() == 0) {
                    promocion.getPromocionPK().setIdPromocion(promocionesTotales.size());
                }
                promociones.add(promocion);
            }
        }
        nombrePromociones.add("Ninguna");
        if (!promociones.isEmpty()) {
            promociones.forEach((promocion) -> {
                nombrePromociones.add(promocion.getCodigo());
            });
        }
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(nombrePromociones);
        comboPromocion.setItems(items);
        comboPromocion.getSelectionModel().select(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblNombreAlumno.setText("");
        ValidatorBase validatorHorario = new RequiredFieldValidator();
        validatorHorario.setMessage("Campo requerido");
        lblNombreAlumno.setValidators(validatorHorario);
        lstGrupo.setExpanded(true);
        lstGrupo.depthProperty().set(1);
        lstAlumnos.setExpanded(true);
        lstAlumnos.depthProperty().set(1);
        setUserData();
        setListGrupos();
        txtMonto.setRequired(true);
        txtMonto.setCurrencyFilter();
        txtMonto.setText("$");
    }

    private boolean setListGrupos() {
        Grupo g = new Grupo();
        nombresGrupos = new ArrayList();
        if (usuario.getTipoUsuario().equals("director")) {
            obtenerPromociones();
            grupos = g.obtenerTodosLosGrupos();
        } else {
            obtenerPromociones();
            grupos = g.obtenerGruposMaestro(maestro.getMaestroPK().getIdMaestro());
        }

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
                                lblNombreAlumno.setText(alumn.getNombre() + " " + alumn.getApellidos());
                            }
                        }

                    });
                }
            }
        });
        return true;
    }

    @Override

    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    @FXML
    private void reinscribir(ActionEvent event) {

        if (!existenCamposVacios()) {
            int almIndex = lstAlumnos.getSelectionModel().getSelectedIndex();
            Alumno alumno = alumnos.get(almIndex);
            int grpIndex = lstGrupo.getSelectionModel().getSelectedIndex();
            Grupo grupo = grupos.get(grpIndex);
            PagoAlumno pagoAlumno = new PagoAlumno();
            pagoAlumno = pagoAlumno.obtenerUltimoPago(alumno.getIdAlumno(),grupo.getGrupoPK().getIdGrupo());
            if (esInscripcionATiempo(pagoAlumno)) {
                guardarReinscripcion(false, null);
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("El pago de inscripción se debe hacer el : " + DateFormat.getDateInstance().format(pagoAlumno.getFechaVencimiento()) + "\n"
                        + "\n ¿Seguro que desea continuar?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    guardarReinscripcion(true, pagoAlumno.getFechaVencimiento());
                }
            }
        }
    }

    public void guardarReinscripcion(boolean esAdelantada, Date fechaVencimiento) {
        int grpIndex = lstGrupo.getSelectionModel().getSelectedIndex();
        int almIndex = lstAlumnos.getSelectionModel().getSelectedIndex();
        Grupo grupo = grupos.get(grpIndex);
        Alumno alumn = alumnos.get(almIndex);
        PagoAlumno pago = new PagoAlumno();
        pago.setAlumno(alumn);
        pago.setGrupo(grupo);
        if (comboPromocion.getSelectionModel().getSelectedIndex() != 0) {
            pago.setPromocion(promociones.get(comboPromocion.getSelectionModel().getSelectedIndex() - 1));
        }
        pago.setEsInscripcion(true);
        pago.setMonto(montoFinal);
        pago.setFechaPago(new Date());
        Date vence = null;
        if (esAdelantada) {
            LocalDate date = fechaVencimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            vence = java.sql.Date.valueOf(date.plusYears(1));
        } else {
            vence = java.sql.Date.valueOf(LocalDate.now().plusYears(1));
        }

        pago.setFechaVencimiento(vence);
        pago.registrarPago();
        setListGrupos();
        Notifications.create()
                .title("¡Exito!")
                .text(alumn.getNombre() + " " + alumn.getApellidos()
                        + " reinscrito en el grupo " + grupo.getNombre())
                .showInformation();
        txtMonto.setText("$");
        lblNombreAlumno.setText("");
        labelMontoFinal.setText("$");
    }

    public boolean esInscripcionATiempo(PagoAlumno pagoAlumno) {
        boolean bandera = true;
        if (pagoAlumno.getFechaVencimiento().after(new Date())) {
            bandera = false;
        }
        return bandera;
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
        return !lblNombreAlumno.validate()
                | huboErrores;
    }

    @FXML
    private void aplicarPromocion(ActionEvent event) {
        labelMontoFinal.setText("");
        if (!txtMonto.getText().replace("$", "").equals("")) {
            if (comboPromocion.getSelectionModel().getSelectedIndex() == 0) {
                montoFinal = Integer.parseInt(txtMonto.getText().replace("$", ""));
                labelMontoFinal.setText("$ " + txtMonto.getText().replace("$", ""));
            } else {
                try {
                    montoFinal = (int) (Integer.parseInt(txtMonto.getText().replace("$", "")) - ((Integer.parseInt(txtMonto.getText().replace("$", "")))
                            * (promociones.get(comboPromocion.getSelectionModel().getSelectedIndex() - 1).getDescuento() * .01)));
                    labelMontoFinal.setText("$ " + String.valueOf(montoFinal));
                } catch (ArrayIndexOutOfBoundsException exception) {
                }
            }
        }
    }

    @FXML
    private void añadirTotal(KeyEvent event) {
        aplicarPromocion(null);
    }
}
