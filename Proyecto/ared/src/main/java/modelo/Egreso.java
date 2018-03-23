/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "egreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Egreso.findAll", query = "SELECT e FROM Egreso e")
    , @NamedQuery(name = "Egreso.findByIdEgreso", query = "SELECT e FROM Egreso e WHERE e.idEgreso = :idEgreso")
    , @NamedQuery(name = "Egreso.findByConcepto", query = "SELECT e FROM Egreso e WHERE e.concepto = :concepto")
    , @NamedQuery(name = "Egreso.findByMonto", query = "SELECT e FROM Egreso e WHERE e.monto = :monto")
    , @NamedQuery(name = "Egreso.findByFecha", query = "SELECT e FROM Egreso e WHERE e.fecha = :fecha")})
public class Egreso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idEgreso")
    private Integer idEgreso;
    @Basic(optional = false)
    @Column(name = "concepto")
    private String concepto;
    @Basic(optional = false)
    @Column(name = "monto")
    private int monto;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "egresoidEgreso")
    private Collection<Publicidad> publicidadCollection;

    public Egreso() {
    }

    public Egreso(Integer idEgreso) {
        this.idEgreso = idEgreso;
    }

    public Egreso(Integer idEgreso, String concepto, int monto, Date fecha) {
        this.idEgreso = idEgreso;
        this.concepto = concepto;
        this.monto = monto;
        this.fecha = fecha;
    }

    public Integer getIdEgreso() {
        return idEgreso;
    }

    public void setIdEgreso(Integer idEgreso) {
        this.idEgreso = idEgreso;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @XmlTransient
    public Collection<Publicidad> getPublicidadCollection() {
        return publicidadCollection;
    }

    public void setPublicidadCollection(Collection<Publicidad> publicidadCollection) {
        this.publicidadCollection = publicidadCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEgreso != null ? idEgreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Egreso)) {
            return false;
        }
        Egreso other = (Egreso) object;
        if ((this.idEgreso == null && other.idEgreso != null) || (this.idEgreso != null && !this.idEgreso.equals(other.idEgreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Egreso[ idEgreso=" + idEgreso + " ]";
    }
    
}
