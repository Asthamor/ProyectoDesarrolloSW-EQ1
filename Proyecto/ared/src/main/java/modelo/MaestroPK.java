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
public class MaestroPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idMaestro")
    private int idMaestro;
    @Basic(optional = false)
    @Column(name = "usuario_nombreUsuario")
    private String usuarionombreUsuario;

    public MaestroPK() {
    }

    public MaestroPK(int idMaestro, String usuarionombreUsuario) {
        this.idMaestro = idMaestro;
        this.usuarionombreUsuario = usuarionombreUsuario;
    }

    public int getIdMaestro() {
        return idMaestro;
    }

    public void setIdMaestro(int idMaestro) {
        this.idMaestro = idMaestro;
    }

    public String getUsuarionombreUsuario() {
        return usuarionombreUsuario;
    }

    public void setUsuarionombreUsuario(String usuarionombreUsuario) {
        this.usuarionombreUsuario = usuarionombreUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idMaestro;
        hash += (usuarionombreUsuario != null ? usuarionombreUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaestroPK)) {
            return false;
        }
        MaestroPK other = (MaestroPK) object;
        if (this.idMaestro != other.idMaestro) {
            return false;
        }
        if ((this.usuarionombreUsuario == null && other.usuarionombreUsuario != null) || (this.usuarionombreUsuario != null && !this.usuarionombreUsuario.equals(other.usuarionombreUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MaestroPK[ idMaestro=" + idMaestro + ", usuarionombreUsuario=" + usuarionombreUsuario + " ]";
    }
    
}
