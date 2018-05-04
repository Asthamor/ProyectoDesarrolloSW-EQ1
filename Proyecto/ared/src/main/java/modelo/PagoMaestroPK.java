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
 * @author raymundo
 */
@Embeddable
public class PagoMaestroPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idPagoMaestro")
    private int idPagoMaestro;
    @Basic(optional = false)
    @Column(name = "maestro_idMaestro")
    private int maestroidMaestro;
    @Basic(optional = false)
    @Column(name = "maestro_usuario_nombreUsuario")
    private String maestrousuarionombreUsuario;

    public PagoMaestroPK() {
    }

    public PagoMaestroPK(int idPagoMaestro, int maestroidMaestro, String maestrousuarionombreUsuario) {
        this.idPagoMaestro = idPagoMaestro;
        this.maestroidMaestro = maestroidMaestro;
        this.maestrousuarionombreUsuario = maestrousuarionombreUsuario;
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

    public String getMaestrousuarionombreUsuario() {
        return maestrousuarionombreUsuario;
    }

    public void setMaestrousuarionombreUsuario(String maestrousuarionombreUsuario) {
        this.maestrousuarionombreUsuario = maestrousuarionombreUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPagoMaestro;
        hash += (int) maestroidMaestro;
        hash += (maestrousuarionombreUsuario != null ? maestrousuarionombreUsuario.hashCode() : 0);
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
        if ((this.maestrousuarionombreUsuario == null && other.maestrousuarionombreUsuario != null) || (this.maestrousuarionombreUsuario != null && !this.maestrousuarionombreUsuario.equals(other.maestrousuarionombreUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PagoMaestroPK[ idPagoMaestro=" + idPagoMaestro + ", maestroidMaestro=" + maestroidMaestro + ", maestrousuarionombreUsuario=" + maestrousuarionombreUsuario + " ]";
    }
    
}
