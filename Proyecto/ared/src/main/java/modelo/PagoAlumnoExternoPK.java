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
public class PagoAlumnoExternoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idPagoAlExterno")
    private int idPagoAlExterno;
    @Basic(optional = false)
    @Column(name = "maestro_idMaestro")
    private int maestroidMaestro;
    @Basic(optional = false)
    @Column(name = "maestro_usuario_nombreUsuario")
    private String maestrousuarionombreUsuario;
    @Basic(optional = false)
    @Column(name = "alumno_idAlumno")
    private int alumnoidAlumno;

    public PagoAlumnoExternoPK() {
    }

    public PagoAlumnoExternoPK(int idPagoAlExterno, int maestroidMaestro, String maestrousuarionombreUsuario, int alumnoidAlumno) {
        this.idPagoAlExterno = idPagoAlExterno;
        this.maestroidMaestro = maestroidMaestro;
        this.maestrousuarionombreUsuario = maestrousuarionombreUsuario;
        this.alumnoidAlumno = alumnoidAlumno;
    }

    public int getIdPagoAlExterno() {
        return idPagoAlExterno;
    }

    public void setIdPagoAlExterno(int idPagoAlExterno) {
        this.idPagoAlExterno = idPagoAlExterno;
    }

    public int getMaestroidMaestro() {
        return maestroidMaestro;
    }

    public void setMaestroidMaestro(int maestroidMaestro) {
        this.maestroidMaestro = maestroidMaestro;
    }

    public String getMaestrousuarionombreUsuario() {
        return maestrousuarionombreUsuario;
    }

    public void setMaestrousuarionombreUsuario(String maestrousuarionombreUsuario) {
        this.maestrousuarionombreUsuario = maestrousuarionombreUsuario;
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
        hash += (int) idPagoAlExterno;
        hash += (int) maestroidMaestro;
        hash += (maestrousuarionombreUsuario != null ? maestrousuarionombreUsuario.hashCode() : 0);
        hash += (int) alumnoidAlumno;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoAlumnoExternoPK)) {
            return false;
        }
        PagoAlumnoExternoPK other = (PagoAlumnoExternoPK) object;
        if (this.idPagoAlExterno != other.idPagoAlExterno) {
            return false;
        }
        if (this.maestroidMaestro != other.maestroidMaestro) {
            return false;
        }
        if ((this.maestrousuarionombreUsuario == null && other.maestrousuarionombreUsuario != null) || (this.maestrousuarionombreUsuario != null && !this.maestrousuarionombreUsuario.equals(other.maestrousuarionombreUsuario))) {
            return false;
        }
        if (this.alumnoidAlumno != other.alumnoidAlumno) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PagoAlumnoExternoPK[ idPagoAlExterno=" + idPagoAlExterno + ", maestroidMaestro=" + maestroidMaestro + ", maestrousuarionombreUsuario=" + maestrousuarionombreUsuario + ", alumnoidAlumno=" + alumnoidAlumno + " ]";
    }
    
}
