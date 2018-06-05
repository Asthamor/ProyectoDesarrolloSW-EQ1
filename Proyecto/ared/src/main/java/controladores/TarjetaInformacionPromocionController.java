/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import modelo.Promocion;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class TarjetaInformacionPromocionController implements Initializable {

    @FXML
    private Label lblNombrePromocion;
    @FXML
    private JFXTextArea txtDescripcionPromocion;
    @FXML
    private Label lblPorcentajePromocion;
    
    private Promocion promocion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtDescripcionPromocion.setEditable(false);
    }

    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
        mostrarInformacionPromocion();
    }

    public void mostrarInformacionPromocion(){
        lblNombrePromocion.setText(promocion.getCodigo());
        txtDescripcionPromocion.setText(promocion.getConcepto());
        lblPorcentajePromocion.setText("% "+String.valueOf(promocion.getDescuento()));
    }
    
}
