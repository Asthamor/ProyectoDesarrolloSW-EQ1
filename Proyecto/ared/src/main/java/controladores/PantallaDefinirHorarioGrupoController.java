package controladores;

import clasesApoyo.Mapas;
import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class PantallaDefinirHorarioGrupoController implements Initializable {

    @FXML
    private ScrollPane scrollAgenda;
    @FXML
    private StackPane pnlAgenda;
    @FXML
    private TextArea txtHorarioGrupo;
    @FXML
    private GridPane agenda;
    @FXML
    private JFXButton btnAceptar;

    private String[] horas;
    private String nombreGrupo;
    private String colorGrupo;
    private Mapas mapas = null;
    private ArrayList<ArrayList<Integer>> horasGrupo;
    private String[] horarioGrupo;
    private boolean editarGrupo = false;
    private String idGrupoEditar = "";
    private PantallaRegistrarGrupoController controladorRegsitrarGrupo = null;
    private PantallaEditarGrupoController controladorEditarGrupo = null;
    private Document document;
    private Element grupos;
    private Element grupo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HorasGrupo();
        HorarioGrupo();
        scrollAgenda.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pnlAgenda.setAlignment(agenda, Pos.CENTER);
        scrollAgenda.setVvalue(0.5);
        horas = obtenerHoras();
        txtHorarioGrupo.setText("");
    }

    public void llenarHorario() {
        agregarAgenda();
        agregarHorarios();
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public void setColorGrupo(String colorGrupo) {
        this.colorGrupo = colorGrupo;
    }

    public void setEditarGrupo(boolean editarGrupo) {
        this.editarGrupo = editarGrupo;
    }

    public void setIdGrupoEditar(String idGrupoEditar) {
        this.idGrupoEditar = idGrupoEditar;
    }

    public void setControladorRegsitrarGrupo(PantallaRegistrarGrupoController controladorRegsitrarGrupo) {
        this.controladorRegsitrarGrupo = controladorRegsitrarGrupo;
    }

    public void setControladorEditarGrupo(PantallaEditarGrupoController controladorEditarGrupo) {
        this.controladorEditarGrupo = controladorEditarGrupo;
    }

    public void HorasGrupo() {
        horasGrupo = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 7; i++) {
            horasGrupo.add(new ArrayList<>());
        }
    }

    public void HorarioGrupo() {
        horarioGrupo = new String[7];
        for (int i = 0; i < 7; i++) {
            horarioGrupo[i] = "";
        }
    }

    public void agregarAgenda() {
        agenda.setVgap(1);
        agenda.setHgap(2);
        for (int i = 0; i < 48; i += 2) {
            StackPane horasEnteras = new StackPane();
            Label lbHoras = new Label(horas[i]);
            horasEnteras.getChildren().add(lbHoras);
            horasEnteras.setAlignment(lbHoras, Pos.TOP_RIGHT);
            agenda.add(horasEnteras, 0, i);
        }

        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < 48; i++) {
                agenda.add(crearTarjetaHorario(j, i, false, true), j + 1, i);
            }
        }
    }

    public String[] obtenerHoras() {
        String[] horas = new String[48];
        int hora = 0;
        for (int i = 0; i < 48; i += 2) {
            horas[i] = hora + ":00";
            horas[i + 1] = hora + ":30";
            hora++;
        }
        return horas;
    }

    public void agregarHorarios() {
        try {
            if (mapas == null) {
                mapas = new Mapas();
            }
            SAXReader reader = new SAXReader();
            document = reader.read(System.getProperty("user.dir") + "/horariosAred.xml");
            Element root = document.getRootElement();
            grupos = root.element("grupos");
            String idGrupo;
            for (Iterator<Element> iter = root.elementIterator("grupos"); iter.hasNext();) {
                Element innerNode = iter.next();
                for (Iterator<Element> iter2 = innerNode.elementIterator("grupo"); iter2.hasNext();) {
                    Element grupo = iter2.next();

                    if (this.editarGrupo && this.idGrupoEditar.equals(grupo.attributeValue("id"))) {
                        this.grupo = grupo;
                        if (iter2.hasNext()) {
                            grupo = iter2.next();
                        } else {
                            break;
                        }
                    }
                    nombreGrupo = grupo.attributeValue("nombreGrupo");
                    colorGrupo = grupo.attributeValue("color");
                    idGrupo = grupo.attributeValue("id");
                    Element horario = grupo.element("horario");
                    for (Iterator<Element> itr3 = horario.elementIterator("dia"); itr3.hasNext();) {
                        Element dia = itr3.next();
                        int columna = Integer.valueOf(dia.attributeValue("num"));
                        for (Iterator<Element> itr4 = dia.elementIterator("hora"); itr4.hasNext();) {
                            int horaIncio = (Integer) mapas.getMapaHoras().get((String.valueOf(((Element) itr4.next()).getData())).trim());
                            int horaFin = (Integer) mapas.getMapaHoras().get((String.valueOf(((Element) itr4.next()).getData())).trim());
                            Parent pantalla = crearTarjetaHorario(columna, horaIncio, false, false);
                            agenda.add(pantalla, columna + 1, horaIncio, 1, (horaFin - horaIncio));
                        }

                    }

                }
            }
            if (editarGrupo) {
                String horario = "";
                for (int i = 0; i < horarioGrupo.length; i++) {
                    if (!horarioGrupo[i].equals("")) {
                        horario += horarioGrupo[i].substring(0, horarioGrupo[i].length() - 1) + "\n";
                    }
                }
                txtHorarioGrupo.setText(horario);
            }
        } catch (DocumentException ex) {
            Logger.getLogger(PantallaDefinirHorarioGrupoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Parent crearTarjetaHorario(int columna, int fila, boolean horarioEditable, boolean horarioLibre) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaDefinirHorarioGrupoController.class.getResource("/fxml/TarjetaHorario.fxml"));
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PantallaDefinirHorarioGrupoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        TarjetaHorarioController controlador = loader.getController();
        controlador.setColorGrupo(this.colorGrupo);
        controlador.setNombreGrupo(this.nombreGrupo);
        controlador.setColumna(columna);
        controlador.setFila(fila);
        controlador.setTxtHorarioGrupo(txtHorarioGrupo);
        controlador.setHorasGrupo(horasGrupo);
        controlador.setHorarioGrupo(horarioGrupo);
        controlador.setHorarioLibre(horarioLibre);
        controlador.setHorarioEditable(horarioEditable);
        return root;
    }

    @FXML
    private void guardarHorario(ActionEvent event) {
        if (txtHorarioGrupo.getText().equals("")) {
            Mensajes.mensajeAlert("Debe ingresar un horario para el grupo");
        } else {
            if (controladorRegsitrarGrupo != null) {
                controladorRegsitrarGrupo.setHorario(txtHorarioGrupo.getText(), this.horarioGrupo,
                        this.horasGrupo, this.document, this.grupos);
            } else {
                controladorEditarGrupo.setHorario(txtHorarioGrupo.getText(), this.document, this.grupos, this.grupo);
                controladorEditarGrupo.setHorarioGrupo(horarioGrupo);
                controladorEditarGrupo.setHorasGrupo(horasGrupo);
            }
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        }
    }

    public void llenarHorario(String horario) {
        if (mapas == null) {
            mapas = new Mapas();
        }
        txtHorarioGrupo.setText(horario);
        String[] dias = horario.split("\n");
        for (int i = 0; i < dias.length; i++) {
            int columna = (Integer) mapas.getMapaDias().get((dias[i].split("\\s"))[0]);
            String[] horasGrupo = (dias[i].replace(mapas.getMapaColumnas().get(columna).toString(), "")).trim().split("\\s");
            for (int j = 0; j < horasGrupo.length; j++) {
                int horaInicio = (Integer) mapas.getMapaHoras().get((horasGrupo[j].split("-"))[0].trim());
                int horaFin = (Integer) mapas.getMapaHoras().get((horasGrupo[j].split("-"))[1].trim());
                for (int k = horaInicio; k < horaFin; k++) {
                    this.horasGrupo.get(columna).add(k);
                }
            }
            horarioGrupo[columna] = dias[i];
        }
        String nombreGrupo = this.nombreGrupo;
        String colorGrupo = this.colorGrupo;
        llenarHorario();
        this.nombreGrupo = nombreGrupo;
        this.colorGrupo = colorGrupo;
        for (int i = 0; i < horasGrupo.size(); i++) {
            if (horasGrupo.get(i).size() > 0) {
                for (int j = 0; j < horasGrupo.get(i).size(); j++) {
                    agenda.add(crearTarjetaHorario(i, horasGrupo.get(i).get(j), true, false), i + 1, horasGrupo.get(i).get(j));
                }
            }
        }
    }

}
