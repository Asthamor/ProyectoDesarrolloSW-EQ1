/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import interfaces.IPagoAlumno;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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

/**
 *
 * @author mauricio
 */
@Entity
@Table(name = "pagoAlumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoAlumno.findAll", query = "SELECT p FROM PagoAlumno p")
    , @NamedQuery(name = "PagoAlumno.findByIdPagoAlumno", query = "SELECT p FROM PagoAlumno p WHERE p.pagoAlumnoPK.idPagoAlumno = :idPagoAlumno")
    , @NamedQuery(name = "PagoAlumno.findByMonto", query = "SELECT p FROM PagoAlumno p WHERE p.monto = :monto")
    , @NamedQuery(name = "PagoAlumno.findByFechaPago", query = "SELECT p FROM PagoAlumno p WHERE p.fechaPago = :fechaPago")
    , @NamedQuery(name = "PagoAlumno.findByUltimaFechaPago", query = "SELECT p FROM PagoAlumno p WHERE p.pagoAlumnoPK.alumnoidAlumno = :idAlumno AND p.esInscripcion = 1  ORDER BY p.fechaVencimiento DESC")
    , @NamedQuery(name = "PagoAlumno.findByPlazo", query = "SELECT p FROM PagoAlumno p WHERE p.plazo = :plazo")
    , @NamedQuery(name = "PagoAlumno.findByAlumnoidAlumno", query = "SELECT p FROM PagoAlumno p WHERE p.pagoAlumnoPK.alumnoidAlumno = :alumnoidAlumno")
    , @NamedQuery(name = "PagoAlumno.findByGrupoidGrupo", query = "SELECT p FROM PagoAlumno p WHERE p.pagoAlumnoPK.grupoidGrupo = :grupoidGrupo")
    , @NamedQuery(name = "PagoAlumno.findByGrupomaestroidMaestro", query = "SELECT p FROM PagoAlumno p WHERE p.pagoAlumnoPK.grupomaestroidMaestro = :grupomaestroidMaestro")
    , @NamedQuery(name = "PagoAlumno.findByGrupomaestrousuarionombreUsuario", query = "SELECT p FROM PagoAlumno p WHERE p.pagoAlumnoPK.grupomaestrousuarionombreUsuario = :grupomaestrousuarionombreUsuario")
    , @NamedQuery(name = "PagoAlumno.findByGrupohorarioidHorario", query = "SELECT p FROM PagoAlumno p WHERE p.pagoAlumnoPK.grupohorarioidHorario = :grupohorarioidHorario")})
public class PagoAlumno implements Serializable, IPagoAlumno {

    @Column(name = "esInscripcion")
    private Boolean esInscripcion;

    @Basic(optional = false)
    @Column(name = "monto")
    private double monto;

    @Basic(optional = false)
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PagoAlumnoPK pagoAlumnoPK;
    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "plazo")
    private Integer plazo;
    @JoinColumn(name = "alumno_idAlumno", referencedColumnName = "idAlumno", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Alumno alumno;
    @JoinColumns({
        @JoinColumn(name = "grupo_idGrupo", referencedColumnName = "idGrupo", insertable = false, updatable = false)
        , @JoinColumn(name = "grupo_maestro_idMaestro", referencedColumnName = "maestro_idMaestro", insertable = false, updatable = false)
        , @JoinColumn(name = "grupo_maestro_usuario_nombreUsuario", referencedColumnName = "maestro_usuario_nombreUsuario", insertable = false, updatable = false)
        , @JoinColumn(name = "grupo_horario_idHorario", referencedColumnName = "horario_idHorario", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Grupo grupo;
    @JoinColumns({
        @JoinColumn(name = "promocion_idPromocion", referencedColumnName = "idPromocion")
        , @JoinColumn(name = "promocion_maestro_idMaestro", referencedColumnName = "maestro_idMaestro")
        , @JoinColumn(name = "promocion_maestro_usuario_nombreUsuario", referencedColumnName = "maestro_usuario_nombreUsuario")})
    @ManyToOne
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

    public PagoAlumno(int idPagoAlumno, int alumnoidAlumno, int grupoidGrupo, int grupomaestroidMaestro, String grupomaestrousuarionombreUsuario, int grupohorarioidHorario) {
        this.pagoAlumnoPK = new PagoAlumnoPK(idPagoAlumno, alumnoidAlumno, grupoidGrupo, grupomaestroidMaestro, grupomaestrousuarionombreUsuario, grupohorarioidHorario);
    }

    public PagoAlumnoPK getPagoAlumnoPK() {
        return pagoAlumnoPK;
    }

    public void setPagoAlumnoPK(PagoAlumnoPK pagoAlumnoPK) {
        this.pagoAlumnoPK = pagoAlumnoPK;
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

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
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

    @Override
    public boolean registrarPagoMensual(PagoAlumno pago) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        PagoAlumnoJpaController controlador = new PagoAlumnoJpaController(entityManagerFactory);
        PagoAlumnoPK pagoPK = new PagoAlumnoPK();
        pagoPK.setAlumnoidAlumno(alumno.getIdAlumno());
        pagoPK.setGrupohorarioidHorario(grupo.getGrupoPK().getHorarioidHorario());
        pagoPK.setGrupoidGrupo(grupo.getGrupoPK().getIdGrupo());
        pagoPK.setGrupomaestroidMaestro(grupo.getGrupoPK().getMaestroidMaestro());
        pagoPK.setGrupomaestrousuarionombreUsuario(grupo.getGrupoPK().getMaestrousuarionombreUsuario());
        pago.setPagoAlumnoPK(pagoPK);
        try {
            controlador.create(pago);
        } catch (Exception ex) {
            Logger.getLogger(PagoAlumno.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean registrarPago() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        PagoAlumnoJpaController controlador = new PagoAlumnoJpaController(entityManagerFactory);
        PagoAlumnoPK pagoPK = new PagoAlumnoPK();
        pagoPK.setAlumnoidAlumno(alumno.getIdAlumno());
        pagoPK.setGrupohorarioidHorario(grupo.getGrupoPK().getHorarioidHorario());
        pagoPK.setGrupoidGrupo(grupo.getGrupoPK().getIdGrupo());
        pagoPK.setGrupomaestroidMaestro(grupo.getGrupoPK().getMaestroidMaestro());
        pagoPK.setGrupomaestrousuarionombreUsuario(grupo.getGrupoPK().getMaestrousuarionombreUsuario());
        this.setPagoAlumnoPK(pagoPK);
        try {
            controlador.create(this);
        } catch (Exception ex) {
            Logger.getLogger(PagoAlumno.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public List<PagoAlumno> obtenerPagosAlumno(int idAlumno) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        PagoAlumnoJpaController controlador = new PagoAlumnoJpaController(entityManagerFactory);
        return controlador.findPagoByIdAlumno(idAlumno);
    }

    public Boolean getEsInscripcion() {
        return esInscripcion;
    }

    public void setEsInscripcion(Boolean esInscripcion) {
        this.esInscripcion = esInscripcion;
    }
    
    public PagoAlumno obtenerUltimoPago(Integer idAlumno){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        PagoAlumnoJpaController controlador = new PagoAlumnoJpaController(entityManagerFactory);
        return controlador.obtenerUltimoPago(idAlumno);
    }

}
