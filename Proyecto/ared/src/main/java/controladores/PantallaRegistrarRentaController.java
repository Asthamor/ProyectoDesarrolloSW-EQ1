package controladores;

import clasesApoyo.Mapas;
import clasesApoyo.Mensajes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import static controladores.PantallaGruposController.crearArchivoXML;
import static controladores.PantallaPrincipalDirectorController.crearPantalla;
import interfaces.Controlador;
import interfaces.IControladorRentas;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Cliente;
import modelo.Horario;
import modelo.PagoRenta;
import modelo.Persona;
import modelo.Renta;
import modelo.RentaPK;
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
public class PantallaRegistrarRentaController implements Initializable, Controlador, IControladorRentas {

    @FXML
    private JFXDatePicker txtDia;
    @FXML
    private GridPane gridHorario;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private Label lblFecha;
    @FXML
    private JFXComboBox<String> cmbCliente;
    @FXML
    private JFXTextField txtMonto;
    @FXML
    private ScrollPane scrollHorario;
    @FXML
    private Label lblHorarioRenta;

    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private Mapas mapas;
    private Document document;
    private String dia;
    private List<Persona> clientes;
    private ArrayList<Integer> horarioRenta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        horarioRenta = new ArrayList();
        lblHorarioRenta.setText("");
        crearArchivoXML();
        scrollHorario.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollHorario.setVvalue(0.5);
        SAXReader reader = new SAXReader();
        try {
            document = reader.read(System.getProperty("user.dir") + "/horariosAred.xml");
        } catch (DocumentException ex) {
            Logger.getLogger(PantallaGruposController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mostrarClientes();
        mostrarFechaActual();
    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;

    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    @Override
    public ArrayList<Integer> getHorarioRenta() {
        return horarioRenta;
    }

    @Override
    public void agregarHora(int fila) {
        horarioRenta.add(fila);
        Collections.sort(horarioRenta);
    }

    public void agregarAgenda() {
        gridHorario.setVgap(1);
        gridHorario.setHgap(2);
        int hora = 0;
        for (int i = 0; i < 48; i += 2) {
            StackPane horasEnteras = new StackPane();
            Label lbHoras = new Label(hora + ":00");
            hora++;
            horasEnteras.getChildren().add(lbHoras);
            horasEnteras.setAlignment(lbHoras, Pos.TOP_RIGHT);
            gridHorario.add(horasEnteras, 0, i);
        }

        for (int i = 0; i < 48; i++) {
            gridHorario.add(crearTarjetaHorario("", "", true, false, i), 1, i);
        }
    }
    
    public void mostrarFechaActual() {
        txtDia.setValue(LocalDate.now());
        lblFecha.setText(txtDia.getValue().toString());
        dia = String.valueOf((txtDia.getValue().getDayOfWeek().getValue()) - 1);
        gridHorario.getChildren().clear();
        agregarAgenda();
        mostrarGrupos();
        mostrarRentas();
    }

    public void mostrarClientes() {
        ArrayList<String> nombresClientes = new ArrayList();
        Cliente instanciaMaestro = new Cliente();
        clientes = instanciaMaestro.obtenerTodos();
        clientes.forEach((cliente) -> {
            nombresClientes.add(cliente.getNombre() + " " + cliente.getApellidos());
        });
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(nombresClientes);
        cmbCliente.setItems(items);
    }

    @Override
    public void mostrarHorarios() {
        if (mapas == null) {
            mapas = new Mapas();
        }
        String horario = "";
        if (!horarioRenta.isEmpty()) {
            int ultimoElemnto = horarioRenta.size() - 1;
            horario = mapas.getMapaFilas().get(horarioRenta.get(0)) + "-"
                    + mapas.getMapaFilas().get(horarioRenta.get(ultimoElemnto) + 1);
        }
        lblHorarioRenta.setText(horario);
    }

    public void mostrarGrupos() {
        if (mapas == null) {
            mapas = new Mapas();
        }
        Element root = document.getRootElement();
        List<org.dom4j.Node> grupoXML = root.selectNodes("/ared/grupos/grupo[horario/dia/@num = '" + dia + "']");
        String nombreGrupo;
        String colorGrupo;

        for (org.dom4j.Node grupo : grupoXML) {
            Element grupoElemnt = (Element) grupo;
            nombreGrupo = grupoElemnt.attributeValue("nombreGrupo");
            colorGrupo = grupoElemnt.attributeValue("color");
            Element horario = grupoElemnt.element("horario");
            for (Iterator<Element> itr3 = horario.elementIterator("dia"); itr3.hasNext();) {
                Element dia = itr3.next();
                int columna = Integer.valueOf(dia.attributeValue("num"));
                for (Iterator<Element> itr4 = dia.elementIterator("hora"); itr4.hasNext();) {
                    int horaInicio = (Integer) mapas.getMapaHoras().get((String.valueOf(((Element) itr4.next()).getData())).trim());
                    int horaFin = (Integer) mapas.getMapaHoras().get((String.valueOf(((Element) itr4.next()).getData())).trim());
                    Parent pantalla = crearTarjetaHorario(nombreGrupo, colorGrupo, false, true, horaInicio);
                    gridHorario.add(pantalla, 1, horaInicio, 1, (horaFin - horaInicio));
                }
            }
        }

    }

    public void mostrarRentas() {
        if (mapas == null) {
            mapas = new Mapas();
        }
        List<org.dom4j.Node> rentas = document.selectNodes("/ared/rentas/renta[@dia = '"
                + txtDia.getValue().toString() + "']");
        for (org.dom4j.Node renta : rentas) {
            String nombreCliente = renta.selectSingleNode("cliente").getText();
            String horario = renta.selectSingleNode("horario").getText();
            int horaInicio = Integer.parseInt(mapas.getMapaHoras().get(horario.split("-")[0]).toString());
            int horaFin = Integer.parseInt(mapas.getMapaHoras().get(horario.split("-")[1]).toString());
            Parent pantalla = crearTarjetaHorario(nombreCliente, "#D7BDE2", false, true, horaInicio);
            gridHorario.add(pantalla, 1, horaInicio, 1, (horaFin - horaInicio));
        }

    }

    public Parent crearTarjetaHorario(String nombreEvento, String colorEvento, boolean ocupado, boolean esEvento, int fila) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaRegistrarRentaController.class.getResource("/fxml/TarjetaHorarioRenta.fxml"));
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PantallaDefinirHorarioGrupoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        TarjetaHorarioRentaController controlador = loader.getController();
        controlador.setColorEvento(colorEvento);
        controlador.setFila(fila);
        controlador.setControlador(this);
        controlador.setNombreEvento(nombreEvento);
        controlador.setHoraLibre(ocupado);
        controlador.setEsGrupo(esEvento);
        return root;
    }

    @FXML
    private void mostrarFecha(ActionEvent event) {
        lblFecha.setText(txtDia.getValue().toString());
        dia = String.valueOf((txtDia.getValue().getDayOfWeek().getValue()) - 1);
        gridHorario.getChildren().clear();
        horarioRenta.clear();
        lblHorarioRenta.setText("");
        agregarAgenda();
        mostrarGrupos();
        mostrarRentas();
    }

    @FXML
    private void guardarRenta(ActionEvent event) {
        Renta renta = new Renta();
        renta.setRentaPK(new RentaPK());
        renta.setCliente((Cliente) clientes.get(cmbCliente.getSelectionModel().getSelectedIndex()));
        Horario horario = new Horario();
        horario.setRutaArchivo("/");
        horario.setIdHorario(1);
        renta.setHorario(horario);
        PagoRenta pagoRenta = new PagoRenta();
        pagoRenta.setMonto(Integer.parseInt(txtMonto.getText()));
        pagoRenta.setFecha(Date.from(txtDia.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        renta.setPagoRenta(pagoRenta);
        if (renta.crearRenta()) {
            guardarRentaXML(renta.obtenerUltimaRenta());
            Mensajes.mensajeExitoso("Renta guardada");            
            pnlPrincipal.getChildren().clear();
            pnlPrincipal.getChildren().add(crearPantalla("/fxml/PantallaRentas.fxml",
                    this.pnlPrincipal, this.pantallaDividida));
            pantallaDividida.getChildren().add(pnlPrincipal);
            
        } else {
            Mensajes.mensajeAlert("Lo sentimos, no se puedo realizar la acción");
        }
    }

    public void guardarRentaXML(String idRenta) {
        Element rentas = document.getRootElement().element("rentas");
        Document document = DocumentHelper.createDocument();
        Element rentaXML = document.addElement("renta");
        rentaXML.addAttribute("dia", lblFecha.getText());
        rentaXML.addAttribute("id", idRenta);
        rentaXML.addElement("monto").addText(txtMonto.getText());
        rentaXML.addElement("horario").addText(lblHorarioRenta.getText());
        rentaXML.addElement("cliente").addText(cmbCliente.getValue());
        rentas.add(rentaXML);
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
