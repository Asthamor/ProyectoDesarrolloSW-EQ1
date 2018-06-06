/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


import interfaces.IPagoAlumnoExterno;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
import modelo.controladores.PagoAlumnoExternoJpaController;
import modelo.controladores.exceptions.NonexistentEntityException;

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
  , @NamedQuery(name = "PagoAlumnoExterno.findByMonto", query = "SELECT p FROM PagoAlumnoExterno p WHERE p.monto = :monto")
  , @NamedQuery(name = "PagoAlumnoExterno.findByFecha", query = "SELECT p FROM PagoAlumnoExterno p WHERE p.fecha = :fecha")
  , @NamedQuery(name = "PagoAlumnoExterno.findByMaestroidMaestro", query = "SELECT p FROM PagoAlumnoExterno p WHERE p.pagoAlumnoExternoPK.maestroidMaestro = :maestroidMaestro")
  , @NamedQuery(name = "PagoAlumnoExterno.findByMaestrousuarionombreUsuario", query = "SELECT p FROM PagoAlumnoExterno p WHERE p.pagoAlumnoExternoPK.maestrousuarionombreUsuario = :maestrousuarionombreUsuario")
  , @NamedQuery(name = "PagoAlumnoExterno.findByAlumnoidAlumno", query = "SELECT p FROM PagoAlumnoExterno p WHERE p.pagoAlumnoExternoPK.alumnoidAlumno = :alumnoidAlumno")})
public class PagoAlumnoExterno implements Serializable, IPagoAlumnoExterno {

  private static final long serialVersionUID = 1L;
  @EmbeddedId
  protected PagoAlumnoExternoPK pagoAlumnoExternoPK;
  @Basic(optional = false)
  @Column(name = "monto")
  private double monto;
  @Column(name = "fecha")
  @Temporal(TemporalType.DATE)
  private Date fecha;
  @JoinColumn(name = "alumno_idAlumno", referencedColumnName = "idAlumno", insertable = false, updatable = false)
  @ManyToOne(optional = false)
  private Alumno alumno;
  @JoinColumns({
    @JoinColumn(name = "maestro_idMaestro", referencedColumnName = "idMaestro", insertable = false, updatable = false)
    , @JoinColumn(name = "maestro_usuario_nombreUsuario", referencedColumnName = "usuario_nombreUsuario", insertable = false, updatable = false)})
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

  public PagoAlumnoExterno(int idPagoAlExterno, int maestroidMaestro, String maestrousuarionombreUsuario, int alumnoidAlumno) {
    this.pagoAlumnoExternoPK = new PagoAlumnoExternoPK(idPagoAlExterno, maestroidMaestro, maestrousuarionombreUsuario, alumnoidAlumno);
  }

  public PagoAlumnoExternoPK getPagoAlumnoExternoPK() {
    return pagoAlumnoExternoPK;
  }

  public void setPagoAlumnoExternoPK(PagoAlumnoExternoPK pagoAlumnoExternoPK) {
    this.pagoAlumnoExternoPK = pagoAlumnoExternoPK;
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
  public boolean registrarPago() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
    PagoAlumnoExternoJpaController controlador = new PagoAlumnoExternoJpaController(entityManagerFactory);
    try {
      controlador.create(this);
    } catch (Exception ex) {
      Logger.getLogger(PagoAlumno.class.getName()).log(Level.SEVERE, null, ex);
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Pago de Alumno externo:\n"
            + "\tAlumno: " + alumno.toString()
            + "\n\tMaestro: " + maestro.toString()
            + "\n\tMonto: $" + Double.toString(monto)
            + "\n\tRealizado: "
            + LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault()).toString();
  }
  
  public String pkToString(){
    return "Pago alumno externo # " + Integer.toString(this.getPagoAlumnoExternoPK().getIdPagoAlExterno());
  }

  @Override
  public boolean eliminar() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
    PagoAlumnoExternoJpaController controlador = new PagoAlumnoExternoJpaController(entityManagerFactory);

    try {
      controlador.destroy(pagoAlumnoExternoPK);
      return true;
    } catch (NonexistentEntityException ex) {
      Logger.getLogger(PagoAlumno.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  @Override
  public List<PagoAlumnoExterno> obtenerTodos() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
    PagoAlumnoExternoJpaController controlador = new PagoAlumnoExternoJpaController(entityManagerFactory);
    List<PagoAlumnoExterno> pagos = controlador.findPagoAlumnoExternoEntities();
    return pagos;
  }

  public boolean generarRecibo() {
    return true;
  }

  public double getMonto() {
    return monto;
  }

  public void setMonto(double monto) {
    this.monto = monto;
  }

}
