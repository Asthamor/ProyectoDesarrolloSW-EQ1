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
public class PagoAlumnoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idPagoAlumno")
    private int idPagoAlumno;
    @Basic(optional = false)
    @Column(name = "Alumno_idAlumno")
    private int alumnoidAlumno;

    public PagoAlumnoPK() {
    }

    public PagoAlumnoPK(int idPagoAlumno, int alumnoidAlumno) {
        this.idPagoAlumno = idPagoAlumno;
        this.alumnoidAlumno = alumnoidAlumno;
    }

    public int getIdPagoAlumno() {
        return idPagoAlumno;
    }

    public void setIdPagoAlumno(int idPagoAlumno) {
        this.idPagoAlumno = idPagoAlumno;
    }

    public int getAlumnoidAlumno() {
        return alumnoidAlumno;
    }

    public void setAlumnoidAlumno(int alumnoidAlumno) {
        this.alumnoidAlumno = alumnoidAlumno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPagoAlumno;
        hash += (int) alumnoidAlumno;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoAlumnoPK)) {
            return false;
        }
        PagoAlumnoPK other = (PagoAlumnoPK) object;
        if (this.idPagoAlumno != other.idPagoAlumno) {
            return false;
        }
        if (this.alumnoidAlumno != other.alumnoidAlumno) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PagoAlumnoPK[ idPagoAlumno=" + idPagoAlumno + ", alumnoidAlumno=" + alumnoidAlumno + " ]";
    }
    
}
