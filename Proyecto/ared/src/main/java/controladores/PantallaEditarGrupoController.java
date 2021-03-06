/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clasesApoyo.JFXLimitedTextField;
import clasesApoyo.JFXLimitedTextArea;
import clasesApoyo.Mapas;
import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
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
import org.controlsfx.control.Notifications;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
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
    private JFXLimitedTextField txtNombreGrupo;
    @FXML
    private JFXLimitedTextField txtTipoDanza;
    @FXML
    private JFXLimitedTextArea txtHorario;
    @FXML
    private JFXButton btnGuardarGrupo;
    @FXML
    private JFXButton btnEliminarGrupo;
    @FXML
    private Label lblNombreMaestro;
    @FXML
    private JFXColorPicker colorPicker;
    @FXML
    private JFXButton btnEditar;

    private Grupo grupo;

    private HBox pantallaDividida;

    private StackPane pnlPrincipal;
    private Document document;
    private Element grupos;
    private Element grupoXML;
    private String[] horarioGrupo;
    private Mapas mapas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapas = new Mapas();
        horarioGrupo = new String[7];
        for (int i = 0; i < 7; i++) {
            horarioGrupo[i] = "";
        }
        crearValidaciones();
    }

    public void crearValidaciones() {
        txtHorario.setHorarioRequiered(true);
        txtNombreGrupo.setRequired(true);
        txtNombreGrupo.setAlphanumericLimiter(100);
        txtTipoDanza.setRequired(true);
        txtTipoDanza.setAlphanumericLimiter(45);
    }

    public void crearHorario() {
        String[] dias = txtHorario.getText().split("\n");
        for (int i = 0; i < dias.length; i++) {
            int columna = (Integer) mapas.getMapaDias().get((dias[i].split("\\s"))[0]);
            horarioGrupo[columna] = dias[i];
        }
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

    public void setHorarioGrupo(String[] horarioGrupo) {
        this.horarioGrupo = horarioGrupo;
    }

    public void setColorGrupo(Color colorGrupo) {
        colorPicker.setValue(colorGrupo);
    }

    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public void setHorario(String horario, Document document,
            Element grupos, Element grupo) {
        this.txtHorario.setText(horario);
        this.document = document;
        this.grupos = grupos;
        this.grupoXML = grupo;
        crearHorario();
    }

    public void mostrarInformacion() {
        txtNombreGrupo.setText(grupo.getNombre());
        txtTipoDanza.setText(grupo.getTipoDanza());
        lblNombreMaestro.setText(grupo.getMaestro().getNombre() + " " + grupo.getMaestro().getApellidos());

    }

    @FXML
    private void eliminarGrupo(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "¿Seguro que desea eliminar el grupo?"
                , ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        grupo.setEsActivo(false);
        if (alert.getResult() == ButtonType.YES) {
            if(grupo.eliminarGrupo()){
                pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaGrupos.fxml", this.pnlPrincipal, this.pantallaDividida));
            pantallaDividida.getChildren().add(pnlPrincipal);
            eliminarObjetoXML();
            Notifications.create()
                        .title("¡Exito!")
                        .text("El grupo se elimino correctamente")
                        .showInformation();
            }            
        }
    }

    @FXML
    private void actualizarInformacion(ActionEvent event) {
        if (!existenCamposVacios()) {
            grupo.setTipoDanza(txtTipoDanza.getText());
                grupo.setNombre(txtNombreGrupo.getText());
                if (grupo.actualizarDatosGrupo(grupo)) {
                    agregarHorarioXML();
                    pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaGrupos.fxml", this.pnlPrincipal, this.pantallaDividida));
                    pantallaDividida.getChildren().add(pnlPrincipal);
                    Notifications.create()
                        .title("¡Exito!")
                        .text("El grupo se actualizó correctamente")
                        .showInformation();
                }
        }

    }

    public boolean existenCamposVacios() {
        txtNombreGrupo.setText(txtNombreGrupo.getText().trim());
        txtTipoDanza.setText(txtTipoDanza.getText().trim());
        return !txtNombreGrupo.validate() | !txtTipoDanza.validate();
    }

    @FXML
    private void editarHorario(ActionEvent event) {
        txtNombreGrupo.setText(txtNombreGrupo.getText().trim());
        if (txtNombreGrupo.validate()) {
            Stage mainStage = (Stage) txtNombreGrupo.getScene().getWindow();
            mainStage.hide();
            Stage stage = new Stage();
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
            controlador.setStage(stage);
            controlador.setColorGrupo(colorPicker.getValue().toString());
            controlador.setNombreGrupo(txtNombreGrupo.getText());
            controlador.setIdGrupoEditar(String.valueOf(grupo.getGrupoPK().getIdGrupo()));
            controlador.llenarHorario(txtHorario.getText());

            Scene scene = new Scene(root);
            
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
                for (int j = 0; j < horas.length; j++) {
                    diaXML.addElement("hora").
                            addText((horas[j].split("-"))[0]);
                    diaXML.addElement("hora").
                            addText((horas[j].split("-"))[1]);
                }
            }
        }
        if (grupos == null) {
            grupos = buscarGrupo();
        }
        grupos.remove(this.grupoXML);
        grupos.add(grupoXML);
        try {
            XMLWriter writer = new XMLWriter(
                    new FileWriter(System.getProperty("user.home") + "/.ared/horariosAred.xml"));
            writer.write(this.document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Element buscarGrupo() {
        SAXReader reader = new SAXReader();
        try {
            document = reader.read(System.getProperty("user.home") + "/.ared/horariosAred.xml");
        } catch (DocumentException ex) {
            Logger.getLogger(PantallaGruposController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Element root = document.getRootElement();
        Element grupos = root.element("grupos");
        Element grupoXML = (Element) grupos.selectSingleNode("/ared/grupos/grupo[@id = "
                + "'" + this.grupo.getGrupoPK().getIdGrupo() + "']");
        return grupoXML;
    }

    public void eliminarObjetoXML() {
        SAXReader reader = new SAXReader();
        try {
            document = reader.read(System.getProperty("user.home") + "/.ared/horariosAred.xml");
        } catch (DocumentException ex) {
            Logger.getLogger(PantallaGruposController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Element root = document.getRootElement();
        Element grupos = root.element("grupos");
        Element grupoXML = (Element) grupos.selectSingleNode("/ared/grupos/grupo[@id = "
                + "'" + this.grupo.getGrupoPK().getIdGrupo() + "']");
        grupos.remove(grupoXML);
        try {
            XMLWriter writer = new XMLWriter(
                    new FileWriter(System.getProperty("user.home") + "/.ared/horariosAred.xml"));
            writer.write(this.document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        Stage mainStage = (Stage) txtNombreGrupo.getScene().getWindow();
        mainStage.show();
    }
}
