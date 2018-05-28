/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import modelo.controladores.ClienteJpaController;
import modelo.controladores.exceptions.NonexistentEntityException;

/**
 *
 * @author alonso
 */
@Entity
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c ORDER BY c.nombre")
    , @NamedQuery(name = "Cliente.findByIdCliente", query = "SELECT c FROM Cliente c WHERE c.idCliente = :idCliente")
    , @NamedQuery(name = "Cliente.findByNombre", query = "SELECT c FROM Cliente c WHERE c.nombre LIKE :nombre")
    , @NamedQuery(name = "Cliente.findByApellidos", query = "SELECT c FROM Cliente c WHERE c.apellidos = :apellidos")
    , @NamedQuery(name = "Cliente.findByTelefono", query = "SELECT c FROM Cliente c WHERE c.telefono = :telefono")
    , @NamedQuery(name = "Cliente.findByEmail", query = "SELECT c FROM Cliente c WHERE c.email = :email")
    , @NamedQuery(name = "Cliente.findByImgFoto", query = "SELECT c FROM Cliente c WHERE c.imgFoto = :imgFoto")})
public class Cliente extends Persona implements Serializable {

    @Column(name = "esActivo")
    private Boolean esActivo;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCliente")
    private Integer idCliente;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private Collection<Renta> rentaCollection;

    public Cliente() {
        super.tipoUsario = "cliente";
    }

    public Cliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente(Integer idCliente, String nombre, String apellidos, String telefono) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
    }

    public Cliente(Persona persona) {
        this.nombre = persona.getNombre();
        this.apellidos = persona.getApellidos();
        this.telefono = persona.getTelefono();
        this.email = persona.getEmail();
        this.imgFoto = persona.getImgFoto();
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
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
    public Collection<Renta> getRentaCollection() {
        return rentaCollection;
    }

    public void setRentaCollection(Collection<Renta> rentaCollection) {
        this.rentaCollection = rentaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    private void guardarImagen(String nombreUsuario) {
        if (this.imgFoto != null && !this.imgFoto.trim().equals("")) {
            String imagePath = System.getProperty("user.dir") + "/userPhoto/";
            File imageDirectory = new File(imagePath);
            if (!imageDirectory.exists()) {
                imageDirectory.mkdir();
            }
            File f = new File(this.imgFoto);
            imageDirectory = new File(
                    imagePath + f.getName() + nombreUsuario);
            System.out.println(f.toPath());
            System.out.println(imageDirectory.toPath());
            try {

                Files.copy(f.toPath(), imageDirectory.toPath(), REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(Maestro.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.imgFoto = imageDirectory.getName();
        }
    }

    @Override
    public String toString() {
        return "modelo.Cliente[ idCliente=" + idCliente + " ]";
    }

    @Override
    public List<Persona> obtenerTodos() {
        List<Persona> personas = new ArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        ClienteJpaController controlador = new ClienteJpaController(entityManagerFactory);
        List<Cliente> clientes = controlador.findClienteEntities();
        for (Cliente cliente : clientes) {
            personas.add(cliente);
        }
        return personas;
    }

    @Override
    public String obtenerImagen() {
        String currentPath = System.getProperty("user.dir");
        String imagePath = "/userPhoto/iconoCliente.png";

        if (this.imgFoto != null) {
            Path classPath = Paths.get(System.getProperty("java.class.path"));
            File f = new File(currentPath + "/userPhoto/" + this.imgFoto);

            if (f.exists()) {
                imagePath = f.getAbsolutePath();//classPath.relativize(f.toPath()).toString();
            }
        } else {
            imagePath = currentPath + imagePath;
        }
        return imagePath;
    }

    @Override
    public List<Persona> buscar(String nombre) {
        List<Persona> personas = new ArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        ClienteJpaController controlador = new ClienteJpaController(entityManagerFactory);
        List<Cliente> clientes = controlador.findClienteByName(nombre);
        for (Cliente cliente : clientes) {
            personas.add(cliente);
        }
        return personas;
    }

    @Override
    public boolean registrar(Persona persona) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        ClienteJpaController controlador = new ClienteJpaController(entityManagerFactory);
        //Registrar usuario y contraseña y obtener el nuevo usuario para el Maestro
        String nombreUsuario = persona.getNombre().replace(" ", "")
                + persona.getApellidos().replace(" ", "") + "cliente";
        //Copiar el archivo de imagen al directorio de la aplicación
        guardarImagen(nombreUsuario);
        try {
            controlador.create(this);
        } catch (Exception ex) {
            Logger.getLogger(Maestro.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean actualizarDatos(boolean editarImagen) {
        boolean seActualizo = true;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        ClienteJpaController controlador = new ClienteJpaController(entityManagerFactory);
        if (editarImagen) {
            guardarImagen(this.getNombre());
        }
        try {
            controlador.edit(this);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Maestro.class.getName()).log(Level.SEVERE, null, ex);
            seActualizo = false;
        } catch (Exception ex) {
            Logger.getLogger(Maestro.class.getName()).log(Level.SEVERE, null, ex);
            seActualizo = false;
        }
        return seActualizo;
    }

    @Override
    public List<Persona> obtenerActivos() {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        ClienteJpaController controlador = new ClienteJpaController(entityManagerFactory);
        List<Cliente> clientes = controlador.findClienteEntities();
        List<Persona> activos = new ArrayList<>();
        for (Cliente c : clientes) {
            if (c.getEsActivo()) {
                activos.add(c);
            }
        }
        return activos;
    }

    @Override
    public List<Persona> obtenerInactivos() {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        ClienteJpaController controlador = new ClienteJpaController(entityManagerFactory);
        List<Cliente> clientes = controlador.findClienteEntities();
        List<Persona> activos = new ArrayList<>();
        for (Cliente c : clientes) {
            if (!c.getEsActivo()) {
                activos.add(c);
            }
        }
        return activos;
    }

    public boolean getEsActivo() {
        if (esActivo != null) {
            return esActivo;
        }
        return false;
    }

    public void setEsActivo(boolean esActivo) {
        this.esActivo = esActivo;
    }

}
