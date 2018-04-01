/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author alonso
 */
@Embeddable
public class RentaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idRenta")
    private int idRenta;
    @Basic(optional = false)
    @Column(name = "Cliente_idCliente")
    private int clienteidCliente;
    @Basic(optional = false)
    @Column(name = "pagoRenta_idPago")
    private int pagoRentaidPago;
    @Basic(optional = false)
    @Column(name = "horario_idHorario")
    private int horarioidHorario;

    public RentaPK() {
    }

    public RentaPK(int idRenta, int clienteidCliente, int pagoRentaidPago, int horarioidHorario) {
        this.idRenta = idRenta;
        this.clienteidCliente = clienteidCliente;
        this.pagoRentaidPago = pagoRentaidPago;
        this.horarioidHorario = horarioidHorario;
    }

    public int getIdRenta() {
        return idRenta;
    }

    public void setIdRenta(int idRenta) {
        this.idRenta = idRenta;
    }

    public int getClienteidCliente() {
        return clienteidCliente;
    }

    public void setClienteidCliente(int clienteidCliente) {
        this.clienteidCliente = clienteidCliente;
    }

    public int getPagoRentaidPago() {
        return pagoRentaidPago;
    }

    public void setPagoRentaidPago(int pagoRentaidPago) {
        this.pagoRentaidPago = pagoRentaidPago;
    }

    public int getHorarioidHorario() {
        return horarioidHorario;
    }

    public void setHorarioidHorario(int horarioidHorario) {
        this.horarioidHorario = horarioidHorario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idRenta;
        hash += (int) clienteidCliente;
        hash += (int) pagoRentaidPago;
        hash += (int) horarioidHorario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RentaPK)) {
            return false;
        }
        RentaPK other = (RentaPK) object;
        if (this.idRenta != other.idRenta) {
            return false;
        }
        if (this.clienteidCliente != other.clienteidCliente) {
            return false;
        }
        if (this.pagoRentaidPago != other.pagoRentaidPago) {
            return false;
        }
        if (this.horarioidHorario != other.horarioidHorario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.RentaPK[ idRenta=" + idRenta + ", clienteidCliente=" + clienteidCliente + ", pagoRentaidPago=" + pagoRentaidPago + ", horarioidHorario=" + horarioidHorario + " ]";
    }
    
}
