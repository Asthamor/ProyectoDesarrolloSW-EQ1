/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import interfaces.Controlador;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Egreso;

/**
 * FXML Controller class
 *
 * @author mau
 */
public class PantallaEgresosController implements Initializable, Controlador {

  @FXML
  private TableView<Egreso> tbEgresos;
  @FXML
  private TableColumn<Egreso, Date> colFecha;
  @FXML
  private JFXButton btnRegEgreso;
  @FXML
  private TableColumn<Egreso, String> colConcepto;
  @FXML
  private TableColumn<Egreso, Double> colMonto;

  private List<Egreso> datosEgreso;
  private List<Egreso> egresosDisplayList;

  private HBox pantallaDividida;
  private StackPane pnlPrincipal;
  @FXML
  private StackPane pnlSecundario;
  @FXML
  private JFXTextField txtBuscar;
  @FXML
  private HBox divHorizontal;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.MEDIUM);
    obtenerEgresos();

    tbEgresos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue != null) {
          cargarTarjetaLateral(tbEgresos.getSelectionModel().getSelectedItem());
        }
      }
    });
    colMonto.setCellFactory(column -> {
      return new TableCell<Egreso, Double>() {
        @Override
        protected void updateItem(Double item, boolean empty) {
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

    colFecha.setCellFactory(column -> {
      return new TableCell<Egreso, Date>() {
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

  @FXML
  private void crearEgreso(ActionEvent event) {
    cargarTarjetaLateral(null);

  }

  private void cargarTarjetaLateral(Egreso egreso) {
    Parent root = null;
    FXMLLoader loader = new FXMLLoader(PantallaEgresosController.class.getResource("/fxml/PantallaEditarEgreso.fxml"));
    try {
      root = (Parent) loader.load();
    } catch (IOException ex) {
      Logger.getLogger(PantallaEgresosController.class.getName()).log(Level.SEVERE, null, ex);
    }
    

    if (root != null) {

      PantallaEditarEgresoController controlador = loader.getController();

      controlador.setEgreso(egreso);
      controlador.setControlador(this);
      controlador.setPantallaDividida(pantallaDividida);
      if (pnlSecundario.getChildren().size() >= 1) {
        pnlSecundario.getChildren().clear();
      }
      pnlSecundario.getChildren().add(root);
      PantallaPrincipalDirectorController.animacionCargarPantalla(pnlSecundario);
      if (divHorizontal.getChildren().size() > 1) {
        divHorizontal.getChildren().remove(1);
      }
      divHorizontal.getChildren().add(pnlSecundario);
    }
  }

  @Override
  public void setPantallaDividida(HBox pantallaDividida) {
    this.pantallaDividida = pantallaDividida;
  }

  @Override
  public void setPnlPrincipal(StackPane pnlPrincipal) {
    this.pnlPrincipal = pnlPrincipal;
  }

  private boolean obtenerEgresos() {
    colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
    colConcepto.setCellValueFactory(new PropertyValueFactory<>("concepto"));
    colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));

    Egreso eg = new Egreso();
    datosEgreso = FXCollections.observableList(new ArrayList<>());
    egresosDisplayList = FXCollections.observableList(new ArrayList<>());
    datosEgreso.addAll(eg.obtenerTodosPorFecha());
    egresosDisplayList.addAll(datosEgreso);
    tbEgresos.setItems((ObservableList<Egreso>) egresosDisplayList);

    return true;
  }

  @FXML
  private void busqueda(KeyEvent event) {
    String busqueda = txtBuscar.getText();
    egresosDisplayList.clear();
    for (Egreso e : datosEgreso){
      if (e.getConcepto().toLowerCase().contains(busqueda.toLowerCase())){
        egresosDisplayList.add(e);
      }
    }
  }

  
  public void refreshList(){
    Egreso eg = new Egreso();
    datosEgreso = FXCollections.observableList(new ArrayList<>());
    egresosDisplayList = FXCollections.observableList(new ArrayList<>());
    datosEgreso.addAll(eg.obtenerTodosPorFecha());
    egresosDisplayList.addAll(datosEgreso);
    tbEgresos.setItems((ObservableList<Egreso>) egresosDisplayList);
  }
}
