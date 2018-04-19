/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import static com.sun.javafx.PlatformUtil.isLinux;
import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import interfaces.Controlador;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import modelo.Grupo;
import modelo.Horario;
import modelo.Maestro;
import modelo.Persona;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaRegistrarGrupoController implements Initializable, Controlador {

    @FXML
    private Label lblRegistrarGrupo;
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
    private JFXComboBox<String> cmbMaestro;
    @FXML
    private JFXButton btnGuardarGrupo;
    @FXML
    private JFXButton btnDefinirHorario;
    @FXML
    private JFXTextArea txtHorario;
    @FXML
    private JFXColorPicker colorPicker;

    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private List<Persona> maestros;
    private ArrayList<ArrayList<Integer>> horasGrupo = null;
    private String[] horarioGrupo = null;
    private Document document;
    private Element grupos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtNombreGrupo.setText("");
        txtTipoDanza.setText("");
        ArrayList<String> nombresMaestros = new ArrayList();
        Maestro instanciaMaestro = new Maestro();
        maestros = instanciaMaestro.obtenerTodos();
        maestros.forEach((maestro) -> {
            nombresMaestros.add(maestro.getNombre() + " " + maestro.getApellidos());
        });
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(nombresMaestros);
        cmbMaestro.setItems(items);
    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public void setHorario(String horario, String[] horarioGrupo, ArrayList<ArrayList<Integer>> horasGrupo, Document document,
             Element grupos) {
        this.txtHorario.setText(horario);
        this.horarioGrupo = horarioGrupo;
        this.horasGrupo = horasGrupo;
        this.document = document;
        this.grupos = grupos;
    }

    @FXML
    private void definirHorario(ActionEvent event) {
        if (!existenCamposVacios()) {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(PantallaDefinirHorarioGrupoController.class.getResource("/fxml/PantallaDefinirHorarioGrupo.fxml"));
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(PantallaDefinirHorarioGrupoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            PantallaDefinirHorarioGrupoController controlador = loader.getController();
            controlador.setControladorRegsitrarGrupo(this);
            controlador.setEditarGrupo(false);
            controlador.setColorGrupo(colorPicker.getValue().toString());
            controlador.setNombreGrupo(txtNombreGrupo.getText());
            controlador.llenarHorario();
            if (!txtHorario.getText().equals("")) {
                controlador.setColorGrupo(colorPicker.getValue().toString());
                controlador.setNombreGrupo(txtNombreGrupo.getText());
                controlador.llenarHorario(txtHorario.getText());
            }

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } else {
            Mensajes.mensajeAlert("Algunos campos estan vacíos");
        }

    }

    @FXML
    private void guardarGrupo(ActionEvent event) {
        if (existenCamposVacios()) {
            Mensajes.mensajeAlert("Algunos campos estan vacíos");

        } else {
//            if (!tamañoValidoCaracteres()) {
//                Mensajes.mensajeAlert("Algunos campos sobre pasan el limite de caracteres");
//            } else {
            Grupo grupo = new Grupo();
            grupo.setTipoDanza(txtTipoDanza.getText());
            grupo.setNombre(txtNombreGrupo.getText());
            grupo.setFechaCreacion(new Date());
            grupo.setMaestro((Maestro) maestros.get(cmbMaestro.getSelectionModel().getSelectedIndex()));
            Horario horario = new Horario();
            horario = horario.obtenerRutaHorario();
            if (horario == null) {
                String rutaHorario;
                String palabraClave = "user.name";
                horario = new Horario();
                horario.setIdHorario(1);
                if (isLinux()) {
                    rutaHorario = "/home/user.name/Horario.xml".replace(palabraClave, System.getProperty(palabraClave));
                } else {
                    rutaHorario = "/Users/user.name/Horario.xml".replace(palabraClave, System.getProperty(palabraClave));
                }
                horario.setRutaArchivo(rutaHorario);
                horario.crearHorario(horario);
            }
            grupo.setHorario(horario);
            if (grupo.registrarGrupo(grupo)) {
                agregarHorario(grupo.obtenerUltimoGrupo(), grupo);
                pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaGrupos.fxml", this.pnlPrincipal, this.pantallaDividida));
                pantallaDividida.getChildren().add(pnlPrincipal);
                Mensajes.mensajeExitoso("El grupo se registro correctamente");
            }
//            }
        }
    }

    public boolean existenCamposVacios() {
        return txtNombreGrupo.getText().equals("") || txtTipoDanza.getText().equals("") || cmbMaestro.getValue() == null;
    }

    public void agregarHorario(String idGrupo, Grupo grupo) {        
        Document document = DocumentHelper.createDocument();
        Element grupoXML = document.addElement("grupo");
        grupoXML.addAttribute("id", idGrupo).
                addAttribute("nombreGrupo", grupo.getNombre()).
                addAttribute("color", colorPicker.getValue().toString());
        Element horarioXML = grupoXML.addElement("horario");
        
        for (int i = 0; i < horarioGrupo.length; i++) {
            if (!horarioGrupo[i].equals("")) {
                String dia = (horarioGrupo[i].split("\\s"))[0];
                String[] horas = (horarioGrupo[i].replace(dia, "")).split(",");
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
        
        grupos.add(grupoXML);
        try {            
            XMLWriter writer = new XMLWriter(
			new FileWriter( "/home/alonso/Desktop/grupoXML.xml" )
		);
            writer.write(this.document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
