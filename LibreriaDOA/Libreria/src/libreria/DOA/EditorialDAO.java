/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.DOA;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import libreria.entidades.Editorial;

/**
 *
 * @author usuario
 */
public class EditorialDAO extends DAO {

    public EditorialDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Editorial> listarEditoriales() {
        String sql = "select * from editoriales;";
        Query query = entityManager.createNativeQuery(sql, Editorial.class);
        return query.getResultList();
    }

    public Editorial buscarEditorialPorId(Integer id) {
        String sql = "select * from editoriales where id like '" + id + "';";
        Query query = entityManager.createNativeQuery(sql, Editorial.class);
        return (Editorial) query.getSingleResult();
    }

    public List<Editorial> buscarEditorialPorNombre(String nombre) {
        String sql = "select * from editoriales where nombre like '" + nombre + "';";
        Query query = entityManager.createNativeQuery(sql, Editorial.class);
        return query.getResultList();
    }

}
