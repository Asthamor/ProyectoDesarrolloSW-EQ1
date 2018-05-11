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
import modelo.Maestro;
import modelo.PagoMaestro;
import modelo.PagoMaestroPK;
import modelo.controladores.exceptions.NonexistentEntityException;
import modelo.controladores.exceptions.PreexistingEntityException;

/**
 *
 * @author mau
 */
public class PagoMaestroJpaController implements Serializable {

  public PagoMaestroJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(PagoMaestro pagoMaestro) throws PreexistingEntityException, Exception {
    if (pagoMaestro.getPagoMaestroPK() == null) {
      pagoMaestro.setPagoMaestroPK(new PagoMaestroPK());
    }
    pagoMaestro.getPagoMaestroPK().setMaestrousuarionombreUsuario(pagoMaestro.getMaestro().getMaestroPK().getUsuarionombreUsuario());
    pagoMaestro.getPagoMaestroPK().setMaestroidMaestro(pagoMaestro.getMaestro().getMaestroPK().getIdMaestro());
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Maestro maestro = pagoMaestro.getMaestro();
      if (maestro != null) {
        maestro = em.getReference(maestro.getClass(), maestro.getMaestroPK());
        pagoMaestro.setMaestro(maestro);
      }
      em.persist(pagoMaestro);
      if (maestro != null) {
        maestro.getPagoMaestroCollection().add(pagoMaestro);
        maestro = em.merge(maestro);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      if (findPagoMaestro(pagoMaestro.getPagoMaestroPK()) != null) {
        throw new PreexistingEntityException("PagoMaestro " + pagoMaestro + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(PagoMaestro pagoMaestro) throws NonexistentEntityException, Exception {
    pagoMaestro.getPagoMaestroPK().setMaestrousuarionombreUsuario(pagoMaestro.getMaestro().getMaestroPK().getUsuarionombreUsuario());
    pagoMaestro.getPagoMaestroPK().setMaestroidMaestro(pagoMaestro.getMaestro().getMaestroPK().getIdMaestro());
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      PagoMaestro persistentPagoMaestro = em.find(PagoMaestro.class, pagoMaestro.getPagoMaestroPK());
      Maestro maestroOld = persistentPagoMaestro.getMaestro();
      Maestro maestroNew = pagoMaestro.getMaestro();
      if (maestroNew != null) {
        maestroNew = em.getReference(maestroNew.getClass(), maestroNew.getMaestroPK());
        pagoMaestro.setMaestro(maestroNew);
      }
      pagoMaestro = em.merge(pagoMaestro);
      if (maestroOld != null && !maestroOld.equals(maestroNew)) {
        maestroOld.getPagoMaestroCollection().remove(pagoMaestro);
        maestroOld = em.merge(maestroOld);
      }
      if (maestroNew != null && !maestroNew.equals(maestroOld)) {
        maestroNew.getPagoMaestroCollection().add(pagoMaestro);
        maestroNew = em.merge(maestroNew);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        PagoMaestroPK id = pagoMaestro.getPagoMaestroPK();
        if (findPagoMaestro(id) == null) {
          throw new NonexistentEntityException("The pagoMaestro with id " + id + " no longer exists.");
        }
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void destroy(PagoMaestroPK id) throws NonexistentEntityException {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      PagoMaestro pagoMaestro;
      try {
        pagoMaestro = em.getReference(PagoMaestro.class, id);
        pagoMaestro.getPagoMaestroPK();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The pagoMaestro with id " + id + " no longer exists.", enfe);
      }
      Maestro maestro = pagoMaestro.getMaestro();
      if (maestro != null) {
        maestro.getPagoMaestroCollection().remove(pagoMaestro);
        maestro = em.merge(maestro);
      }
      em.remove(pagoMaestro);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<PagoMaestro> findPagoMaestroEntities() {
    return findPagoMaestroEntities(true, -1, -1);
  }

  public List<PagoMaestro> findPagoMaestroEntities(int maxResults, int firstResult) {
    return findPagoMaestroEntities(false, maxResults, firstResult);
  }

  private List<PagoMaestro> findPagoMaestroEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(PagoMaestro.class));
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

  public PagoMaestro findPagoMaestro(PagoMaestroPK id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(PagoMaestro.class, id);
    } finally {
      em.close();
    }
  }

  public int getPagoMaestroCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<PagoMaestro> rt = cq.from(PagoMaestro.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
