/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.DOA;

import javax.persistence.EntityManager;

/**
 *
 * @author usuario
 * @param <T>
 */
public abstract class DAO<T> {

    protected EntityManager entityManager;
    private Class<T> entityClass;

    public DAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void guardar(T objeto) throws Exception {

        try {
            if (objeto == null) {
                throw new Exception("Debe inresar un usuario");
            }
            entityManager.getTransaction().begin();
            entityManager.persist(objeto);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }

    }

    public void modificar(T objeto) throws Exception {

        try {
            if (objeto == null) {
                throw new Exception("Debe inresar un usuario");
            }
            entityManager.getTransaction().begin();
            entityManager.merge(objeto);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }

    }

    public void eliminar(T objeto) throws Exception {
  try {
            if (objeto == null) {
                throw new Exception("Debe inresar un usuario");
            }

            entityManager.getTransaction().begin();
            entityManager.remove(objeto);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }
}
