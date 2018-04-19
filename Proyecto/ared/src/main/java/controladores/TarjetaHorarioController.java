package controladores;

import clasesApoyo.Mapas;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class TarjetaHorarioController implements Initializable {

    @FXML
    private StackPane pnlPrincipal;
    @FXML
    private Label lblNombreGrupo;
    @FXML
    private JFXButton botonLibre;

    private String colorGrupo;
    private String nombreGrupo;
    private final Background focusBackground = new Background(new BackgroundFill(Color.web("#BDC3C7"), CornerRadii.EMPTY, Insets.EMPTY));
    private final Background unfocusBackground = new Background(new BackgroundFill(Color.web("#e0e0e0"), CornerRadii.EMPTY, Insets.EMPTY));
    private Background ocupado;
    private Background grupo;
    private boolean horarioLibre = true;
    private boolean editable = false;
    private int columna;
    private int fila;
    private TextArea txtHorarioGrupo;
    private Mapas mapas = null;
    private ArrayList<ArrayList<Integer>> horasGrupo;
    private String[] horarioGrupo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        botonLibre.setVisible(editable);
        pnlPrincipal.backgroundProperty().bind(Bindings
                .when(pnlPrincipal.focusedProperty())
                .then(focusBackground)
                .otherwise(unfocusBackground)
        );
    }

    public void setHorarioLibre(boolean horarioLibre) {
        this.horarioLibre = horarioLibre;
        if (this.horarioLibre) {
            lblNombreGrupo.setText("");
        } else {
            ocupado = new Background(new BackgroundFill(Color.web(colorGrupo), CornerRadii.EMPTY, Insets.EMPTY));
            pnlPrincipal.backgroundProperty().bind(Bindings
                    .when(pnlPrincipal.focusedProperty())
                    .then(ocupado)
                    .otherwise(ocupado)
            );
            lblNombreGrupo.setText(nombreGrupo);
        }
    }

    public void setHorasGrupo(ArrayList<ArrayList<Integer>> horasGrupo) {
        this.horasGrupo = horasGrupo;
    }

    public void setHorarioGrupo(String[] horarioGrupo) {
        this.horarioGrupo = horarioGrupo;
    }

    public void setTxtHorarioGrupo(TextArea txtHorarioGrupo) {
        this.txtHorarioGrupo = txtHorarioGrupo;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setHorarioEditable(boolean editable) {
        this.editable = editable;
        botonLibre.setVisible(editable);
    }

    public void setColorGrupo(String colorGrupo) {
        this.colorGrupo = colorGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public StackPane getPnlPrincipal() {
        return pnlPrincipal;
    }

    @FXML
    private void cambiarDeColor(MouseEvent event) {
        if (horarioLibre) {
            pnlPrincipal.requestFocus();
        }
    }

    @FXML
    private void cambiarLibre(ActionEvent event) {
        horarioLibre = true;
        botonLibre.setVisible(false);
        lblNombreGrupo.setText("");
        horasGrupo.get(columna).remove(Integer.valueOf(fila));
        mostrarHorario();
        pnlPrincipal.backgroundProperty().bind(Bindings
                .when(pnlPrincipal.focusedProperty())
                .then(focusBackground)
                .otherwise(unfocusBackground)
        );

    }

    @FXML
    private void agregarDia(MouseEvent event) {
        if (horarioLibre) {
            lblNombreGrupo.setText(nombreGrupo);
            grupo = new Background(new BackgroundFill(Color.web(colorGrupo), CornerRadii.EMPTY, Insets.EMPTY));
            pnlPrincipal.backgroundProperty().bind(Bindings
                    .when(pnlPrincipal.focusedProperty())
                    .then(grupo)
                    .otherwise(grupo)
            );
            horarioLibre = false;
            botonLibre.setVisible(true);
            determinarDia();
        }
    }

    public void determinarDia() {
        horasGrupo.get(columna).add(fila);
        Collections.sort(horasGrupo.get(columna));
        mostrarHorario();
    }

    public void mostrarHorario() {
        if(mapas == null)
            mapas = new Mapas();
        String horarioDia = mapas.getMapaColumnas().get(columna).toString();
        if (horasGrupo.get(columna).size() > 0) {
            int horaInicio = horasGrupo.get(columna).get(0);
            int horaFin = horasGrupo.get(columna).get(0);
            for (int i = 0; i < horasGrupo.get(columna).size(); i++) {
                if (i == horasGrupo.get(columna).size() - 1) {
                    horarioDia += " " + mapas.getMapaFilas().get(horaInicio) + "-" + mapas.getMapaFilas().get(horaFin + 1);
                } else {
                    if ((horasGrupo.get(columna).get(i + 1) - horasGrupo.get(columna).get(i)) == 1) {
                        horaFin = horasGrupo.get(columna).get(i + 1);

                    } else {
                        horarioDia += " " + mapas.getMapaFilas().get(horaInicio) + "-" + mapas.getMapaFilas().get(horaFin + 1);
                        horaInicio = horasGrupo.get(columna).get(i + 1);
                        horaFin = horasGrupo.get(columna).get(i + 1);
                    }
                }

            }
        }else{
            horarioDia = "";
        }
        horarioGrupo[columna] = horarioDia;
        String horario = "";
        for(int i = 0; i < horarioGrupo.length; i++){
            if(!horarioGrupo[i].equals("")){
                horario += horarioGrupo[i] + "\n";
            }
                
        }
        txtHorarioGrupo.setText(horario);
    }

}
