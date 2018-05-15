/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXButton;
import interfaces.Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import modelo.Renta;
import modelo.RentaXML;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class PantallaRentasController implements Initializable, Controlador {

    @FXML
    private TableView<RentaXML> tbRentas;
    @FXML
    private TableColumn<RentaXML, String> colCliente;
    @FXML
    private TableColumn<RentaXML, String> colFecha;
    @FXML
    private TableColumn<RentaXML, String> colHorario;
    @FXML
    private TableColumn<RentaXML, String> colMonto;
    @FXML
    private JFXButton btnNuevaRenta;
    
    private HBox pantallaDividida;
    private StackPane pnlPrincipal;
    private List<RentaXML> rentas;
    private Document document;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rentas = new ArrayList();
        PantallaGruposController.crearArchivoXML();
        SAXReader reader = new SAXReader();
        try {
            document = reader.read(System.getProperty("user.dir") + "/horariosAred.xml");
        } catch (DocumentException ex) {
            Logger.getLogger(PantallaGruposController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mostrarRentas();
    }

    @FXML
    private void crearRenta(ActionEvent event) {
        PantallaPrincipalDirectorController.limpiarPanelPrincipal(pnlPrincipal, pantallaDividida);
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/PantallaRegistrarRenta.fxml"));
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalDirectorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PantallaRegistrarRentaController controlador = loader.getController();
        controlador.setPantallaDividida(pantallaDividida);
        controlador.setPnlPrincipal(pnlPrincipal);
        pnlPrincipal.getChildren().add(root);
        pantallaDividida.getChildren().add(pnlPrincipal);
    }
    
    public void mostrarRentas(){
        leerRentasXML();
        colCliente.setCellValueFactory(new PropertyValueFactory<RentaXML, String>("nombreCliente"));
        colFecha.setCellValueFactory(new PropertyValueFactory<RentaXML, String>("dia"));
        colHorario.setCellValueFactory(new PropertyValueFactory<RentaXML, String>("horario"));
        colMonto.setCellValueFactory(new PropertyValueFactory<RentaXML, String>("monto"));
        tbRentas.setItems(FXCollections.observableArrayList(rentas));
    }
    
    public void leerRentasXML(){
        List<org.dom4j.Node> nodos = document.selectNodes("/ared/rentas/renta");
        for(org.dom4j.Node renta: nodos){
            RentaXML rentaXML = new RentaXML();
            rentaXML.setId(renta.valueOf("@id"));
            rentaXML.setDia(renta.valueOf("@dia"));
            rentaXML.setNombreCliente(renta.selectSingleNode("cliente").getText());
            rentaXML.setHorario(renta.selectSingleNode("horario").getText());
            rentaXML.setMonto(renta.selectSingleNode("monto").getText());
            rentas.add(rentaXML);
        }
    }

    @Override
    public void setPantallaDividida(HBox pantallaDividida) {
        this.pantallaDividida = pantallaDividida;
    }

    @Override
    public void setPnlPrincipal(StackPane pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

}
