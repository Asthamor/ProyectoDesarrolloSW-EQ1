/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasesApoyo;

import java.util.HashMap;

/**
 *
 * @author alonso
 */
public class Mapas {
    private  HashMap mapaFilas = new HashMap();
    private  HashMap mapaColumnas = new HashMap();
    private  HashMap mapaDias = new HashMap();
    private HashMap mapaHoras = new HashMap();
    private String[] horas = new String[48];
    
    
    public Mapas(){
        obtenerHoras();
        valoresHorario();
        valoresDiasSemana();
        
    }
    
    public void obtenerHoras() {
        int hora = 0;
        for (int i = 0; i < 48; i += 2) {
            horas[i] = hora + ":00";
            horas[i + 1] = hora + ":30";
            hora++;
        }
    }
    
    public void valoresHorario() {
        for (int i = 0; i < 48; i += 2) {
            mapaHoras.put(horas[i], i);
            mapaHoras.put(horas[i + 1], i + 1);
            mapaFilas.put(i, horas[i]);
            mapaFilas.put(i + 1, horas[i + 1]);
        }
    }
    
    public void valoresDiasSemana() {
        String[] diasSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        for (int i = 0; i < 7; i++) {
            mapaColumnas.put(i, diasSemana[i]);
            mapaDias.put(diasSemana[i], i);
        }
    }

    public HashMap getMapaFilas() {
        return mapaFilas;
    }

    public HashMap getMapaColumnas() {
        return mapaColumnas;
    }

    public HashMap getMapaDias() {
        return mapaDias;
    }

    public HashMap getMapaHoras() {
        return mapaHoras;
    }

    public String[] getHoras() {
        return horas;
    }
    
    
}
