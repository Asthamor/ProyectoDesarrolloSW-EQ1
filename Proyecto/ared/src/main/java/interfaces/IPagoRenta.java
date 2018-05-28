/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import modelo.PagoRenta;

/**
 *
 * @author alonso
 */
public interface IPagoRenta {
    public PagoRenta obtenerUltimoPago();
    public boolean actualizarPago();
    public boolean eliminarPago();
}
