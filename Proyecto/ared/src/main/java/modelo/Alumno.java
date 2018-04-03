/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import interfaces.IAlumno;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import modelo.controladores.AlumnoJpaController;
import modelo.controladores.exceptions.NonexistentEntityException;

/**
 *
 * @author alonso
 */
@Entity 
@Table(name = "alumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alumno.findAll", query = "SELECT a FROM Alumno a")
    , @NamedQuery(name = "Alumno.findByIdAlumno", query = "SELECT a FROM Alumno a WHERE a.idAlumno = :idAlumno")
    , @NamedQuery(name = "Alumno.findByNombre", query = "SELECT a FROM Alumno a WHERE a.nombre LIKE :nombre")
    , @NamedQuery(name = "Alumno.findByApellidos", query = "SELECT a FROM Alumno a WHERE a.apellidos = :apellidos")
    , @NamedQuery(name = "Alumno.findByTelefono", query = "SELECT a FROM Alumno a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "Alumno.findByEmail", query = "SELECT a FROM Alumno a WHERE a.email = :email")
    , @NamedQuery(name = "Alumno.findByImgFoto", query = "SELECT a FROM Alumno a WHERE a.imgFoto = :imgFoto")})
public class Alumno extends Persona implements Serializable, IAlumno {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAlumno")
    private Integer idAlumno;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "email")
    private String email;
    @Column(name = "imgFoto")
    private String imgFoto;
    @JoinTable(name = "grupo_has_alumno", joinColumns = {
        @JoinColumn(name = "alumno_idAlumno", referencedColumnName = "idAlumno")}, inverseJoinColumns = {
        @JoinColumn(name = "grupo_idGrupo", referencedColumnName = "idGrupo")
        , @JoinColumn(name = "grupo_maestro_idMaestro", referencedColumnName = "maestro_idMaestro")
        , @JoinColumn(name = "grupo_maestro_usuario_nombreUsuario", referencedColumnName = "maestro_usuario_nombreUsuario")
        , @JoinColumn(name = "grupo_horario_idHorario", referencedColumnName = "horario_idHorario")})
    @ManyToMany
    private Collection<Grupo> grupoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alumno")
    private Collection<PagoAlumnoExterno> pagoAlumnoExternoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alumno")
    private Collection<PagoAlumno> pagoAlumnoCollection;

    public Alumno() {
        super.tipoUsario = "alumno";
    }

    public Alumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Alumno(Integer idAlumno, String nombre, String apellidos, String telefono) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
    }

    public Alumno(Persona persona) {
        this.nombre = persona.getNombre();
        this.apellidos = persona.getApellidos();
        this.telefono = persona.getTelefono();
        this.email = persona.getEmail();
        this.imgFoto = persona.getImgFoto();
    }

    public Integer getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgFoto() {
        return imgFoto;
    }

    public void setImgFoto(String imgFoto) {
        this.imgFoto = imgFoto;
    }

    public String getTipoUsario() {
        return super.tipoUsario;
    }

    @XmlTransient
    public Collection<Grupo> getGrupoCollection() {
        return grupoCollection;
    }

    public void setGrupoCollection(Collection<Grupo> grupoCollection) {
        this.grupoCollection = grupoCollection;
    }

    @XmlTransient
    public Collection<PagoAlumnoExterno> getPagoAlumnoExternoCollection() {
        return pagoAlumnoExternoCollection;
    }

    public void setPagoAlumnoExternoCollection(Collection<PagoAlumnoExterno> pagoAlumnoExternoCollection) {
        this.pagoAlumnoExternoCollection = pagoAlumnoExternoCollection;
    }

    @XmlTransient
    public Collection<PagoAlumno> getPagoAlumnoCollection() {
        return pagoAlumnoCollection;
    }

    public void setPagoAlumnoCollection(Collection<PagoAlumno> pagoAlumnoCollection) {
        this.pagoAlumnoCollection = pagoAlumnoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlumno != null ? idAlumno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alumno)) {
            return false;
        }
        Alumno other = (Alumno) object;
        if ((this.idAlumno == null && other.idAlumno != null) || (this.idAlumno != null && !this.idAlumno.equals(other.idAlumno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Alumno[ idAlumno=" + idAlumno + " ]";
    }

    @Override
    public List<Persona> obtenerTodos() {
        List<Persona> personas = new ArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        AlumnoJpaController controlador = new AlumnoJpaController(entityManagerFactory);
        List<Alumno> alumnos = controlador.findAlumnoEntities();
        for (Alumno alumno : alumnos) {
            personas.add(alumno);
        }
        return personas;
    }

    @Override
    public boolean actualizarDatos(Persona persona) {
        boolean seActualizo = false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        AlumnoJpaController controlador = new AlumnoJpaController(entityManagerFactory);
        Alumno alumno = new Alumno(persona);
        alumno.setIdAlumno(idAlumno);
        alumno.setGrupoCollection(grupoCollection);
        alumno.setPagoAlumnoCollection(pagoAlumnoCollection);
        alumno.setPagoAlumnoExternoCollection(pagoAlumnoExternoCollection);
        try {
            controlador.edit(alumno);
            seActualizo = true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
        return seActualizo;
    }

    @Override
    public List<Persona> buscar(String nombre) {
       List<Persona> personas = new ArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        AlumnoJpaController controlador = new AlumnoJpaController(entityManagerFactory);
        List<Alumno> alumnos = controlador.findAlumnoByName(nombre);
        for (Alumno alumno : alumnos) {
            personas.add(alumno);
        }
        return personas;
    }

    @Override
    public boolean registrar(Persona persona) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        AlumnoJpaController controlador = new AlumnoJpaController(entityManagerFactory);
        Alumno alumno = new Alumno(persona);
        controlador.create(alumno);
        return true;
    }

    @Override
    public List<Alumno> obtenerAlumnosInscritos(String idGrupo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Alumno> obtenerAlumnosNoInscritos(String idGrupo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
