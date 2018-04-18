/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alonso
 */
@Entity
@Table(name = "director")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Director.findAll", query = "SELECT d FROM Director d")
    , @NamedQuery(name = "Director.findByIdDirector", query = "SELECT d FROM Director d WHERE d.directorPK.idDirector = :idDirector")
    , @NamedQuery(name = "Director.findByNombre", query = "SELECT d FROM Director d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "Director.findByApellidos", query = "SELECT d FROM Director d WHERE d.apellidos = :apellidos")
    , @NamedQuery(name = "Director.findByTelefono", query = "SELECT d FROM Director d WHERE d.telefono = :telefono")
    , @NamedQuery(name = "Director.findByEmail", query = "SELECT d FROM Director d WHERE d.email = :email")
    , @NamedQuery(name = "Director.findByImgFoto", query = "SELECT d FROM Director d WHERE d.imgFoto = :imgFoto")
    , @NamedQuery(name = "Director.findByUsuarionombreUsuario", query = "SELECT d FROM Director d WHERE d.directorPK.usuarionombreUsuario = :usuarionombreUsuario")})
public class Director extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DirectorPK directorPK;
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
    @JoinColumn(name = "usuario_nombreUsuario", referencedColumnName = "nombreUsuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Director() {
    }

    public Director(DirectorPK directorPK) {
        this.directorPK = directorPK;
    }

    public Director(DirectorPK directorPK, String nombre, String apellidos, String telefono) {
        this.directorPK = directorPK;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
    }

    public Director(int idDirector, String usuarionombreUsuario) {
        this.directorPK = new DirectorPK(idDirector, usuarionombreUsuario);
    }

    public DirectorPK getDirectorPK() {
        return directorPK;
    }

    public void setDirectorPK(DirectorPK directorPK) {
        this.directorPK = directorPK;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (directorPK != null ? directorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Director)) {
            return false;
        }
        Director other = (Director) object;
        if ((this.directorPK == null && other.directorPK != null) || (this.directorPK != null && !this.directorPK.equals(other.directorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Director[ directorPK=" + directorPK + " ]";
    }

  @Override
  public List<Persona> obtenerTodos() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean actualizarDatos( ) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<Persona> buscar(String nombre) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean registrar(Persona persona) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean actualizarDatos(Persona persona) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
    
}
