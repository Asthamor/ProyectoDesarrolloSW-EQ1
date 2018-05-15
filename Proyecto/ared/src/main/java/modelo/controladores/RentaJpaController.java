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
import modelo.Cliente;
import modelo.Horario;
import modelo.PagoRenta;
import modelo.Renta;
import modelo.RentaPK;
import modelo.controladores.exceptions.NonexistentEntityException;
import modelo.controladores.exceptions.PreexistingEntityException;

/**
 *
 * @author alonso
 */
public class RentaJpaController implements Serializable {

    public RentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Renta renta) throws PreexistingEntityException, Exception {
        if (renta.getRentaPK() == null) {
            renta.setRentaPK(new RentaPK());
        }
        renta.getRentaPK().setHorarioidHorario(renta.getHorario().getIdHorario());
        renta.getRentaPK().setClienteidCliente(renta.getCliente().getIdCliente());
        renta.getRentaPK().setPagoRentaidPago(renta.getPagoRenta().getIdPago());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = renta.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getIdCliente());
                renta.setCliente(cliente);
            }
            Horario horario = renta.getHorario();
            if (horario != null) {
                horario = em.getReference(horario.getClass(), horario.getIdHorario());
                renta.setHorario(horario);
            }
            PagoRenta pagoRenta = renta.getPagoRenta();
            if (pagoRenta != null) {
                pagoRenta = em.getReference(pagoRenta.getClass(), pagoRenta.getIdPago());
                renta.setPagoRenta(pagoRenta);
            }
            em.persist(renta);
            if (cliente != null) {
                cliente.getRentaCollection().add(renta);
                cliente = em.merge(cliente);
            }
            if (horario != null) {
                horario.getRentaCollection().add(renta);
                horario = em.merge(horario);
            }
            if (pagoRenta != null) {
                pagoRenta.getRentaCollection().add(renta);
                pagoRenta = em.merge(pagoRenta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRenta(renta.getRentaPK()) != null) {
                throw new PreexistingEntityException("Renta " + renta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Renta renta) throws NonexistentEntityException, Exception {
        renta.getRentaPK().setHorarioidHorario(renta.getHorario().getIdHorario());
        renta.getRentaPK().setClienteidCliente(renta.getCliente().getIdCliente());
        renta.getRentaPK().setPagoRentaidPago(renta.getPagoRenta().getIdPago());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Renta persistentRenta = em.find(Renta.class, renta.getRentaPK());
            Cliente clienteOld = persistentRenta.getCliente();
            Cliente clienteNew = renta.getCliente();
            Horario horarioOld = persistentRenta.getHorario();
            Horario horarioNew = renta.getHorario();
            PagoRenta pagoRentaOld = persistentRenta.getPagoRenta();
            PagoRenta pagoRentaNew = renta.getPagoRenta();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getIdCliente());
                renta.setCliente(clienteNew);
            }
            if (horarioNew != null) {
                horarioNew = em.getReference(horarioNew.getClass(), horarioNew.getIdHorario());
                renta.setHorario(horarioNew);
            }
            if (pagoRentaNew != null) {
                pagoRentaNew = em.getReference(pagoRentaNew.getClass(), pagoRentaNew.getIdPago());
                renta.setPagoRenta(pagoRentaNew);
            }
            renta = em.merge(renta);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getRentaCollection().remove(renta);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getRentaCollection().add(renta);
                clienteNew = em.merge(clienteNew);
            }
            if (horarioOld != null && !horarioOld.equals(horarioNew)) {
                horarioOld.getRentaCollection().remove(renta);
                horarioOld = em.merge(horarioOld);
            }
            if (horarioNew != null && !horarioNew.equals(horarioOld)) {
                horarioNew.getRentaCollection().add(renta);
                horarioNew = em.merge(horarioNew);
            }
            if (pagoRentaOld != null && !pagoRentaOld.equals(pagoRentaNew)) {
                pagoRentaOld.getRentaCollection().remove(renta);
                pagoRentaOld = em.merge(pagoRentaOld);
            }
            if (pagoRentaNew != null && !pagoRentaNew.equals(pagoRentaOld)) {
                pagoRentaNew.getRentaCollection().add(renta);
                pagoRentaNew = em.merge(pagoRentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RentaPK id = renta.getRentaPK();
                if (findRenta(id) == null) {
                    throw new NonexistentEntityException("The renta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(RentaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Renta renta;
            try {
                renta = em.getReference(Renta.class, id);
                renta.getRentaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The renta with id " + id + " no longer exists.", enfe);
            }
            Cliente cliente = renta.getCliente();
            if (cliente != null) {
                cliente.getRentaCollection().remove(renta);
                cliente = em.merge(cliente);
            }
            Horario horario = renta.getHorario();
            if (horario != null) {
                horario.getRentaCollection().remove(renta);
                horario = em.merge(horario);
            }
            PagoRenta pagoRenta = renta.getPagoRenta();
            if (pagoRenta != null) {
                pagoRenta.getRentaCollection().remove(renta);
                pagoRenta = em.merge(pagoRenta);
            }
            em.remove(renta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Renta> findRentaEntities() {
        return findRentaEntities(true, -1, -1);
    }

    public List<Renta> findRentaEntities(int maxResults, int firstResult) {
        return findRentaEntities(false, maxResults, firstResult);
    }

    private List<Renta> findRentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Renta.class));
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

    public Renta findRenta(RentaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Renta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Renta> rt = cq.from(Renta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public String obtenerUltimoRegistro(){
        EntityManager em = getEntityManager();
        String id = "";
        try{
            id = em.createNamedQuery("Renta.obtenerUltimoRegistro").setMaxResults(1).getSingleResult().toString();
        }finally{
            em.close();
        }
        return id;
    }
    
}
