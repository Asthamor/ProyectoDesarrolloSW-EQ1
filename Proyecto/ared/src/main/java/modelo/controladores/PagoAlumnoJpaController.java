/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Alumno;
import modelo.Grupo;
import modelo.PagoAlumno;
import modelo.PagoAlumnoPK;
import modelo.Promocion;
import modelo.controladores.exceptions.NonexistentEntityException;
import modelo.controladores.exceptions.PreexistingEntityException;

/**
 *
 * @author mau
 */
public class PagoAlumnoJpaController implements Serializable {

  public PagoAlumnoJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(PagoAlumno pagoAlumno) throws PreexistingEntityException, Exception {
    if (pagoAlumno.getPagoAlumnoPK() == null) {
      pagoAlumno.setPagoAlumnoPK(new PagoAlumnoPK());
    }
    pagoAlumno.getPagoAlumnoPK().setGrupomaestroidMaestro(pagoAlumno.getGrupo().getGrupoPK().getMaestroidMaestro());
    pagoAlumno.getPagoAlumnoPK().setAlumnoidAlumno(pagoAlumno.getAlumno().getIdAlumno());
    pagoAlumno.getPagoAlumnoPK().setGrupohorarioidHorario(pagoAlumno.getGrupo().getGrupoPK().getHorarioidHorario());
    pagoAlumno.getPagoAlumnoPK().setGrupomaestrousuarionombreUsuario(pagoAlumno.getGrupo().getGrupoPK().getMaestrousuarionombreUsuario());
    pagoAlumno.getPagoAlumnoPK().setGrupoidGrupo(pagoAlumno.getGrupo().getGrupoPK().getIdGrupo());
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Alumno alumno = pagoAlumno.getAlumno();
      if (alumno != null) {
        alumno = em.getReference(alumno.getClass(), alumno.getIdAlumno());
        pagoAlumno.setAlumno(alumno);
      }
      Grupo grupo = pagoAlumno.getGrupo();
      if (grupo != null) {
        grupo = em.getReference(grupo.getClass(), grupo.getGrupoPK());
        pagoAlumno.setGrupo(grupo);
      }
      Promocion promocion = pagoAlumno.getPromocion();
      if (promocion != null) {
        promocion = em.getReference(promocion.getClass(), promocion.getPromocionPK());
        pagoAlumno.setPromocion(promocion);
      }
      em.persist(pagoAlumno);
      if (alumno != null) {
        alumno.getPagoAlumnoCollection().add(pagoAlumno);
        alumno = em.merge(alumno);
      }
      if (grupo != null) {
        grupo.getPagoAlumnoCollection().add(pagoAlumno);
        grupo = em.merge(grupo);
      }
      if (promocion != null) {
        promocion.getPagoAlumnoCollection().add(pagoAlumno);
        promocion = em.merge(promocion);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      if (findPagoAlumno(pagoAlumno.getPagoAlumnoPK()) != null) {
        throw new PreexistingEntityException("PagoAlumno " + pagoAlumno + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(PagoAlumno pagoAlumno) throws NonexistentEntityException, Exception {
    pagoAlumno.getPagoAlumnoPK().setGrupomaestroidMaestro(pagoAlumno.getGrupo().getGrupoPK().getMaestroidMaestro());
    pagoAlumno.getPagoAlumnoPK().setAlumnoidAlumno(pagoAlumno.getAlumno().getIdAlumno());
    pagoAlumno.getPagoAlumnoPK().setGrupohorarioidHorario(pagoAlumno.getGrupo().getGrupoPK().getHorarioidHorario());
    pagoAlumno.getPagoAlumnoPK().setGrupomaestrousuarionombreUsuario(pagoAlumno.getGrupo().getGrupoPK().getMaestrousuarionombreUsuario());
    pagoAlumno.getPagoAlumnoPK().setGrupoidGrupo(pagoAlumno.getGrupo().getGrupoPK().getIdGrupo());
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      PagoAlumno persistentPagoAlumno = em.find(PagoAlumno.class, pagoAlumno.getPagoAlumnoPK());
      Alumno alumnoOld = persistentPagoAlumno.getAlumno();
      Alumno alumnoNew = pagoAlumno.getAlumno();
      Grupo grupoOld = persistentPagoAlumno.getGrupo();
      Grupo grupoNew = pagoAlumno.getGrupo();
      Promocion promocionOld = persistentPagoAlumno.getPromocion();
      Promocion promocionNew = pagoAlumno.getPromocion();
      if (alumnoNew != null) {
        alumnoNew = em.getReference(alumnoNew.getClass(), alumnoNew.getIdAlumno());
        pagoAlumno.setAlumno(alumnoNew);
      }
      if (grupoNew != null) {
        grupoNew = em.getReference(grupoNew.getClass(), grupoNew.getGrupoPK());
        pagoAlumno.setGrupo(grupoNew);
      }
      if (promocionNew != null) {
        promocionNew = em.getReference(promocionNew.getClass(), promocionNew.getPromocionPK());
        pagoAlumno.setPromocion(promocionNew);
      }
      pagoAlumno = em.merge(pagoAlumno);
      if (alumnoOld != null && !alumnoOld.equals(alumnoNew)) {
        alumnoOld.getPagoAlumnoCollection().remove(pagoAlumno);
        alumnoOld = em.merge(alumnoOld);
      }
      if (alumnoNew != null && !alumnoNew.equals(alumnoOld)) {
        alumnoNew.getPagoAlumnoCollection().add(pagoAlumno);
        alumnoNew = em.merge(alumnoNew);
      }
      if (grupoOld != null && !grupoOld.equals(grupoNew)) {
        grupoOld.getPagoAlumnoCollection().remove(pagoAlumno);
        grupoOld = em.merge(grupoOld);
      }
      if (grupoNew != null && !grupoNew.equals(grupoOld)) {
        grupoNew.getPagoAlumnoCollection().add(pagoAlumno);
        grupoNew = em.merge(grupoNew);
      }
      if (promocionOld != null && !promocionOld.equals(promocionNew)) {
        promocionOld.getPagoAlumnoCollection().remove(pagoAlumno);
        promocionOld = em.merge(promocionOld);
      }
      if (promocionNew != null && !promocionNew.equals(promocionOld)) {
        promocionNew.getPagoAlumnoCollection().add(pagoAlumno);
        promocionNew = em.merge(promocionNew);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        PagoAlumnoPK id = pagoAlumno.getPagoAlumnoPK();
        if (findPagoAlumno(id) == null) {
          throw new NonexistentEntityException("The pagoAlumno with id " + id + " no longer exists.");
        }
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void destroy(PagoAlumnoPK id) throws NonexistentEntityException {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      PagoAlumno pagoAlumno;
      try {
        pagoAlumno = em.getReference(PagoAlumno.class, id);
        pagoAlumno.getPagoAlumnoPK();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The pagoAlumno with id " + id + " no longer exists.", enfe);
      }
      Alumno alumno = pagoAlumno.getAlumno();
      if (alumno != null) {
        alumno.getPagoAlumnoCollection().remove(pagoAlumno);
        alumno = em.merge(alumno);
      }
      Grupo grupo = pagoAlumno.getGrupo();
      if (grupo != null) {
        grupo.getPagoAlumnoCollection().remove(pagoAlumno);
        grupo = em.merge(grupo);
      }
      Promocion promocion = pagoAlumno.getPromocion();
      if (promocion != null) {
        promocion.getPagoAlumnoCollection().remove(pagoAlumno);
        promocion = em.merge(promocion);
      }
      em.remove(pagoAlumno);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<PagoAlumno> findPagoAlumnoEntities() {
    return findPagoAlumnoEntities(true, -1, -1);
  }

  public List<PagoAlumno> findPagoAlumnoEntities(int maxResults, int firstResult) {
    return findPagoAlumnoEntities(false, maxResults, firstResult);
  }

  private List<PagoAlumno> findPagoAlumnoEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(PagoAlumno.class));
      Query q = em.createQuery(cq);
      if (!all) {
        q.setMaxResults(maxResults);
        q.setFirstResult(firstResult);
      }
      return q.getResultList();
    } finally {
      em.close();
    }
  }

  public PagoAlumno findPagoAlumno(PagoAlumnoPK id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(PagoAlumno.class, id);
    } finally {
      em.close();
    }
  }

  public int getPagoAlumnoCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<PagoAlumno> rt = cq.from(PagoAlumno.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
