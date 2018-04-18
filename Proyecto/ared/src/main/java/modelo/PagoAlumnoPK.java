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
 * @author mauricio
 */
@Embeddable
public class PagoAlumnoPK implements Serializable {

  @Basic(optional = false)
  @Column(name = "idPagoAlumno")
  private int idPagoAlumno;
  @Basic(optional = false)
  @Column(name = "alumno_idAlumno")
  private int alumnoidAlumno;
  @Basic(optional = false)
  @Column(name = "grupo_idGrupo")
  private int grupoidGrupo;
  @Basic(optional = false)
  @Column(name = "grupo_maestro_idMaestro")
  private int grupomaestroidMaestro;
  @Basic(optional = false)
  @Column(name = "grupo_maestro_usuario_nombreUsuario")
  private String grupomaestrousuarionombreUsuario;
  @Basic(optional = false)
  @Column(name = "grupo_horario_idHorario")
  private int grupohorarioidHorario;

  public PagoAlumnoPK() {
  }

  public PagoAlumnoPK(int idPagoAlumno, int alumnoidAlumno, int grupoidGrupo, int grupomaestroidMaestro, String grupomaestrousuarionombreUsuario, int grupohorarioidHorario) {
    this.idPagoAlumno = idPagoAlumno;
    this.alumnoidAlumno = alumnoidAlumno;
    this.grupoidGrupo = grupoidGrupo;
    this.grupomaestroidMaestro = grupomaestroidMaestro;
    this.grupomaestrousuarionombreUsuario = grupomaestrousuarionombreUsuario;
    this.grupohorarioidHorario = grupohorarioidHorario;
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

  public int getGrupoidGrupo() {
    return grupoidGrupo;
  }

  public void setGrupoidGrupo(int grupoidGrupo) {
    this.grupoidGrupo = grupoidGrupo;
  }

  public int getGrupomaestroidMaestro() {
    return grupomaestroidMaestro;
  }

  public void setGrupomaestroidMaestro(int grupomaestroidMaestro) {
    this.grupomaestroidMaestro = grupomaestroidMaestro;
  }

  public String getGrupomaestrousuarionombreUsuario() {
    return grupomaestrousuarionombreUsuario;
  }

  public void setGrupomaestrousuarionombreUsuario(String grupomaestrousuarionombreUsuario) {
    this.grupomaestrousuarionombreUsuario = grupomaestrousuarionombreUsuario;
  }

  public int getGrupohorarioidHorario() {
    return grupohorarioidHorario;
  }

  public void setGrupohorarioidHorario(int grupohorarioidHorario) {
    this.grupohorarioidHorario = grupohorarioidHorario;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (int) idPagoAlumno;
    hash += (int) alumnoidAlumno;
    hash += (int) grupoidGrupo;
    hash += (int) grupomaestroidMaestro;
    hash += (grupomaestrousuarionombreUsuario != null ? grupomaestrousuarionombreUsuario.hashCode() : 0);
    hash += (int) grupohorarioidHorario;
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
    if (this.grupoidGrupo != other.grupoidGrupo) {
      return false;
    }
    if (this.grupomaestroidMaestro != other.grupomaestroidMaestro) {
      return false;
    }
    if ((this.grupomaestrousuarionombreUsuario == null && other.grupomaestrousuarionombreUsuario != null) || (this.grupomaestrousuarionombreUsuario != null && !this.grupomaestrousuarionombreUsuario.equals(other.grupomaestrousuarionombreUsuario))) {
      return false;
    }
    if (this.grupohorarioidHorario != other.grupohorarioidHorario) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "modelo.PagoAlumnoPK[ idPagoAlumno=" + idPagoAlumno + ", alumnoidAlumno=" + alumnoidAlumno + ", grupoidGrupo=" + grupoidGrupo + ", grupomaestroidMaestro=" + grupomaestroidMaestro + ", grupomaestrousuarionombreUsuario=" + grupomaestrousuarionombreUsuario + ", grupohorarioidHorario=" + grupohorarioidHorario + " ]";
  }
  
}
