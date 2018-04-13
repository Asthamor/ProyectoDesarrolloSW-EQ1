/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import modelo.Horario;

/**
 *
 * @author raymundo
 */
public interface IHorario {
    public Horario obtenerRutaHorario();
    public boolean crearHorario(Horario horario);
    
}
