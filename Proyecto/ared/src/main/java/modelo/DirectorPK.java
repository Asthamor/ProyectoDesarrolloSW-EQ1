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
public class DirectorPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idDirector")
    private int idDirector;
    @Basic(optional = false)
    @Column(name = "usuario_nombreUsuario")
    private String usuarionombreUsuario;

    public DirectorPK() {
    }

    public DirectorPK(int idDirector, String usuarionombreUsuario) {
        this.idDirector = idDirector;
        this.usuarionombreUsuario = usuarionombreUsuario;
    }

    public int getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
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
        hash += (int) idDirector;
        hash += (usuarionombreUsuario != null ? usuarionombreUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DirectorPK)) {
            return false;
        }
        DirectorPK other = (DirectorPK) object;
        if (this.idDirector != other.idDirector) {
            return false;
        }
        if ((this.usuarionombreUsuario == null && other.usuarionombreUsuario != null) || (this.usuarionombreUsuario != null && !this.usuarionombreUsuario.equals(other.usuarionombreUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.DirectorPK[ idDirector=" + idDirector + ", usuarionombreUsuario=" + usuarionombreUsuario + " ]";
    }
    
}
