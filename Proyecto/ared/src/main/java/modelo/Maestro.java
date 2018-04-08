/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import interfaces.IMaestro;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import modelo.controladores.AlumnoJpaController;
import modelo.controladores.MaestroJpaController;
import modelo.controladores.exceptions.NonexistentEntityException;

/**
 *
 * @author alonso
 */
@Entity
@Table(name = "maestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maestro.findAll", query = "SELECT m FROM Maestro m")
    , @NamedQuery(name = "Maestro.findByIdMaestro", query = "SELECT m FROM Maestro m WHERE m.maestroPK.idMaestro = :idMaestro")
    , @NamedQuery(name = "Maestro.findByNombre", query = "SELECT m FROM Maestro m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Maestro.findByApellidos", query = "SELECT m FROM Maestro m WHERE m.apellidos = :apellidos")
    , @NamedQuery(name = "Maestro.findByTelefono", query = "SELECT m FROM Maestro m WHERE m.telefono = :telefono")
    , @NamedQuery(name = "Maestro.findByEmail", query = "SELECT m FROM Maestro m WHERE m.email = :email")
    , @NamedQuery(name = "Maestro.findByImgFoto", query = "SELECT m FROM Maestro m WHERE m.imgFoto = :imgFoto")
    , @NamedQuery(name = "Maestro.findByUsuarionombreUsuario", query = "SELECT m FROM Maestro m WHERE m.maestroPK.usuarionombreUsuario = :usuarionombreUsuario")})
public class Maestro extends Persona implements Serializable, IMaestro{

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MaestroPK maestroPK;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maestro")
    private Collection<Promocion> promocionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maestro")
    private Collection<Grupo> grupoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maestro")
    private Collection<Publicidad> publicidadCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maestro")
    private Collection<PagoAlumnoExterno> pagoAlumnoExternoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maestro")
    private Collection<PagoMaestro> pagoMaestroCollection;
    @JoinColumn(name = "usuario_nombreUsuario", referencedColumnName = "nombreUsuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Maestro() {
        super.tipoUsario = "maestro";
    }

    public Maestro(MaestroPK maestroPK) {
        this.maestroPK = maestroPK;
    }

    public Maestro(MaestroPK maestroPK, String nombre, String apellidos, String telefono) {
        this.maestroPK = maestroPK;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
    }
    
    public Maestro(MaestroPK maestroPK, Persona persona) {
        this.maestroPK = maestroPK;
        this.nombre = persona.getNombre();
        this.apellidos = persona.getApellidos();
        this.telefono = persona.getTelefono();
        this.email = persona.getEmail();
        this.imgFoto = persona.getImgFoto();
    }

    public Maestro(int idMaestro, String usuarionombreUsuario) {
        this.maestroPK = new MaestroPK(idMaestro, usuarionombreUsuario);
    }

    public MaestroPK getMaestroPK() {
        return maestroPK;
    }

    public void setMaestroPK(MaestroPK maestroPK) {
        this.maestroPK = maestroPK;
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
    public Collection<Promocion> getPromocionCollection() {
        return promocionCollection;
    }

    public void setPromocionCollection(Collection<Promocion> promocionCollection) {
        this.promocionCollection = promocionCollection;
    }

    @XmlTransient
    public Collection<Grupo> getGrupoCollection() {
        return grupoCollection;
    }

    public void setGrupoCollection(Collection<Grupo> grupoCollection) {
        this.grupoCollection = grupoCollection;
    }

    @XmlTransient
    public Collection<Publicidad> getPublicidadCollection() {
        return publicidadCollection;
    }

    public void setPublicidadCollection(Collection<Publicidad> publicidadCollection) {
        this.publicidadCollection = publicidadCollection;
    }

    @XmlTransient
    public Collection<PagoAlumnoExterno> getPagoAlumnoExternoCollection() {
        return pagoAlumnoExternoCollection;
    }

    public void setPagoAlumnoExternoCollection(Collection<PagoAlumnoExterno> pagoAlumnoExternoCollection) {
        this.pagoAlumnoExternoCollection = pagoAlumnoExternoCollection;
    }

    @XmlTransient
    public Collection<PagoMaestro> getPagoMaestroCollection() {
        return pagoMaestroCollection;
    }

    public void setPagoMaestroCollection(Collection<PagoMaestro> pagoMaestroCollection) {
        this.pagoMaestroCollection = pagoMaestroCollection;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (maestroPK != null ? maestroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Maestro)) {
            return false;
        }
        Maestro other = (Maestro) object;
        if ((this.maestroPK == null && other.maestroPK != null) || (this.maestroPK != null && !this.maestroPK.equals(other.maestroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Maestro[ maestroPK=" + maestroPK + " ]";
    }

    @Override
    public List<Persona> obtenerTodos() {
        List<Persona> personas = new ArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        MaestroJpaController controlador = new MaestroJpaController(entityManagerFactory);
        List<Maestro> maestros = controlador.findMaestroEntities();
        for (Maestro maestro : maestros) {
            personas.add(maestro);
        }
        return personas;
    }

    @Override
    public boolean actualizarDatos(Persona persona) {
        boolean seActualizo = false;
        return seActualizo;
    }

    @Override
    public List<Persona> buscar(String nombre) {
        List<Persona> personas = new ArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
      
        MaestroJpaController controlador = new MaestroJpaController(entityManagerFactory);
        List<Maestro> maestros = controlador.findMaestroByName(nombre);
        for (Maestro maestro : maestros) {
            personas.add(maestro);
        }
        return personas;
    }

    @Override
    public boolean registrar(Persona persona) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        MaestroJpaController controlador = new MaestroJpaController(entityManagerFactory);
        //Obtener nombre de usuario para el Maestro
        String nombreUsuario = persona.getNombre() + persona.getApellidos();
        usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContraseña(nombreUsuario);
        nombreUsuario = usuario.regNuevoUsuario(this.getTipoUsario());
        
        
        this.maestroPK = new MaestroPK();
        this.maestroPK.setUsuarionombreUsuario(nombreUsuario);
        
        try {
            controlador.create(this);
        } catch (Exception ex) {
            Logger.getLogger(Maestro.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public Maestro obtenerDatos(String maestro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Maestro> obtenerActivos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean obtenerEstado() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String obtenerImagen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
