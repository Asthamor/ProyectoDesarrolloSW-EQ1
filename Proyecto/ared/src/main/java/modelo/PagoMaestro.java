/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import modelo.controladores.PagoAlumnoJpaController;
import modelo.controladores.PagoMaestroJpaController;

/**
 *
 * @author raymundo
 */
@Entity
@Table(name = "pagomaestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoMaestro.findAll", query = "SELECT p FROM PagoMaestro p")
    , @NamedQuery(name = "PagoMaestro.findByIdPagoMaestro", query = "SELECT p FROM PagoMaestro p WHERE p.pagoMaestroPK.idPagoMaestro = :idPagoMaestro")
    , @NamedQuery(name = "PagoMaestro.findByMonto", query = "SELECT p FROM PagoMaestro p WHERE p.monto = :monto")
    , @NamedQuery(name = "PagoMaestro.findByFechaPago", query = "SELECT p FROM PagoMaestro p WHERE p.fechaPago = :fechaPago")
    , @NamedQuery(name = "PagoMaestro.findByPlazo", query = "SELECT p FROM PagoMaestro p WHERE p.plazo = :plazo")
    , @NamedQuery(name = "PagoMaestro.findByFechaVencimiento", query = "SELECT p FROM PagoMaestro p WHERE p.fechaVencimiento = :fechaVencimiento")
    , @NamedQuery(name = "PagoMaestro.findByMaestroidMaestro", query = "SELECT p FROM PagoMaestro p WHERE p.pagoMaestroPK.maestroidMaestro = :maestroidMaestro")
    , @NamedQuery(name = "PagoMaestro.findByMaestrousuarionombreUsuario", query = "SELECT p FROM PagoMaestro p WHERE p.pagoMaestroPK.maestrousuarionombreUsuario = :maestrousuarionombreUsuario")})
public class PagoMaestro implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PagoMaestroPK pagoMaestroPK;
    @Column(name = "monto")
    private Integer monto;
    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "plazo")
    private Integer plazo;
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
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

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
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

    public boolean registrarPago() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        PagoMaestroJpaController controlador = new PagoMaestroJpaController(entityManagerFactory);
        PagoMaestroPK pagoPK = new PagoMaestroPK();
        pagoPK.setMaestroidMaestro(maestro.getMaestroPK().getIdMaestro());
        pagoPK.setMaestrousuarionombreUsuario(maestro.getMaestroPK().getUsuarionombreUsuario());
        this.setPagoMaestroPK(pagoPK);
        try {
            controlador.create(this);
        } catch (Exception ex) {
            Logger.getLogger(PagoAlumno.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

}
