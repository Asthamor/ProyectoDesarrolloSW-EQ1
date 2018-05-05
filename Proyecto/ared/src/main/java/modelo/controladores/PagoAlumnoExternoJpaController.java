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
import modelo.Maestro;
import modelo.PagoAlumnoExterno;
import modelo.PagoAlumnoExternoPK;
import modelo.controladores.exceptions.NonexistentEntityException;
import modelo.controladores.exceptions.PreexistingEntityException;

/**
 *
 * @author mau
 */
public class PagoAlumnoExternoJpaController implements Serializable {

  public PagoAlumnoExternoJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(PagoAlumnoExterno pagoAlumnoExterno) throws PreexistingEntityException, Exception {
    if (pagoAlumnoExterno.getPagoAlumnoExternoPK() == null) {
      pagoAlumnoExterno.setPagoAlumnoExternoPK(new PagoAlumnoExternoPK());
    }
    pagoAlumnoExterno.getPagoAlumnoExternoPK().setMaestroidMaestro(pagoAlumnoExterno.getMaestro().getMaestroPK().getIdMaestro());
    pagoAlumnoExterno.getPagoAlumnoExternoPK().setAlumnoidAlumno(pagoAlumnoExterno.getAlumno().getIdAlumno());
    pagoAlumnoExterno.getPagoAlumnoExternoPK().setMaestrousuarionombreUsuario(pagoAlumnoExterno.getMaestro().getMaestroPK().getUsuarionombreUsuario());
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Alumno alumno = pagoAlumnoExterno.getAlumno();
      if (alumno != null) {
        alumno = em.getReference(alumno.getClass(), alumno.getIdAlumno());
        pagoAlumnoExterno.setAlumno(alumno);
      }
      Maestro maestro = pagoAlumnoExterno.getMaestro();
      if (maestro != null) {
        maestro = em.getReference(maestro.getClass(), maestro.getMaestroPK());
        pagoAlumnoExterno.setMaestro(maestro);
      }
      em.persist(pagoAlumnoExterno);
      if (alumno != null) {
        alumno.getPagoAlumnoExternoCollection().add(pagoAlumnoExterno);
        alumno = em.merge(alumno);
      }
      if (maestro != null) {
        maestro.getPagoAlumnoExternoCollection().add(pagoAlumnoExterno);
        maestro = em.merge(maestro);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      if (findPagoAlumnoExterno(pagoAlumnoExterno.getPagoAlumnoExternoPK()) != null) {
        throw new PreexistingEntityException("PagoAlumnoExterno " + pagoAlumnoExterno + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(PagoAlumnoExterno pagoAlumnoExterno) throws NonexistentEntityException, Exception {
    pagoAlumnoExterno.getPagoAlumnoExternoPK().setMaestroidMaestro(pagoAlumnoExterno.getMaestro().getMaestroPK().getIdMaestro());
    pagoAlumnoExterno.getPagoAlumnoExternoPK().setAlumnoidAlumno(pagoAlumnoExterno.getAlumno().getIdAlumno());
    pagoAlumnoExterno.getPagoAlumnoExternoPK().setMaestrousuarionombreUsuario(pagoAlumnoExterno.getMaestro().getMaestroPK().getUsuarionombreUsuario());
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      PagoAlumnoExterno persistentPagoAlumnoExterno = em.find(PagoAlumnoExterno.class, pagoAlumnoExterno.getPagoAlumnoExternoPK());
      Alumno alumnoOld = persistentPagoAlumnoExterno.getAlumno();
      Alumno alumnoNew = pagoAlumnoExterno.getAlumno();
      Maestro maestroOld = persistentPagoAlumnoExterno.getMaestro();
      Maestro maestroNew = pagoAlumnoExterno.getMaestro();
      if (alumnoNew != null) {
        alumnoNew = em.getReference(alumnoNew.getClass(), alumnoNew.getIdAlumno());
        pagoAlumnoExterno.setAlumno(alumnoNew);
      }
      if (maestroNew != null) {
        maestroNew = em.getReference(maestroNew.getClass(), maestroNew.getMaestroPK());
        pagoAlumnoExterno.setMaestro(maestroNew);
      }
      pagoAlumnoExterno = em.merge(pagoAlumnoExterno);
      if (alumnoOld != null && !alumnoOld.equals(alumnoNew)) {
        alumnoOld.getPagoAlumnoExternoCollection().remove(pagoAlumnoExterno);
        alumnoOld = em.merge(alumnoOld);
      }
      if (alumnoNew != null && !alumnoNew.equals(alumnoOld)) {
        alumnoNew.getPagoAlumnoExternoCollection().add(pagoAlumnoExterno);
        alumnoNew = em.merge(alumnoNew);
      }
      if (maestroOld != null && !maestroOld.equals(maestroNew)) {
        maestroOld.getPagoAlumnoExternoCollection().remove(pagoAlumnoExterno);
        maestroOld = em.merge(maestroOld);
      }
      if (maestroNew != null && !maestroNew.equals(maestroOld)) {
        maestroNew.getPagoAlumnoExternoCollection().add(pagoAlumnoExterno);
        maestroNew = em.merge(maestroNew);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        PagoAlumnoExternoPK id = pagoAlumnoExterno.getPagoAlumnoExternoPK();
        if (findPagoAlumnoExterno(id) == null) {
          throw new NonexistentEntityException("The pagoAlumnoExterno with id " + id + " no longer exists.");
        }
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void destroy(PagoAlumnoExternoPK id) throws NonexistentEntityException {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      PagoAlumnoExterno pagoAlumnoExterno;
      try {
        pagoAlumnoExterno = em.getReference(PagoAlumnoExterno.class, id);
        pagoAlumnoExterno.getPagoAlumnoExternoPK();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The pagoAlumnoExterno with id " + id + " no longer exists.", enfe);
      }
      Alumno alumno = pagoAlumnoExterno.getAlumno();
      if (alumno != null) {
        alumno.getPagoAlumnoExternoCollection().remove(pagoAlumnoExterno);
        alumno = em.merge(alumno);
      }
      Maestro maestro = pagoAlumnoExterno.getMaestro();
      if (maestro != null) {
        maestro.getPagoAlumnoExternoCollection().remove(pagoAlumnoExterno);
        maestro = em.merge(maestro);
      }
      em.remove(pagoAlumnoExterno);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<PagoAlumnoExterno> findPagoAlumnoExternoEntities() {
    return findPagoAlumnoExternoEntities(true, -1, -1);
  }

  public List<PagoAlumnoExterno> findPagoAlumnoExternoEntities(int maxResults, int firstResult) {
    return findPagoAlumnoExternoEntities(false, maxResults, firstResult);
  }

  private List<PagoAlumnoExterno> findPagoAlumnoExternoEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(PagoAlumnoExterno.class));
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

  public PagoAlumnoExterno findPagoAlumnoExterno(PagoAlumnoExternoPK id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(PagoAlumnoExterno.class, id);
    } finally {
      em.close();
    }
  }

  public int getPagoAlumnoExternoCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<PagoAlumnoExterno> rt = cq.from(PagoAlumnoExterno.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
