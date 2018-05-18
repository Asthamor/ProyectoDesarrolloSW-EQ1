/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import interfaces.IRenta;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import modelo.controladores.RentaJpaController;
import modelo.controladores.exceptions.NonexistentEntityException;

/**
 *
 * @author alonso
 */
@Entity
@Table(name = "renta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Renta.findAll", query = "SELECT r FROM Renta r")
    , @NamedQuery(name = "Renta.findByIdRenta", query = "SELECT r FROM Renta r WHERE r.rentaPK.idRenta = :idRenta")
    , @NamedQuery(name = "Renta.obtenerUltimoRegistro", query = "SELECT r.rentaPK.idRenta FROM Renta r order by r.rentaPK.idRenta desc")
    , @NamedQuery(name = "Renta.findByClienteidCliente", query = "SELECT r FROM Renta r WHERE r.rentaPK.clienteidCliente = :clienteidCliente")
    , @NamedQuery(name = "Renta.findByPagoRentaidPago", query = "SELECT r FROM Renta r WHERE r.rentaPK.pagoRentaidPago = :pagoRentaidPago")
    , @NamedQuery(name = "Renta.findByHorarioidHorario", query = "SELECT r FROM Renta r WHERE r.rentaPK.horarioidHorario = :horarioidHorario")})
public class Renta implements Serializable, IRenta {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RentaPK rentaPK;
    @JoinColumn(name = "Cliente_idCliente", referencedColumnName = "idCliente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "horario_idHorario", referencedColumnName = "idHorario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Horario horario;
    @JoinColumn(name = "pagoRenta_idPago", referencedColumnName = "idPago", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PagoRenta pagoRenta;

    public Renta() {
    }

    public Renta(RentaPK rentaPK) {
        this.rentaPK = rentaPK;
    }

    public Renta(int idRenta, int clienteidCliente, int pagoRentaidPago, int horarioidHorario) {
        this.rentaPK = new RentaPK(idRenta, clienteidCliente, pagoRentaidPago, horarioidHorario);
    }

    public RentaPK getRentaPK() {
        return rentaPK;
    }

    public void setRentaPK(RentaPK rentaPK) {
        this.rentaPK = rentaPK;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public PagoRenta getPagoRenta() {
        return pagoRenta;
    }

    public void setPagoRenta(PagoRenta pagoRenta) {
        this.pagoRenta = pagoRenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rentaPK != null ? rentaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Renta)) {
            return false;
        }
        Renta other = (Renta) object;
        if ((this.rentaPK == null && other.rentaPK != null) || (this.rentaPK != null && !this.rentaPK.equals(other.rentaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Renta[ rentaPK=" + rentaPK + " ]";
    }

    @Override
    public List<Renta> obtenerTodaRentas() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        RentaJpaController controlador = new RentaJpaController(entityManagerFactory);
        return controlador.findRentaEntities();
    }

    @Override
    public boolean crearRenta() {
        boolean seCreo = false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        RentaJpaController controlador = new RentaJpaController(entityManagerFactory);
        if (this.getPagoRenta().registrarPago()) {
            try {
                pagoRenta = pagoRenta.obtenerUltimoPago();
                controlador.create(this);
                seCreo = true;
            } catch (Exception ex) {
                Logger.getLogger(Renta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return seCreo;
    }

    @Override
    public boolean editarRenta() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Renta> buscarRenta(Date fecha) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Renta buscarRenta(int id){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        RentaJpaController controlador = new RentaJpaController(entityManagerFactory);
        return controlador.buscarRenta(id);
    }
    
    public String obtenerUltimaRenta(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        RentaJpaController controlador = new RentaJpaController(entityManagerFactory);
        return controlador.obtenerUltimoRegistro();
    }
    
    public boolean eliminarRenta(){
        boolean seElimino = false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("uv.pulpos_ared_jar_1.0-SNAPSHOTPU", null);
        RentaJpaController controlador = new RentaJpaController(entityManagerFactory);
        try {
            controlador.destroy(rentaPK);
            seElimino = true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Renta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return seElimino;
    }

}
