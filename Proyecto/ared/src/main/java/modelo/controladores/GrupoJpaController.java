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
import modelo.PagoAlumno;
import modelo.Asistencia;
import modelo.Grupo;
import modelo.GrupoPK;
import modelo.controladores.exceptions.IllegalOrphanException;
import modelo.controladores.exceptions.NonexistentEntityException;
import modelo.controladores.exceptions.PreexistingEntityException;

/**
 *
 * @author mau
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
        if (grupo.getPagoAlumnoCollection() == null) {
            grupo.setPagoAlumnoCollection(new ArrayList<PagoAlumno>());
        }
        if (grupo.getAsistenciaCollection() == null) {
            grupo.setAsistenciaCollection(new ArrayList<Asistencia>());
        }
        grupo.getGrupoPK().setMaestrousuarionombreUsuario(grupo.getMaestro().getMaestroPK().getUsuarionombreUsuario());
        grupo.getGrupoPK().setHorarioidHorario(grupo.getHorario().getIdHorario());
        grupo.getGrupoPK().setMaestroidMaestro(grupo.getMaestro().getMaestroPK().getIdMaestro());
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
            Collection<PagoAlumno> attachedPagoAlumnoCollection = new ArrayList<PagoAlumno>();
            for (PagoAlumno pagoAlumnoCollectionPagoAlumnoToAttach : grupo.getPagoAlumnoCollection()) {
                pagoAlumnoCollectionPagoAlumnoToAttach = em.getReference(pagoAlumnoCollectionPagoAlumnoToAttach.getClass(), pagoAlumnoCollectionPagoAlumnoToAttach.getPagoAlumnoPK());
                attachedPagoAlumnoCollection.add(pagoAlumnoCollectionPagoAlumnoToAttach);
            }
            grupo.setPagoAlumnoCollection(attachedPagoAlumnoCollection);
            Collection<Asistencia> attachedAsistenciaCollection = new ArrayList<Asistencia>();
            for (Asistencia asistenciaCollectionAsistenciaToAttach : grupo.getAsistenciaCollection()) {
                asistenciaCollectionAsistenciaToAttach = em.getReference(asistenciaCollectionAsistenciaToAttach.getClass(), asistenciaCollectionAsistenciaToAttach.getAsistenciaPK());
                attachedAsistenciaCollection.add(asistenciaCollectionAsistenciaToAttach);
            }
            grupo.setAsistenciaCollection(attachedAsistenciaCollection);
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
            for (PagoAlumno pagoAlumnoCollectionPagoAlumno : grupo.getPagoAlumnoCollection()) {
                Grupo oldGrupoOfPagoAlumnoCollectionPagoAlumno = pagoAlumnoCollectionPagoAlumno.getGrupo();
                pagoAlumnoCollectionPagoAlumno.setGrupo(grupo);
                pagoAlumnoCollectionPagoAlumno = em.merge(pagoAlumnoCollectionPagoAlumno);
                if (oldGrupoOfPagoAlumnoCollectionPagoAlumno != null) {
                    oldGrupoOfPagoAlumnoCollectionPagoAlumno.getPagoAlumnoCollection().remove(pagoAlumnoCollectionPagoAlumno);
                    oldGrupoOfPagoAlumnoCollectionPagoAlumno = em.merge(oldGrupoOfPagoAlumnoCollectionPagoAlumno);
                }
            }
            for (Asistencia asistenciaCollectionAsistencia : grupo.getAsistenciaCollection()) {
                Grupo oldGrupoOfAsistenciaCollectionAsistencia = asistenciaCollectionAsistencia.getGrupo();
                asistenciaCollectionAsistencia.setGrupo(grupo);
                asistenciaCollectionAsistencia = em.merge(asistenciaCollectionAsistencia);
                if (oldGrupoOfAsistenciaCollectionAsistencia != null) {
                    oldGrupoOfAsistenciaCollectionAsistencia.getAsistenciaCollection().remove(asistenciaCollectionAsistencia);
                    oldGrupoOfAsistenciaCollectionAsistencia = em.merge(oldGrupoOfAsistenciaCollectionAsistencia);
                }
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

    public void edit(Grupo grupo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        grupo.getGrupoPK().setMaestrousuarionombreUsuario(grupo.getMaestro().getMaestroPK().getUsuarionombreUsuario());
        grupo.getGrupoPK().setHorarioidHorario(grupo.getHorario().getIdHorario());
        grupo.getGrupoPK().setMaestroidMaestro(grupo.getMaestro().getMaestroPK().getIdMaestro());
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
            Collection<PagoAlumno> pagoAlumnoCollectionOld = persistentGrupo.getPagoAlumnoCollection();
            Collection<PagoAlumno> pagoAlumnoCollectionNew = grupo.getPagoAlumnoCollection();
            Collection<Asistencia> asistenciaCollectionOld = persistentGrupo.getAsistenciaCollection();
            Collection<Asistencia> asistenciaCollectionNew = grupo.getAsistenciaCollection();
            List<String> illegalOrphanMessages = null;
            for (PagoAlumno pagoAlumnoCollectionOldPagoAlumno : pagoAlumnoCollectionOld) {
                if (!pagoAlumnoCollectionNew.contains(pagoAlumnoCollectionOldPagoAlumno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PagoAlumno " + pagoAlumnoCollectionOldPagoAlumno + " since its grupo field is not nullable.");
                }
            }
            for (Asistencia asistenciaCollectionOldAsistencia : asistenciaCollectionOld) {
                if (!asistenciaCollectionNew.contains(asistenciaCollectionOldAsistencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asistencia " + asistenciaCollectionOldAsistencia + " since its grupo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
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
            Collection<PagoAlumno> attachedPagoAlumnoCollectionNew = new ArrayList<PagoAlumno>();
            for (PagoAlumno pagoAlumnoCollectionNewPagoAlumnoToAttach : pagoAlumnoCollectionNew) {
                pagoAlumnoCollectionNewPagoAlumnoToAttach = em.getReference(pagoAlumnoCollectionNewPagoAlumnoToAttach.getClass(), pagoAlumnoCollectionNewPagoAlumnoToAttach.getPagoAlumnoPK());
                attachedPagoAlumnoCollectionNew.add(pagoAlumnoCollectionNewPagoAlumnoToAttach);
            }
            pagoAlumnoCollectionNew = attachedPagoAlumnoCollectionNew;
            grupo.setPagoAlumnoCollection(pagoAlumnoCollectionNew);
            Collection<Asistencia> attachedAsistenciaCollectionNew = new ArrayList<Asistencia>();
            for (Asistencia asistenciaCollectionNewAsistenciaToAttach : asistenciaCollectionNew) {
                asistenciaCollectionNewAsistenciaToAttach = em.getReference(asistenciaCollectionNewAsistenciaToAttach.getClass(), asistenciaCollectionNewAsistenciaToAttach.getAsistenciaPK());
                attachedAsistenciaCollectionNew.add(asistenciaCollectionNewAsistenciaToAttach);
            }
            asistenciaCollectionNew = attachedAsistenciaCollectionNew;
            grupo.setAsistenciaCollection(asistenciaCollectionNew);
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
            for (PagoAlumno pagoAlumnoCollectionNewPagoAlumno : pagoAlumnoCollectionNew) {
                if (!pagoAlumnoCollectionOld.contains(pagoAlumnoCollectionNewPagoAlumno)) {
                    Grupo oldGrupoOfPagoAlumnoCollectionNewPagoAlumno = pagoAlumnoCollectionNewPagoAlumno.getGrupo();
                    pagoAlumnoCollectionNewPagoAlumno.setGrupo(grupo);
                    pagoAlumnoCollectionNewPagoAlumno = em.merge(pagoAlumnoCollectionNewPagoAlumno);
                    if (oldGrupoOfPagoAlumnoCollectionNewPagoAlumno != null && !oldGrupoOfPagoAlumnoCollectionNewPagoAlumno.equals(grupo)) {
                        oldGrupoOfPagoAlumnoCollectionNewPagoAlumno.getPagoAlumnoCollection().remove(pagoAlumnoCollectionNewPagoAlumno);
                        oldGrupoOfPagoAlumnoCollectionNewPagoAlumno = em.merge(oldGrupoOfPagoAlumnoCollectionNewPagoAlumno);
                    }
                }
            }
            for (Asistencia asistenciaCollectionNewAsistencia : asistenciaCollectionNew) {
                if (!asistenciaCollectionOld.contains(asistenciaCollectionNewAsistencia)) {
                    Grupo oldGrupoOfAsistenciaCollectionNewAsistencia = asistenciaCollectionNewAsistencia.getGrupo();
                    asistenciaCollectionNewAsistencia.setGrupo(grupo);
                    asistenciaCollectionNewAsistencia = em.merge(asistenciaCollectionNewAsistencia);
                    if (oldGrupoOfAsistenciaCollectionNewAsistencia != null && !oldGrupoOfAsistenciaCollectionNewAsistencia.equals(grupo)) {
                        oldGrupoOfAsistenciaCollectionNewAsistencia.getAsistenciaCollection().remove(asistenciaCollectionNewAsistencia);
                        oldGrupoOfAsistenciaCollectionNewAsistencia = em.merge(oldGrupoOfAsistenciaCollectionNewAsistencia);
                    }
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

    public void destroy(GrupoPK id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Collection<PagoAlumno> pagoAlumnoCollectionOrphanCheck = grupo.getPagoAlumnoCollection();
            for (PagoAlumno pagoAlumnoCollectionOrphanCheckPagoAlumno : pagoAlumnoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the PagoAlumno " + pagoAlumnoCollectionOrphanCheckPagoAlumno + " in its pagoAlumnoCollection field has a non-nullable grupo field.");
            }
            Collection<Asistencia> asistenciaCollectionOrphanCheck = grupo.getAsistenciaCollection();
            for (Asistencia asistenciaCollectionOrphanCheckAsistencia : asistenciaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the Asistencia " + asistenciaCollectionOrphanCheckAsistencia + " in its asistenciaCollection field has a non-nullable grupo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
