/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import interfaces.IGrupo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import modelo.controladores.GrupoJpaController;
import modelo.controladores.exceptions.IllegalOrphanException;
import modelo.controladores.exceptions.NonexistentEntityException;

/**
 *
 * @author alonso
 */
@Entity
@Table(name = "grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g")
    , @NamedQuery(name = "Grupo.findByIdGrupo", query = "SELECT g FROM Grupo g WHERE g.grupoPK.idGrupo = :idGrupo")
    , @NamedQuery(name = "Grupo.findByEstado", query = "SELECT g FROM Grupo g WHERE g.estado = :estado")
    , @NamedQuery(name = "Grupo.findByFechaCreacion", query = "SELECT g FROM Grupo g WHERE g.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "Grupo.findByMaestroidMaestro", query = "SELECT g FROM Grupo g WHERE g.grupoPK.maestroidMaestro = :maestroidMaestro")
    , @NamedQuery(name = "Grupo.findByMaestrousuarionombreUsuario", query = "SELECT g FROM Grupo g WHERE g.grupoPK.maestrousuarionombreUsuario = :maestrousuarionombreUsuario")
    , @NamedQuery(name = "Grupo.findByHorarioidHorario", query = "SELECT g FROM Grupo g WHERE g.grupoPK.horarioidHorario = :horarioidHorario")})
public class Grupo implements Serializable, IGrupo {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private Collection<Asistencia> asistenciaCollection;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
  private Collection<PagoAlumno> pagoAlumnoCollection;

    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "tipoDanza")
    private String tipoDanza;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GrupoPK grupoPK;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @ManyToMany(mappedBy = "grupoCollection")
    private Collection<Alumno> alumnoCollection;
    @JoinColumn(name = "horario_idHorario", referencedColumnName = "idHorario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Horario horario;
    @JoinColumns({
        @JoinColumn(name = "maestro_idMaestro", referencedColumnName = "idMaestro", insertable = false, updatable = false)
        , @JoinColumn(name = "maestro_usuario_nombreUsuario", referencedColumnName = "usuario_nombreUsuario", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Maestro maestro;

    public Grupo() {
    }

    public Grupo(GrupoPK grupoPK) {
        this.grupoPK = grupoPK;
    }

    public Grupo(int idGrupo, int maestroidMaestro, String maestrousuarionombreUsuario, int horarioidHorario) {
        this.grupoPK = new GrupoPK(idGrupo, maestroidMaestro, maestrousuarionombreUsuario, horarioidHorario);
    }

    public GrupoPK getGrupoPK() {
        return grupoPK;
    }

    public void setGrupoPK(GrupoPK grupoPK) {
        this.grupoPK = grupoPK;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @XmlTransient
    public Collection<Alumno> getAlumnoCollection() {
        return alumnoCollection;
    }

    public void setAlumnoCollection(Collection<Alumno> alumnoCollection) {
        this.alumnoCollection = alumnoCollection;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
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
        hash += (grupoPK != null ? grupoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.grupoPK == null && other.grupoPK != null) || (this.grupoPK != null && !this.grupoPK.equals(other.grupoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Grupo[ grupoPK=" + grupoPK + " ]";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDanza() {
        return tipoDanza;
    }

    public void setTipoDanza(String tipoDanza) {
        this.tipoDanza = tipoDanza;
    }

    @Override
    public List<Grupo> obtenerTodosLosGrupos() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        GrupoJpaController controlador = new GrupoJpaController(entityManagerFactory);
        List<Grupo> grupos = controlador.findGrupoEntities();
        return grupos;
    }

    @Override
    public boolean registrarGrupo(Grupo grupo) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        GrupoJpaController controlador = new GrupoJpaController(entityManagerFactory);
        try {
            controlador.create(grupo);
        } catch (Exception ex) {
            Logger.getLogger(Grupo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;

    }

    @Override
    public boolean actualizarDatosGrupo(Grupo grupo) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        GrupoJpaController controlador = new GrupoJpaController(entityManagerFactory);
        grupo.setGrupoPK(grupoPK);
        grupo.setMaestro(maestro);
        grupo.setEstado(estado);
        grupo.setFechaCreacion(fechaCreacion);
        grupo.setHorario(horario);
        grupo.setAlumnoCollection(alumnoCollection);
        try {
            controlador.edit(grupo);
        } catch (Exception ex) {
            Logger.getLogger(Grupo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;

    }

    @Override
    public boolean eliminarGrupo(GrupoPK grupoPK) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        GrupoJpaController controlador = new GrupoJpaController(entityManagerFactory);
        try {
            controlador.destroy(grupoPK);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Grupo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(Grupo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }


//    @Override
//    public boolean registrarInscripcionAlumno(String idAlumno, GrupoPK grupoPK) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public boolean eluminarInscripciónAlumno(String idAlumno, GrupoPK grupoPK) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

  @XmlTransient
  public Collection<PagoAlumno> getPagoAlumnoCollection() {
    return pagoAlumnoCollection;
  }

  public void setPagoAlumnoCollection(Collection<PagoAlumno> pagoAlumnoCollection) {
    this.pagoAlumnoCollection = pagoAlumnoCollection;
  }
  
  @Override
  public List<Alumno> obtenerAlumnosNoInscritos(){
    Alumno a = new Alumno();
    List<Alumno> noInscritos = new ArrayList();
    List<Persona> alumnos =  a.obtenerTodos();
    List<Alumno> inscritos = new ArrayList<>(this.getAlumnoCollection());
    for (Iterator<Persona> it = alumnos.iterator(); it.hasNext();) {
      Alumno alumno = (Alumno) it.next();
      if(!inscritos.contains(alumno)){
        noInscritos.add(alumno);
      }
    }
    return noInscritos;
  }
  
  @Override
  public String obtenerUltimoGrupo() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        GrupoJpaController controlador = new GrupoJpaController(entityManagerFactory);
        return controlador.ultimoRegistro();
    }

    @XmlTransient
    public Collection<Asistencia> getAsistenciaCollection() {
        return asistenciaCollection;
    }

    public void setAsistenciaCollection(Collection<Asistencia> asistenciaCollection) {
        this.asistenciaCollection = asistenciaCollection;
    }
}
