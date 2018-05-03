/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modelo.Grupo;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaEditarGrupoController implements Initializable {

    @FXML
    private Label lblDetalleGrupo;
    @FXML
    private Label lblNombreGrupo;
    @FXML
    private Label lblMaestro;
    @FXML
    private Label lblTipoDanza;
    @FXML
    private Label lblHorario;
    @FXML
    private JFXTextField txtNombreGrupo;
    @FXML
    private JFXTextField txtTipoDanza;
    @FXML
    private JFXTextArea txtHorario;
    @FXML
    private JFXButton btnGuardarGrupo;
    @FXML
    private JFXButton btnEliminarGrupo;
    @FXML
    private JFXButton btnEditarHorario;

    private Grupo grupo;

    private HBox pantallaDividida;

    private StackPane pnlPrincipal;
    private Document document;
    private Element grupos;
    private HashMap mapaColumnas;
    private Color colorGrupo;
    private Element grupoXML;
    private ArrayList<ArrayList<Integer>> horasGrupo;
    private String[] horarioGrupo;

    private TarjetaInformacionGrupoController controlador;

    @FXML
    private Label lblNombreMaestro;
    @FXML
    private JFXColorPicker colorPicker;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
        mostrarInformacion();
    }

    public void agregarHorario(String horario) {
        txtHorario.setText(horario);
    }

    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    public void setHorasGrupo(ArrayList<ArrayList<Integer>> horasGrupo) {
        this.horasGrupo = horasGrupo;
    }

    public void setHorarioGrupo(String[] horarioGrupo) {
        this.horarioGrupo = horarioGrupo;
    }

    public void setColorGrupo(Color colorGrupo) {
        this.colorGrupo = colorGrupo;
        colorPicker.setValue(colorGrupo);
    }

    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public void setControlador(TarjetaInformacionGrupoController controlador) {
        this.controlador = controlador;
    }

    public void setHorario(String horario, Document document,
            Element grupos, Element grupo) {
        this.txtHorario.setText(horario);
        this.document = document;
        this.grupos = grupos;
        this.grupoXML = grupo;
    }

    public void mostrarInformacion() {
        txtNombreGrupo.setText(grupo.getNombre());
        txtTipoDanza.setText(grupo.getTipoDanza());
        lblNombreMaestro.setText(grupo.getMaestro().getNombre() + " " + grupo.getMaestro().getApellidos());

    }

    @FXML
    private void eliminarGrupo(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "¿Seguro que desea eliminar el grupo?,"
                + " se eliminaran las inscripciones y pagos asocioados.", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            grupo.eliminarGrupo(grupo.getGrupoPK());
            pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaGrupos.fxml", this.pnlPrincipal, this.pantallaDividida));
            pantallaDividida.getChildren().add(pnlPrincipal);
            Mensajes.mensajeExitoso("Grupo eliminado correctamente");
        }
    }

    @FXML
    private void actualizarInformacion(ActionEvent event) {
        if (existenCamposVacios()) {
            Mensajes.mensajeAlert("Algunos campos estan vacíos");
        } else {
            if (tamañoInvalidoCaracteres()) {
                Mensajes.mensajeAlert("Algunos campos sobre pasan el limite de caracteres");
            } else {
            grupo.setTipoDanza(txtTipoDanza.getText());
            grupo.setNombre(txtNombreGrupo.getText());
            if (grupo.actualizarDatosGrupo(grupo)) {
                agregarHorarioXML();
                pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaGrupos.fxml", this.pnlPrincipal, this.pantallaDividida));
                pantallaDividida.getChildren().add(pnlPrincipal);
                Mensajes.mensajeExitoso("La información se actualizó correctamente");
            }
            }

        }

    }
    
    public boolean tamañoInvalidoCaracteres(){
        return txtNombreGrupo.getText().length() > 100 || txtTipoDanza.getText().length() > 45;
    }

    public boolean existenCamposVacios() {
        return txtNombreGrupo.getText().equals("") || txtTipoDanza.getText().equals("");
    }

    @FXML
    private void editarHorario(ActionEvent event) {
        if (!existenCamposVacios()) {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(PantallaDefinirHorarioGrupoController.class.getResource("/fxml/PantallaDefinirHorarioGrupo.fxml"));
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(PantallaDefinirHorarioGrupoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            PantallaDefinirHorarioGrupoController controlador = loader.getController();
            controlador.setControladorEditarGrupo(this);
            controlador.setEditarGrupo(true);
            controlador.setColorGrupo(colorPicker.getValue().toString());
            controlador.setNombreGrupo(txtNombreGrupo.getText());
            controlador.setIdGrupoEditar(String.valueOf(grupo.getGrupoPK().getIdGrupo()));
            controlador.llenarHorario(txtHorario.getText());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }
    
    public void agregarHorarioXML() {        
        Document document = DocumentHelper.createDocument();
        Element grupoXML = document.addElement("grupo");
        grupoXML.addAttribute("id", String.valueOf(grupo.getGrupoPK().getIdGrupo())).
                addAttribute("nombreGrupo", grupo.getNombre()).
                addAttribute("color", colorPicker.getValue().toString());
        Element horarioXML = grupoXML.addElement("horario");
        
        for (int i = 0; i < horarioGrupo.length; i++) {
            if (!horarioGrupo[i].equals("")) {
                String dia = (horarioGrupo[i].split("\\s"))[0];
                String[] horas = ((horarioGrupo[i].replace(dia, "")).trim()).split("\\s");
                System.out.println(horas.length);
                Element diaXML = horarioXML.addElement("dia").
                        addAttribute("num", String.valueOf(i)).
                        addAttribute("nombreDia", dia);
                for(int j = 0; j < horas.length; j++){
                    Element horaInicio = diaXML.addElement("hora").
                            addText((horas[j].split("-"))[0]);
                    Element horaFin = diaXML.addElement("hora").
                            addText((horas[j].split("-"))[1]);
                }
            }
        }
        grupos.remove(this.grupoXML);
        grupos.add(grupoXML);
        try {            
            XMLWriter writer = new XMLWriter(
			new FileWriter(System.getProperty("user.dir") + "/horariosAred.xml"));
            writer.write(this.document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
