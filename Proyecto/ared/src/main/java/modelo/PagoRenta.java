/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import interfaces.IIngreso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import modelo.controladores.PagoRentaJpaController;

/**
 *
 * @author alonso
 */
@Entity
@Table(name = "pagoRenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoRenta.findAll", query = "SELECT p FROM PagoRenta p")
    , @NamedQuery(name = "PagoRenta.findByIdPago", query = "SELECT p FROM PagoRenta p WHERE p.idPago = :idPago")
    , @NamedQuery(name = "PagoRenta.obtenerUltimoPago", query = "SELECT p FROM PagoRenta p order by p.idPago desc")
    , @NamedQuery(name = "PagoRenta.findByMonto", query = "SELECT p FROM PagoRenta p WHERE p.monto = :monto")
    , @NamedQuery(name = "PagoRenta.findByFecha", query = "SELECT p FROM PagoRenta p WHERE p.fecha = :fecha")})
public class PagoRenta extends Ingreso implements Serializable, IIngreso {

    @Basic(optional = false)
    @Column(name = "monto")
    private double monto;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idPago")
    private Integer idPago;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pagoRenta")
    private Collection<Renta> rentaCollection;

    public PagoRenta() {
        super.tipo = "Renta";
    }

    public PagoRenta(Integer idPago) {
        super.tipo = "Renta";
        this.idPago = idPago;
    }

    public PagoRenta(Integer idPago, int monto) {
        super.tipo = "Renta";
        this.idPago = idPago;
        this.monto = monto;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        hash += (idPago != null ? idPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoRenta)) {
            return false;
        }
        PagoRenta other = (PagoRenta) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PagoRenta[ idPago=" + idPago + " ]";
    }

    @Override
    public boolean registrarPago() {
        boolean seRegistro = false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        PagoRentaJpaController controladorPago = new PagoRentaJpaController(entityManagerFactory);
        try {
            controladorPago.create(this);
            seRegistro = true;
        } catch (Exception ex) {
            Logger.getLogger(PagoRenta.class.getName()).log(Level.SEVERE, null, ex);
        }

        return seRegistro;
    }

    @Override
    public boolean generarRecibo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public PagoRenta obtenerUltimoPago() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        PagoRentaJpaController controladorPago = new PagoRentaJpaController(entityManagerFactory);
        return controladorPago.obtenerUltimoPago();
    }

    @Override
    public List<Ingreso> obtenerTodos() {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);

        PagoRentaJpaController controlador = new PagoRentaJpaController(entityManagerFactory);
        List<PagoRenta> rentas = controlador.findPagoRentaEntities();
        List<Ingreso> result = new ArrayList<>();
        for (PagoRenta p : rentas) {
            result.add((Ingreso) p);
        }
        return result;
    }

    @Override
    public String getNombre() {
        if (!getRentaCollection().isEmpty()) {
            Renta renta = (Renta) getRentaCollection().toArray()[0];
            Cliente client = renta.getCliente();
            super.nombre = client.getNombre() + " " + client.getApellidos();
            return nombre;
        }
        return "--------";
    }

    public String getTipo() {
        super.tipo = "Renta";
        return tipo;
    }

    @Override
    public Date getDate() {
        return fecha;
    }

    @Override
    public Double getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

  @Override
  public Double getMonto(){
    return this.monto;
  }
}
