/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesApoyo;

import controladores.PantallaGruposController;
import controladores.PantallaPrincipalDirectorController;
import controladores.TarjetaNotificacionController;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import modelo.Alumno;
import modelo.Grupo;
import modelo.Maestro;
import modelo.PagoAlumno;
import modelo.PagoMaestro;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 *
 * @author alonso
 */
public class Notificaciones {

    private int diasAntelacion;
    private boolean notificacionesDirector;
    private Maestro maestro;
    private Date dia;

    public Notificaciones(int diasAntelacion, boolean notificacionesDirector) {
        this.diasAntelacion = diasAntelacion;
        this.notificacionesDirector = notificacionesDirector;
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(new Date());
        calendario.add(Calendar.DATE, 1);
        dia = calendario.getTime();

    }

    public void setMaestro(Maestro maestro) {
        this.maestro = maestro;
    }

    public List<StackPane> buscarNotificaciones() {
        List<StackPane> notificaciones = new ArrayList();
        if (notificacionesDirector) {
            notificaciones = buscarNotificionesDirector();
        } else {
            notificaciones = buscarNotificionesMaestro();
            if (notificaciones.size() == 0) {
                StackPane panel = new StackPane();
                panel.getChildren().add(crearPantalla("Por el momento no tiene notificaciones", ""));
                notificaciones.add(panel);
            }
        }

        return notificaciones;
    }

    public List<StackPane> buscarNotificionesDirector() {
        List<StackPane> notificaciones = new ArrayList();
        notificaciones.addAll(buscarNotificionesMaestro());
        notificaciones.addAll(buscarProximasRentas());
        notificaciones.addAll(buscarNotificacionPagoMaestro());
        if (notificaciones.size() == 0) {
            StackPane panel = new StackPane();
            panel.getChildren().add(crearPantalla("Por el momento no tiene notificaciones", ""));
            notificaciones.add(panel);
        }
        return notificaciones;
    }

    public List<StackPane> buscarProximasRentas() {
        List<StackPane> notificaciones = new ArrayList();
        LocalDate date = dia.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Document document = null;
        SAXReader reader = new SAXReader();
        try {
            document = reader.read(System.getProperty("user.dir") + "/horariosAred.xml");
        } catch (DocumentException ex) {
            Logger.getLogger(PantallaGruposController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<org.dom4j.Node> rentas = document.selectNodes("/ared/rentas/renta[@dia = '"
                + date.toString() + "']");
        for (org.dom4j.Node renta : rentas) {
            String nombreCliente = renta.selectSingleNode("cliente").getText();
            String horario = renta.selectSingleNode("horario").getText();
            StackPane panelNotificacion = new StackPane();
            String cadena = nombreCliente + " ocupará\n el espacio el día "
                    + DateFormat.getDateInstance().format(dia) + " de " + horario;
            panelNotificacion.getChildren().add(crearPantalla(cadena, "renta"));
            notificaciones.add(panelNotificacion);
        }
        return notificaciones;
    }

    public List<StackPane> buscarNotificacionPagoMaestro() {
        List<StackPane> notificaciones = new ArrayList();
        PagoMaestro pagoMaestro = new PagoMaestro();
//        LocalDate local = LocalDate.parse("2018-06-23");
//        Date date = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<PagoMaestro> pagos = (pagoMaestro.obtenerMaestroPorFechaVencimiento(dia));
        for (PagoMaestro pago : pagos) {
            StackPane panel = new StackPane();
            String cadena = pago.getMaestro().getNombre() + " " + pago.getMaestro().getApellidos()
                    + " debe pagar \nel día " + DateFormat.getDateInstance().format(dia);
            panel.getChildren().add(crearPantalla(cadena, "maestro"));
            notificaciones.add(panel);
        }

        return notificaciones;
    }

    public List<StackPane> buscarNotificionesMaestro() {
        List<StackPane> notificaciones = new ArrayList();
        List<Grupo> gruposMaestro = new ArrayList(maestro.getGrupoCollection());
        List<PagoAlumno> pagos = new ArrayList();
        for (Grupo grupo : gruposMaestro) {
            pagos.addAll(new ArrayList(grupo.getPagoAlumnoCollection()));
        }

//        LocalDate local = LocalDate.parse("2019-06-03");
//        Date date = Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<PagoAlumno> pagosProximos = new ArrayList();
        List<PagoAlumno> pagosInscripcion = new ArrayList();
        
        for (PagoAlumno pago : pagos) {
            if (dia.equals(pago.getFechaVencimiento()) && alumnoEnGrupo(gruposMaestro, pago.getAlumno())) {
                pagosProximos.add(pago);
            }
            
            if (pago.getEsInscripcion() && dia.equals(pago.getFechaVencimiento()) && alumnoEnGrupo(gruposMaestro, pago.getAlumno())) {
                pagosInscripcion.add(pago);
            }            
        }

        for (PagoAlumno pago : pagosProximos) {
            StackPane panelNotificacion = new StackPane();
            String cadena = pago.getAlumno().getNombre() + " " + pago.getAlumno().getApellidos()
                    + " del grupo " + pago.getGrupo().getNombre() + " \ndebe pagar el día "
                    + DateFormat.getDateInstance().format(dia);
            panelNotificacion.getChildren().add(crearPantalla(cadena, "alumno"));
            notificaciones.add(panelNotificacion);
        }
        
        for(PagoAlumno pago: pagosInscripcion){
            StackPane panelNotificacion = new StackPane();
            String cadena = pago.getAlumno().getNombre() + " " + pago.getAlumno().getApellidos()
                    + " del grupo " + pago.getGrupo().getNombre() + " \ndebe pagar reinscripción el día "
                    + DateFormat.getDateInstance().format(dia);
            panelNotificacion.getChildren().add(crearPantalla(cadena, "alumno"));
            notificaciones.add(panelNotificacion);
        }

        return notificaciones;
    }

    public boolean alumnoEnGrupo(List<Grupo> gruposMaestro, Alumno alumno) {
        boolean estaEnGrupo = false;
        for (Grupo grupo : gruposMaestro) {
            List<Alumno> alumnos = new ArrayList(grupo.getAlumnoCollection());
            if (alumnos.contains(alumno)) {
                estaEnGrupo = true;
            }
        }

        return estaEnGrupo;
    }

    public Parent crearPantalla(String cadena, String tipoNotificacion) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(PantallaPrincipalDirectorController.class.getResource("/fxml/TarjetaNotificacion.fxml"));
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(PantallaPrincipalDirectorController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        TarjetaNotificacionController controlador = loader.getController();
        controlador.setNotificacion(cadena);
        controlador.setTipoNotificacion(tipoNotificacion);
        return root;
    }
}
