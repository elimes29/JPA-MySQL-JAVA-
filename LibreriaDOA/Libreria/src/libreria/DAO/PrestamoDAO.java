/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import libreria.entidades.Cliente;
import libreria.entidades.Libro;
import libreria.entidades.Prestamo;

/**
 *
 * @author usuario
 */
public class PrestamoDAO extends DAO<Prestamo> {

    public PrestamoDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Integer> listarIdClientePrestamoActivo() {
        String sql = "select cliente_id from prestamos where fecha_devolucion like null;";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

    public List<Long> listarIsbnLibroPrestamoActivo(Cliente cliente) {
        String sql = "select libro_isbn from prestamos inner join clientes on  CLIENTE_ID=clientes.ID where fecha_devolucion is null and clientes.ID="+cliente.getId()+";";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

    public List<Prestamo> listarPrestamos() {
        String sql = "select * from prestamos;";
        Query query = entityManager.createNativeQuery(sql, Prestamo.class);
        return query.getResultList();
    }

    public List<Cliente> listarClientesPrestamosActivos() {
        String sql = "select clientes.* from clientes inner join prestamos on clientes.id = prestamos.cliente_id where prestamos.fecha_devolucion is null;";
        Query query = entityManager.createNativeQuery(sql, Cliente.class);
        return query.getResultList();
    }

    public List<Cliente> listarClientesPrestamosNoActivos() {
        String sql = "select clientes.* from clientes inner join prestamos on clientes.id = prestamos.cliente_id where prestamos.fecha_devolucion is not null;";
        Query query = entityManager.createNativeQuery(sql, Cliente.class);
        return query.getResultList();
    }

    public List<Libro> listarLibrosActivosPorCliente(Cliente cliente) {
        String sql = "select libros.* from (select prestamos.* from clientes inner join prestamos on clientes.id = prestamos.cliente_id where clientes.documento = ' " + cliente.getDocumento() +" ' ) as r inner join libros on r.libro_isbn = isbn;";
        Query query = entityManager.createNativeQuery(sql, Libro.class);
        return query.getResultList();
    }

    public Prestamo ubicarPrestamo(Cliente cliente,Libro libro){
        //String sql = "select * from prestamos where cliente_id = "+ cliente.getId() +" and libro_isbn = "+ libro.getIsbn() +";";
        String sql = "select * from prestamos where cliente_id = 2 and libro_isbn = 123456789;";
        Query query = entityManager.createNativeQuery(sql, Prestamo.class);
        return (Prestamo) query.getSingleResult();
    }
}
