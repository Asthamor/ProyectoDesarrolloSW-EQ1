/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author alonso
 */
@Embeddable
public class PagoMaestroPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idPagoMaestro")
    private int idPagoMaestro;
    @Basic(optional = false)
    @Column(name = "Maestro_idMaestro")
    private int maestroidMaestro;

    public PagoMaestroPK() {
    }

    public PagoMaestroPK(int idPagoMaestro, int maestroidMaestro) {
        this.idPagoMaestro = idPagoMaestro;
        this.maestroidMaestro = maestroidMaestro;
    }

    public int getIdPagoMaestro() {
        return idPagoMaestro;
    }

    public void setIdPagoMaestro(int idPagoMaestro) {
        this.idPagoMaestro = idPagoMaestro;
    }

    public int getMaestroidMaestro() {
        return maestroidMaestro;
    }

    public void setMaestroidMaestro(int maestroidMaestro) {
        this.maestroidMaestro = maestroidMaestro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPagoMaestro;
        hash += (int) maestroidMaestro;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoMaestroPK)) {
            return false;
        }
        PagoMaestroPK other = (PagoMaestroPK) object;
        if (this.idPagoMaestro != other.idPagoMaestro) {
            return false;
        }
        if (this.maestroidMaestro != other.maestroidMaestro) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PagoMaestroPK[ idPagoMaestro=" + idPagoMaestro + ", maestroidMaestro=" + maestroidMaestro + " ]";
    }
    
}
