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
import modelo.Alumno;
import modelo.PagoAlumnoExterno;
import modelo.PagoAlumno;
import modelo.controladores.exceptions.IllegalOrphanException;
import modelo.controladores.exceptions.NonexistentEntityException;

/**
 *
 * @author alonso
 */
public class AlumnoJpaController implements Serializable {

    public AlumnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alumno alumno) {
        if (alumno.getGrupoCollection() == null) {
            alumno.setGrupoCollection(new ArrayList<Grupo>());
        }
        if (alumno.getPagoAlumnoExternoCollection() == null) {
            alumno.setPagoAlumnoExternoCollection(new ArrayList<PagoAlumnoExterno>());
        }
        if (alumno.getPagoAlumnoCollection() == null) {
            alumno.setPagoAlumnoCollection(new ArrayList<PagoAlumno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Grupo> attachedGrupoCollection = new ArrayList<Grupo>();
            for (Grupo grupoCollectionGrupoToAttach : alumno.getGrupoCollection()) {
                grupoCollectionGrupoToAttach = em.getReference(grupoCollectionGrupoToAttach.getClass(), grupoCollectionGrupoToAttach.getGrupoPK());
                attachedGrupoCollection.add(grupoCollectionGrupoToAttach);
            }
            alumno.setGrupoCollection(attachedGrupoCollection);
            Collection<PagoAlumnoExterno> attachedPagoAlumnoExternoCollection = new ArrayList<PagoAlumnoExterno>();
            for (PagoAlumnoExterno pagoAlumnoExternoCollectionPagoAlumnoExternoToAttach : alumno.getPagoAlumnoExternoCollection()) {
                pagoAlumnoExternoCollectionPagoAlumnoExternoToAttach = em.getReference(pagoAlumnoExternoCollectionPagoAlumnoExternoToAttach.getClass(), pagoAlumnoExternoCollectionPagoAlumnoExternoToAttach.getPagoAlumnoExternoPK());
                attachedPagoAlumnoExternoCollection.add(pagoAlumnoExternoCollectionPagoAlumnoExternoToAttach);
            }
            alumno.setPagoAlumnoExternoCollection(attachedPagoAlumnoExternoCollection);
            Collection<PagoAlumno> attachedPagoAlumnoCollection = new ArrayList<PagoAlumno>();
            for (PagoAlumno pagoAlumnoCollectionPagoAlumnoToAttach : alumno.getPagoAlumnoCollection()) {
                pagoAlumnoCollectionPagoAlumnoToAttach = em.getReference(pagoAlumnoCollectionPagoAlumnoToAttach.getClass(), pagoAlumnoCollectionPagoAlumnoToAttach.getPagoAlumnoPK());
                attachedPagoAlumnoCollection.add(pagoAlumnoCollectionPagoAlumnoToAttach);
            }
            alumno.setPagoAlumnoCollection(attachedPagoAlumnoCollection);
            em.persist(alumno);
            for (Grupo grupoCollectionGrupo : alumno.getGrupoCollection()) {
                grupoCollectionGrupo.getAlumnoCollection().add(alumno);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
            }
            for (PagoAlumnoExterno pagoAlumnoExternoCollectionPagoAlumnoExterno : alumno.getPagoAlumnoExternoCollection()) {
                Alumno oldAlumnoOfPagoAlumnoExternoCollectionPagoAlumnoExterno = pagoAlumnoExternoCollectionPagoAlumnoExterno.getAlumno();
                pagoAlumnoExternoCollectionPagoAlumnoExterno.setAlumno(alumno);
                pagoAlumnoExternoCollectionPagoAlumnoExterno = em.merge(pagoAlumnoExternoCollectionPagoAlumnoExterno);
                if (oldAlumnoOfPagoAlumnoExternoCollectionPagoAlumnoExterno != null) {
                    oldAlumnoOfPagoAlumnoExternoCollectionPagoAlumnoExterno.getPagoAlumnoExternoCollection().remove(pagoAlumnoExternoCollectionPagoAlumnoExterno);
                    oldAlumnoOfPagoAlumnoExternoCollectionPagoAlumnoExterno = em.merge(oldAlumnoOfPagoAlumnoExternoCollectionPagoAlumnoExterno);
                }
            }
            for (PagoAlumno pagoAlumnoCollectionPagoAlumno : alumno.getPagoAlumnoCollection()) {
                Alumno oldAlumnoOfPagoAlumnoCollectionPagoAlumno = pagoAlumnoCollectionPagoAlumno.getAlumno();
                pagoAlumnoCollectionPagoAlumno.setAlumno(alumno);
                pagoAlumnoCollectionPagoAlumno = em.merge(pagoAlumnoCollectionPagoAlumno);
                if (oldAlumnoOfPagoAlumnoCollectionPagoAlumno != null) {
                    oldAlumnoOfPagoAlumnoCollectionPagoAlumno.getPagoAlumnoCollection().remove(pagoAlumnoCollectionPagoAlumno);
                    oldAlumnoOfPagoAlumnoCollectionPagoAlumno = em.merge(oldAlumnoOfPagoAlumnoCollectionPagoAlumno);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alumno alumno) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno persistentAlumno = em.find(Alumno.class, alumno.getIdAlumno());
            Collection<Grupo> grupoCollectionOld = persistentAlumno.getGrupoCollection();
            Collection<Grupo> grupoCollectionNew = alumno.getGrupoCollection();
            Collection<PagoAlumnoExterno> pagoAlumnoExternoCollectionOld = persistentAlumno.getPagoAlumnoExternoCollection();
            Collection<PagoAlumnoExterno> pagoAlumnoExternoCollectionNew = alumno.getPagoAlumnoExternoCollection();
            Collection<PagoAlumno> pagoAlumnoCollectionOld = persistentAlumno.getPagoAlumnoCollection();
            Collection<PagoAlumno> pagoAlumnoCollectionNew = alumno.getPagoAlumnoCollection();
            List<String> illegalOrphanMessages = null;
            for (PagoAlumnoExterno pagoAlumnoExternoCollectionOldPagoAlumnoExterno : pagoAlumnoExternoCollectionOld) {
                if (!pagoAlumnoExternoCollectionNew.contains(pagoAlumnoExternoCollectionOldPagoAlumnoExterno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PagoAlumnoExterno " + pagoAlumnoExternoCollectionOldPagoAlumnoExterno + " since its alumno field is not nullable.");
                }
            }
            for (PagoAlumno pagoAlumnoCollectionOldPagoAlumno : pagoAlumnoCollectionOld) {
                if (!pagoAlumnoCollectionNew.contains(pagoAlumnoCollectionOldPagoAlumno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PagoAlumno " + pagoAlumnoCollectionOldPagoAlumno + " since its alumno field is not nullable.");
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
            alumno.setGrupoCollection(grupoCollectionNew);
            Collection<PagoAlumnoExterno> attachedPagoAlumnoExternoCollectionNew = new ArrayList<PagoAlumnoExterno>();
            for (PagoAlumnoExterno pagoAlumnoExternoCollectionNewPagoAlumnoExternoToAttach : pagoAlumnoExternoCollectionNew) {
                pagoAlumnoExternoCollectionNewPagoAlumnoExternoToAttach = em.getReference(pagoAlumnoExternoCollectionNewPagoAlumnoExternoToAttach.getClass(), pagoAlumnoExternoCollectionNewPagoAlumnoExternoToAttach.getPagoAlumnoExternoPK());
                attachedPagoAlumnoExternoCollectionNew.add(pagoAlumnoExternoCollectionNewPagoAlumnoExternoToAttach);
            }
            pagoAlumnoExternoCollectionNew = attachedPagoAlumnoExternoCollectionNew;
            alumno.setPagoAlumnoExternoCollection(pagoAlumnoExternoCollectionNew);
            Collection<PagoAlumno> attachedPagoAlumnoCollectionNew = new ArrayList<PagoAlumno>();
            for (PagoAlumno pagoAlumnoCollectionNewPagoAlumnoToAttach : pagoAlumnoCollectionNew) {
                pagoAlumnoCollectionNewPagoAlumnoToAttach = em.getReference(pagoAlumnoCollectionNewPagoAlumnoToAttach.getClass(), pagoAlumnoCollectionNewPagoAlumnoToAttach.getPagoAlumnoPK());
                attachedPagoAlumnoCollectionNew.add(pagoAlumnoCollectionNewPagoAlumnoToAttach);
            }
            pagoAlumnoCollectionNew = attachedPagoAlumnoCollectionNew;
            alumno.setPagoAlumnoCollection(pagoAlumnoCollectionNew);
            alumno = em.merge(alumno);
            for (Grupo grupoCollectionOldGrupo : grupoCollectionOld) {
                if (!grupoCollectionNew.contains(grupoCollectionOldGrupo)) {
                    grupoCollectionOldGrupo.getAlumnoCollection().remove(alumno);
                    grupoCollectionOldGrupo = em.merge(grupoCollectionOldGrupo);
                }
            }
            for (Grupo grupoCollectionNewGrupo : grupoCollectionNew) {
                if (!grupoCollectionOld.contains(grupoCollectionNewGrupo)) {
                    grupoCollectionNewGrupo.getAlumnoCollection().add(alumno);
                    grupoCollectionNewGrupo = em.merge(grupoCollectionNewGrupo);
                }
            }
            for (PagoAlumnoExterno pagoAlumnoExternoCollectionNewPagoAlumnoExterno : pagoAlumnoExternoCollectionNew) {
                if (!pagoAlumnoExternoCollectionOld.contains(pagoAlumnoExternoCollectionNewPagoAlumnoExterno)) {
                    Alumno oldAlumnoOfPagoAlumnoExternoCollectionNewPagoAlumnoExterno = pagoAlumnoExternoCollectionNewPagoAlumnoExterno.getAlumno();
                    pagoAlumnoExternoCollectionNewPagoAlumnoExterno.setAlumno(alumno);
                    pagoAlumnoExternoCollectionNewPagoAlumnoExterno = em.merge(pagoAlumnoExternoCollectionNewPagoAlumnoExterno);
                    if (oldAlumnoOfPagoAlumnoExternoCollectionNewPagoAlumnoExterno != null && !oldAlumnoOfPagoAlumnoExternoCollectionNewPagoAlumnoExterno.equals(alumno)) {
                        oldAlumnoOfPagoAlumnoExternoCollectionNewPagoAlumnoExterno.getPagoAlumnoExternoCollection().remove(pagoAlumnoExternoCollectionNewPagoAlumnoExterno);
                        oldAlumnoOfPagoAlumnoExternoCollectionNewPagoAlumnoExterno = em.merge(oldAlumnoOfPagoAlumnoExternoCollectionNewPagoAlumnoExterno);
                    }
                }
            }
            for (PagoAlumno pagoAlumnoCollectionNewPagoAlumno : pagoAlumnoCollectionNew) {
                if (!pagoAlumnoCollectionOld.contains(pagoAlumnoCollectionNewPagoAlumno)) {
                    Alumno oldAlumnoOfPagoAlumnoCollectionNewPagoAlumno = pagoAlumnoCollectionNewPagoAlumno.getAlumno();
                    pagoAlumnoCollectionNewPagoAlumno.setAlumno(alumno);
                    pagoAlumnoCollectionNewPagoAlumno = em.merge(pagoAlumnoCollectionNewPagoAlumno);
                    if (oldAlumnoOfPagoAlumnoCollectionNewPagoAlumno != null && !oldAlumnoOfPagoAlumnoCollectionNewPagoAlumno.equals(alumno)) {
                        oldAlumnoOfPagoAlumnoCollectionNewPagoAlumno.getPagoAlumnoCollection().remove(pagoAlumnoCollectionNewPagoAlumno);
                        oldAlumnoOfPagoAlumnoCollectionNewPagoAlumno = em.merge(oldAlumnoOfPagoAlumnoCollectionNewPagoAlumno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alumno.getIdAlumno();
                if (findAlumno(id) == null) {
                    throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.");
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
            Alumno alumno;
            try {
                alumno = em.getReference(Alumno.class, id);
                alumno.getIdAlumno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PagoAlumnoExterno> pagoAlumnoExternoCollectionOrphanCheck = alumno.getPagoAlumnoExternoCollection();
            for (PagoAlumnoExterno pagoAlumnoExternoCollectionOrphanCheckPagoAlumnoExterno : pagoAlumnoExternoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumno (" + alumno + ") cannot be destroyed since the PagoAlumnoExterno " + pagoAlumnoExternoCollectionOrphanCheckPagoAlumnoExterno + " in its pagoAlumnoExternoCollection field has a non-nullable alumno field.");
            }
            Collection<PagoAlumno> pagoAlumnoCollectionOrphanCheck = alumno.getPagoAlumnoCollection();
            for (PagoAlumno pagoAlumnoCollectionOrphanCheckPagoAlumno : pagoAlumnoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumno (" + alumno + ") cannot be destroyed since the PagoAlumno " + pagoAlumnoCollectionOrphanCheckPagoAlumno + " in its pagoAlumnoCollection field has a non-nullable alumno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Grupo> grupoCollection = alumno.getGrupoCollection();
            for (Grupo grupoCollectionGrupo : grupoCollection) {
                grupoCollectionGrupo.getAlumnoCollection().remove(alumno);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
            }
            em.remove(alumno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alumno> findAlumnoEntities() {
        return findAlumnoEntities(true, -1, -1);
    }

    public List<Alumno> findAlumnoEntities(int maxResults, int firstResult) {
        return findAlumnoEntities(false, maxResults, firstResult);
    }

    private List<Alumno> findAlumnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alumno.class));
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

    public Alumno findAlumno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alumno.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlumnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alumno> rt = cq.from(Alumno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Alumno> findAlumnoByName(String name){
        EntityManager em = getEntityManager();
        return em.createQuery("SELECT a FROM Alumno a WHERE a.nombre LIKE :nombre").setParameter("nombre", name+"%").getResultList();
    }
    
}
