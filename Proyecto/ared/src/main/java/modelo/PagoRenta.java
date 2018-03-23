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
@Table(name = "pagoRenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoRenta.findAll", query = "SELECT p FROM PagoRenta p")
    , @NamedQuery(name = "PagoRenta.findByIdPago", query = "SELECT p FROM PagoRenta p WHERE p.idPago = :idPago")
    , @NamedQuery(name = "PagoRenta.findByMonto", query = "SELECT p FROM PagoRenta p WHERE p.monto = :monto")
    , @NamedQuery(name = "PagoRenta.findByFecha", query = "SELECT p FROM PagoRenta p WHERE p.fecha = :fecha")})
public class PagoRenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idPago")
    private Integer idPago;
    @Basic(optional = false)
    @Column(name = "monto")
    private int monto;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pagoRenta")
    private Collection<Renta> rentaCollection;

    public PagoRenta() {
    }

    public PagoRenta(Integer idPago) {
        this.idPago = idPago;
    }

    public PagoRenta(Integer idPago, int monto) {
        this.idPago = idPago;
        this.monto = monto;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
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
    public Collection<Renta> getRentaCollection() {
        return rentaCollection;
    }

    public void setRentaCollection(Collection<Renta> rentaCollection) {
        this.rentaCollection = rentaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPago != null ? idPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoRenta)) {
            return false;
        }
        PagoRenta other = (PagoRenta) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PagoRenta[ idPago=" + idPago + " ]";
    }
    
}
