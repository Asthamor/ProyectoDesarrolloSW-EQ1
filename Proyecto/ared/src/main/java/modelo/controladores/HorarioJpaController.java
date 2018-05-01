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
import modelo.Grupo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Horario;
import modelo.Renta;
import modelo.controladores.exceptions.IllegalOrphanException;
import modelo.controladores.exceptions.NonexistentEntityException;
import modelo.controladores.exceptions.PreexistingEntityException;

/**
 *
 * @author mau
 */
public class HorarioJpaController implements Serializable {

    public HorarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Horario horario) throws PreexistingEntityException, Exception {
        if (horario.getGrupoCollection() == null) {
            horario.setGrupoCollection(new ArrayList<Grupo>());
        }
        if (horario.getRentaCollection() == null) {
            horario.setRentaCollection(new ArrayList<Renta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Grupo> attachedGrupoCollection = new ArrayList<Grupo>();
            for (Grupo grupoCollectionGrupoToAttach : horario.getGrupoCollection()) {
                grupoCollectionGrupoToAttach = em.getReference(grupoCollectionGrupoToAttach.getClass(), grupoCollectionGrupoToAttach.getGrupoPK());
                attachedGrupoCollection.add(grupoCollectionGrupoToAttach);
            }
            horario.setGrupoCollection(attachedGrupoCollection);
            Collection<Renta> attachedRentaCollection = new ArrayList<Renta>();
            for (Renta rentaCollectionRentaToAttach : horario.getRentaCollection()) {
                rentaCollectionRentaToAttach = em.getReference(rentaCollectionRentaToAttach.getClass(), rentaCollectionRentaToAttach.getRentaPK());
                attachedRentaCollection.add(rentaCollectionRentaToAttach);
            }
            horario.setRentaCollection(attachedRentaCollection);
            em.persist(horario);
            for (Grupo grupoCollectionGrupo : horario.getGrupoCollection()) {
                Horario oldHorarioOfGrupoCollectionGrupo = grupoCollectionGrupo.getHorario();
                grupoCollectionGrupo.setHorario(horario);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
                if (oldHorarioOfGrupoCollectionGrupo != null) {
                    oldHorarioOfGrupoCollectionGrupo.getGrupoCollection().remove(grupoCollectionGrupo);
                    oldHorarioOfGrupoCollectionGrupo = em.merge(oldHorarioOfGrupoCollectionGrupo);
                }
            }
            for (Renta rentaCollectionRenta : horario.getRentaCollection()) {
                Horario oldHorarioOfRentaCollectionRenta = rentaCollectionRenta.getHorario();
                rentaCollectionRenta.setHorario(horario);
                rentaCollectionRenta = em.merge(rentaCollectionRenta);
                if (oldHorarioOfRentaCollectionRenta != null) {
                    oldHorarioOfRentaCollectionRenta.getRentaCollection().remove(rentaCollectionRenta);
                    oldHorarioOfRentaCollectionRenta = em.merge(oldHorarioOfRentaCollectionRenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHorario(horario.getIdHorario()) != null) {
                throw new PreexistingEntityException("Horario " + horario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Horario horario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Horario persistentHorario = em.find(Horario.class, horario.getIdHorario());
            Collection<Grupo> grupoCollectionOld = persistentHorario.getGrupoCollection();
            Collection<Grupo> grupoCollectionNew = horario.getGrupoCollection();
            Collection<Renta> rentaCollectionOld = persistentHorario.getRentaCollection();
            Collection<Renta> rentaCollectionNew = horario.getRentaCollection();
            List<String> illegalOrphanMessages = null;
            for (Grupo grupoCollectionOldGrupo : grupoCollectionOld) {
                if (!grupoCollectionNew.contains(grupoCollectionOldGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Grupo " + grupoCollectionOldGrupo + " since its horario field is not nullable.");
                }
            }
            for (Renta rentaCollectionOldRenta : rentaCollectionOld) {
                if (!rentaCollectionNew.contains(rentaCollectionOldRenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Renta " + rentaCollectionOldRenta + " since its horario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Grupo> attachedGrupoCollectionNew = new ArrayList<Grupo>();
            for (Grupo grupoCollectionNewGrupoToAttach : grupoCollectionNew) {
                grupoCollectionNewGrupoToAttach = em.getReference(grupoCollectionNewGrupoToAttach.getClass(), grupoCollectionNewGrupoToAttach.getGrupoPK());
                attachedGrupoCollectionNew.add(grupoCollectionNewGrupoToAttach);
            }
            grupoCollectionNew = attachedGrupoCollectionNew;
            horario.setGrupoCollection(grupoCollectionNew);
            Collection<Renta> attachedRentaCollectionNew = new ArrayList<Renta>();
            for (Renta rentaCollectionNewRentaToAttach : rentaCollectionNew) {
                rentaCollectionNewRentaToAttach = em.getReference(rentaCollectionNewRentaToAttach.getClass(), rentaCollectionNewRentaToAttach.getRentaPK());
                attachedRentaCollectionNew.add(rentaCollectionNewRentaToAttach);
            }
            rentaCollectionNew = attachedRentaCollectionNew;
            horario.setRentaCollection(rentaCollectionNew);
            horario = em.merge(horario);
            for (Grupo grupoCollectionNewGrupo : grupoCollectionNew) {
                if (!grupoCollectionOld.contains(grupoCollectionNewGrupo)) {
                    Horario oldHorarioOfGrupoCollectionNewGrupo = grupoCollectionNewGrupo.getHorario();
                    grupoCollectionNewGrupo.setHorario(horario);
                    grupoCollectionNewGrupo = em.merge(grupoCollectionNewGrupo);
                    if (oldHorarioOfGrupoCollectionNewGrupo != null && !oldHorarioOfGrupoCollectionNewGrupo.equals(horario)) {
                        oldHorarioOfGrupoCollectionNewGrupo.getGrupoCollection().remove(grupoCollectionNewGrupo);
                        oldHorarioOfGrupoCollectionNewGrupo = em.merge(oldHorarioOfGrupoCollectionNewGrupo);
                    }
                }
            }
            for (Renta rentaCollectionNewRenta : rentaCollectionNew) {
                if (!rentaCollectionOld.contains(rentaCollectionNewRenta)) {
                    Horario oldHorarioOfRentaCollectionNewRenta = rentaCollectionNewRenta.getHorario();
                    rentaCollectionNewRenta.setHorario(horario);
                    rentaCollectionNewRenta = em.merge(rentaCollectionNewRenta);
                    if (oldHorarioOfRentaCollectionNewRenta != null && !oldHorarioOfRentaCollectionNewRenta.equals(horario)) {
                        oldHorarioOfRentaCollectionNewRenta.getRentaCollection().remove(rentaCollectionNewRenta);
                        oldHorarioOfRentaCollectionNewRenta = em.merge(oldHorarioOfRentaCollectionNewRenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = horario.getIdHorario();
                if (findHorario(id) == null) {
                    throw new NonexistentEntityException("The horario with id " + id + " no longer exists.");
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
            Horario horario;
            try {
                horario = em.getReference(Horario.class, id);
                horario.getIdHorario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The horario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Grupo> grupoCollectionOrphanCheck = horario.getGrupoCollection();
            for (Grupo grupoCollectionOrphanCheckGrupo : grupoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Horario (" + horario + ") cannot be destroyed since the Grupo " + grupoCollectionOrphanCheckGrupo + " in its grupoCollection field has a non-nullable horario field.");
            }
            Collection<Renta> rentaCollectionOrphanCheck = horario.getRentaCollection();
            for (Renta rentaCollectionOrphanCheckRenta : rentaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Horario (" + horario + ") cannot be destroyed since the Renta " + rentaCollectionOrphanCheckRenta + " in its rentaCollection field has a non-nullable horario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(horario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Horario> findHorarioEntities() {
        return findHorarioEntities(true, -1, -1);
    }

    public List<Horario> findHorarioEntities(int maxResults, int firstResult) {
        return findHorarioEntities(false, maxResults, firstResult);
    }

    private List<Horario> findHorarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Horario.class));
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

    public Horario findHorario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Horario.class, id);
        } finally {
            em.close();
        }
    }

    public int getHorarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Horario> rt = cq.from(Horario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
