/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.controladores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Maestro;
import modelo.PagoAlumno;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Promocion;
import modelo.PromocionPK;
import modelo.controladores.exceptions.NonexistentEntityException;
import modelo.controladores.exceptions.PreexistingEntityException;

/**
 *
 * @author mau
 */
public class PromocionJpaController implements Serializable {

  public PromocionJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Promocion promocion) throws PreexistingEntityException, Exception {
    if (promocion.getPromocionPK() == null) {
      promocion.setPromocionPK(new PromocionPK());
    }
    if (promocion.getPagoAlumnoCollection() == null) {
      promocion.setPagoAlumnoCollection(new ArrayList<PagoAlumno>());
    }
    promocion.getPromocionPK().setMaestroidMaestro(promocion.getMaestro().getMaestroPK().getIdMaestro());
    promocion.getPromocionPK().setMaestrousuarionombreUsuario(promocion.getMaestro().getMaestroPK().getUsuarionombreUsuario());
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Maestro maestro = promocion.getMaestro();
      if (maestro != null) {
        maestro = em.getReference(maestro.getClass(), maestro.getMaestroPK());
        promocion.setMaestro(maestro);
      }
      Collection<PagoAlumno> attachedPagoAlumnoCollection = new ArrayList<PagoAlumno>();
      for (PagoAlumno pagoAlumnoCollectionPagoAlumnoToAttach : promocion.getPagoAlumnoCollection()) {
        pagoAlumnoCollectionPagoAlumnoToAttach = em.getReference(pagoAlumnoCollectionPagoAlumnoToAttach.getClass(), pagoAlumnoCollectionPagoAlumnoToAttach.getPagoAlumnoPK());
        attachedPagoAlumnoCollection.add(pagoAlumnoCollectionPagoAlumnoToAttach);
      }
      promocion.setPagoAlumnoCollection(attachedPagoAlumnoCollection);
      em.persist(promocion);
      if (maestro != null) {
        maestro.getPromocionCollection().add(promocion);
        maestro = em.merge(maestro);
      }
      for (PagoAlumno pagoAlumnoCollectionPagoAlumno : promocion.getPagoAlumnoCollection()) {
        Promocion oldPromocionOfPagoAlumnoCollectionPagoAlumno = pagoAlumnoCollectionPagoAlumno.getPromocion();
        pagoAlumnoCollectionPagoAlumno.setPromocion(promocion);
        pagoAlumnoCollectionPagoAlumno = em.merge(pagoAlumnoCollectionPagoAlumno);
        if (oldPromocionOfPagoAlumnoCollectionPagoAlumno != null) {
          oldPromocionOfPagoAlumnoCollectionPagoAlumno.getPagoAlumnoCollection().remove(pagoAlumnoCollectionPagoAlumno);
          oldPromocionOfPagoAlumnoCollectionPagoAlumno = em.merge(oldPromocionOfPagoAlumnoCollectionPagoAlumno);
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      if (findPromocion(promocion.getPromocionPK()) != null) {
        throw new PreexistingEntityException("Promocion " + promocion + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Promocion promocion) throws NonexistentEntityException, Exception {
    promocion.getPromocionPK().setMaestroidMaestro(promocion.getMaestro().getMaestroPK().getIdMaestro());
    promocion.getPromocionPK().setMaestrousuarionombreUsuario(promocion.getMaestro().getMaestroPK().getUsuarionombreUsuario());
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Promocion persistentPromocion = em.find(Promocion.class, promocion.getPromocionPK());
      Maestro maestroOld = persistentPromocion.getMaestro();
      Maestro maestroNew = promocion.getMaestro();
      Collection<PagoAlumno> pagoAlumnoCollectionOld = persistentPromocion.getPagoAlumnoCollection();
      Collection<PagoAlumno> pagoAlumnoCollectionNew = promocion.getPagoAlumnoCollection();
      if (maestroNew != null) {
        maestroNew = em.getReference(maestroNew.getClass(), maestroNew.getMaestroPK());
        promocion.setMaestro(maestroNew);
      }
      Collection<PagoAlumno> attachedPagoAlumnoCollectionNew = new ArrayList<PagoAlumno>();
      for (PagoAlumno pagoAlumnoCollectionNewPagoAlumnoToAttach : pagoAlumnoCollectionNew) {
        pagoAlumnoCollectionNewPagoAlumnoToAttach = em.getReference(pagoAlumnoCollectionNewPagoAlumnoToAttach.getClass(), pagoAlumnoCollectionNewPagoAlumnoToAttach.getPagoAlumnoPK());
        attachedPagoAlumnoCollectionNew.add(pagoAlumnoCollectionNewPagoAlumnoToAttach);
      }
      pagoAlumnoCollectionNew = attachedPagoAlumnoCollectionNew;
      promocion.setPagoAlumnoCollection(pagoAlumnoCollectionNew);
      promocion = em.merge(promocion);
      if (maestroOld != null && !maestroOld.equals(maestroNew)) {
        maestroOld.getPromocionCollection().remove(promocion);
        maestroOld = em.merge(maestroOld);
      }
      if (maestroNew != null && !maestroNew.equals(maestroOld)) {
        maestroNew.getPromocionCollection().add(promocion);
        maestroNew = em.merge(maestroNew);
      }
      for (PagoAlumno pagoAlumnoCollectionOldPagoAlumno : pagoAlumnoCollectionOld) {
        if (!pagoAlumnoCollectionNew.contains(pagoAlumnoCollectionOldPagoAlumno)) {
          pagoAlumnoCollectionOldPagoAlumno.setPromocion(null);
          pagoAlumnoCollectionOldPagoAlumno = em.merge(pagoAlumnoCollectionOldPagoAlumno);
        }
      }
      for (PagoAlumno pagoAlumnoCollectionNewPagoAlumno : pagoAlumnoCollectionNew) {
        if (!pagoAlumnoCollectionOld.contains(pagoAlumnoCollectionNewPagoAlumno)) {
          Promocion oldPromocionOfPagoAlumnoCollectionNewPagoAlumno = pagoAlumnoCollectionNewPagoAlumno.getPromocion();
          pagoAlumnoCollectionNewPagoAlumno.setPromocion(promocion);
          pagoAlumnoCollectionNewPagoAlumno = em.merge(pagoAlumnoCollectionNewPagoAlumno);
          if (oldPromocionOfPagoAlumnoCollectionNewPagoAlumno != null && !oldPromocionOfPagoAlumnoCollectionNewPagoAlumno.equals(promocion)) {
            oldPromocionOfPagoAlumnoCollectionNewPagoAlumno.getPagoAlumnoCollection().remove(pagoAlumnoCollectionNewPagoAlumno);
            oldPromocionOfPagoAlumnoCollectionNewPagoAlumno = em.merge(oldPromocionOfPagoAlumnoCollectionNewPagoAlumno);
          }
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        PromocionPK id = promocion.getPromocionPK();
        if (findPromocion(id) == null) {
          throw new NonexistentEntityException("The promocion with id " + id + " no longer exists.");
        }
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void destroy(PromocionPK id) throws NonexistentEntityException {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Promocion promocion;
      try {
        promocion = em.getReference(Promocion.class, id);
        promocion.getPromocionPK();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The promocion with id " + id + " no longer exists.", enfe);
      }
      Maestro maestro = promocion.getMaestro();
      if (maestro != null) {
        maestro.getPromocionCollection().remove(promocion);
        maestro = em.merge(maestro);
      }
      Collection<PagoAlumno> pagoAlumnoCollection = promocion.getPagoAlumnoCollection();
      for (PagoAlumno pagoAlumnoCollectionPagoAlumno : pagoAlumnoCollection) {
        pagoAlumnoCollectionPagoAlumno.setPromocion(null);
        pagoAlumnoCollectionPagoAlumno = em.merge(pagoAlumnoCollectionPagoAlumno);
      }
      em.remove(promocion);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Promocion> findPromocionEntities() {
    return findPromocionEntities(true, -1, -1);
  }

  public List<Promocion> findPromocionEntities(int maxResults, int firstResult) {
    return findPromocionEntities(false, maxResults, firstResult);
  }

  private List<Promocion> findPromocionEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Promocion.class));
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

  public Promocion findPromocion(PromocionPK id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Promocion.class, id);
    } finally {
      em.close();
    }
  }

  public int getPromocionCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Promocion> rt = cq.from(Promocion.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }

  
}
