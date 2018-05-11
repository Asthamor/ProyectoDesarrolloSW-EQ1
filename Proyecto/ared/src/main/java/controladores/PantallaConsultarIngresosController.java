/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import interfaces.Controlador;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Ingreso;
import modelo.PagoAlumno;
import modelo.PagoAlumnoExterno;
import modelo.PagoMaestro;
import modelo.PagoRenta;
import modelo.Persona;
import modelo.Usuario;

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
  private TableColumn<Ingreso, Integer> tcMonto;
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
  private TableColumn<PagoAlumnoExterno, Integer> tcMontoPaAlumn;
  @FXML
  private TableColumn<PagoAlumnoExterno, PagoAlumnoExterno> tcEliminar;
  @FXML
  private JFXTextField txtBuscarPago;

  private Object selectedIndex;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    setCurrencyFormatters();
    setDateFormatter();

    obtenerPagos();
    obtenerPagosAlumnos();
    // TODO
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
            .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);

    tcFecha.setCellFactory(column -> {
      return new TableCell<Ingreso, Date>() {
        @Override
        protected void updateItem(Date item, boolean empty) {
          super.updateItem(item, empty);

          if (item == null || empty) {
            setText("-----");
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
      return new TableCell<Ingreso, Integer>() {
        @Override
        protected void updateItem(Integer item, boolean empty) {
          super.updateItem(item, empty);

          if (item == null || empty) {
            setText("$0");
            setStyle("");
          } else {
            setText("$" + item);
            setStyle("");

          }
        }
      };
    });

    tcMontoPaAlumn.setCellFactory(column -> {
      return new TableCell<PagoAlumnoExterno, Integer>() {
        @Override
        protected void updateItem(Integer item, boolean empty) {
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
      private final JFXButton delete = new JFXButton("Eliminar");

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
          if (item.eliminar()) {
            getTableView().getItems().remove(item);
            datosPagoAlumnos.remove(seleccion);
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
    String busqueda = txtBuscarPago.getText().toLowerCase();
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
    String busqueda = txtBuscarPAlumno.getText().toLowerCase();
    if (!busqueda.isEmpty()) {
      pagosAlumnos.clear();
      for (PagoAlumnoExterno pae : datosPagoAlumnos) {
        if (pae.getAlumno().toString().toLowerCase().contains(busqueda)
                || pae.getMaestro().toString().toLowerCase().contains(busqueda)) {
          pagosAlumnos.add(pae);
        }
      }
      tbPagosAlumnos.setItems(pagosAlumnos);
    } else {
      pagosAlumnos.clear();
      pagosAlumnos.addAll(datosPagoAlumnos);
      tbPagosAlumnos.setItems(pagosAlumnos);
    }
  }

  @FXML
  private void generarReciboAction(ActionEvent event) {
    if (selectedIndex != null) {
      if (selectedIndex.getClass().equals(PagoAlumnoExterno.class)) {
        ((PagoAlumnoExterno) selectedIndex).generarRecibo();
      } else {
        if (selectedIndex.getClass().equals(PagoRenta.class)) {
          ((PagoRenta) selectedIndex).generarRecibo();
        }
        if (selectedIndex.getClass().equals(PagoMaestro.class)) {
          ((PagoMaestro) selectedIndex).generarRecibo();
        }
      }

    }

  }
}
