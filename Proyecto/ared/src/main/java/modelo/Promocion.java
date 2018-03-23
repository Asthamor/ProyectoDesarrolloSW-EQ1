/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alonso
 */
@Entity
@Table(name = "promocion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promocion.findAll", query = "SELECT p FROM Promocion p")
    , @NamedQuery(name = "Promocion.findByIdPromoci\u00f3n", query = "SELECT p FROM Promocion p WHERE p.promocionPK.idPromoci\u00f3n = :idPromoci\u00f3n")
    , @NamedQuery(name = "Promocion.findByCodigo", query = "SELECT p FROM Promocion p WHERE p.codigo = :codigo")
    , @NamedQuery(name = "Promocion.findByMaestroidMaestro", query = "SELECT p FROM Promocion p WHERE p.promocionPK.maestroidMaestro = :maestroidMaestro")
    , @NamedQuery(name = "Promocion.findByConcepto", query = "SELECT p FROM Promocion p WHERE p.concepto = :concepto")
    , @NamedQuery(name = "Promocion.findByDescuento", query = "SELECT p FROM Promocion p WHERE p.descuento = :descuento")})
public class Promocion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PromocionPK promocionPK;
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "concepto")
    private String concepto;
    @Basic(optional = false)
    @Column(name = "descuento")
    private int descuento;
    @JoinColumn(name = "maestro_idMaestro", referencedColumnName = "idMaestro", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Maestro maestro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "promoci\u00f3nidPromoci\u00f3n")
    private Collection<PagoAlumno> pagoAlumnoCollection;

    public Promocion() {
    }

    public Promocion(PromocionPK promocionPK) {
        this.promocionPK = promocionPK;
    }

    public Promocion(PromocionPK promocionPK, String codigo, int descuento) {
        this.promocionPK = promocionPK;
        this.codigo = codigo;
        this.descuento = descuento;
    }

    public Promocion(int idPromoción, int maestroidMaestro) {
        this.promocionPK = new PromocionPK(idPromoción, maestroidMaestro);
    }

    public PromocionPK getPromocionPK() {
        return promocionPK;
    }

    public void setPromocionPK(PromocionPK promocionPK) {
        this.promocionPK = promocionPK;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public Maestro getMaestro() {
        return maestro;
    }

    public void setMaestro(Maestro maestro) {
        this.maestro = maestro;
    }

    @XmlTransient
    public Collection<PagoAlumno> getPagoAlumnoCollection() {
        return pagoAlumnoCollection;
    }

    public void setPagoAlumnoCollection(Collection<PagoAlumno> pagoAlumnoCollection) {
        this.pagoAlumnoCollection = pagoAlumnoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promocionPK != null ? promocionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promocion)) {
            return false;
        }
        Promocion other = (Promocion) object;
        if ((this.promocionPK == null && other.promocionPK != null) || (this.promocionPK != null && !this.promocionPK.equals(other.promocionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Promocion[ promocionPK=" + promocionPK + " ]";
    }
    
}
