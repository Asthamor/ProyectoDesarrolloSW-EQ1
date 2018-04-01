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
import modelo.Renta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.PagoRenta;
import modelo.controladores.exceptions.IllegalOrphanException;
import modelo.controladores.exceptions.NonexistentEntityException;
import modelo.controladores.exceptions.PreexistingEntityException;

/**
 *
 * @author alonso
 */
public class PagoRentaJpaController implements Serializable {

    public PagoRentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagoRenta pagoRenta) throws PreexistingEntityException, Exception {
        if (pagoRenta.getRentaCollection() == null) {
            pagoRenta.setRentaCollection(new ArrayList<Renta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Renta> attachedRentaCollection = new ArrayList<Renta>();
            for (Renta rentaCollectionRentaToAttach : pagoRenta.getRentaCollection()) {
                rentaCollectionRentaToAttach = em.getReference(rentaCollectionRentaToAttach.getClass(), rentaCollectionRentaToAttach.getRentaPK());
                attachedRentaCollection.add(rentaCollectionRentaToAttach);
            }
            pagoRenta.setRentaCollection(attachedRentaCollection);
            em.persist(pagoRenta);
            for (Renta rentaCollectionRenta : pagoRenta.getRentaCollection()) {
                PagoRenta oldPagoRentaOfRentaCollectionRenta = rentaCollectionRenta.getPagoRenta();
                rentaCollectionRenta.setPagoRenta(pagoRenta);
                rentaCollectionRenta = em.merge(rentaCollectionRenta);
                if (oldPagoRentaOfRentaCollectionRenta != null) {
                    oldPagoRentaOfRentaCollectionRenta.getRentaCollection().remove(rentaCollectionRenta);
                    oldPagoRentaOfRentaCollectionRenta = em.merge(oldPagoRentaOfRentaCollectionRenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPagoRenta(pagoRenta.getIdPago()) != null) {
                throw new PreexistingEntityException("PagoRenta " + pagoRenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagoRenta pagoRenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagoRenta persistentPagoRenta = em.find(PagoRenta.class, pagoRenta.getIdPago());
            Collection<Renta> rentaCollectionOld = persistentPagoRenta.getRentaCollection();
            Collection<Renta> rentaCollectionNew = pagoRenta.getRentaCollection();
            List<String> illegalOrphanMessages = null;
            for (Renta rentaCollectionOldRenta : rentaCollectionOld) {
                if (!rentaCollectionNew.contains(rentaCollectionOldRenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Renta " + rentaCollectionOldRenta + " since its pagoRenta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Renta> attachedRentaCollectionNew = new ArrayList<Renta>();
            for (Renta rentaCollectionNewRentaToAttach : rentaCollectionNew) {
                rentaCollectionNewRentaToAttach = em.getReference(rentaCollectionNewRentaToAttach.getClass(), rentaCollectionNewRentaToAttach.getRentaPK());
                attachedRentaCollectionNew.add(rentaCollectionNewRentaToAttach);
            }
            rentaCollectionNew = attachedRentaCollectionNew;
            pagoRenta.setRentaCollection(rentaCollectionNew);
            pagoRenta = em.merge(pagoRenta);
            for (Renta rentaCollectionNewRenta : rentaCollectionNew) {
                if (!rentaCollectionOld.contains(rentaCollectionNewRenta)) {
                    PagoRenta oldPagoRentaOfRentaCollectionNewRenta = rentaCollectionNewRenta.getPagoRenta();
                    rentaCollectionNewRenta.setPagoRenta(pagoRenta);
                    rentaCollectionNewRenta = em.merge(rentaCollectionNewRenta);
                    if (oldPagoRentaOfRentaCollectionNewRenta != null && !oldPagoRentaOfRentaCollectionNewRenta.equals(pagoRenta)) {
                        oldPagoRentaOfRentaCollectionNewRenta.getRentaCollection().remove(rentaCollectionNewRenta);
                        oldPagoRentaOfRentaCollectionNewRenta = em.merge(oldPagoRentaOfRentaCollectionNewRenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagoRenta.getIdPago();
                if (findPagoRenta(id) == null) {
                    throw new NonexistentEntityException("The pagoRenta with id " + id + " no longer exists.");
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
            PagoRenta pagoRenta;
            try {
                pagoRenta = em.getReference(PagoRenta.class, id);
                pagoRenta.getIdPago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoRenta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Renta> rentaCollectionOrphanCheck = pagoRenta.getRentaCollection();
            for (Renta rentaCollectionOrphanCheckRenta : rentaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PagoRenta (" + pagoRenta + ") cannot be destroyed since the Renta " + rentaCollectionOrphanCheckRenta + " in its rentaCollection field has a non-nullable pagoRenta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pagoRenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PagoRenta> findPagoRentaEntities() {
        return findPagoRentaEntities(true, -1, -1);
    }

    public List<PagoRenta> findPagoRentaEntities(int maxResults, int firstResult) {
        return findPagoRentaEntities(false, maxResults, firstResult);
    }

    private List<PagoRenta> findPagoRentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagoRenta.class));
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

    public PagoRenta findPagoRenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagoRenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoRentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagoRenta> rt = cq.from(PagoRenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
