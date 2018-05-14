/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class TarjetaHorarioRentaController implements Initializable {

    @FXML
    private Label lblNombreGrupo;
    @FXML
    private JFXButton btnLibre;
    @FXML
    private StackPane pnlPrincipal;

    private String nombreEvento;
    private String colorEvento;
    private boolean horaLibre = true;
    private boolean esGrupo;
    private Background ocupado;
    private final Background focusBackground = new Background(new BackgroundFill(Color.web("#BDC3C7"), CornerRadii.EMPTY, Insets.EMPTY));
    private final Background unfocusBackground = new Background(new BackgroundFill(Color.web("#e0e0e0"), CornerRadii.EMPTY, Insets.EMPTY));
    private int fila;
    private PantallaRegistrarRentaController controlador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pnlPrincipal.backgroundProperty().bind(Bindings
                .when(pnlPrincipal.focusedProperty())
                .then(focusBackground)
                .otherwise(unfocusBackground)
        );
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setControlador(PantallaRegistrarRentaController controlador) {
        this.controlador = controlador;
    }

    public void setHoraLibre(boolean horaLibre) {
        this.horaLibre = horaLibre;
        if (this.horaLibre) {
            btnLibre.setVisible(false);
            lblNombreGrupo.setText("");
        } else {
            btnLibre.setVisible(true);
            ocupado = new Background(new BackgroundFill(Color.web(this.colorEvento), CornerRadii.EMPTY, Insets.EMPTY));
            pnlPrincipal.backgroundProperty().bind(Bindings
                    .when(pnlPrincipal.focusedProperty())
                    .then(ocupado)
                    .otherwise(ocupado)
            );
            lblNombreGrupo.setText(this.nombreEvento);
        }
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
        lblNombreGrupo.setText(this.nombreEvento);
    }

    public void setColorEvento(String colorEvento) {
        this.colorEvento = colorEvento;
    }

    public void setEsGrupo(boolean esGrupo) {
        this.esGrupo = esGrupo;
        if (this.esGrupo) {
            btnLibre.setVisible(false);
        }
    }

    @FXML
    private void cambiarColor(MouseEvent event) {
        if (horaLibre) {
            pnlPrincipal.requestFocus();
        }
    }

    @FXML
    private void reservarHora(MouseEvent event) {
        if (horaLibre && !esHoraSalteada()) {
            btnLibre.setVisible(true);
            ocupado = new Background(new BackgroundFill(Color.web("#ffe6fd"), CornerRadii.EMPTY, Insets.EMPTY));
            pnlPrincipal.backgroundProperty().bind(Bindings
                    .when(pnlPrincipal.focusedProperty())
                    .then(ocupado)
                    .otherwise(ocupado)
            );
            lblNombreGrupo.setText("Reservado");
            
            controlador.agregarHora(fila);
            controlador.mostrarHorarios();
        }
    }
    
    public boolean esHoraSalteada(){
        boolean horaSalteada = true;
        if(controlador.getHorarioRenta().isEmpty()){
            horaSalteada = false;
        }else{
            int ultimoElemnto = controlador.getHorarioRenta().size()-1;
            horaSalteada = !((controlador.getHorarioRenta().get(0) == fila +1) || (controlador.getHorarioRenta().get(ultimoElemnto) == fila -1));
        }
        return horaSalteada;
    }

    @FXML
    private void horaLibre(ActionEvent event) {
        btnLibre.setVisible(false);
        lblNombreGrupo.setText("");
        pnlPrincipal.backgroundProperty().bind(Bindings
                .when(pnlPrincipal.focusedProperty())
                .then(focusBackground)
                .otherwise(unfocusBackground)
        );
        controlador.getHorarioRenta().remove(Integer.valueOf(fila));
        controlador.mostrarHorarios();
    }
}
