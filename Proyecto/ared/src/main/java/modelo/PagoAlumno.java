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
@Table(name = "pagoAlumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoAlumno.findAll", query = "SELECT p FROM PagoAlumno p")
    , @NamedQuery(name = "PagoAlumno.findByIdPagoAlumno", query = "SELECT p FROM PagoAlumno p WHERE p.pagoAlumnoPK.idPagoAlumno = :idPagoAlumno")
    , @NamedQuery(name = "PagoAlumno.findByAlumnoidAlumno", query = "SELECT p FROM PagoAlumno p WHERE p.pagoAlumnoPK.alumnoidAlumno = :alumnoidAlumno")
    , @NamedQuery(name = "PagoAlumno.findByMonto", query = "SELECT p FROM PagoAlumno p WHERE p.monto = :monto")
    , @NamedQuery(name = "PagoAlumno.findByFechaPago", query = "SELECT p FROM PagoAlumno p WHERE p.fechaPago = :fechaPago")
    , @NamedQuery(name = "PagoAlumno.findByPlazo", query = "SELECT p FROM PagoAlumno p WHERE p.plazo = :plazo")})
public class PagoAlumno implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PagoAlumnoPK pagoAlumnoPK;
    @Basic(optional = false)
    @Column(name = "monto")
    private int monto;
    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "plazo")
    private Integer plazo;
    @JoinColumn(name = "Alumno_idAlumno", referencedColumnName = "idAlumno", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Alumno alumno;
    @JoinColumn(name = "Promoci\u00f3n_idPromoci\u00f3n", referencedColumnName = "idPromoci\u00f3n")
    @ManyToOne(optional = false)
    private Promocion promociónidPromoción;

    public PagoAlumno() {
    }

    public PagoAlumno(PagoAlumnoPK pagoAlumnoPK) {
        this.pagoAlumnoPK = pagoAlumnoPK;
    }

    public PagoAlumno(PagoAlumnoPK pagoAlumnoPK, int monto) {
        this.pagoAlumnoPK = pagoAlumnoPK;
        this.monto = monto;
    }

    public PagoAlumno(int idPagoAlumno, int alumnoidAlumno) {
        this.pagoAlumnoPK = new PagoAlumnoPK(idPagoAlumno, alumnoidAlumno);
    }

    public PagoAlumnoPK getPagoAlumnoPK() {
        return pagoAlumnoPK;
    }

    public void setPagoAlumnoPK(PagoAlumnoPK pagoAlumnoPK) {
        this.pagoAlumnoPK = pagoAlumnoPK;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Promocion getPromociónidPromoción() {
        return promociónidPromoción;
    }

    public void setPromociónidPromoción(Promocion promociónidPromoción) {
        this.promociónidPromoción = promociónidPromoción;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pagoAlumnoPK != null ? pagoAlumnoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoAlumno)) {
            return false;
        }
        PagoAlumno other = (PagoAlumno) object;
        if ((this.pagoAlumnoPK == null && other.pagoAlumnoPK != null) || (this.pagoAlumnoPK != null && !this.pagoAlumnoPK.equals(other.pagoAlumnoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PagoAlumno[ pagoAlumnoPK=" + pagoAlumnoPK + " ]";
    }
    
}