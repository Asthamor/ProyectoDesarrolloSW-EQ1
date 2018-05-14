/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.Date;
import java.util.List;
import modelo.Renta;

/**
 *
 * @author alonso
 */
public interface IRenta {
    public List<Renta> obtenerTodaRentas();
    public boolean crearRenta();
    public boolean editarRenta();
    public List<Renta> buscarRenta(Date fecha);
}
