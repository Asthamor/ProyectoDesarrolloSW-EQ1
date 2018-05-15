/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.ArrayList;

/**
 *
 * @author alonso
 */
public interface IControladorRentas {
    public ArrayList<Integer> getHorarioRenta();
     public void agregarHora(int fila);
     public void mostrarHorarios();
}
