/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.JFXLimitedTextField;
import clasesApoyo.Recibo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import static controladores.PantallaPrincipalDirectorController.limpiarPanelPrincipal;
import interfaces.Controlador;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXListView;
import java.io.File;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import modelo.Alumno;
import modelo.Grupo;
import modelo.HistorialPagos;
import modelo.Maestro;
import modelo.PagoAlumno;
import modelo.Promocion;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author raymundo
 */
public class PantallaRegistrarPagoAlumnoController implements Initializable, Controlador {

    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private JFXLimitedTextField txtMonto;
    @FXML
    private JFXListView<String> lstAlumnos;
    @FXML
    private JFXComboBox<String> cboxPromocion;
    @FXML
    private Label lblGrupo;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblMontoTotal;
    @FXML
    private JFXLimitedTextField lblAlumno;
    @FXML
    private TableView<HistorialPagos> tbPagos;
    @FXML
    private TableColumn<HistorialPagos, String> colFecha;
    @FXML
    private TableColumn<HistorialPagos, String> colMonto;
    @FXML
    private TableColumn<HistorialPagos, String> colPromocion;
    @FXML
    private StackPane pnlTabla;
    @FXML
    private Label lblFechaProximoPago;

    private Double montoFinal;
    private Grupo grupo;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private List<Grupo> grupos;
    private List<PagoAlumno> pagos;
    private List<HistorialPagos> historialesPagos;
    private Maestro maestro;
    private ArrayList<String> nombresAlumnos;
    private ArrayList<String> nombrePromociones;
    private List<Alumno> alumnos;
    private List<Promocion> promociones;
    @FXML
    private TableColumn<HistorialPagos, String> colTipo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstAlumnos.setExpanded(true);
        lstAlumnos.depthProperty().set(1);
        pnlTabla.getStyleClass().add("panel");
        tbPagos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nombresAlumnos = new ArrayList();
        txtMonto.setRequired(true);
        txtMonto.setCurrencyFilter();
        txtMonto.setText("$");
        ValidatorBase validatorHorario = new RequiredFieldValidator();
        validatorHorario.setMessage("Campo requerido");
        lblAlumno.setValidators(validatorHorario);
        maestro = new Maestro();
        String nombreUsuario = System.getProperty("nombreUsuario");
        maestro = maestro.obtenerMaestro(nombreUsuario);
        nombrePromociones = new ArrayList();
        promociones = new ArrayList();
        Promocion promocionAux = new Promocion();
        List<Promocion> promocionesTotales = promocionAux.obtenerPromociones();
        List<Promocion> promocionesAux = new ArrayList(maestro.getPromocionCollection());
        for (Promocion promocion : promocionesAux) {
            if (promocion.getParaInscripcion() == 0) {
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
        cboxPromocion.setItems(items);
        cboxPromocion.getSelectionModel().select(0);
        tbPagos.setEditable(false);
    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
        mostrarAlumnos();
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void mostrarHistorial() {
        colMonto.setCellValueFactory(new PropertyValueFactory<HistorialPagos, String>("monto"));
        colFecha.setCellValueFactory(new PropertyValueFactory<HistorialPagos, String>("fechaPago"));
        colPromocion.setCellValueFactory(new PropertyValueFactory<HistorialPagos, String>("nombrePromocion"));
        colTipo.setCellValueFactory(new PropertyValueFactory<HistorialPagos, String>("tipo"));
        tbPagos.setItems(FXCollections.observableArrayList(historialesPagos));
    }

    public void mostrarAlumnos() {
        grupos = maestro.obtenerGruposMaestro(maestro.getMaestroPK().getIdMaestro());
        for (Grupo grupoAux : grupos) {
            if (grupoAux.getGrupoPK().getIdGrupo() == grupo.getGrupoPK().getIdGrupo()) {
                grupo = grupoAux;
            }
        }
        alumnos = new ArrayList(grupo.getAlumnoCollection());
        alumnos.forEach((alumno) -> {
            nombresAlumnos.add(alumno.getNombre() + " " + alumno.getApellidos());
        });
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(nombresAlumnos);
        lstAlumnos.setItems(items);
        lstAlumnos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                historialesPagos = new ArrayList();
                if (lstAlumnos.getSelectionModel().getSelectedIndex() != -1) {
                    PagoAlumno pagoAlumno = new PagoAlumno();
                    pagos = pagoAlumno.obtenerPagosAlumno(alumnos.get(lstAlumnos.getSelectionModel().getSelectedIndex()).getIdAlumno());
                    for (int i = pagos.size() - 1; i >= 0; i--) {
                        if (pagos.get(i).getGrupo().getGrupoPK().getIdGrupo() == grupo.getGrupoPK().getIdGrupo()) {
                            HistorialPagos historial = new HistorialPagos();
                            historial.setFechaPago(DateFormat.getDateInstance().format(pagos.get(i).getFechaPago()));
                            if (pagos.get(i).getEsInscripcion()) {
                                historial.setTipo("Inscripción");
                            } else {
                                historial.setTipo("Mensualidad");
                            }
                            historial.setMonto("$" + String.valueOf(pagos.get(i).getMonto()));
                            if (pagos.get(i).getPromocion() != null) {
                                historial.setNombrePromocion(pagos.get(i).getPromocion().getCodigo());
                            } else {
                                historial.setNombrePromocion("Ninguna");
                            }
                            historialesPagos.add(historial);
                        }
                    }
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
                    if (ultimoPago == null) {
                        lblFecha.setText("Primer pago");
                        lblFechaProximoPago.setText("Primer pago");
                    } else {
                        lblFecha.setText(DateFormat.getDateInstance().format(ultimoPago.getFechaPago()));
                        lblFechaProximoPago.setText(DateFormat.getDateInstance().format(ultimoPago.getFechaVencimiento()));
                    }
                    lblGrupo.setText(grupo.getNombre());
                    lblAlumno.setText(alumnos.get(lstAlumnos.getSelectionModel().getSelectedIndex()).getNombre() + " " + alumnos.get(lstAlumnos.getSelectionModel().getSelectedIndex()).getApellidos());
                    mostrarHistorial();
                }
            }

        });

    }

    @FXML
    private void añadirTotal(KeyEvent event) {
        lblMontoTotal.setText("");
        if (!txtMonto.getText().replace("$", "").equals("")) {
            if (cboxPromocion.getSelectionModel().getSelectedIndex() == 0) {
                montoFinal = Double.valueOf(txtMonto.getText().replace("$", ""));
                lblMontoTotal.setText(txtMonto.getText().replace("$", ""));
            } else {
                try {
                    montoFinal = (Double.valueOf(txtMonto.getText().replace("$", "")) - ((Double.valueOf(txtMonto.getText().replace("$", "")))
                            * (promociones.get(cboxPromocion.getSelectionModel().getSelectedIndex() - 1).getDescuento() * .01)));
                    lblMontoTotal.setText(String.valueOf(montoFinal));
                } catch (NumberFormatException exception) {
                }
            }
        }

    }

    @FXML
    private void registrarAlumno(ActionEvent event) {
        boolean bandera = false;
        if (!existenCamposVacios()) {
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(new Date());
            calendario.add(Calendar.MONTH, 1);
            Date fechaVencimiento = calendario.getTime();
            List<PagoAlumno> pagos = new ArrayList<>(alumnos.get(lstAlumnos.getSelectionModel().getSelectedIndex()).getPagoAlumnoCollection());
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
            Date fechaActualPago;
            if (ultimoPago == null) {
                fechaActualPago = new Date();
            } else {
                fechaActualPago = ultimoPago.getFechaVencimiento();
            }
            Date fechaActual = new Date();
            if (fechaActualPago.getTime() > fechaActual.getTime()) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Atención");
                alert.setHeaderText("Atención");
                alert.setContentText("El ultimo pago se realizó el: " + DateFormat.getDateInstance().format(ultimoPago.getFechaPago()) + ",\n"
                        + "el proximo pago se debe realizar el: " + DateFormat.getDateInstance().format(fechaActualPago) + "\n ¿Seguro que desea continuar?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    bandera = false;
                    calendario.setTime(fechaActualPago);
                    calendario.add(Calendar.MONTH, 1);
                    fechaVencimiento = calendario.getTime();
                } else {
                    bandera = true;
                }

            }
            if (bandera != true) {
                PagoAlumno pagoAlumno = new PagoAlumno();
                pagoAlumno.setAlumno(alumnos.get(lstAlumnos.getSelectionModel().getSelectedIndex()));
                pagoAlumno.setGrupo(grupo);
                pagoAlumno.setEsInscripcion(false);
                pagoAlumno.setFechaPago(new Date());
                if (cboxPromocion.getSelectionModel().getSelectedIndex() != 0) {
                    pagoAlumno.setPromocion(promociones.get(cboxPromocion.getSelectionModel().getSelectedIndex() - 1));
                }
                pagoAlumno.setFechaVencimiento(fechaVencimiento);
                pagoAlumno.setMonto(montoFinal);
                if (pagoAlumno.registrarPagoMensual(pagoAlumno)) {
                    File recibo = null;
                    FileChooser fchooser = new FileChooser();
                    fchooser.setSelectedExtensionFilter(new ExtensionFilter("Archivos pdf","*.pdf"));
                    fchooser.setInitialDirectory(new File(System.getProperty("user.home")));
                    fchooser.setInitialFileName("pago "+pagoAlumno.getAlumno().toString()+".pdf");
                    fchooser.setTitle("Guardar Recibo");
                    try {
                        recibo = Recibo.crearReciboPagoAlumno(
                                System.getProperty("user.home")+"/.ared/", "recibotemp",
                                pagoAlumno, pagoAlumno.getAlumno(), 
                                pagoAlumno.getGrupo().getMaestro());
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaConsultarIngresosController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    File destino = fchooser.showSaveDialog(btnRegistrar.getScene().getWindow());
                    if (destino != null && recibo != null) {
                        try {
                            Files.copy(recibo.toPath(), destino.toPath(), REPLACE_EXISTING);
                        } catch (IOException ex) {
                            Logger.getLogger(PantallaConsultarIngresosController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    limpiarPanelPrincipal(pnlPrincipal, pantallaDividida);
                    Parent root = null;
                    FXMLLoader loader = new FXMLLoader(PantallaRegistrarPagoAlumnoController.class.getResource("/fxml/PantallaRegistrarPagoAlumno.fxml"));
                    try {
                        root = (Parent) loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(PantallaRegistrarPagoAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    PantallaRegistrarPagoAlumnoController controlador = loader.getController();
                    controlador.setGrupo(grupo);
                    controlador.setPantallaDividida(pantallaDividida);
                    controlador.setPnlPrincipal(pnlPrincipal);
                    pnlPrincipal.getChildren().add(root);
                    pantallaDividida.getChildren().add(pnlPrincipal);
                    Notifications.create()
                            .title("¡Exito!")
                            .text("El pago se registro correctamente")
                            .showInformation();
                }
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
        return !lblAlumno.validate()
                | huboErrores;
    }

    public boolean tamañoInvalidoCaracteres() {
        return txtMonto.getText().length() > 11;
    }

    public boolean existenCamposErroneos() {
        return !txtMonto.validate();
    }

    @FXML
    private void limpiarMonto(ActionEvent event) {
        añadirTotal(null);
    }

}
