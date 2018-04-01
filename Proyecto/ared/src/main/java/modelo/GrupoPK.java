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
public class GrupoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idGrupo")
    private int idGrupo;
    @Basic(optional = false)
    @Column(name = "maestro_idMaestro")
    private int maestroidMaestro;
    @Basic(optional = false)
    @Column(name = "maestro_usuario_nombreUsuario")
    private String maestrousuarionombreUsuario;
    @Basic(optional = false)
    @Column(name = "horario_idHorario")
    private int horarioidHorario;

    public GrupoPK() {
    }

    public GrupoPK(int idGrupo, int maestroidMaestro, String maestrousuarionombreUsuario, int horarioidHorario) {
        this.idGrupo = idGrupo;
        this.maestroidMaestro = maestroidMaestro;
        this.maestrousuarionombreUsuario = maestrousuarionombreUsuario;
        this.horarioidHorario = horarioidHorario;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
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

    public int getHorarioidHorario() {
        return horarioidHorario;
    }

    public void setHorarioidHorario(int horarioidHorario) {
        this.horarioidHorario = horarioidHorario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idGrupo;
        hash += (int) maestroidMaestro;
        hash += (maestrousuarionombreUsuario != null ? maestrousuarionombreUsuario.hashCode() : 0);
        hash += (int) horarioidHorario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoPK)) {
            return false;
        }
        GrupoPK other = (GrupoPK) object;
        if (this.idGrupo != other.idGrupo) {
            return false;
        }
        if (this.maestroidMaestro != other.maestroidMaestro) {
            return false;
        }
        if ((this.maestrousuarionombreUsuario == null && other.maestrousuarionombreUsuario != null) || (this.maestrousuarionombreUsuario != null && !this.maestrousuarionombreUsuario.equals(other.maestrousuarionombreUsuario))) {
            return false;
        }
        if (this.horarioidHorario != other.horarioidHorario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.GrupoPK[ idGrupo=" + idGrupo + ", maestroidMaestro=" + maestroidMaestro + ", maestrousuarionombreUsuario=" + maestrousuarionombreUsuario + ", horarioidHorario=" + horarioidHorario + " ]";
    }
    
}
