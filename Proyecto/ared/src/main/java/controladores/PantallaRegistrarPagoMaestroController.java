/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Maestro;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author raymundo
 */
public class PantallaRegistrarPagoMaestroController implements Initializable, Controlador {

    @FXML
    private ImageView imgMaestro;
    @FXML
    private ListView<String> lstMaestros;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private Label lblFechaPago;
    @FXML
    private Label lblNombreMaestro;
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    @FXML
    private JFXTextField txtMonto;
    @FXML
    private Label labelProximoPago;

    private ArrayList<String> nombresColaboradores;
    private Maestro maestro;
    private List<Persona> maestros;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombresColaboradores = new ArrayList();
        ValidatorBase requeridos = new NumberValidator();
        requeridos.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
                .glyph(FontAwesomeIcon.WARNING)
                .size("1em")
                .styleClass("error")
                .build());
        txtMonto.getValidators().add(requeridos);
        ValidatorBase requeridos2 = new RequiredFieldValidator();
        requeridos.setMessage("Monto necesario");
        requeridos.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
                .glyph(FontAwesomeIcon.WARNING)
                .size("1em")
                .styleClass("error")
                .build());
        txtMonto.getValidators().add(requeridos2);
        //txtMonto.setNumLimiter(6);
    }

    public void mostrarColaboradores() {
        lblFechaPago.setText(DateFormat.getDateInstance().format(new Date()));
        maestro = new Maestro();
        maestros = maestro.obtenerTodos();
        maestros.forEach((colaborador) -> {
            nombresColaboradores.add(colaborador.getNombre() + " " + colaborador.getApellidos());
        });
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(nombresColaboradores);
        lstMaestros.setItems(items);
        lstMaestros.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (lstMaestros.getSelectionModel().getSelectedIndex() != -1) {
                    
                    lblNombreMaestro.setText(maestros.get(lstMaestros.getSelectionModel().getSelectedIndex()).getNombre() + " " + maestros.get(lstMaestros.getSelectionModel().getSelectedIndex()).getApellidos());
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

}
