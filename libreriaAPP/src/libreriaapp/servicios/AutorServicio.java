/*
servicios: en este paquete se almacenarán aquellas clases que llevarán adelante la lógica
del negocio. En general se crea un servicio para administrar las operaciones CRUD
(Create, Remove, Update, Delete) cada una de las entidades y las consultas de cada
entidad.
 */
package libreriaapp.servicios;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import libreriaapp.entidades.Autor;

/**
 *
 * @author usuario
 */
public class AutorServicio {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("libreriaAPPPU");
    EntityManager em = emf.createEntityManager();
    Scanner leer = new Scanner(System.in);
    Scanner leer1 = new Scanner(System.in);

    public void crearAutor() {
        //instanciamos un autor
        Autor autor = new Autor();
        //solicitamos el nombre del autor
        System.out.println("Ingrese el nombre del autor");
        //guardamos el nombre del autor en el objeto
        autor.setNombre(leer.nextLine());
        autor.setAlta(true);

        //Peristimos el objeto en la base de datos
        em.getTransaction().begin();
        em.persist(autor);
        em.getTransaction().commit();
    }

    public void eliminarAutor() {

        System.out.println("Los autores disponibles son:");
        List<Autor> autoresDisp = em.createQuery("SELECT a FROM Autor a ").getResultList();
        mostrarAutores(autoresDisp);

        System.out.println("Ingrese el nombre del autor que desea borrar");

        String nombreAutor = leer.nextLine();
        
        List<Autor> autores = buscarAutorPorNombre(nombreAutor);

        //Usamos el método find para buscar el alumno a borrar
        em.getTransaction().begin();

        //Borramos los autores
        for (Autor autor : autores) {
            em.remove(autor);
        }
        em.getTransaction().commit();

    }

    public void actualizarAutor() {
        System.out.println("¿Cual Autor desea actualizar?");
        System.out.println("Los autores disponibles son:");
        List<Autor> autoresDisp = em.createQuery("SELECT a FROM Autor a ").getResultList();
        mostrarAutores(autoresDisp);
        System.out.println("Ingrese el Id del autor a Actualizar");
        

//Usamos el método find para buscar el alumno a editar
        Autor autor = em.find(Autor.class, leer1.nextInt());
        
        System.out.println("¿A qué nombre desea cambiar?");
        String nombreNuevo = leer.nextLine();
//Le asignamos un nuevo nombre
        autor.setNombre(nombreNuevo);
        em.getTransaction().begin();
//Actualizamos el alumno
        em.merge(autor);
        em.getTransaction().commit();
    }

    public void mostrarAutores(List<Autor> autoresDisp) {

        for (Autor autor : autoresDisp) {
            System.out.println("Nombre: " + autor.getNombre() + " Id: " + autor.getId());
        }
    }
    
    public List<Autor>  buscarAutorPorNombre(String nombre){
        // Usamos el metodo createQuery y le ponemos la query de JPQL  
        List<Autor> autores = em.createQuery("SELECT a FROM Autor a WHERE a.nombre = :nombre").setParameter("nombre", nombre).getResultList();
        return autores;
    }

}

