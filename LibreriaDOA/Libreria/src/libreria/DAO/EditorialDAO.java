/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.DAO;

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

    public List<Integer> idEditorialDisponibles() {
        String sql = "select id from editoriales;";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

    public List<Editorial> listarEsitorialesActivos() {
        String sql = "select * from editoriales where Alta like 1;";
        Query query = entityManager.createNativeQuery(sql, Editorial.class);
        return query.getResultList();
    }

    public List<Editorial> listarEditorialesNoActivos() {
        String sql = "select * from editoriales where Alta like 0;";
        Query query = entityManager.createNativeQuery(sql, Editorial.class);
        return query.getResultList();
    }

    public void darAlta(Integer id) throws Exception {
        Editorial autor = entityManager.find(Editorial.class, id);
        autor.setAlta(true);
        super.modificar(autor);
        //entityManager.getTransaction().begin();
        //entityManager.merge(autor);
        //entityManager.getTransaction().commit();
        System.out.println("Dado de alta");
    }

    public void darBaja(Integer id) throws Exception {
        Editorial autor = entityManager.find(Editorial.class, id);
        autor.setAlta(false);
        super.modificar(autor);
        //entityManager.getTransaction().begin();
        //entityManager.merge(autor);
        //entityManager.getTransaction().commit();
        System.out.println("Dado de baja");
    }

    public List<Editorial> listarEditorialesConLibros() {
        String sql = "select distinct editoriales.* from libros inner join editoriales on libros.EDITORIAL_ID=editoriales.ID;";
        Query query = entityManager.createNativeQuery(sql, Editorial.class);
        return query.getResultList();
    }

    public List<Editorial> listarEditorialesSinLibros() {
        String sql = "select distinct editoriales.* from libros right join editoriales on libros.EDITORIAL_ID=editoriales.ID where libros.EDITORIAL_ID is null;";
        Query query = entityManager.createNativeQuery(sql, Editorial.class);
        return query.getResultList();
    }

    public List<String> nombreEditorialesDisponibles() {
        String sql = "select nombre from editoriales;";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

}
