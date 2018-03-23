/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alonso
 */
@Entity
@Table(name = "grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g")
    , @NamedQuery(name = "Grupo.findByIdGrupo", query = "SELECT g FROM Grupo g WHERE g.grupoPK.idGrupo = :idGrupo")
    , @NamedQuery(name = "Grupo.findByMaestroidMaestro", query = "SELECT g FROM Grupo g WHERE g.grupoPK.maestroidMaestro = :maestroidMaestro")
    , @NamedQuery(name = "Grupo.findByHorarioidHorario", query = "SELECT g FROM Grupo g WHERE g.grupoPK.horarioidHorario = :horarioidHorario")
    , @NamedQuery(name = "Grupo.findByEstado", query = "SELECT g FROM Grupo g WHERE g.estado = :estado")
    , @NamedQuery(name = "Grupo.findByFechaCreacion", query = "SELECT g FROM Grupo g WHERE g.fechaCreacion = :fechaCreacion")})
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GrupoPK grupoPK;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @ManyToMany(mappedBy = "grupoCollection")
    private Collection<Alumno> alumnoCollection;
    @JoinColumn(name = "Horario_idHorario", referencedColumnName = "idHorario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Horario horario;
    @JoinColumn(name = "Maestro_idMaestro", referencedColumnName = "idMaestro", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Maestro maestro;

    public Grupo() {
    }

    public Grupo(GrupoPK grupoPK) {
        this.grupoPK = grupoPK;
    }

    public Grupo(int idGrupo, int maestroidMaestro, int horarioidHorario) {
        this.grupoPK = new GrupoPK(idGrupo, maestroidMaestro, horarioidHorario);
    }

    public GrupoPK getGrupoPK() {
        return grupoPK;
    }

    public void setGrupoPK(GrupoPK grupoPK) {
        this.grupoPK = grupoPK;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @XmlTransient
    public Collection<Alumno> getAlumnoCollection() {
        return alumnoCollection;
    }

    public void setAlumnoCollection(Collection<Alumno> alumnoCollection) {
        this.alumnoCollection = alumnoCollection;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Maestro getMaestro() {
        return maestro;
    }

    public void setMaestro(Maestro maestro) {
        this.maestro = maestro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoPK != null ? grupoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.grupoPK == null && other.grupoPK != null) || (this.grupoPK != null && !this.grupoPK.equals(other.grupoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Grupo[ grupoPK=" + grupoPK + " ]";
    }
    
}
