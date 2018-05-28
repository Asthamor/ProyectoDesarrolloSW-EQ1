/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import modelo.controladores.PublicidadJpaController;

/**
 *
 * @author alonso
 */
@Entity
@Table(name = "publicidad")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Publicidad.findAll", query = "SELECT p FROM Publicidad p")
  ,
  @NamedQuery(name = "Publicidad.findAllOrderByDate", query = "SELECT p FROM Publicidad p WHERE p.fechaInicio <= CURRENT_DATE ORDER BY p.fechaInicio")
  , @NamedQuery(name = "Publicidad.findByIdPublicidad", query = "SELECT p FROM Publicidad p WHERE p.publicidadPK.idPublicidad = :idPublicidad")
  , @NamedQuery(name = "Publicidad.findByFechaInicio", query = "SELECT p FROM Publicidad p WHERE p.fechaInicio = :fechaInicio")
  , @NamedQuery(name = "Publicidad.findByFechaFin", query = "SELECT p FROM Publicidad p WHERE p.fechaFin = :fechaFin")
  , @NamedQuery(name = "Publicidad.findByDescripcion", query = "SELECT p FROM Publicidad p WHERE p.descripcion = :descripcion")
  , @NamedQuery(name = "Publicidad.findByMonto", query = "SELECT p FROM Publicidad p WHERE p.monto = :monto")
  , @NamedQuery(name = "Publicidad.findByUrl", query = "SELECT p FROM Publicidad p WHERE p.url = :url")
  , @NamedQuery(name = "Publicidad.findByMaestroidMaestro", query = "SELECT p FROM Publicidad p WHERE p.publicidadPK.maestroidMaestro = :maestroidMaestro")
  , @NamedQuery(name = "Publicidad.findByMaestrousuarionombreUsuario", query = "SELECT p FROM Publicidad p WHERE p.publicidadPK.maestrousuarionombreUsuario = :maestrousuarionombreUsuario")})
public class Publicidad implements Serializable {

  @Basic(optional = false)
  @Column(name = "monto")
  private double monto;

  private static final long serialVersionUID = 1L;
  @EmbeddedId
  protected PublicidadPK publicidadPK;
  @Basic(optional = false)
  @Column(name = "fechaInicio")
  @Temporal(TemporalType.DATE)
  private Date fechaInicio;
  @Basic(optional = false)
  @Column(name = "fechaFin")
  @Temporal(TemporalType.DATE)
  private Date fechaFin;
  @Column(name = "descripcion")
  private String descripcion;
  @Column(name = "url")
  private String url;
  @JoinColumn(name = "Egreso_idEgreso", referencedColumnName = "idEgreso")
  @ManyToOne(optional = false)
  private Egreso egresoidEgreso;
  @JoinColumns({
    @JoinColumn(name = "maestro_idMaestro", referencedColumnName = "idMaestro", insertable = false, updatable = false)
    , @JoinColumn(name = "maestro_usuario_nombreUsuario", referencedColumnName = "usuario_nombreUsuario", insertable = false, updatable = false)})
  @ManyToOne(optional = false)
  private Maestro maestro;

  public Publicidad() {
  }

  public Publicidad(PublicidadPK publicidadPK) {
    this.publicidadPK = publicidadPK;
  }

  public Publicidad(PublicidadPK publicidadPK, Date fechaInicio, Date fechaFin, int monto) {
    this.publicidadPK = publicidadPK;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.monto = monto;
  }

  public Publicidad(int idPublicidad, int maestroidMaestro, String maestrousuarionombreUsuario) {
    this.publicidadPK = new PublicidadPK(idPublicidad, maestroidMaestro, maestrousuarionombreUsuario);
  }

  public PublicidadPK getPublicidadPK() {
    return publicidadPK;
  }

  public void setPublicidadPK(PublicidadPK publicidadPK) {
    this.publicidadPK = publicidadPK;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Egreso getEgresoidEgreso() {
    return egresoidEgreso;
  }

  public void setEgresoidEgreso(Egreso egresoidEgreso) {
    this.egresoidEgreso = egresoidEgreso;
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
    hash += (publicidadPK != null ? publicidadPK.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Publicidad)) {
      return false;
    }
    Publicidad other = (Publicidad) object;
    if ((this.publicidadPK == null && other.publicidadPK != null) || (this.publicidadPK != null && !this.publicidadPK.equals(other.publicidadPK))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "modelo.Publicidad[ publicidadPK=" + publicidadPK + " ]";
  }

  public List<Publicidad> obtenerTodoOrdenFecha() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
    PublicidadJpaController controlador = new PublicidadJpaController(entityManagerFactory);
    return controlador.getAllbyDate();
  }

  public double getMonto() {
    return monto;
  }

  public void setMonto(double monto) {
    this.monto = monto;
  }

}
