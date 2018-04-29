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
import modelo.Horario;
import modelo.Maestro;
import modelo.Alumno;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Grupo;
import modelo.GrupoPK;
import modelo.controladores.exceptions.NonexistentEntityException;
import modelo.controladores.exceptions.PreexistingEntityException;

/**
 *
 * @author mauricio
 */
public class GrupoJpaController implements Serializable {

    public GrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grupo grupo) throws PreexistingEntityException, Exception {
        if (grupo.getGrupoPK() == null) {
            grupo.setGrupoPK(new GrupoPK());
        }
        if (grupo.getAlumnoCollection() == null) {
            grupo.setAlumnoCollection(new ArrayList<Alumno>());
        }
        grupo.getGrupoPK().setMaestroidMaestro(grupo.getMaestro().getMaestroPK().getIdMaestro());
        grupo.getGrupoPK().setMaestrousuarionombreUsuario(grupo.getMaestro().getMaestroPK().getUsuarionombreUsuario());
        grupo.getGrupoPK().setHorarioidHorario(grupo.getHorario().getIdHorario());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Horario horario = grupo.getHorario();
            if (horario != null) {
                horario = em.getReference(horario.getClass(), horario.getIdHorario());
                grupo.setHorario(horario);
            }
            Maestro maestro = grupo.getMaestro();
            if (maestro != null) {
                maestro = em.getReference(maestro.getClass(), maestro.getMaestroPK());
                grupo.setMaestro(maestro);
            }
            Collection<Alumno> attachedAlumnoCollection = new ArrayList<Alumno>();
            for (Alumno alumnoCollectionAlumnoToAttach : grupo.getAlumnoCollection()) {
                alumnoCollectionAlumnoToAttach = em.getReference(alumnoCollectionAlumnoToAttach.getClass(), alumnoCollectionAlumnoToAttach.getIdAlumno());
                attachedAlumnoCollection.add(alumnoCollectionAlumnoToAttach);
            }
            grupo.setAlumnoCollection(attachedAlumnoCollection);
            em.persist(grupo);
            if (horario != null) {
                horario.getGrupoCollection().add(grupo);
                horario = em.merge(horario);
            }
            if (maestro != null) {
                maestro.getGrupoCollection().add(grupo);
                maestro = em.merge(maestro);
            }
        for (Alumno alumnoCollectionAlumno : grupo.getAlumnoCollection()) {
                alumnoCollectionAlumno.getGrupoCollection().add(grupo);
                alumnoCollectionAlumno = em.merge(alumnoCollectionAlumno);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGrupo(grupo.getGrupoPK()) != null) {
                throw new PreexistingEntityException("Grupo " + grupo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupo grupo) throws NonexistentEntityException, Exception {
        grupo.getGrupoPK().setMaestroidMaestro(grupo.getMaestro().getMaestroPK().getIdMaestro());
        grupo.getGrupoPK().setMaestrousuarionombreUsuario(grupo.getMaestro().getMaestroPK().getUsuarionombreUsuario());
        grupo.getGrupoPK().setHorarioidHorario(grupo.getHorario().getIdHorario());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getGrupoPK());
            Horario horarioOld = persistentGrupo.getHorario();
            Horario horarioNew = grupo.getHorario();
            Maestro maestroOld = persistentGrupo.getMaestro();
            Maestro maestroNew = grupo.getMaestro();
            Collection<Alumno> alumnoCollectionOld = persistentGrupo.getAlumnoCollection();
            Collection<Alumno> alumnoCollectionNew = grupo.getAlumnoCollection();
            if (horarioNew != null) {
                horarioNew = em.getReference(horarioNew.getClass(), horarioNew.getIdHorario());
                grupo.setHorario(horarioNew);
            }
            if (maestroNew != null) {
                maestroNew = em.getReference(maestroNew.getClass(), maestroNew.getMaestroPK());
                grupo.setMaestro(maestroNew);
            }
            Collection<Alumno> attachedAlumnoCollectionNew = new ArrayList<Alumno>();
            for (Alumno alumnoCollectionNewAlumnoToAttach : alumnoCollectionNew) {
                alumnoCollectionNewAlumnoToAttach = em.getReference(alumnoCollectionNewAlumnoToAttach.getClass(), alumnoCollectionNewAlumnoToAttach.getIdAlumno());
                attachedAlumnoCollectionNew.add(alumnoCollectionNewAlumnoToAttach);
            }
            alumnoCollectionNew = attachedAlumnoCollectionNew;
            grupo.setAlumnoCollection(alumnoCollectionNew);
            grupo = em.merge(grupo);
            if (horarioOld != null && !horarioOld.equals(horarioNew)) {
                horarioOld.getGrupoCollection().remove(grupo);
                horarioOld = em.merge(horarioOld);
            }
            if (horarioNew != null && !horarioNew.equals(horarioOld)) {
                horarioNew.getGrupoCollection().add(grupo);
                horarioNew = em.merge(horarioNew);
            }
            if (maestroOld != null && !maestroOld.equals(maestroNew)) {
                maestroOld.getGrupoCollection().remove(grupo);
                maestroOld = em.merge(maestroOld);
            }
            if (maestroNew != null && !maestroNew.equals(maestroOld)) {
                maestroNew.getGrupoCollection().add(grupo);
                maestroNew = em.merge(maestroNew);
            }
            for (Alumno alumnoCollectionOldAlumno : alumnoCollectionOld) {
                if (!alumnoCollectionNew.contains(alumnoCollectionOldAlumno)) {
                    alumnoCollectionOldAlumno.getGrupoCollection().remove(grupo);
                    alumnoCollectionOldAlumno = em.merge(alumnoCollectionOldAlumno);
                }
            }
            for (Alumno alumnoCollectionNewAlumno : alumnoCollectionNew) {
                if (!alumnoCollectionOld.contains(alumnoCollectionNewAlumno)) {
                    alumnoCollectionNewAlumno.getGrupoCollection().add(grupo);
                    alumnoCollectionNewAlumno = em.merge(alumnoCollectionNewAlumno);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                GrupoPK id = grupo.getGrupoPK();
                if (findGrupo(id) == null) {
                    throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(GrupoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getGrupoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            Horario horario = grupo.getHorario();
            if (horario != null) {
                horario.getGrupoCollection().remove(grupo);
                horario = em.merge(horario);
            }
            Maestro maestro = grupo.getMaestro();
            if (maestro != null) {
                maestro.getGrupoCollection().remove(grupo);
                maestro = em.merge(maestro);
            }
            Collection<Alumno> alumnoCollection = grupo.getAlumnoCollection();
            for (Alumno alumnoCollectionAlumno : alumnoCollection) {
                alumnoCollectionAlumno.getGrupoCollection().remove(grupo);
                alumnoCollectionAlumno = em.merge(alumnoCollectionAlumno);
            }
            em.remove(grupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grupo> findGrupoEntities() {
        return findGrupoEntities(true, -1, -1);
    }

    public List<Grupo> findGrupoEntities(int maxResults, int firstResult) {
        return findGrupoEntities(false, maxResults, firstResult);
    }

    private List<Grupo> findGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupo.class));
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

    public Grupo findGrupo(GrupoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupo> rt = cq.from(Grupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public String ultimoRegistro(){
        String id = "";
        EntityManager em = getEntityManager();
        List <Grupo> grupo;
        try{
            grupo = em.createQuery("select g from Grupo g order by g.grupoPK.idGrupo desc").setMaxResults(1).getResultList();
        } finally{
            em.close();
        }
        id = String.valueOf(grupo.get(0).getGrupoPK().getIdGrupo());
        return id;
    }
    
}
