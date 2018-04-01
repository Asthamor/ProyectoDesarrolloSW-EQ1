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
import modelo.Publicidad;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Egreso;
import modelo.controladores.exceptions.IllegalOrphanException;
import modelo.controladores.exceptions.NonexistentEntityException;
import modelo.controladores.exceptions.PreexistingEntityException;

/**
 *
 * @author alonso
 */
public class EgresoJpaController implements Serializable {

    public EgresoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Egreso egreso) throws PreexistingEntityException, Exception {
        if (egreso.getPublicidadCollection() == null) {
            egreso.setPublicidadCollection(new ArrayList<Publicidad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Publicidad> attachedPublicidadCollection = new ArrayList<Publicidad>();
            for (Publicidad publicidadCollectionPublicidadToAttach : egreso.getPublicidadCollection()) {
                publicidadCollectionPublicidadToAttach = em.getReference(publicidadCollectionPublicidadToAttach.getClass(), publicidadCollectionPublicidadToAttach.getPublicidadPK());
                attachedPublicidadCollection.add(publicidadCollectionPublicidadToAttach);
            }
            egreso.setPublicidadCollection(attachedPublicidadCollection);
            em.persist(egreso);
            for (Publicidad publicidadCollectionPublicidad : egreso.getPublicidadCollection()) {
                Egreso oldEgresoidEgresoOfPublicidadCollectionPublicidad = publicidadCollectionPublicidad.getEgresoidEgreso();
                publicidadCollectionPublicidad.setEgresoidEgreso(egreso);
                publicidadCollectionPublicidad = em.merge(publicidadCollectionPublicidad);
                if (oldEgresoidEgresoOfPublicidadCollectionPublicidad != null) {
                    oldEgresoidEgresoOfPublicidadCollectionPublicidad.getPublicidadCollection().remove(publicidadCollectionPublicidad);
                    oldEgresoidEgresoOfPublicidadCollectionPublicidad = em.merge(oldEgresoidEgresoOfPublicidadCollectionPublicidad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEgreso(egreso.getIdEgreso()) != null) {
                throw new PreexistingEntityException("Egreso " + egreso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Egreso egreso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Egreso persistentEgreso = em.find(Egreso.class, egreso.getIdEgreso());
            Collection<Publicidad> publicidadCollectionOld = persistentEgreso.getPublicidadCollection();
            Collection<Publicidad> publicidadCollectionNew = egreso.getPublicidadCollection();
            List<String> illegalOrphanMessages = null;
            for (Publicidad publicidadCollectionOldPublicidad : publicidadCollectionOld) {
                if (!publicidadCollectionNew.contains(publicidadCollectionOldPublicidad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Publicidad " + publicidadCollectionOldPublicidad + " since its egresoidEgreso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Publicidad> attachedPublicidadCollectionNew = new ArrayList<Publicidad>();
            for (Publicidad publicidadCollectionNewPublicidadToAttach : publicidadCollectionNew) {
                publicidadCollectionNewPublicidadToAttach = em.getReference(publicidadCollectionNewPublicidadToAttach.getClass(), publicidadCollectionNewPublicidadToAttach.getPublicidadPK());
                attachedPublicidadCollectionNew.add(publicidadCollectionNewPublicidadToAttach);
            }
            publicidadCollectionNew = attachedPublicidadCollectionNew;
            egreso.setPublicidadCollection(publicidadCollectionNew);
            egreso = em.merge(egreso);
            for (Publicidad publicidadCollectionNewPublicidad : publicidadCollectionNew) {
                if (!publicidadCollectionOld.contains(publicidadCollectionNewPublicidad)) {
                    Egreso oldEgresoidEgresoOfPublicidadCollectionNewPublicidad = publicidadCollectionNewPublicidad.getEgresoidEgreso();
                    publicidadCollectionNewPublicidad.setEgresoidEgreso(egreso);
                    publicidadCollectionNewPublicidad = em.merge(publicidadCollectionNewPublicidad);
                    if (oldEgresoidEgresoOfPublicidadCollectionNewPublicidad != null && !oldEgresoidEgresoOfPublicidadCollectionNewPublicidad.equals(egreso)) {
                        oldEgresoidEgresoOfPublicidadCollectionNewPublicidad.getPublicidadCollection().remove(publicidadCollectionNewPublicidad);
                        oldEgresoidEgresoOfPublicidadCollectionNewPublicidad = em.merge(oldEgresoidEgresoOfPublicidadCollectionNewPublicidad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = egreso.getIdEgreso();
                if (findEgreso(id) == null) {
                    throw new NonexistentEntityException("The egreso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Egreso egreso;
            try {
                egreso = em.getReference(Egreso.class, id);
                egreso.getIdEgreso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The egreso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Publicidad> publicidadCollectionOrphanCheck = egreso.getPublicidadCollection();
            for (Publicidad publicidadCollectionOrphanCheckPublicidad : publicidadCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Egreso (" + egreso + ") cannot be destroyed since the Publicidad " + publicidadCollectionOrphanCheckPublicidad + " in its publicidadCollection field has a non-nullable egresoidEgreso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(egreso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Egreso> findEgresoEntities() {
        return findEgresoEntities(true, -1, -1);
    }

    public List<Egreso> findEgresoEntities(int maxResults, int firstResult) {
        return findEgresoEntities(false, maxResults, firstResult);
    }

    private List<Egreso> findEgresoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Egreso.class));
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

    public Egreso findEgreso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Egreso.class, id);
        } finally {
            em.close();
        }
    }

    public int getEgresoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Egreso> rt = cq.from(Egreso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
