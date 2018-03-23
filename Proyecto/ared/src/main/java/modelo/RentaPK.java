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
    @Column(name = "Horario_idHorario")
    private int horarioidHorario;
    @Basic(optional = false)
    @Column(name = "Cliente_idCliente")
    private int clienteidCliente;
    @Basic(optional = false)
    @Column(name = "Pago_idPago")
    private int pagoidPago;

    public RentaPK() {
    }

    public RentaPK(int idRenta, int horarioidHorario, int clienteidCliente, int pagoidPago) {
        this.idRenta = idRenta;
        this.horarioidHorario = horarioidHorario;
        this.clienteidCliente = clienteidCliente;
        this.pagoidPago = pagoidPago;
    }

    public int getIdRenta() {
        return idRenta;
    }

    public void setIdRenta(int idRenta) {
        this.idRenta = idRenta;
    }

    public int getHorarioidHorario() {
        return horarioidHorario;
    }

    public void setHorarioidHorario(int horarioidHorario) {
        this.horarioidHorario = horarioidHorario;
    }

    public int getClienteidCliente() {
        return clienteidCliente;
    }

    public void setClienteidCliente(int clienteidCliente) {
        this.clienteidCliente = clienteidCliente;
    }

    public int getPagoidPago() {
        return pagoidPago;
    }

    public void setPagoidPago(int pagoidPago) {
        this.pagoidPago = pagoidPago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idRenta;
        hash += (int) horarioidHorario;
        hash += (int) clienteidCliente;
        hash += (int) pagoidPago;
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
        if (this.horarioidHorario != other.horarioidHorario) {
            return false;
        }
        if (this.clienteidCliente != other.clienteidCliente) {
            return false;
        }
        if (this.pagoidPago != other.pagoidPago) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.RentaPK[ idRenta=" + idRenta + ", horarioidHorario=" + horarioidHorario + ", clienteidCliente=" + clienteidCliente + ", pagoidPago=" + pagoidPago + " ]";
    }
    
}
