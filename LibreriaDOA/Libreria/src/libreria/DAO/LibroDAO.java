/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;
import libreria.entidades.Libro;

/**
 *
 * @author usuario
 */
public class LibroDAO extends DAO {

    public LibroDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public Libro buscarLibroPorIsbn(Long isbn) {
        String sql = "select * from libros where isbn = " + isbn + ";";
        Query query = entityManager.createNativeQuery(sql, Libro.class);
        return (Libro) query.getSingleResult();
    }

    public List<Libro> buscarLibroPorTitulo(String nombre) {
        String sql = "select * from libros where titulo like '" + nombre + "';";
        Query query = entityManager.createNativeQuery(sql, Libro.class);
        return query.getResultList();
    }

    public List<Libro> listarLibros() {
        String sql = "select * from libros;";
        Query query = entityManager.createNativeQuery(sql, Libro.class);
        return query.getResultList();
    }

    public List<Libro> buscarLibrosPorAutor(Autor autor) {
        String sql = "select * from libros inner join autores on libros.autor_id=autores.id where autores.id like " + autor.getId() + ";";
        Query query = entityManager.createNativeQuery(sql, Libro.class);
        return query.getResultList();
    }

    public List<Libro> buscarLibrosPorEditorial(Editorial editorial) {
        String sql = "select * from libros where EDITORIAL_ID like " + editorial.getId() + ";";
        Query query = entityManager.createNativeQuery(sql, Libro.class);
        return query.getResultList();

    }

    public List<Long> isbnLibrosDisponibles() {
        
        String sql = "select isbn from libros;";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }
    

}
