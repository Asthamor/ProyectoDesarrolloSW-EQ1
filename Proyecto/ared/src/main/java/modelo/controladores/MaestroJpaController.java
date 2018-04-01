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
import modelo.Usuario;
import modelo.Promocion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Grupo;
import modelo.Maestro;
import modelo.MaestroPK;
import modelo.Publicidad;
import modelo.PagoAlumnoExterno;
import modelo.PagoMaestro;
import modelo.controladores.exceptions.IllegalOrphanException;
import modelo.controladores.exceptions.NonexistentEntityException;
import modelo.controladores.exceptions.PreexistingEntityException;

/**
 *
 * @author alonso
 */
public class MaestroJpaController implements Serializable {

    public MaestroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Maestro maestro) throws PreexistingEntityException, Exception {
        if (maestro.getMaestroPK() == null) {
            maestro.setMaestroPK(new MaestroPK());
        }
        if (maestro.getPromocionCollection() == null) {
            maestro.setPromocionCollection(new ArrayList<Promocion>());
        }
        if (maestro.getGrupoCollection() == null) {
            maestro.setGrupoCollection(new ArrayList<Grupo>());
        }
        if (maestro.getPublicidadCollection() == null) {
            maestro.setPublicidadCollection(new ArrayList<Publicidad>());
        }
        if (maestro.getPagoAlumnoExternoCollection() == null) {
            maestro.setPagoAlumnoExternoCollection(new ArrayList<PagoAlumnoExterno>());
        }
        if (maestro.getPagoMaestroCollection() == null) {
            maestro.setPagoMaestroCollection(new ArrayList<PagoMaestro>());
        }
        maestro.getMaestroPK().setUsuarionombreUsuario(maestro.getUsuario().getNombreUsuario());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = maestro.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getNombreUsuario());
                maestro.setUsuario(usuario);
            }
            Collection<Promocion> attachedPromocionCollection = new ArrayList<Promocion>();
            for (Promocion promocionCollectionPromocionToAttach : maestro.getPromocionCollection()) {
                promocionCollectionPromocionToAttach = em.getReference(promocionCollectionPromocionToAttach.getClass(), promocionCollectionPromocionToAttach.getPromocionPK());
                attachedPromocionCollection.add(promocionCollectionPromocionToAttach);
            }
            maestro.setPromocionCollection(attachedPromocionCollection);
            Collection<Grupo> attachedGrupoCollection = new ArrayList<Grupo>();
            for (Grupo grupoCollectionGrupoToAttach : maestro.getGrupoCollection()) {
                grupoCollectionGrupoToAttach = em.getReference(grupoCollectionGrupoToAttach.getClass(), grupoCollectionGrupoToAttach.getGrupoPK());
                attachedGrupoCollection.add(grupoCollectionGrupoToAttach);
            }
            maestro.setGrupoCollection(attachedGrupoCollection);
            Collection<Publicidad> attachedPublicidadCollection = new ArrayList<Publicidad>();
            for (Publicidad publicidadCollectionPublicidadToAttach : maestro.getPublicidadCollection()) {
                publicidadCollectionPublicidadToAttach = em.getReference(publicidadCollectionPublicidadToAttach.getClass(), publicidadCollectionPublicidadToAttach.getPublicidadPK());
                attachedPublicidadCollection.add(publicidadCollectionPublicidadToAttach);
            }
            maestro.setPublicidadCollection(attachedPublicidadCollection);
            Collection<PagoAlumnoExterno> attachedPagoAlumnoExternoCollection = new ArrayList<PagoAlumnoExterno>();
            for (PagoAlumnoExterno pagoAlumnoExternoCollectionPagoAlumnoExternoToAttach : maestro.getPagoAlumnoExternoCollection()) {
                pagoAlumnoExternoCollectionPagoAlumnoExternoToAttach = em.getReference(pagoAlumnoExternoCollectionPagoAlumnoExternoToAttach.getClass(), pagoAlumnoExternoCollectionPagoAlumnoExternoToAttach.getPagoAlumnoExternoPK());
                attachedPagoAlumnoExternoCollection.add(pagoAlumnoExternoCollectionPagoAlumnoExternoToAttach);
            }
            maestro.setPagoAlumnoExternoCollection(attachedPagoAlumnoExternoCollection);
            Collection<PagoMaestro> attachedPagoMaestroCollection = new ArrayList<PagoMaestro>();
            for (PagoMaestro pagoMaestroCollectionPagoMaestroToAttach : maestro.getPagoMaestroCollection()) {
                pagoMaestroCollectionPagoMaestroToAttach = em.getReference(pagoMaestroCollectionPagoMaestroToAttach.getClass(), pagoMaestroCollectionPagoMaestroToAttach.getPagoMaestroPK());
                attachedPagoMaestroCollection.add(pagoMaestroCollectionPagoMaestroToAttach);
            }
            maestro.setPagoMaestroCollection(attachedPagoMaestroCollection);
            em.persist(maestro);
            if (usuario != null) {
                usuario.getMaestroCollection().add(maestro);
                usuario = em.merge(usuario);
            }
            for (Promocion promocionCollectionPromocion : maestro.getPromocionCollection()) {
                Maestro oldMaestroOfPromocionCollectionPromocion = promocionCollectionPromocion.getMaestro();
                promocionCollectionPromocion.setMaestro(maestro);
                promocionCollectionPromocion = em.merge(promocionCollectionPromocion);
                if (oldMaestroOfPromocionCollectionPromocion != null) {
                    oldMaestroOfPromocionCollectionPromocion.getPromocionCollection().remove(promocionCollectionPromocion);
                    oldMaestroOfPromocionCollectionPromocion = em.merge(oldMaestroOfPromocionCollectionPromocion);
                }
            }
            for (Grupo grupoCollectionGrupo : maestro.getGrupoCollection()) {
                Maestro oldMaestroOfGrupoCollectionGrupo = grupoCollectionGrupo.getMaestro();
                grupoCollectionGrupo.setMaestro(maestro);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
                if (oldMaestroOfGrupoCollectionGrupo != null) {
                    oldMaestroOfGrupoCollectionGrupo.getGrupoCollection().remove(grupoCollectionGrupo);
                    oldMaestroOfGrupoCollectionGrupo = em.merge(oldMaestroOfGrupoCollectionGrupo);
                }
            }
            for (Publicidad publicidadCollectionPublicidad : maestro.getPublicidadCollection()) {
                Maestro oldMaestroOfPublicidadCollectionPublicidad = publicidadCollectionPublicidad.getMaestro();
                publicidadCollectionPublicidad.setMaestro(maestro);
                publicidadCollectionPublicidad = em.merge(publicidadCollectionPublicidad);
                if (oldMaestroOfPublicidadCollectionPublicidad != null) {
                    oldMaestroOfPublicidadCollectionPublicidad.getPublicidadCollection().remove(publicidadCollectionPublicidad);
                    oldMaestroOfPublicidadCollectionPublicidad = em.merge(oldMaestroOfPublicidadCollectionPublicidad);
                }
            }
            for (PagoAlumnoExterno pagoAlumnoExternoCollectionPagoAlumnoExterno : maestro.getPagoAlumnoExternoCollection()) {
                Maestro oldMaestroOfPagoAlumnoExternoCollectionPagoAlumnoExterno = pagoAlumnoExternoCollectionPagoAlumnoExterno.getMaestro();
                pagoAlumnoExternoCollectionPagoAlumnoExterno.setMaestro(maestro);
                pagoAlumnoExternoCollectionPagoAlumnoExterno = em.merge(pagoAlumnoExternoCollectionPagoAlumnoExterno);
                if (oldMaestroOfPagoAlumnoExternoCollectionPagoAlumnoExterno != null) {
                    oldMaestroOfPagoAlumnoExternoCollectionPagoAlumnoExterno.getPagoAlumnoExternoCollection().remove(pagoAlumnoExternoCollectionPagoAlumnoExterno);
                    oldMaestroOfPagoAlumnoExternoCollectionPagoAlumnoExterno = em.merge(oldMaestroOfPagoAlumnoExternoCollectionPagoAlumnoExterno);
                }
            }
            for (PagoMaestro pagoMaestroCollectionPagoMaestro : maestro.getPagoMaestroCollection()) {
                Maestro oldMaestroOfPagoMaestroCollectionPagoMaestro = pagoMaestroCollectionPagoMaestro.getMaestro();
                pagoMaestroCollectionPagoMaestro.setMaestro(maestro);
                pagoMaestroCollectionPagoMaestro = em.merge(pagoMaestroCollectionPagoMaestro);
                if (oldMaestroOfPagoMaestroCollectionPagoMaestro != null) {
                    oldMaestroOfPagoMaestroCollectionPagoMaestro.getPagoMaestroCollection().remove(pagoMaestroCollectionPagoMaestro);
                    oldMaestroOfPagoMaestroCollectionPagoMaestro = em.merge(oldMaestroOfPagoMaestroCollectionPagoMaestro);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMaestro(maestro.getMaestroPK()) != null) {
                throw new PreexistingEntityException("Maestro " + maestro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Maestro maestro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        maestro.getMaestroPK().setUsuarionombreUsuario(maestro.getUsuario().getNombreUsuario());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestro persistentMaestro = em.find(Maestro.class, maestro.getMaestroPK());
            Usuario usuarioOld = persistentMaestro.getUsuario();
            Usuario usuarioNew = maestro.getUsuario();
            Collection<Promocion> promocionCollectionOld = persistentMaestro.getPromocionCollection();
            Collection<Promocion> promocionCollectionNew = maestro.getPromocionCollection();
            Collection<Grupo> grupoCollectionOld = persistentMaestro.getGrupoCollection();
            Collection<Grupo> grupoCollectionNew = maestro.getGrupoCollection();
            Collection<Publicidad> publicidadCollectionOld = persistentMaestro.getPublicidadCollection();
            Collection<Publicidad> publicidadCollectionNew = maestro.getPublicidadCollection();
            Collection<PagoAlumnoExterno> pagoAlumnoExternoCollectionOld = persistentMaestro.getPagoAlumnoExternoCollection();
            Collection<PagoAlumnoExterno> pagoAlumnoExternoCollectionNew = maestro.getPagoAlumnoExternoCollection();
            Collection<PagoMaestro> pagoMaestroCollectionOld = persistentMaestro.getPagoMaestroCollection();
            Collection<PagoMaestro> pagoMaestroCollectionNew = maestro.getPagoMaestroCollection();
            List<String> illegalOrphanMessages = null;
            for (Promocion promocionCollectionOldPromocion : promocionCollectionOld) {
                if (!promocionCollectionNew.contains(promocionCollectionOldPromocion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Promocion " + promocionCollectionOldPromocion + " since its maestro field is not nullable.");
                }
            }
            for (Grupo grupoCollectionOldGrupo : grupoCollectionOld) {
                if (!grupoCollectionNew.contains(grupoCollectionOldGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Grupo " + grupoCollectionOldGrupo + " since its maestro field is not nullable.");
                }
            }
            for (Publicidad publicidadCollectionOldPublicidad : publicidadCollectionOld) {
                if (!publicidadCollectionNew.contains(publicidadCollectionOldPublicidad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Publicidad " + publicidadCollectionOldPublicidad + " since its maestro field is not nullable.");
                }
            }
            for (PagoAlumnoExterno pagoAlumnoExternoCollectionOldPagoAlumnoExterno : pagoAlumnoExternoCollectionOld) {
                if (!pagoAlumnoExternoCollectionNew.contains(pagoAlumnoExternoCollectionOldPagoAlumnoExterno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PagoAlumnoExterno " + pagoAlumnoExternoCollectionOldPagoAlumnoExterno + " since its maestro field is not nullable.");
                }
            }
            for (PagoMaestro pagoMaestroCollectionOldPagoMaestro : pagoMaestroCollectionOld) {
                if (!pagoMaestroCollectionNew.contains(pagoMaestroCollectionOldPagoMaestro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PagoMaestro " + pagoMaestroCollectionOldPagoMaestro + " since its maestro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getNombreUsuario());
                maestro.setUsuario(usuarioNew);
            }
            Collection<Promocion> attachedPromocionCollectionNew = new ArrayList<Promocion>();
            for (Promocion promocionCollectionNewPromocionToAttach : promocionCollectionNew) {
                promocionCollectionNewPromocionToAttach = em.getReference(promocionCollectionNewPromocionToAttach.getClass(), promocionCollectionNewPromocionToAttach.getPromocionPK());
                attachedPromocionCollectionNew.add(promocionCollectionNewPromocionToAttach);
            }
            promocionCollectionNew = attachedPromocionCollectionNew;
            maestro.setPromocionCollection(promocionCollectionNew);
            Collection<Grupo> attachedGrupoCollectionNew = new ArrayList<Grupo>();
            for (Grupo grupoCollectionNewGrupoToAttach : grupoCollectionNew) {
                grupoCollectionNewGrupoToAttach = em.getReference(grupoCollectionNewGrupoToAttach.getClass(), grupoCollectionNewGrupoToAttach.getGrupoPK());
                attachedGrupoCollectionNew.add(grupoCollectionNewGrupoToAttach);
            }
            grupoCollectionNew = attachedGrupoCollectionNew;
            maestro.setGrupoCollection(grupoCollectionNew);
            Collection<Publicidad> attachedPublicidadCollectionNew = new ArrayList<Publicidad>();
            for (Publicidad publicidadCollectionNewPublicidadToAttach : publicidadCollectionNew) {
                publicidadCollectionNewPublicidadToAttach = em.getReference(publicidadCollectionNewPublicidadToAttach.getClass(), publicidadCollectionNewPublicidadToAttach.getPublicidadPK());
                attachedPublicidadCollectionNew.add(publicidadCollectionNewPublicidadToAttach);
            }
            publicidadCollectionNew = attachedPublicidadCollectionNew;
            maestro.setPublicidadCollection(publicidadCollectionNew);
            Collection<PagoAlumnoExterno> attachedPagoAlumnoExternoCollectionNew = new ArrayList<PagoAlumnoExterno>();
            for (PagoAlumnoExterno pagoAlumnoExternoCollectionNewPagoAlumnoExternoToAttach : pagoAlumnoExternoCollectionNew) {
                pagoAlumnoExternoCollectionNewPagoAlumnoExternoToAttach = em.getReference(pagoAlumnoExternoCollectionNewPagoAlumnoExternoToAttach.getClass(), pagoAlumnoExternoCollectionNewPagoAlumnoExternoToAttach.getPagoAlumnoExternoPK());
                attachedPagoAlumnoExternoCollectionNew.add(pagoAlumnoExternoCollectionNewPagoAlumnoExternoToAttach);
            }
            pagoAlumnoExternoCollectionNew = attachedPagoAlumnoExternoCollectionNew;
            maestro.setPagoAlumnoExternoCollection(pagoAlumnoExternoCollectionNew);
            Collection<PagoMaestro> attachedPagoMaestroCollectionNew = new ArrayList<PagoMaestro>();
            for (PagoMaestro pagoMaestroCollectionNewPagoMaestroToAttach : pagoMaestroCollectionNew) {
                pagoMaestroCollectionNewPagoMaestroToAttach = em.getReference(pagoMaestroCollectionNewPagoMaestroToAttach.getClass(), pagoMaestroCollectionNewPagoMaestroToAttach.getPagoMaestroPK());
                attachedPagoMaestroCollectionNew.add(pagoMaestroCollectionNewPagoMaestroToAttach);
            }
            pagoMaestroCollectionNew = attachedPagoMaestroCollectionNew;
            maestro.setPagoMaestroCollection(pagoMaestroCollectionNew);
            maestro = em.merge(maestro);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getMaestroCollection().remove(maestro);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getMaestroCollection().add(maestro);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Promocion promocionCollectionNewPromocion : promocionCollectionNew) {
                if (!promocionCollectionOld.contains(promocionCollectionNewPromocion)) {
                    Maestro oldMaestroOfPromocionCollectionNewPromocion = promocionCollectionNewPromocion.getMaestro();
                    promocionCollectionNewPromocion.setMaestro(maestro);
                    promocionCollectionNewPromocion = em.merge(promocionCollectionNewPromocion);
                    if (oldMaestroOfPromocionCollectionNewPromocion != null && !oldMaestroOfPromocionCollectionNewPromocion.equals(maestro)) {
                        oldMaestroOfPromocionCollectionNewPromocion.getPromocionCollection().remove(promocionCollectionNewPromocion);
                        oldMaestroOfPromocionCollectionNewPromocion = em.merge(oldMaestroOfPromocionCollectionNewPromocion);
                    }
                }
            }
            for (Grupo grupoCollectionNewGrupo : grupoCollectionNew) {
                if (!grupoCollectionOld.contains(grupoCollectionNewGrupo)) {
                    Maestro oldMaestroOfGrupoCollectionNewGrupo = grupoCollectionNewGrupo.getMaestro();
                    grupoCollectionNewGrupo.setMaestro(maestro);
                    grupoCollectionNewGrupo = em.merge(grupoCollectionNewGrupo);
                    if (oldMaestroOfGrupoCollectionNewGrupo != null && !oldMaestroOfGrupoCollectionNewGrupo.equals(maestro)) {
                        oldMaestroOfGrupoCollectionNewGrupo.getGrupoCollection().remove(grupoCollectionNewGrupo);
                        oldMaestroOfGrupoCollectionNewGrupo = em.merge(oldMaestroOfGrupoCollectionNewGrupo);
                    }
                }
            }
            for (Publicidad publicidadCollectionNewPublicidad : publicidadCollectionNew) {
                if (!publicidadCollectionOld.contains(publicidadCollectionNewPublicidad)) {
                    Maestro oldMaestroOfPublicidadCollectionNewPublicidad = publicidadCollectionNewPublicidad.getMaestro();
                    publicidadCollectionNewPublicidad.setMaestro(maestro);
                    publicidadCollectionNewPublicidad = em.merge(publicidadCollectionNewPublicidad);
                    if (oldMaestroOfPublicidadCollectionNewPublicidad != null && !oldMaestroOfPublicidadCollectionNewPublicidad.equals(maestro)) {
                        oldMaestroOfPublicidadCollectionNewPublicidad.getPublicidadCollection().remove(publicidadCollectionNewPublicidad);
                        oldMaestroOfPublicidadCollectionNewPublicidad = em.merge(oldMaestroOfPublicidadCollectionNewPublicidad);
                    }
                }
            }
            for (PagoAlumnoExterno pagoAlumnoExternoCollectionNewPagoAlumnoExterno : pagoAlumnoExternoCollectionNew) {
                if (!pagoAlumnoExternoCollectionOld.contains(pagoAlumnoExternoCollectionNewPagoAlumnoExterno)) {
                    Maestro oldMaestroOfPagoAlumnoExternoCollectionNewPagoAlumnoExterno = pagoAlumnoExternoCollectionNewPagoAlumnoExterno.getMaestro();
                    pagoAlumnoExternoCollectionNewPagoAlumnoExterno.setMaestro(maestro);
                    pagoAlumnoExternoCollectionNewPagoAlumnoExterno = em.merge(pagoAlumnoExternoCollectionNewPagoAlumnoExterno);
                    if (oldMaestroOfPagoAlumnoExternoCollectionNewPagoAlumnoExterno != null && !oldMaestroOfPagoAlumnoExternoCollectionNewPagoAlumnoExterno.equals(maestro)) {
                        oldMaestroOfPagoAlumnoExternoCollectionNewPagoAlumnoExterno.getPagoAlumnoExternoCollection().remove(pagoAlumnoExternoCollectionNewPagoAlumnoExterno);
                        oldMaestroOfPagoAlumnoExternoCollectionNewPagoAlumnoExterno = em.merge(oldMaestroOfPagoAlumnoExternoCollectionNewPagoAlumnoExterno);
                    }
                }
            }
            for (PagoMaestro pagoMaestroCollectionNewPagoMaestro : pagoMaestroCollectionNew) {
                if (!pagoMaestroCollectionOld.contains(pagoMaestroCollectionNewPagoMaestro)) {
                    Maestro oldMaestroOfPagoMaestroCollectionNewPagoMaestro = pagoMaestroCollectionNewPagoMaestro.getMaestro();
                    pagoMaestroCollectionNewPagoMaestro.setMaestro(maestro);
                    pagoMaestroCollectionNewPagoMaestro = em.merge(pagoMaestroCollectionNewPagoMaestro);
                    if (oldMaestroOfPagoMaestroCollectionNewPagoMaestro != null && !oldMaestroOfPagoMaestroCollectionNewPagoMaestro.equals(maestro)) {
                        oldMaestroOfPagoMaestroCollectionNewPagoMaestro.getPagoMaestroCollection().remove(pagoMaestroCollectionNewPagoMaestro);
                        oldMaestroOfPagoMaestroCollectionNewPagoMaestro = em.merge(oldMaestroOfPagoMaestroCollectionNewPagoMaestro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MaestroPK id = maestro.getMaestroPK();
                if (findMaestro(id) == null) {
                    throw new NonexistentEntityException("The maestro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MaestroPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestro maestro;
            try {
                maestro = em.getReference(Maestro.class, id);
                maestro.getMaestroPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The maestro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Promocion> promocionCollectionOrphanCheck = maestro.getPromocionCollection();
            for (Promocion promocionCollectionOrphanCheckPromocion : promocionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Maestro (" + maestro + ") cannot be destroyed since the Promocion " + promocionCollectionOrphanCheckPromocion + " in its promocionCollection field has a non-nullable maestro field.");
            }
            Collection<Grupo> grupoCollectionOrphanCheck = maestro.getGrupoCollection();
            for (Grupo grupoCollectionOrphanCheckGrupo : grupoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Maestro (" + maestro + ") cannot be destroyed since the Grupo " + grupoCollectionOrphanCheckGrupo + " in its grupoCollection field has a non-nullable maestro field.");
            }
            Collection<Publicidad> publicidadCollectionOrphanCheck = maestro.getPublicidadCollection();
            for (Publicidad publicidadCollectionOrphanCheckPublicidad : publicidadCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Maestro (" + maestro + ") cannot be destroyed since the Publicidad " + publicidadCollectionOrphanCheckPublicidad + " in its publicidadCollection field has a non-nullable maestro field.");
            }
            Collection<PagoAlumnoExterno> pagoAlumnoExternoCollectionOrphanCheck = maestro.getPagoAlumnoExternoCollection();
            for (PagoAlumnoExterno pagoAlumnoExternoCollectionOrphanCheckPagoAlumnoExterno : pagoAlumnoExternoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Maestro (" + maestro + ") cannot be destroyed since the PagoAlumnoExterno " + pagoAlumnoExternoCollectionOrphanCheckPagoAlumnoExterno + " in its pagoAlumnoExternoCollection field has a non-nullable maestro field.");
            }
            Collection<PagoMaestro> pagoMaestroCollectionOrphanCheck = maestro.getPagoMaestroCollection();
            for (PagoMaestro pagoMaestroCollectionOrphanCheckPagoMaestro : pagoMaestroCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Maestro (" + maestro + ") cannot be destroyed since the PagoMaestro " + pagoMaestroCollectionOrphanCheckPagoMaestro + " in its pagoMaestroCollection field has a non-nullable maestro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuario = maestro.getUsuario();
            if (usuario != null) {
                usuario.getMaestroCollection().remove(maestro);
                usuario = em.merge(usuario);
            }
            em.remove(maestro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Maestro> findMaestroEntities() {
        return findMaestroEntities(true, -1, -1);
    }

    public List<Maestro> findMaestroEntities(int maxResults, int firstResult) {
        return findMaestroEntities(false, maxResults, firstResult);
    }

    private List<Maestro> findMaestroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Maestro.class));
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

    public Maestro findMaestro(MaestroPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Maestro.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaestroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Maestro> rt = cq.from(Maestro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
