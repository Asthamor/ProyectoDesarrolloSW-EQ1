/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.controladores;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import modelo.Grupo;

/**
 *
 * @author mau
 */
@Entity
@Table(name = "asistencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asistencia.findAll", query = "SELECT a FROM Asistencia a")
    , @NamedQuery(name = "Asistencia.findByIdasistencia", query = "SELECT a FROM Asistencia a WHERE a.asistenciaPK.idasistencia = :idasistencia")
    , @NamedQuery(name = "Asistencia.findByFecha", query = "SELECT a FROM Asistencia a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "Asistencia.findByGrupoidGrupo", query = "SELECT a FROM Asistencia a WHERE a.asistenciaPK.grupoidGrupo = :grupoidGrupo")
    , @NamedQuery(name = "Asistencia.findByGrupomaestroidMaestro", query = "SELECT a FROM Asistencia a WHERE a.asistenciaPK.grupomaestroidMaestro = :grupomaestroidMaestro")
    , @NamedQuery(name = "Asistencia.findByGrupomaestrousuarionombreUsuario", query = "SELECT a FROM Asistencia a WHERE a.asistenciaPK.grupomaestrousuarionombreUsuario = :grupomaestrousuarionombreUsuario")
    , @NamedQuery(name = "Asistencia.findByGrupohorarioidHorario", query = "SELECT a FROM Asistencia a WHERE a.asistenciaPK.grupohorarioidHorario = :grupohorarioidHorario")})
public class Asistencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AsistenciaPK asistenciaPK;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumns({
        @JoinColumn(name = "grupo_idGrupo", referencedColumnName = "idGrupo", insertable = false, updatable = false)
        , @JoinColumn(name = "grupo_maestro_idMaestro", referencedColumnName = "maestro_idMaestro", insertable = false, updatable = false)
        , @JoinColumn(name = "grupo_maestro_usuario_nombreUsuario", referencedColumnName = "maestro_usuario_nombreUsuario", insertable = false, updatable = false)
        , @JoinColumn(name = "grupo_horario_idHorario", referencedColumnName = "horario_idHorario", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Grupo grupo;

    public Asistencia() {
    }

    public Asistencia(AsistenciaPK asistenciaPK) {
        this.asistenciaPK = asistenciaPK;
    }

    public Asistencia(int idasistencia, int grupoidGrupo, int grupomaestroidMaestro, String grupomaestrousuarionombreUsuario, int grupohorarioidHorario) {
        this.asistenciaPK = new AsistenciaPK(idasistencia, grupoidGrupo, grupomaestroidMaestro, grupomaestrousuarionombreUsuario, grupohorarioidHorario);
    }

    public AsistenciaPK getAsistenciaPK() {
        return asistenciaPK;
    }

    public void setAsistenciaPK(AsistenciaPK asistenciaPK) {
        this.asistenciaPK = asistenciaPK;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (asistenciaPK != null ? asistenciaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asistencia)) {
            return false;
        }
        Asistencia other = (Asistencia) object;
        if ((this.asistenciaPK == null && other.asistenciaPK != null) || (this.asistenciaPK != null && !this.asistenciaPK.equals(other.asistenciaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.controladores.Asistencia[ asistenciaPK=" + asistenciaPK + " ]";
    }
    
}
