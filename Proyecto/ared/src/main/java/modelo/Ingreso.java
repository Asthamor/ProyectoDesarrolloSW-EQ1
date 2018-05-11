/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import interfaces.IIngreso;
import java.util.Date;

/**
 *
 * @author mau
 */
public abstract class Ingreso implements IIngreso{
  
  private double monto;
  private Date fecha;
  protected String nombre;
  protected String tipo;

  public Date getDate(){
    return fecha;
  }
  
  
}
