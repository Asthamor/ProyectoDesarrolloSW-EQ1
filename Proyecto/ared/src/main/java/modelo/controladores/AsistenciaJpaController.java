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
import modelo.Asistencia;
import modelo.AsistenciaPK;
import modelo.Grupo;
import modelo.controladores.exceptions.NonexistentEntityException;
import modelo.controladores.exceptions.PreexistingEntityException;

/**
 *
 * @author mau
 */
public class AsistenciaJpaController implements Serializable {

    public AsistenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asistencia asistencia) throws PreexistingEntityException, Exception {
        if (asistencia.getAsistenciaPK() == null) {
            asistencia.setAsistenciaPK(new AsistenciaPK());
        }
        asistencia.getAsistenciaPK().setGrupomaestrousuarionombreUsuario(asistencia.getGrupo().getGrupoPK().getMaestrousuarionombreUsuario());
        asistencia.getAsistenciaPK().setGrupohorarioidHorario(asistencia.getGrupo().getGrupoPK().getHorarioidHorario());
        asistencia.getAsistenciaPK().setGrupoidGrupo(asistencia.getGrupo().getGrupoPK().getIdGrupo());
        asistencia.getAsistenciaPK().setGrupomaestroidMaestro(asistencia.getGrupo().getGrupoPK().getMaestroidMaestro());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo grupo = asistencia.getGrupo();
            if (grupo != null) {
                grupo = em.getReference(grupo.getClass(), grupo.getGrupoPK());
                asistencia.setGrupo(grupo);
            }
            em.persist(asistencia);
            if (grupo != null) {
                grupo.getAsistenciaCollection().add(asistencia);
                grupo = em.merge(grupo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAsistencia(asistencia.getAsistenciaPK()) != null) {
                throw new PreexistingEntityException("Asistencia " + asistencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asistencia asistencia) throws NonexistentEntityException, Exception {
        asistencia.getAsistenciaPK().setGrupomaestrousuarionombreUsuario(asistencia.getGrupo().getGrupoPK().getMaestrousuarionombreUsuario());
        asistencia.getAsistenciaPK().setGrupohorarioidHorario(asistencia.getGrupo().getGrupoPK().getHorarioidHorario());
        asistencia.getAsistenciaPK().setGrupoidGrupo(asistencia.getGrupo().getGrupoPK().getIdGrupo());
        asistencia.getAsistenciaPK().setGrupomaestroidMaestro(asistencia.getGrupo().getGrupoPK().getMaestroidMaestro());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asistencia persistentAsistencia = em.find(Asistencia.class, asistencia.getAsistenciaPK());
            Grupo grupoOld = persistentAsistencia.getGrupo();
            Grupo grupoNew = asistencia.getGrupo();
            if (grupoNew != null) {
                grupoNew = em.getReference(grupoNew.getClass(), grupoNew.getGrupoPK());
                asistencia.setGrupo(grupoNew);
            }
            asistencia = em.merge(asistencia);
            if (grupoOld != null && !grupoOld.equals(grupoNew)) {
                grupoOld.getAsistenciaCollection().remove(asistencia);
                grupoOld = em.merge(grupoOld);
            }
            if (grupoNew != null && !grupoNew.equals(grupoOld)) {
                grupoNew.getAsistenciaCollection().add(asistencia);
                grupoNew = em.merge(grupoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                AsistenciaPK id = asistencia.getAsistenciaPK();
                if (findAsistencia(id) == null) {
                    throw new NonexistentEntityException("The asistencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AsistenciaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asistencia asistencia;
            try {
                asistencia = em.getReference(Asistencia.class, id);
                asistencia.getAsistenciaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistencia with id " + id + " no longer exists.", enfe);
            }
            Grupo grupo = asistencia.getGrupo();
            if (grupo != null) {
                grupo.getAsistenciaCollection().remove(asistencia);
                grupo = em.merge(grupo);
            }
            em.remove(asistencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asistencia> findAsistenciaEntities() {
        return findAsistenciaEntities(true, -1, -1);
    }

    public List<Asistencia> findAsistenciaEntities(int maxResults, int firstResult) {
        return findAsistenciaEntities(false, maxResults, firstResult);
    }

    private List<Asistencia> findAsistenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asistencia.class));
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

    public Asistencia findAsistencia(AsistenciaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asistencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asistencia> rt = cq.from(Asistencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
