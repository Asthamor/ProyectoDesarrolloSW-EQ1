/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import interfaces.IPagoAlumno;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
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
import modelo.controladores.PromocionJpaController;

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
    , @NamedQuery(name = "PagoAlumno.findByMonto", query = "SELECT p FROM PagoAlumno p WHERE p.monto = :monto")
    , @NamedQuery(name = "PagoAlumno.findByFechaPago", query = "SELECT p FROM PagoAlumno p WHERE p.fechaPago = :fechaPago")
    , @NamedQuery(name = "PagoAlumno.findByPlazo", query = "SELECT p FROM PagoAlumno p WHERE p.plazo = :plazo")
    , @NamedQuery(name = "PagoAlumno.findByAlumnoidAlumno", query = "SELECT p FROM PagoAlumno p WHERE p.pagoAlumnoPK.alumnoidAlumno = :alumnoidAlumno")})
public class PagoAlumno implements Serializable, IPagoAlumno {

    @JoinColumns({
        @JoinColumn(name = "grupo_idGrupo", referencedColumnName = "idGrupo", insertable = false, updatable = false)
        , @JoinColumn(name = "grupo_maestro_idMaestro", referencedColumnName = "maestro_idMaestro", insertable = false, updatable = false)
        , @JoinColumn(name = "grupo_maestro_usuario_nombreUsuario", referencedColumnName = "maestro_usuario_nombreUsuario", insertable = false, updatable = false)
        , @JoinColumn(name = "grupo_horario_idHorario", referencedColumnName = "horario_idHorario", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Grupo grupo;

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
    @JoinColumn(name = "alumno_idAlumno", referencedColumnName = "idAlumno", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Alumno alumno;
    @JoinColumns({
        @JoinColumn(name = "promocion_idPromocion", referencedColumnName = "idPromocion")
        , @JoinColumn(name = "promocion_maestro_idMaestro", referencedColumnName = "maestro_idMaestro")
        , @JoinColumn(name = "promocion_maestro_usuario_nombreUsuario", referencedColumnName = "maestro_usuario_nombreUsuario")})
    @ManyToOne(optional = false)
    private Promocion promocion;

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

    public Promocion getPromocion() {
        return promocion;
    }

    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
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

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public boolean registrarPagoMensual(PagoAlumno pago) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        PagoAlumnoJpaController controlador = new PagoAlumnoJpaController(entityManagerFactory);
        PromocionJpaController promocion = new PromocionJpaController(entityManagerFactory);
        Grupo grupo = pago.getGrupo();
        PromocionPK p = new PromocionPK();
        p.setIdPromocion(1);
        p.setMaestroidMaestro(2);
        p.setMaestrousuarionombreUsuario("ChristopherPerez1");
        Promocion promocion2 = promocion.findPromocion(p);
        pago.setPromocion(promocion2);
        try {
            controlador.create(pago);
        } catch (Exception ex) {
            Logger.getLogger(PagoAlumno.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

}
