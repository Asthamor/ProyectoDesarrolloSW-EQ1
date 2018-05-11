/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import modelo.Ingreso;

/**
 *
 * @author mau
 */
public interface IIngreso {
  public boolean registrarPago();
  public List<Ingreso> obtenerTodos();
  public boolean generarRecibo();
  public String getNombre();
}
