/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import clasesApoyo.Mensajes;
import interfaces.IPagoMaestro;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import modelo.controladores.PagoMaestroJpaController;

/**
 *
 * @author raymundo
 */
@Entity
@Table(name = "pagoMaestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoMaestro.findAll", query = "SELECT p FROM PagoMaestro p")
    , @NamedQuery(name = "PagoMaestro.findByIdPagoMaestro", query = "SELECT p FROM PagoMaestro p WHERE p.pagoMaestroPK.idPagoMaestro = :idPagoMaestro")
    , @NamedQuery(name = "PagoMaestro.findByMonto", query = "SELECT p FROM PagoMaestro p WHERE p.monto = :monto")
    , @NamedQuery(name = "PagoMaestro.findByFechaPago", query = "SELECT p FROM PagoMaestro p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "PagoMaestro.findByPlazo", query = "SELECT p FROM PagoMaestro p WHERE p.plazo = :plazo")
    , @NamedQuery(name = "PagoMaestro.findByFechaVencimiento", query = "SELECT p FROM PagoMaestro p WHERE p.fechaVencimiento = :fechaVencimiento")
    , @NamedQuery(name = "PagoMaestro.findByMaestroidMaestro", query = "SELECT p FROM PagoMaestro p WHERE p.pagoMaestroPK.maestroidMaestro = :maestroidMaestro")
    , @NamedQuery(name = "PagoMaestro.findByMaestrousuarionombreUsuario", query = "SELECT p FROM PagoMaestro p WHERE p.pagoMaestroPK.maestrousuarionombreUsuario = :maestrousuarionombreUsuario")})
public class PagoMaestro extends Ingreso implements Serializable, IPagoMaestro {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto")
    private Double monto;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PagoMaestroPK pagoMaestroPK;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
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
        super.tipo = "Maestro";
    }

    public PagoMaestro(PagoMaestroPK pagoMaestroPK) {
        super.tipo = "Maestro";
        this.pagoMaestroPK = pagoMaestroPK;
    }

    public PagoMaestro(int idPagoMaestro, int maestroidMaestro, String maestrousuarionombreUsuario) {
        super.tipo = "Maestro";
        this.pagoMaestroPK = new PagoMaestroPK(idPagoMaestro, maestroidMaestro, maestrousuarionombreUsuario);
    }

    public PagoMaestroPK getPagoMaestroPK() {
        return pagoMaestroPK;
    }

    public void setPagoMaestroPK(PagoMaestroPK pagoMaestroPK) {
        this.pagoMaestroPK = pagoMaestroPK;
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

    @Override
    public boolean registrarPago() {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
            PagoMaestroJpaController controlador = new PagoMaestroJpaController(entityManagerFactory);
            PagoMaestroPK pagoPK = new PagoMaestroPK();
            pagoPK.setMaestroidMaestro(maestro.getMaestroPK().getIdMaestro());
            pagoPK.setMaestrousuarionombreUsuario(maestro.getMaestroPK().getUsuarionombreUsuario());
            this.setPagoMaestroPK(pagoPK);

            controlador.create(this);
        } catch (Exception ex) {
            Logger.getLogger(PagoAlumno.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean generarRecibo() {
        Mensajes.mensajeAlert("PagoMaestro.generarRecibo() Por Implementar");
        return true;
    }

    @Override
    public List<Ingreso> obtenerTodos() {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);

        PagoMaestroJpaController controlador = new PagoMaestroJpaController(entityManagerFactory);
        List<PagoMaestro> rentas = controlador.findPagoMaestroEntities();
        List<Ingreso> result = new ArrayList<>();
        for (PagoMaestro p : rentas) {
            result.add((Ingreso) p);
        }
        return result;
    }

    @Override
    public String getNombre() {
        super.nombre = getMaestro().getNombre() + " " + getMaestro().getApellidos();
        return nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        super.tipo = "Maestro";
        return tipo;
    }

    @Override
    public Double getMonto() {
        return monto;
    }

    @Override
    public Date getDate() {
        return fecha;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public List<PagoMaestro> obtenerMaestroPorFechaVencimiento(Date fechaVencimiento) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        PagoMaestroJpaController controlador = new PagoMaestroJpaController(entityManagerFactory);
        return controlador.findByFechaVencimiento(fechaVencimiento);
    }

}
