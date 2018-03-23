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
public class PromocionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idPromoci\u00f3n")
    private int idPromoción;
    @Basic(optional = false)
    @Column(name = "maestro_idMaestro")
    private int maestroidMaestro;

    public PromocionPK() {
    }

    public PromocionPK(int idPromoción, int maestroidMaestro) {
        this.idPromoción = idPromoción;
        this.maestroidMaestro = maestroidMaestro;
    }

    public int getIdPromoción() {
        return idPromoción;
    }

    public void setIdPromoción(int idPromoción) {
        this.idPromoción = idPromoción;
    }

    public int getMaestroidMaestro() {
        return maestroidMaestro;
    }

    public void setMaestroidMaestro(int maestroidMaestro) {
        this.maestroidMaestro = maestroidMaestro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPromoción;
        hash += (int) maestroidMaestro;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PromocionPK)) {
            return false;
        }
        PromocionPK other = (PromocionPK) object;
        if (this.idPromoción != other.idPromoción) {
            return false;
        }
        if (this.maestroidMaestro != other.maestroidMaestro) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PromocionPK[ idPromoci\u00f3n=" + idPromoción + ", maestroidMaestro=" + maestroidMaestro + " ]";
    }
    
}
