/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.DOA;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import libreria.entidades.Autor;

/**
 *
 * @author usuario
 */
public class AutorDAO extends DAO<Autor> {

    public AutorDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public Autor buscarAutorPorId(Integer id) throws Exception {

        try {

            String sql = "select id from autores;";
            Query query = entityManager.createNativeQuery(sql);
            List<Integer> ids = query.getResultList();

            if ((ids.isEmpty())) {
                throw new Exception("No hay autores para modificar en la base de datos.");
            }

            if (!(ids.contains(id))) {
                throw new Exception("Debe ingresar un id válido");
            }

            sql = "select * from autores where id like '" + id + "';";
            query = entityManager.createNativeQuery(sql, Autor.class);
            return (Autor) query.getSingleResult();
        } catch (NoResultException ex) {
            throw ex;
        }

    }

    public List<Autor> buscarAutorPorNombre(String nombre) {
        String sql = "select * from autores where nombre like '" + nombre + "';";
        Query query = entityManager.createNativeQuery(sql, Autor.class);
        return query.getResultList();
    }

    public List<Autor> listarAutores() {
        String sql = "select * from autores;";
        Query query = entityManager.createNativeQuery(sql, Autor.class);
        return query.getResultList();
    }

    public List<Autor> listarAutoresActivos() {
        String sql = "select * from autores where Alta like 1;";
        Query query = entityManager.createNativeQuery(sql, Autor.class);
        return query.getResultList();
    }

    public List<Autor> listarAutoresNoActivos() {
        String sql = "select * from autores where Alta like 0;";
        Query query = entityManager.createNativeQuery(sql, Autor.class);
        return query.getResultList();
    }

    public void darAlta(Integer id) {
        Autor autor = entityManager.find(Autor.class, id);
        autor.setAlta(true);
        entityManager.getTransaction().begin();
        entityManager.merge(autor);
        entityManager.getTransaction().commit();
        System.out.println("Dado de alta");
    }

    public void darBaja(Integer id) {
        Autor autor = entityManager.find(Autor.class, id);
        autor.setAlta(false);
        entityManager.getTransaction().begin();
        entityManager.merge(autor);
        entityManager.getTransaction().commit();
        System.out.println("Dado de baja");
    }

    public List<Integer> idAutoresDisponibles() {
        String sql = "select id from autores;";
        Query query = entityManager.createNativeQuery(sql, Autor.class);
        return query.getResultList();
    }
}
