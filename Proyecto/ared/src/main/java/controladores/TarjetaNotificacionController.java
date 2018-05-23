/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author alonso
 */
public class TarjetaNotificacionController implements Initializable {

    @FXML
    private Label lblNotificacion;
    private String notificacion;
    private String tipoNotificacion;
    @FXML
    private FontAwesomeIconView iconoRenta;
    @FXML
    private FontAwesomeIconView iconoAlumno;
    @FXML
    private FontAwesomeIconView iconoMaestro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setNotificacion(String notificacion) {
        this.notificacion = notificacion;
        lblNotificacion.setText(this.notificacion);
    }

    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
        
        switch (this.tipoNotificacion){
            case "renta":
                iconoRenta.setVisible(true);
                iconoAlumno.setVisible(false);
                iconoMaestro.setVisible(false);
                break;
            case "alumno":
                iconoRenta.setVisible(false);
                iconoAlumno.setVisible(true);
                iconoMaestro.setVisible(false);
                break;
        }
    }
    
    
}
