/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.controladores;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author mau
 */
@Embeddable
public class AsistenciaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idasistencia")
    private int idasistencia;
    @Basic(optional = false)
    @Column(name = "grupo_idGrupo")
    private int grupoidGrupo;
    @Basic(optional = false)
    @Column(name = "grupo_maestro_idMaestro")
    private int grupomaestroidMaestro;
    @Basic(optional = false)
    @Column(name = "grupo_maestro_usuario_nombreUsuario")
    private String grupomaestrousuarionombreUsuario;
    @Basic(optional = false)
    @Column(name = "grupo_horario_idHorario")
    private int grupohorarioidHorario;

    public AsistenciaPK() {
    }

    public AsistenciaPK(int idasistencia, int grupoidGrupo, int grupomaestroidMaestro, String grupomaestrousuarionombreUsuario, int grupohorarioidHorario) {
        this.idasistencia = idasistencia;
        this.grupoidGrupo = grupoidGrupo;
        this.grupomaestroidMaestro = grupomaestroidMaestro;
        this.grupomaestrousuarionombreUsuario = grupomaestrousuarionombreUsuario;
        this.grupohorarioidHorario = grupohorarioidHorario;
    }

    public int getIdasistencia() {
        return idasistencia;
    }

    public void setIdasistencia(int idasistencia) {
        this.idasistencia = idasistencia;
    }

    public int getGrupoidGrupo() {
        return grupoidGrupo;
    }

    public void setGrupoidGrupo(int grupoidGrupo) {
        this.grupoidGrupo = grupoidGrupo;
    }

    public int getGrupomaestroidMaestro() {
        return grupomaestroidMaestro;
    }

    public void setGrupomaestroidMaestro(int grupomaestroidMaestro) {
        this.grupomaestroidMaestro = grupomaestroidMaestro;
    }

    public String getGrupomaestrousuarionombreUsuario() {
        return grupomaestrousuarionombreUsuario;
    }

    public void setGrupomaestrousuarionombreUsuario(String grupomaestrousuarionombreUsuario) {
        this.grupomaestrousuarionombreUsuario = grupomaestrousuarionombreUsuario;
    }

    public int getGrupohorarioidHorario() {
        return grupohorarioidHorario;
    }

    public void setGrupohorarioidHorario(int grupohorarioidHorario) {
        this.grupohorarioidHorario = grupohorarioidHorario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idasistencia;
        hash += (int) grupoidGrupo;
        hash += (int) grupomaestroidMaestro;
        hash += (grupomaestrousuarionombreUsuario != null ? grupomaestrousuarionombreUsuario.hashCode() : 0);
        hash += (int) grupohorarioidHorario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsistenciaPK)) {
            return false;
        }
        AsistenciaPK other = (AsistenciaPK) object;
        if (this.idasistencia != other.idasistencia) {
            return false;
        }
        if (this.grupoidGrupo != other.grupoidGrupo) {
            return false;
        }
        if (this.grupomaestroidMaestro != other.grupomaestroidMaestro) {
            return false;
        }
        if ((this.grupomaestrousuarionombreUsuario == null && other.grupomaestrousuarionombreUsuario != null) || (this.grupomaestrousuarionombreUsuario != null && !this.grupomaestrousuarionombreUsuario.equals(other.grupomaestrousuarionombreUsuario))) {
            return false;
        }
        if (this.grupohorarioidHorario != other.grupohorarioidHorario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.controladores.AsistenciaPK[ idasistencia=" + idasistencia + ", grupoidGrupo=" + grupoidGrupo + ", grupomaestroidMaestro=" + grupomaestroidMaestro + ", grupomaestrousuarionombreUsuario=" + grupomaestrousuarionombreUsuario + ", grupohorarioidHorario=" + grupohorarioidHorario + " ]";
    }
    
}
