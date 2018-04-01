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
import modelo.Director;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Maestro;
import modelo.Usuario;
import modelo.controladores.exceptions.IllegalOrphanException;
import modelo.controladores.exceptions.NonexistentEntityException;
import modelo.controladores.exceptions.PreexistingEntityException;

/**
 *
 * @author alonso
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getDirectorCollection() == null) {
            usuario.setDirectorCollection(new ArrayList<Director>());
        }
        if (usuario.getMaestroCollection() == null) {
            usuario.setMaestroCollection(new ArrayList<Maestro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Director> attachedDirectorCollection = new ArrayList<Director>();
            for (Director directorCollectionDirectorToAttach : usuario.getDirectorCollection()) {
                directorCollectionDirectorToAttach = em.getReference(directorCollectionDirectorToAttach.getClass(), directorCollectionDirectorToAttach.getDirectorPK());
                attachedDirectorCollection.add(directorCollectionDirectorToAttach);
            }
            usuario.setDirectorCollection(attachedDirectorCollection);
            Collection<Maestro> attachedMaestroCollection = new ArrayList<Maestro>();
            for (Maestro maestroCollectionMaestroToAttach : usuario.getMaestroCollection()) {
                maestroCollectionMaestroToAttach = em.getReference(maestroCollectionMaestroToAttach.getClass(), maestroCollectionMaestroToAttach.getMaestroPK());
                attachedMaestroCollection.add(maestroCollectionMaestroToAttach);
            }
            usuario.setMaestroCollection(attachedMaestroCollection);
            em.persist(usuario);
            for (Director directorCollectionDirector : usuario.getDirectorCollection()) {
                Usuario oldUsuarioOfDirectorCollectionDirector = directorCollectionDirector.getUsuario();
                directorCollectionDirector.setUsuario(usuario);
                directorCollectionDirector = em.merge(directorCollectionDirector);
                if (oldUsuarioOfDirectorCollectionDirector != null) {
                    oldUsuarioOfDirectorCollectionDirector.getDirectorCollection().remove(directorCollectionDirector);
                    oldUsuarioOfDirectorCollectionDirector = em.merge(oldUsuarioOfDirectorCollectionDirector);
                }
            }
            for (Maestro maestroCollectionMaestro : usuario.getMaestroCollection()) {
                Usuario oldUsuarioOfMaestroCollectionMaestro = maestroCollectionMaestro.getUsuario();
                maestroCollectionMaestro.setUsuario(usuario);
                maestroCollectionMaestro = em.merge(maestroCollectionMaestro);
                if (oldUsuarioOfMaestroCollectionMaestro != null) {
                    oldUsuarioOfMaestroCollectionMaestro.getMaestroCollection().remove(maestroCollectionMaestro);
                    oldUsuarioOfMaestroCollectionMaestro = em.merge(oldUsuarioOfMaestroCollectionMaestro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getNombreUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getNombreUsuario());
            Collection<Director> directorCollectionOld = persistentUsuario.getDirectorCollection();
            Collection<Director> directorCollectionNew = usuario.getDirectorCollection();
            Collection<Maestro> maestroCollectionOld = persistentUsuario.getMaestroCollection();
            Collection<Maestro> maestroCollectionNew = usuario.getMaestroCollection();
            List<String> illegalOrphanMessages = null;
            for (Director directorCollectionOldDirector : directorCollectionOld) {
                if (!directorCollectionNew.contains(directorCollectionOldDirector)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Director " + directorCollectionOldDirector + " since its usuario field is not nullable.");
                }
            }
            for (Maestro maestroCollectionOldMaestro : maestroCollectionOld) {
                if (!maestroCollectionNew.contains(maestroCollectionOldMaestro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Maestro " + maestroCollectionOldMaestro + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Director> attachedDirectorCollectionNew = new ArrayList<Director>();
            for (Director directorCollectionNewDirectorToAttach : directorCollectionNew) {
                directorCollectionNewDirectorToAttach = em.getReference(directorCollectionNewDirectorToAttach.getClass(), directorCollectionNewDirectorToAttach.getDirectorPK());
                attachedDirectorCollectionNew.add(directorCollectionNewDirectorToAttach);
            }
            directorCollectionNew = attachedDirectorCollectionNew;
            usuario.setDirectorCollection(directorCollectionNew);
            Collection<Maestro> attachedMaestroCollectionNew = new ArrayList<Maestro>();
            for (Maestro maestroCollectionNewMaestroToAttach : maestroCollectionNew) {
                maestroCollectionNewMaestroToAttach = em.getReference(maestroCollectionNewMaestroToAttach.getClass(), maestroCollectionNewMaestroToAttach.getMaestroPK());
                attachedMaestroCollectionNew.add(maestroCollectionNewMaestroToAttach);
            }
            maestroCollectionNew = attachedMaestroCollectionNew;
            usuario.setMaestroCollection(maestroCollectionNew);
            usuario = em.merge(usuario);
            for (Director directorCollectionNewDirector : directorCollectionNew) {
                if (!directorCollectionOld.contains(directorCollectionNewDirector)) {
                    Usuario oldUsuarioOfDirectorCollectionNewDirector = directorCollectionNewDirector.getUsuario();
                    directorCollectionNewDirector.setUsuario(usuario);
                    directorCollectionNewDirector = em.merge(directorCollectionNewDirector);
                    if (oldUsuarioOfDirectorCollectionNewDirector != null && !oldUsuarioOfDirectorCollectionNewDirector.equals(usuario)) {
                        oldUsuarioOfDirectorCollectionNewDirector.getDirectorCollection().remove(directorCollectionNewDirector);
                        oldUsuarioOfDirectorCollectionNewDirector = em.merge(oldUsuarioOfDirectorCollectionNewDirector);
                    }
                }
            }
            for (Maestro maestroCollectionNewMaestro : maestroCollectionNew) {
                if (!maestroCollectionOld.contains(maestroCollectionNewMaestro)) {
                    Usuario oldUsuarioOfMaestroCollectionNewMaestro = maestroCollectionNewMaestro.getUsuario();
                    maestroCollectionNewMaestro.setUsuario(usuario);
                    maestroCollectionNewMaestro = em.merge(maestroCollectionNewMaestro);
                    if (oldUsuarioOfMaestroCollectionNewMaestro != null && !oldUsuarioOfMaestroCollectionNewMaestro.equals(usuario)) {
                        oldUsuarioOfMaestroCollectionNewMaestro.getMaestroCollection().remove(maestroCollectionNewMaestro);
                        oldUsuarioOfMaestroCollectionNewMaestro = em.merge(oldUsuarioOfMaestroCollectionNewMaestro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getNombreUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getNombreUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Director> directorCollectionOrphanCheck = usuario.getDirectorCollection();
            for (Director directorCollectionOrphanCheckDirector : directorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Director " + directorCollectionOrphanCheckDirector + " in its directorCollection field has a non-nullable usuario field.");
            }
            Collection<Maestro> maestroCollectionOrphanCheck = usuario.getMaestroCollection();
            for (Maestro maestroCollectionOrphanCheckMaestro : maestroCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Maestro " + maestroCollectionOrphanCheckMaestro + " in its maestroCollection field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
