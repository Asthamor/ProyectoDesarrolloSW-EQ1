/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alonso
 */
@Entity
@Table(name = "pagoAlumnoExterno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoAlumnoExterno.findAll", query = "SELECT p FROM PagoAlumnoExterno p")
    , @NamedQuery(name = "PagoAlumnoExterno.findByIdPagoAlExterno", query = "SELECT p FROM PagoAlumnoExterno p WHERE p.pagoAlumnoExternoPK.idPagoAlExterno = :idPagoAlExterno")
    , @NamedQuery(name = "PagoAlumnoExterno.findByMaestroidMaestro", query = "SELECT p FROM PagoAlumnoExterno p WHERE p.pagoAlumnoExternoPK.maestroidMaestro = :maestroidMaestro")
    , @NamedQuery(name = "PagoAlumnoExterno.findByAlumnoidAlumno", query = "SELECT p FROM PagoAlumnoExterno p WHERE p.pagoAlumnoExternoPK.alumnoidAlumno = :alumnoidAlumno")
    , @NamedQuery(name = "PagoAlumnoExterno.findByMonto", query = "SELECT p FROM PagoAlumnoExterno p WHERE p.monto = :monto")
    , @NamedQuery(name = "PagoAlumnoExterno.findByFecha", query = "SELECT p FROM PagoAlumnoExterno p WHERE p.fecha = :fecha")})
public class PagoAlumnoExterno implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PagoAlumnoExternoPK pagoAlumnoExternoPK;
    @Basic(optional = false)
    @Column(name = "monto")
    private int monto;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "Alumno_idAlumno", referencedColumnName = "idAlumno", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Alumno alumno;
    @JoinColumn(name = "Maestro_idMaestro", referencedColumnName = "idMaestro", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Maestro maestro;

    public PagoAlumnoExterno() {
    }

    public PagoAlumnoExterno(PagoAlumnoExternoPK pagoAlumnoExternoPK) {
        this.pagoAlumnoExternoPK = pagoAlumnoExternoPK;
    }

    public PagoAlumnoExterno(PagoAlumnoExternoPK pagoAlumnoExternoPK, int monto) {
        this.pagoAlumnoExternoPK = pagoAlumnoExternoPK;
        this.monto = monto;
    }

    public PagoAlumnoExterno(int idPagoAlExterno, int maestroidMaestro, int alumnoidAlumno) {
        this.pagoAlumnoExternoPK = new PagoAlumnoExternoPK(idPagoAlExterno, maestroidMaestro, alumnoidAlumno);
    }

    public PagoAlumnoExternoPK getPagoAlumnoExternoPK() {
        return pagoAlumnoExternoPK;
    }

    public void setPagoAlumnoExternoPK(PagoAlumnoExternoPK pagoAlumnoExternoPK) {
        this.pagoAlumnoExternoPK = pagoAlumnoExternoPK;
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

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
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
        hash += (pagoAlumnoExternoPK != null ? pagoAlumnoExternoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoAlumnoExterno)) {
            return false;
        }
        PagoAlumnoExterno other = (PagoAlumnoExterno) object;
        if ((this.pagoAlumnoExternoPK == null && other.pagoAlumnoExternoPK != null) || (this.pagoAlumnoExternoPK != null && !this.pagoAlumnoExternoPK.equals(other.pagoAlumnoExternoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PagoAlumnoExterno[ pagoAlumnoExternoPK=" + pagoAlumnoExternoPK + " ]";
    }
    
}
