/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
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
    public String regNuevoUsuario(String tipoUsuario){
        EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        UsuarioJpaController controlador = new UsuarioJpaController(entityManagerFactory);
        this.tipoUsuario = tipoUsuario;
        int mod = 1;
        String usuarioReg = nombreUsuario;
        if (controlador.findUsuario(nombreUsuario) != null){
            while (controlador.findUsuario(usuarioReg) != null){
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
    
}
