/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import modelo.controladores.UsuarioJpaController;
import modelo.controladores.exceptions.NonexistentEntityException;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author alonso
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
  , @NamedQuery(name = "Usuario.findByNombreUsuario", query = "SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
  , @NamedQuery(name = "Usuario.findByContrase\u00f1a", query = "SELECT u FROM Usuario u WHERE u.contrase\u00f1a = :contrase\u00f1a")})
public class Usuario implements Serializable {

  @Basic(optional = false)
  @Column(name = "salt")
  private String salt;

  @Basic(optional = false)
  @Column(name = "tipoUsuario")
  private String tipoUsuario;

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "nombreUsuario")
  private String nombreUsuario;
  @Basic(optional = false)
  @Column(name = "contrase\u00f1a")
  private String contraseña;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
  private Collection<Director> directorCollection;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
  private Collection<Maestro> maestroCollection;

  public Usuario() {
  }

  public Usuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public Usuario(String nombreUsuario, String contraseña) {
    this.nombreUsuario = nombreUsuario;
    this.contraseña = contraseña;
  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public String getContraseña() {
    return contraseña;
  }

  public void setContraseña(String contraseña) {
    this.contraseña = contraseña;
  }

  @XmlTransient
  public Collection<Director> getDirectorCollection() {
    return directorCollection;
  }

  public void setDirectorCollection(Collection<Director> directorCollection) {
    this.directorCollection = directorCollection;
  }

  @XmlTransient
  public Collection<Maestro> getMaestroCollection() {
    return maestroCollection;
  }

  public void setMaestroCollection(Collection<Maestro> maestroCollection) {
    this.maestroCollection = maestroCollection;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (nombreUsuario != null ? nombreUsuario.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Usuario)) {
      return false;
    }
    Usuario other = (Usuario) object;
    if ((this.nombreUsuario == null && other.nombreUsuario != null) || (this.nombreUsuario != null && !this.nombreUsuario.equals(other.nombreUsuario))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "modelo.Usuario[ nombreUsuario=" + nombreUsuario + " ]";
  }

  public String getTipoUsuario() {
    return tipoUsuario;
  }

  public void setTipoUsuario(String tipoUsuario) {
    this.tipoUsuario = tipoUsuario;
  }

  //Registrar un nuevo usuario, si el nombre de usuario existe, se agrega un
  //número al final del nombre de usuario automáticamente y regresa el nombre
  //de usuario con el que fue registrado
  public String regNuevoUsuario(String tipoUsuario) {
    EntityManagerFactory entityManagerFactory = Persistence
        .createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
    UsuarioJpaController controlador = new UsuarioJpaController(entityManagerFactory);
    this.tipoUsuario = tipoUsuario;
    this.salt = generarSalt();
    try {
      this.contraseña = generarPass(nombreUsuario, this.salt);
    } catch (NoSuchAlgorithmException ex) {
      Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
    } catch (UnsupportedEncodingException ex) {
      Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
    }
    int mod = 1;
    String usuarioReg = nombreUsuario;
    if (controlador.findUsuario(nombreUsuario) != null) {
      while (controlador.findUsuario(usuarioReg) != null) {
        usuarioReg = nombreUsuario + Integer.toString(mod);
        mod++;
      }
      this.nombreUsuario = usuarioReg;
    }

    try {

      controlador.create(this);
    } catch (Exception ex) {
      Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
    }
    return this.nombreUsuario;
  }

  public boolean registar(String tipoUsuario) {
    if (this.nombreUsuario == null || this.contraseña == null) {
      return false;
    }
    EntityManagerFactory entityManagerFactory = Persistence
        .createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
    UsuarioJpaController controlador = new UsuarioJpaController(entityManagerFactory);
    this.tipoUsuario = tipoUsuario;

    this.setSalt(generarSalt());
    try {
      this.contraseña = (generarPass(this.nombreUsuario, this.salt));
      controlador.create(this);
    } catch (NoSuchAlgorithmException ex) {
      Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
    } catch (UnsupportedEncodingException ex) {
      Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
    }

    return true;
  }

  public boolean editarPassword(Usuario usuario) {
    EntityManagerFactory entityManagerFactory = Persistence
        .createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
    UsuarioJpaController controlador = new UsuarioJpaController(entityManagerFactory);
    this.salt = generarSalt();

    try {
      this.contraseña = generarPass(this.contraseña, this.salt);
      controlador.edit(usuario);
      return true;
    } catch (NonexistentEntityException ex) {
      Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  public boolean editar(Usuario usuario) {
    EntityManagerFactory entityManagerFactory = Persistence
        .createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
    UsuarioJpaController controlador = new UsuarioJpaController(entityManagerFactory);

    try {
      controlador.edit(usuario);
      return true;
    } catch (NonexistentEntityException ex) {
      Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  private String generarPass(String pass, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    pass = pass + salt;
    MessageDigest md;
    md = MessageDigest.getInstance("SHA-256");
    md.update(pass.getBytes("UTF-8"));
    byte[] dPass = md.digest();
    BigInteger bigInt = new BigInteger(1, dPass);
    String hashTxt = bigInt.toString(16);
    return hashTxt;
  }

  private String generarSalt() {
    SecureRandom sRandom = new SecureRandom();
    byte bytes[] = new byte[20];
    sRandom.nextBytes(bytes);
    String result = Base64.encodeBase64String(bytes);
    return result.substring(0, 20);
  }

  public int autenticar(String nombreUsuario, String contraseña) {
    int result = -1;
    EntityManagerFactory entityManagerFactory = Persistence
        .createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
    UsuarioJpaController controlador = new UsuarioJpaController(entityManagerFactory);
    Usuario usuario = controlador.findUsuario(nombreUsuario);
    if (usuario != null) {
      
      try {
        String password = generarPass(nombreUsuario, usuario.getSalt());
        if(password.equals(usuario.contraseña)){
          if("maestro".equals(usuario.getTipoUsuario())){
           result = 1; 
          } else if("director".equals(usuario.getTipoUsuario())){
            result = 0;
          }
        }
      } catch (NoSuchAlgorithmException ex) {
        Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
      } catch (UnsupportedEncodingException ex) {
        Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
      }

    }
    return result;
  }
  
  public Usuario buscar(String nombreUsuario){
    EntityManagerFactory entityManagerFactory = Persistence
        .createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
    UsuarioJpaController controlador = new UsuarioJpaController(entityManagerFactory);
    Usuario usuario = controlador.findUsuario(nombreUsuario);
    return usuario;
  }
}
