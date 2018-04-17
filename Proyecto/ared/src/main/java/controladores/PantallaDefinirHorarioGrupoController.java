package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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

    private String[] horas;
    private String nombreGrupo;
    private String colorGrupo;
    private HashMap mapaHoras;
    private static HashMap mapaFilas = new HashMap();
    private static HashMap mapaColumnas = new HashMap();
    private ArrayList<ArrayList<Integer>> horasGrupo = new ArrayList<ArrayList<Integer>>();
    private String[] horarioGrupo = new String[6];
    private boolean editarGrupo = false;
    private String idGrupoEditar = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int i = 0; i < 6; i++) {
            horasGrupo.add(new ArrayList<>());
            horarioGrupo[i] = "";
        }
        scrollAgenda.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        pnlAgenda.setAlignment(agenda, Pos.CENTER);
        scrollAgenda.setVvalue(0.5);
        horas = obtenerHoras();
        mapaHoras = valoresHorario();
        valoresDiasSemana();
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

        for (int j = 0; j < 6; j++) {
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
            SAXReader reader = new SAXReader();
            Document document = reader.read("/home/alonso/Downloads/grupoXML.xml");
            Element root = document.getRootElement();
            Element grupos = root.element("grupos");
            String idGrupo;
            for (Iterator<Element> iter = root.elementIterator("grupos"); iter.hasNext();) {
                Element innerNode = iter.next();
                for (Iterator<Element> iter2 = innerNode.elementIterator("grupo"); iter2.hasNext();) {
                    Element grupo = iter2.next();
                    nombreGrupo = grupo.attributeValue("nombreGrupo");
                    colorGrupo = grupo.attributeValue("color");
                    idGrupo = grupo.attributeValue("id");
                    Element horario = grupo.element("horario");
                    for (Iterator<Element> itr3 = horario.elementIterator("dia"); itr3.hasNext();) {
                        Element dia = itr3.next();
                        int columna = Integer.valueOf(dia.attributeValue("num"));
                        if (this.editarGrupo && this.idGrupoEditar.equals(idGrupo)) {
                            horarioGrupo[columna] = mapaColumnas.get(columna) + " ";
                        }
                        for (Iterator<Element> itr4 = dia.elementIterator("hora"); itr4.hasNext();) {                                                        
                            int horaIncio = (Integer) mapaHoras.get((String.valueOf(((Element) itr4.next()).getData())).trim());
                            int horaFin = (Integer) mapaHoras.get((String.valueOf(((Element) itr4.next()).getData())).trim());
                            Parent pantalla = crearTarjetaHorario(columna,horaIncio , false, false);
                            if (editarGrupo && this.idGrupoEditar.equals(idGrupo)) {
                                
                                horarioGrupo[columna] += mapaFilas.get(horaIncio) + "-" + mapaFilas.get(horaFin) + ", ";
                                for (int i = horaIncio; i < horaFin; i++) {
                                    horasGrupo.get(columna).add(i);
                                    agenda.add(crearTarjetaHorario(columna, i, true, false), columna + 1, i);
                                }                                
                            } else {                                
                                agenda.add(pantalla, columna + 1, horaIncio, 1, (horaFin - horaIncio));
                            }
                            
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

    public HashMap valoresHorario() {
        HashMap hm = new HashMap();
        for (int i = 0; i < 48; i += 2) {
            hm.put(horas[i], i);
            hm.put(horas[i + 1], i + 1);
            mapaFilas.put(i, horas[i]);
            mapaFilas.put(i + 1, horas[i + 1]);
        }
        return hm;
    }

    public void valoresDiasSemana() {
        String[] diasSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
        for (int i = 0; i < 6; i++) {
            mapaColumnas.put(i, diasSemana[i]);
        }
    }
    
    public Parent crearTarjetaHorario(int columna,int fila,boolean horarioEditable,boolean horarioLibre){
        Parent root = null;
                FXMLLoader loader = new FXMLLoader(PantallaDefinirHorarioGrupoController.class.getResource("TarjetaHorario.fxml"));
                try {
                    root = (Parent) loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(PantallaDefinirHorarioGrupoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                TarjetaHorarioController controlador = loader.getController();
                controlador.setColorGrupo(this.colorGrupo);
                controlador.setNombreGrupo(this.nombreGrupo);
                controlador.setMapaColumnas(mapaColumnas);
                controlador.setMapaFilas(mapaFilas);
                controlador.setColumna(columna);
                controlador.setFila(fila);
                controlador.setTxtHorarioGrupo(txtHorarioGrupo);
                controlador.setHorasGrupo(horasGrupo);
                controlador.setHorarioGrupo(horarioGrupo);
                controlador.setHorarioLibre(horarioLibre);
                controlador.setHorarioEditable(horarioEditable);
                return root;
    }
}
