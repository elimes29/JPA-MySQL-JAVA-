/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreriaapp.servicios;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import libreriaapp.entidades.Editorial;

/**
 *
 * @author usuario
 */
public class EditorialServicio {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("libreriaAPPPU");
    EntityManager em = emf.createEntityManager();
    Scanner leer = new Scanner(System.in);
    Scanner leer1 = new Scanner(System.in);

    public void crearEditorial() {
        //instanciamos un autor
        Editorial editorial = new Editorial();
        //solicitamos el nombre del editorial
        System.out.println("Ingrese el nombre de la editorial");
        //guardamos el nombre del autor en el objeto
        editorial.setNombre(leer.nextLine());
        editorial.setAlta(true);
        System.out.println(editorial);

        //Peristimos el objeto en la base de datos
        em.getTransaction().begin();
        em.persist(editorial);
        em.getTransaction().commit();
    }

    public void eliminarEditorial() {

        System.out.println("Los editoriales disponibles son:");
        List<Editorial> editorialesDisp = em.createQuery("SELECT a FROM Editorial a ").getResultList();
        mostrarEditoriales(editorialesDisp);

        System.out.println("Ingrese el Id del editorial que desea borrar");
        int idEditorial = leer1.nextInt();
        Editorial ed = em.find(Editorial.class, idEditorial);

        //Usamos el método find para buscar el alumno a borrar
        em.getTransaction().begin();

        //Borramos los autores
        em.remove(ed);
        em.getTransaction().commit();

    }

    public void actualizarEditorial() {
        System.out.println("¿Cual Editorial desea actualizar?");
        System.out.println("Los editoriales disponibles son:");
        List<Editorial> editorialesDisp = em.createQuery("SELECT a FROM Editorial a ").getResultList();
        mostrarEditoriales(editorialesDisp);
        System.out.println("Ingrese el Id del Editorial a Actualizar");

//Usamos el método find para buscar el alumno a editar
        Editorial editorial = em.find(Editorial.class, leer1.nextInt());

        System.out.println("¿A qué nombre desea cambiar?");
        String nombreNuevo = leer.nextLine();
//Le asignamos un nuevo nombre
        editorial.setNombre(nombreNuevo);
        em.getTransaction().begin();
//Actualizamos el alumno
        em.merge(editorial);
        em.getTransaction().commit();
    }

    public void mostrarEditoriales(List<Editorial> editorialesDisp) {

        for (Editorial editorial : editorialesDisp) {
            System.out.println("Nombre: " + editorial.getNombre() + " Id: " + editorial.getId());
        }
    }

    public List<Editorial> buscarEditorialPorNombre(String nombre) {
        // Usamos el metodo createQuery y le ponemos la query de JPQL  
        List<Editorial> editoriales = em.createQuery("SELECT a FROM Editorial a WHERE a.nombre = :nombre").setParameter("nombre", nombre).getResultList();
        return editoriales;
    }

}

