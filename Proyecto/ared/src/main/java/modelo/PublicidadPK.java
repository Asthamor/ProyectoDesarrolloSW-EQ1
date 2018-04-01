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
public class PublicidadPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idPublicidad")
    private int idPublicidad;
    @Basic(optional = false)
    @Column(name = "maestro_idMaestro")
    private int maestroidMaestro;
    @Basic(optional = false)
    @Column(name = "maestro_usuario_nombreUsuario")
    private String maestrousuarionombreUsuario;

    public PublicidadPK() {
    }

    public PublicidadPK(int idPublicidad, int maestroidMaestro, String maestrousuarionombreUsuario) {
        this.idPublicidad = idPublicidad;
        this.maestroidMaestro = maestroidMaestro;
        this.maestrousuarionombreUsuario = maestrousuarionombreUsuario;
    }

    public int getIdPublicidad() {
        return idPublicidad;
    }

    public void setIdPublicidad(int idPublicidad) {
        this.idPublicidad = idPublicidad;
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
        hash += (int) idPublicidad;
        hash += (int) maestroidMaestro;
        hash += (maestrousuarionombreUsuario != null ? maestrousuarionombreUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PublicidadPK)) {
            return false;
        }
        PublicidadPK other = (PublicidadPK) object;
        if (this.idPublicidad != other.idPublicidad) {
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
        return "modelo.PublicidadPK[ idPublicidad=" + idPublicidad + ", maestroidMaestro=" + maestroidMaestro + ", maestrousuarionombreUsuario=" + maestrousuarionombreUsuario + " ]";
    }
    
}
