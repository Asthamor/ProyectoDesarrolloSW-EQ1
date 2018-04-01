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
public class PromocionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idPromocion")
    private int idPromocion;
    @Basic(optional = false)
    @Column(name = "maestro_idMaestro")
    private int maestroidMaestro;
    @Basic(optional = false)
    @Column(name = "maestro_usuario_nombreUsuario")
    private String maestrousuarionombreUsuario;

    public PromocionPK() {
    }

    public PromocionPK(int idPromocion, int maestroidMaestro, String maestrousuarionombreUsuario) {
        this.idPromocion = idPromocion;
        this.maestroidMaestro = maestroidMaestro;
        this.maestrousuarionombreUsuario = maestrousuarionombreUsuario;
    }

    public int getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
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
        hash += (int) idPromocion;
        hash += (int) maestroidMaestro;
        hash += (maestrousuarionombreUsuario != null ? maestrousuarionombreUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PromocionPK)) {
            return false;
        }
        PromocionPK other = (PromocionPK) object;
        if (this.idPromocion != other.idPromocion) {
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
        return "modelo.PromocionPK[ idPromocion=" + idPromocion + ", maestroidMaestro=" + maestroidMaestro + ", maestrousuarionombreUsuario=" + maestrousuarionombreUsuario + " ]";
    }
    
}
