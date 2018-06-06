/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.Mensajes;
import clasesApoyo.Recibo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import interfaces.Controlador;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import modelo.Cliente;
import modelo.Ingreso;
import modelo.Maestro;
import modelo.PagoAlumnoExterno;
import modelo.PagoMaestro;
import modelo.PagoRenta;
import modelo.Persona;
import modelo.Usuario;
import org.controlsfx.control.Notifications;
import org.dom4j.DocumentException;

/**
 * FXML Controller class
 *
 * @author mau
 */
public class PantallaConsultarIngresosController implements Initializable, Controlador {

  @FXML
  private Label lblPagos;
  @FXML
  private TableView<Ingreso> tbPagos;
  @FXML
  private Label lblPagosAlumnos;
  @FXML
  private JFXTextField txtBuscarPAlumno;
  @FXML
  private TableView<PagoAlumnoExterno> tbPagosAlumnos;
  @FXML
  private JFXButton btnGenerarRecibo;

  private List<Ingreso> datosIngresos;
  private ObservableList<Ingreso> ingresos;

  private List<PagoAlumnoExterno> datosPagoAlumnos;
  private ObservableList<PagoAlumnoExterno> pagosAlumnos;

  private HBox pantallaDividida;
  private StackPane pnlPrincipal;

  private Persona persona;
  private Usuario usuario;
  @FXML
  private TableColumn<Ingreso, Date> tcFecha;
  @FXML
  private TableColumn<Ingreso, String> tcTipo;
  @FXML
  private TableColumn<Ingreso, Double> tcMonto;
  @FXML
  private TableColumn<Ingreso, String> tcNombre;
  @FXML
  private JFXComboBox<String> cbFiltrarPago;
  @FXML
  private JFXComboBox<String> cbFiltrarPA;
  @FXML
  private TableColumn<PagoAlumnoExterno, String> tcAlumno;
  @FXML
  private TableColumn<PagoAlumnoExterno, String> tcMaestro;
  @FXML
  private TableColumn<PagoAlumnoExterno, Date> tcFechaPaAlumn;
  @FXML
  private TableColumn<PagoAlumnoExterno, Double> tcMontoPaAlumn;
  @FXML
  private TableColumn<PagoAlumnoExterno, PagoAlumnoExterno> tcEliminar;
  @FXML
  private JFXTextField txtBuscarPago;

  private Object selectedIndex;
  @FXML
  private StackPane stack;
  @FXML
  private JFXButton btnEstadisticas;
  @FXML
  private StackPane pnlTabla1;
  @FXML
  private StackPane pnlTabla2;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    setCurrencyFormatters();
    setDateFormatter();
    tbPagos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    tbPagosAlumnos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    pnlTabla1.getStyleClass().add("panel");
    pnlTabla2.getStyleClass().add("panel");
    obtenerPagos();
    obtenerPagosAlumnos();

    tbPagos.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
              if (newSelection != null) {
                selectedIndex = tbPagos.getSelectionModel().getSelectedItem();
              }
            });

    tbPagosAlumnos.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
              if (newSelection != null) {
                selectedIndex = tbPagosAlumnos.getSelectionModel().getSelectedItem();
              }
            });

  }

  private void setDateFormatter() {
    DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.MEDIUM);

    tcFecha.setCellFactory(column -> {
      return new TableCell<Ingreso, Date>() {
        @Override
        protected void updateItem(Date item, boolean empty) {
          super.updateItem(item, empty);

          if (item == null || empty) {
            setText("");
            setStyle("");
          } else {
            // Formato de fecha
            Instant instant = Instant.ofEpochMilli(item.getTime());
            setText(formatter.format(
                    LocalDateTime.ofInstant(instant, ZoneId.systemDefault())));
            setStyle("");

          }
        }
      };
    });

    tcFechaPaAlumn.setCellFactory(column -> {
      return new TableCell<PagoAlumnoExterno, Date>() {
        @Override
        protected void updateItem(Date item, boolean empty) {
          super.updateItem(item, empty);

          if (item == null || empty) {
            setText("");
            setStyle("");
          } else {
            // Formato de fecha
            Instant instant = Instant.ofEpochMilli(item.getTime());
            setText(formatter.format(
                    LocalDateTime.ofInstant(instant, ZoneId.systemDefault())));
            setStyle("");

          }
        }
      };
    });
    
  }

  private void setCurrencyFormatters() {
    tcMonto.setCellFactory(column -> {
      return new TableCell<Ingreso, Double>() {
        @Override
        protected void updateItem(Double item, boolean empty) {
          super.updateItem(item, empty);

          if (item == null || empty) {
            setText("");
            setStyle("");
          } else {
            setText("$" + item);
            setStyle("");

          }
        }
      };
    });

    tcMontoPaAlumn.setCellFactory(column -> {
      return new TableCell<PagoAlumnoExterno, Double>() {
        @Override
        protected void updateItem(Double item, boolean empty) {
          super.updateItem(item, empty);

          if (item == null || empty) {
            setText(null);
            setStyle("");
          } else {
            setText("$" + item);
            setStyle("");

          }
        }
      };
    });

  }

  private void obtenerPagos() {
    tcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
    tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    tcMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));

    PagoMaestro pm = new PagoMaestro();
    PagoRenta pr = new PagoRenta();
    ingresos = tbPagos.getItems();

    datosIngresos = new ArrayList();
    datosIngresos.addAll(pm.obtenerTodos());
    datosIngresos.addAll(pr.obtenerTodos());
    ingresos.addAll(datosIngresos);
    tbPagos.setItems(ingresos);
  }

  private void obtenerPagosAlumnos() {
    tcAlumno.setCellValueFactory(new PropertyValueFactory<>("alumno"));
    tcMaestro.setCellValueFactory(new PropertyValueFactory<>("maestro"));
    tcFechaPaAlumn.setCellValueFactory(new PropertyValueFactory("fecha"));
    tcMontoPaAlumn.setCellValueFactory(new PropertyValueFactory<>("monto"));
    tcEliminar.setCellValueFactory(
            param -> new ReadOnlyObjectWrapper<>(param.getValue())
    );

    tcEliminar.setCellFactory(param -> new TableCell<PagoAlumnoExterno, PagoAlumnoExterno>() {
      private final JFXButton delete = new JFXButton("", GlyphsDude.createIcon(FontAwesomeIcon.TRASH, "15px"));

      @Override
      protected void updateItem(PagoAlumnoExterno item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null) {
          setGraphic(null);
          return;
        }

        setGraphic(delete);
        delete.setOnAction(event -> {
          PagoAlumnoExterno seleccion = item;
          ButtonType respuesta = Mensajes.dialogoConfirmacion("Confirmar eliminar",
                  "Desea eliminar el registro\n"
                  + ((PagoAlumnoExterno) item).toString());
          
          if (respuesta.getButtonData() == ButtonType.APPLY.getButtonData()) {
            if (item.eliminar()) {
              getTableView().getItems().remove(item);
              datosPagoAlumnos.remove(seleccion);
              Notifications.create()
                .title("¡Exito!")
                .text("Registro de pago eliminado")
                .showInformation();
            }
          }

        });
      }
    });

    PagoAlumnoExterno pae = new PagoAlumnoExterno();
    datosPagoAlumnos = pae.obtenerTodos();

    pagosAlumnos = tbPagosAlumnos.getItems();
    pagosAlumnos.addAll(datosPagoAlumnos);
    tbPagosAlumnos.setItems(pagosAlumnos);
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
  private void buscarPagos(KeyEvent event) {
    String busqueda = txtBuscarPago.getText().toLowerCase(Locale.getDefault());
    if (!busqueda.isEmpty()) {
      ingresos.clear();
      for (Ingreso ing : datosIngresos) {
        if (ing.getNombre().toLowerCase().contains(busqueda)) {
          ingresos.add(ing);
        }
      }
      tbPagos.setItems(ingresos);
    } else {
      ingresos.clear();
      ingresos.addAll(datosIngresos);
      tbPagos.setItems(ingresos);
    }
  }

  @FXML
  private void buscarPAE(KeyEvent event) {
    String busqueda = txtBuscarPAlumno.getText().toLowerCase(Locale.getDefault());
    if (!busqueda.isEmpty()) {
      pagosAlumnos.clear();
      datosPagoAlumnos.stream().filter((pae)
              -> (pae.getAlumno().toString().toLowerCase().contains(busqueda)
              || pae.getMaestro().toString().toLowerCase().contains(busqueda)))
              .forEachOrdered((pae) -> {
                pagosAlumnos.add(pae);
              });
      tbPagosAlumnos.setItems(pagosAlumnos);
    } else {
      pagosAlumnos.clear();
      pagosAlumnos.addAll(datosPagoAlumnos);
      tbPagosAlumnos.setItems(pagosAlumnos);
    }
  }

  @FXML
  private void generarReciboAction(ActionEvent event) {
    Maestro director = new Maestro();
    director = director.obtenerMaestro(System.getProperty("nombreUsuario"));
    File recibo = null;
    FileChooser fchooser = new FileChooser();
    fchooser.setInitialDirectory(new File(System.getProperty("user.home")));
    fchooser.setTitle("Guardar Recibo");

    if (selectedIndex != null) {
      if (selectedIndex.getClass().equals(PagoAlumnoExterno.class)) {
        PagoAlumnoExterno pae = (PagoAlumnoExterno) selectedIndex;
        try {
          recibo = Recibo.crearReciboPagoAlumnoExterno(
                  System.getProperty("user.dir"), "recibotemp", pae, pae.getAlumno(), director);
        } catch (IOException ex) {
          Logger.getLogger(PantallaConsultarIngresosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        File destino = fchooser.showSaveDialog(btnGenerarRecibo.getScene().getWindow());
        if (destino != null && recibo != null) {
          try {
            Files.copy(recibo.toPath(), destino.toPath(), REPLACE_EXISTING);
          } catch (IOException ex) {
            Logger.getLogger(PantallaConsultarIngresosController.class.getName()).log(Level.SEVERE, null, ex);
          }
        }

      } else {
        if (selectedIndex.getClass().equals(PagoRenta.class)) {
          PagoRenta pr = (PagoRenta) selectedIndex;

          try {
            recibo = Recibo.crearReciboPagoIngreso(
                    System.getProperty("user.dir"), "recibotemp", pr, pr.getCliente(), director);
          } catch (DocumentException | IOException ex) {
            Logger.getLogger(PantallaConsultarIngresosController.class.getName()).log(Level.SEVERE, null, ex);
          }
          File destino = fchooser.showSaveDialog(btnGenerarRecibo.getScene().getWindow());
          if (destino != null && recibo != null) {
            try {
              Files.copy(recibo.toPath(), destino.toPath(), REPLACE_EXISTING);
            } catch (IOException ex) {
              Logger.getLogger(PantallaConsultarIngresosController.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }
        if (selectedIndex.getClass().equals(PagoMaestro.class)) {
          PagoMaestro pm = (PagoMaestro) selectedIndex;
          try {
            recibo = Recibo.crearReciboPagoIngreso(
                    System.getProperty("user.dir"), "recibotemp", pm, pm.getMaestro(), director);
          } catch (DocumentException | IOException ex) {
            Logger.getLogger(PantallaConsultarIngresosController.class.getName()).log(Level.SEVERE, null, ex);
          }
          File destino = fchooser.showSaveDialog(btnGenerarRecibo.getScene().getWindow());
          if (destino != null && recibo != null) {
            try {
              Files.copy(recibo.toPath(), destino.toPath(), REPLACE_EXISTING);
            } catch (IOException ex) {
              Logger.getLogger(PantallaConsultarIngresosController.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }
      }
    }
  }

  @FXML
  private void mostrarEstadisticas(ActionEvent event) {
    PantallaPrincipalDirectorController.limpiarPanelPrincipal(pnlPrincipal, pantallaDividida);
    Parent root = null;
    FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/PantallaEstadisticas.fxml"));
    try {
      root = (Parent) loader.load();
    } catch (IOException ex) {
      Logger.getLogger(PantallaPrincipalDirectorController.class.getName()).log(Level.SEVERE, null, ex);
    }
    PantallaEstadisticasController controlador = loader.getController();
    controlador.setPantallaDividida(pantallaDividida);
    controlador.setPnlPrincipal(pnlPrincipal);
    pnlPrincipal.getChildren().add(root);
    pantallaDividida.getChildren().add(pnlPrincipal);
  }
}
