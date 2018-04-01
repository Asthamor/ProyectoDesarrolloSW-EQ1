/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

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

/**
 *
 * @author alonso
 */
@Entity
@Table(name = "pagoMaestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoMaestro.findAll", query = "SELECT p FROM PagoMaestro p")
    , @NamedQuery(name = "PagoMaestro.findByIdPagoMaestro", query = "SELECT p FROM PagoMaestro p WHERE p.pagoMaestroPK.idPagoMaestro = :idPagoMaestro")
    , @NamedQuery(name = "PagoMaestro.findByMonto", query = "SELECT p FROM PagoMaestro p WHERE p.monto = :monto")
    , @NamedQuery(name = "PagoMaestro.findByFecha", query = "SELECT p FROM PagoMaestro p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "PagoMaestro.findByPlazo", query = "SELECT p FROM PagoMaestro p WHERE p.plazo = :plazo")
    , @NamedQuery(name = "PagoMaestro.findByMaestroidMaestro", query = "SELECT p FROM PagoMaestro p WHERE p.pagoMaestroPK.maestroidMaestro = :maestroidMaestro")
    , @NamedQuery(name = "PagoMaestro.findByMaestrousuarionombreUsuario", query = "SELECT p FROM PagoMaestro p WHERE p.pagoMaestroPK.maestrousuarionombreUsuario = :maestrousuarionombreUsuario")})
public class PagoMaestro implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PagoMaestroPK pagoMaestroPK;
    @Column(name = "monto")
    private Integer monto;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "plazo")
    private Integer plazo;
    @JoinColumns({
        @JoinColumn(name = "maestro_idMaestro", referencedColumnName = "idMaestro", insertable = false, updatable = false)
        , @JoinColumn(name = "maestro_usuario_nombreUsuario", referencedColumnName = "usuario_nombreUsuario", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Maestro maestro;

    public PagoMaestro() {
    }

    public PagoMaestro(PagoMaestroPK pagoMaestroPK) {
        this.pagoMaestroPK = pagoMaestroPK;
    }

    public PagoMaestro(int idPagoMaestro, int maestroidMaestro, String maestrousuarionombreUsuario) {
        this.pagoMaestroPK = new PagoMaestroPK(idPagoMaestro, maestroidMaestro, maestrousuarionombreUsuario);
    }

    public PagoMaestroPK getPagoMaestroPK() {
        return pagoMaestroPK;
    }

    public void setPagoMaestroPK(PagoMaestroPK pagoMaestroPK) {
        this.pagoMaestroPK = pagoMaestroPK;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
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
        hash += (pagoMaestroPK != null ? pagoMaestroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoMaestro)) {
            return false;
        }
        PagoMaestro other = (PagoMaestro) object;
        if ((this.pagoMaestroPK == null && other.pagoMaestroPK != null) || (this.pagoMaestroPK != null && !this.pagoMaestroPK.equals(other.pagoMaestroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PagoMaestro[ pagoMaestroPK=" + pagoMaestroPK + " ]";
    }
    
}
