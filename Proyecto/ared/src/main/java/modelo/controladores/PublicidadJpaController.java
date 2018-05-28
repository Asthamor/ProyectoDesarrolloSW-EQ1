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
import modelo.Egreso;
import modelo.Maestro;
import modelo.Publicidad;
import modelo.PublicidadPK;
import modelo.controladores.exceptions.NonexistentEntityException;
import modelo.controladores.exceptions.PreexistingEntityException;

/**
 *
 * @author mau
 */
public class PublicidadJpaController implements Serializable {

  public PublicidadJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Publicidad publicidad) throws PreexistingEntityException, Exception {
    if (publicidad.getPublicidadPK() == null) {
      publicidad.setPublicidadPK(new PublicidadPK());
    }
    publicidad.getPublicidadPK().setMaestrousuarionombreUsuario(publicidad.getMaestro().getMaestroPK().getUsuarionombreUsuario());
    publicidad.getPublicidadPK().setMaestroidMaestro(publicidad.getMaestro().getMaestroPK().getIdMaestro());
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Egreso egresoidEgreso = publicidad.getEgresoidEgreso();
      if (egresoidEgreso != null) {
        egresoidEgreso = em.getReference(egresoidEgreso.getClass(), egresoidEgreso.getIdEgreso());
        publicidad.setEgresoidEgreso(egresoidEgreso);
      }
      Maestro maestro = publicidad.getMaestro();
      if (maestro != null) {
        maestro = em.getReference(maestro.getClass(), maestro.getMaestroPK());
        publicidad.setMaestro(maestro);
      }
      em.persist(publicidad);
      if (egresoidEgreso != null) {
        egresoidEgreso.getPublicidadCollection().add(publicidad);
        egresoidEgreso = em.merge(egresoidEgreso);
      }
      if (maestro != null) {
        maestro.getPublicidadCollection().add(publicidad);
        maestro = em.merge(maestro);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      if (findPublicidad(publicidad.getPublicidadPK()) != null) {
        throw new PreexistingEntityException("Publicidad " + publicidad + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Publicidad publicidad) throws NonexistentEntityException, Exception {
    publicidad.getPublicidadPK().setMaestrousuarionombreUsuario(publicidad.getMaestro().getMaestroPK().getUsuarionombreUsuario());
    publicidad.getPublicidadPK().setMaestroidMaestro(publicidad.getMaestro().getMaestroPK().getIdMaestro());
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Publicidad persistentPublicidad = em.find(Publicidad.class, publicidad.getPublicidadPK());
      Egreso egresoidEgresoOld = persistentPublicidad.getEgresoidEgreso();
      Egreso egresoidEgresoNew = publicidad.getEgresoidEgreso();
      Maestro maestroOld = persistentPublicidad.getMaestro();
      Maestro maestroNew = publicidad.getMaestro();
      if (egresoidEgresoNew != null) {
        egresoidEgresoNew = em.getReference(egresoidEgresoNew.getClass(), egresoidEgresoNew.getIdEgreso());
        publicidad.setEgresoidEgreso(egresoidEgresoNew);
      }
      if (maestroNew != null) {
        maestroNew = em.getReference(maestroNew.getClass(), maestroNew.getMaestroPK());
        publicidad.setMaestro(maestroNew);
      }
      publicidad = em.merge(publicidad);
      if (egresoidEgresoOld != null && !egresoidEgresoOld.equals(egresoidEgresoNew)) {
        egresoidEgresoOld.getPublicidadCollection().remove(publicidad);
        egresoidEgresoOld = em.merge(egresoidEgresoOld);
      }
      if (egresoidEgresoNew != null && !egresoidEgresoNew.equals(egresoidEgresoOld)) {
        egresoidEgresoNew.getPublicidadCollection().add(publicidad);
        egresoidEgresoNew = em.merge(egresoidEgresoNew);
      }
      if (maestroOld != null && !maestroOld.equals(maestroNew)) {
        maestroOld.getPublicidadCollection().remove(publicidad);
        maestroOld = em.merge(maestroOld);
      }
      if (maestroNew != null && !maestroNew.equals(maestroOld)) {
        maestroNew.getPublicidadCollection().add(publicidad);
        maestroNew = em.merge(maestroNew);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        PublicidadPK id = publicidad.getPublicidadPK();
        if (findPublicidad(id) == null) {
          throw new NonexistentEntityException("The publicidad with id " + id + " no longer exists.");
        }
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void destroy(PublicidadPK id) throws NonexistentEntityException {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Publicidad publicidad;
      try {
        publicidad = em.getReference(Publicidad.class, id);
        publicidad.getPublicidadPK();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The publicidad with id " + id + " no longer exists.", enfe);
      }
      Egreso egresoidEgreso = publicidad.getEgresoidEgreso();
      if (egresoidEgreso != null) {
        egresoidEgreso.getPublicidadCollection().remove(publicidad);
        egresoidEgreso = em.merge(egresoidEgreso);
      }
      Maestro maestro = publicidad.getMaestro();
      if (maestro != null) {
        maestro.getPublicidadCollection().remove(publicidad);
        maestro = em.merge(maestro);
      }
      em.remove(publicidad);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Publicidad> findPublicidadEntities() {
    return findPublicidadEntities(true, -1, -1);
  }

  public List<Publicidad> findPublicidadEntities(int maxResults, int firstResult) {
    return findPublicidadEntities(false, maxResults, firstResult);
  }

  private List<Publicidad> findPublicidadEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Publicidad.class));
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

  public Publicidad findPublicidad(PublicidadPK id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Publicidad.class, id);
    } finally {
      em.close();
    }
  }

  public int getPublicidadCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Publicidad> rt = cq.from(Publicidad.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }

  public List<Publicidad> getAllbyDate() {
    EntityManager em = getEntityManager();
    return em.createNamedQuery("Publicidad.findAllOrderByDate").getResultList();
  }

}
