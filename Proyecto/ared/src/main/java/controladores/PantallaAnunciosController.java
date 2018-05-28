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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Egreso;
import modelo.Publicidad;

/**
 * FXML Controller class
 *
 * @author mau
 */
public class PantallaAnunciosController implements Initializable, Controlador {

  @FXML
  private HBox divHorizontal;
  @FXML
  private JFXTextField txtBuscar;
  @FXML
  private TableView<Publicidad> tbPublicidad;
  @FXML
  private TableColumn<Publicidad, String> colCreador;
  @FXML
  private TableColumn<Publicidad, Double> colMonto;
  @FXML
  private TableColumn<Publicidad, String> colURL;
  @FXML
  private TableColumn<Publicidad, Date> colInicio;
  @FXML
  private TableColumn<Publicidad, Date> colFin;
  @FXML
  private TableColumn<Publicidad, String> colDescripcion;
  @FXML
  private JFXButton btnRegistrarPublicidad;

  private HBox pantallaDividida;
  private StackPane pnlPrincipal;
  private StackPane pnlSecundario = new StackPane();
  
  private List<Publicidad> datosPublicidad;
  private List<Publicidad> publicidadDisplayList;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.MEDIUM);
    tbPublicidad.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue != null) {
          cargarTarjetaLateral(tbPublicidad.getSelectionModel().getSelectedItem());
        }
      }
    });
    colMonto.setCellFactory(column -> {
      return new TableCell<Publicidad, Double>() {
        @Override
        protected void updateItem(Double item, boolean empty) {
          super.updateItem(item, empty);

          if (item == null || empty) {
      
          } else {
            setText("$" + item);
            setStyle("");

          }
        }
      };
    });

    colInicio.setCellFactory(column -> {
      return new TableCell<Publicidad, Date>() {
        @Override
        protected void updateItem(Date item, boolean empty) {
          super.updateItem(item, empty);

          if (item == null || empty) {
           
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
    colFin.setCellFactory(column -> {
      return new TableCell<Publicidad, Date>() {
        @Override
        protected void updateItem(Date item, boolean empty) {
          super.updateItem(item, empty);

          if (item == null || empty) {
           
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
    obtenerDatos();

    // TODO
  }

  @Override
  public void setPantallaDividida(HBox pantallaDividida) {
    this.pantallaDividida = pantallaDividida;
  }

  @Override
  public void setPnlPrincipal(StackPane pnlPrincipal) {
    this.pnlPrincipal = pnlPrincipal;
  }

  private boolean obtenerDatos() {
    colInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
    colFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
    colCreador.setCellValueFactory(new PropertyValueFactory<>("publicidadPK.maestroIdMaestro"));
    colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
    colURL.setCellValueFactory(new PropertyValueFactory<>("url"));

    Publicidad pub = new Publicidad();
    datosPublicidad = FXCollections.observableArrayList(new ArrayList());
    publicidadDisplayList = FXCollections.observableArrayList(new ArrayList());
    datosPublicidad.addAll(pub.obtenerTodoOrdenFecha());
    publicidadDisplayList.addAll(datosPublicidad);
    tbPublicidad.setItems((ObservableList<Publicidad>) publicidadDisplayList);

    return true;
  }

  @FXML
  private void busqueda(KeyEvent event) {
    String busqueda = txtBuscar.getText();
    publicidadDisplayList.clear();
    for (Publicidad pub : datosPublicidad){
      if (pub.getDescripcion().toLowerCase().contains(busqueda.toLowerCase())){
        publicidadDisplayList.add(pub);
      }
    }
  }

  @FXML
  private void crearPublicidad(ActionEvent event) {
    cargarTarjetaLateral(null);
  }
  
  private void cargarTarjetaLateral(Publicidad publicidad){
    Parent root = null;
    FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/PantallaEditarAnuncio.fxml"));
    try {
      root = (Parent) loader.load();
    } catch (IOException ex) {
      Logger.getLogger(PantallaPrincipalDirectorController.class.getName()).log(Level.SEVERE, null, ex);
    }
    

    if (root != null) {

      PantallaEditarAnuncioController controlador = loader.getController();

      controlador.setControlador(this);
      controlador.setPublicidad(publicidad);
      controlador.setPantallaDividida(pantallaDividida);
      if (pnlPrincipal.getChildren().size() > 1) {
        pnlPrincipal.getChildren().remove(1);
      }
      pnlSecundario.getChildren().add(root);
      PantallaPrincipalDirectorController.animacionCargarPantalla(pnlSecundario);
      if (pantallaDividida.getChildren().size() > 1) {
        pantallaDividida.getChildren().remove(1);
      }
      pantallaDividida.getChildren().add(pnlSecundario);
    }
    
  }

   public void refreshList(){
    Publicidad pub = new Publicidad();
    datosPublicidad = FXCollections.observableList(new ArrayList<>());
    publicidadDisplayList = FXCollections.observableList(new ArrayList<>());
    datosPublicidad.addAll(pub.obtenerTodoOrdenFecha());
    publicidadDisplayList.addAll(datosPublicidad);
    tbPublicidad.setItems((ObservableList<Publicidad>) publicidadDisplayList);
  }
}
