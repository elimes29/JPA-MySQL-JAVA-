/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.servicio;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import libreria.DAO.AutorDAO;
import libreria.DAO.LibroDAO;
import libreria.entidades.Autor;
import libreria.entidades.Libro;

/**
 *
 * @author usuario
 */
public class AutorServicio {

    private final AutorDAO autorDao;
    private final LibroDAO libroDao;
    EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();
    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    Scanner leer1 = new Scanner(System.in).useDelimiter("\n");

    public AutorServicio() {
        this.autorDao = new AutorDAO(em);
        this.libroDao = new LibroDAO(em);
    }

    public Autor crearAutor() throws Exception {
        Autor autor = new Autor();
        System.out.println("Ingrese el Nombre del Autor");
        String nombre = leer.nextLine().toUpperCase();
        autor.setNombre(nombre);
        autor.setAlta(true);
        autorDao.guardar(autor);
        System.out.println("************Autor Creado exitosamente********");
        return autor;
    }

    public void mostrarAutores() {
        System.out.println("Los autores dispinibles son:");
        List<Autor> autorLista;
        autorLista = autorDao.listarAutores();
        if (autorLista.size() > 0) {
            for (Autor autor : autorLista) {
                String activo = "No";
                if (autor.getAlta()) {
                    activo = "Si";
                }
                System.out.println("Id: " + autor.getId() + "/ Nombre: " + autor.getNombre() + "/ Activo: " + activo);
            }
        } else {
            System.out.println("No hay autores que mostar");
        }

    }

    public Boolean mostrarAutoresActivos() {
        System.out.println("Los autores dispinibles son:");
        List<Autor> autorLista;
        autorLista = autorDao.listarAutoresActivos();
        if (autorLista.size() > 0) {
            for (Autor autor : autorLista) {
                String activo = "No";
                if (autor.getAlta()) {
                    activo = "Si";
                    System.out.println("Id: " + autor.getId() + "/ Nombre: " + autor.getNombre() + "/ Activo: " + activo);
                }
            }
        } else {
            System.out.println("No hay autores Activos");

        }
        return (autorLista.size() > 0);
    }

    public Boolean mostrarAutoresNoActivos() {
        System.out.println("Los autores dispinibles son:");
        List<Autor> autorLista;
        autorLista = autorDao.listarAutoresNoActivos();
        if (autorLista.size() > 0) {
            for (Autor autor : autorLista) {
                String activo = "Si";
                if (!(autor.getAlta())) {
                    activo = "No";
                    System.out.println("Id: " + autor.getId() + "/ Nombre: " + autor.getNombre() + "/ Activo: " + activo);
                }
            }
        } else {
            System.out.println("No hay autores que no estén activos");
        }
        return (autorLista.size() > 0);
    }

    public void darAltaAutor() {

        System.out.println("Los autores disponibles No Activos son:");
        if (mostrarAutoresNoActivos()) {
            System.out.println("Indique el Id del autor que desea dar de alta");
            int id = leer.nextInt();
            autorDao.darAlta(id);
        }
    }

    public void darBajaAutor() {

        System.out.println("Los autores disponibles Activos son:");
        if (mostrarAutoresActivos()) {
            System.out.println("Indique el Id del autor que desea dar de baja");
            int id = leer.nextInt();
            autorDao.darBaja(id);
        }
    }

    public List<Autor> buscarAutorporNombre() {

        System.out.println("Ingrese el nombre del autor a buscar");
        String nombre = leer.nextLine();
        List<Autor> listaAutores = autorDao.buscarAutorPorNombre(nombre);

        if (listaAutores.size() > 0) {
            for (Autor autor : listaAutores) {
                String activo = "No";
                if (autor.getAlta()) {
                    activo = "Si";
                }
                System.out.println("Id: " + autor.getId() + "/ Nombre: " + autor.getNombre() + "/ Activo: " + activo);
            }
        } else {
            System.out.println("No hay autores con ese nombre");
        }

        return listaAutores;
    }

    public Autor buscarAutorPorId() throws Exception {

        System.out.println("Ingrese el id del autor a buscar");
        int id = leer.nextInt();
        Autor autor = autorDao.buscarAutorPorId(id);
        return autor;

    }

    public void modificarAutor() throws Exception {

        try {

            mostrarAutores();
            System.out.println("Cuál autor desea modificar, indique el id?");
            int id = leer1.nextInt();
            //Validando id
            if (autorDao.idAutoresDisponibles().contains(id)) {
                Autor autor = autorDao.buscarAutorPorId(id);

                if (!(autor.getId().equals(null))) {
                    System.out.println("Desea eliminar autor?(S/N)");
                    String siNo = leer.nextLine();
                    if (siNo.equalsIgnoreCase("S")) {
                        autorDao.eliminar(autor);
                    } else { //Modificando
                        System.out.println("Desea modificar el nombre del Autor? (S/N)");
                        String siNo1 = leer.nextLine();
                        if (siNo1.equalsIgnoreCase("S")) {
                            List<String> nombreAutores = autorDao.nombreAutoresDisponibles();
                            boolean autorRepetido = true;

                            while (autorRepetido) {
                                //Validando no ingresar un nombre de autor existente
                                System.out.println("Cual es el nuevo nombre del autor?");
                                System.out.println("");
                                String nombreNuevo = leer.nextLine().toUpperCase();
                                if (nombreAutores.contains(nombreNuevo)) {
                                    System.out.println("El nombre de ese autor ya se encuantra agreado");
                                } else {
                                    autor.setNombre(nombreNuevo);
                                    autorRepetido = false;
                                    System.out.println("Nombre de autor correcto");
                                }
                            }

                            autorDao.modificar(autor);

                        }
                        System.out.println("Desea dar de baja al autor? (s/n)");
                        String siNo2 = leer.next();
                        if (siNo2.equalsIgnoreCase("S")) {
                            autor.setAlta(false);
                            autorDao.modificar(autor);
                        }
                    }
                } else {
                    System.out.println("error de id de autor");
                }

            } else {
                System.out.println("*************NO HAY AUTOR CON ESE ID********************");
            }

        } catch (Exception e) {
            throw e;
        }

    }

    public void eliminarAutor() throws Exception {
        mostrarAutores();
        System.out.println("Cuál autor desea eliminar?, indique el id");
        int id = leer1.nextInt();

        if (autorDao.idAutoresDisponibles().contains(id)) {
            Autor autor = autorDao.buscarAutorPorId(id);
            List<Libro> libros = libroDao.buscarLibrosPorAutor(autor);
            if (libros.isEmpty()) {
                autorDao.eliminar(autor);
                System.out.println("*************Autor Eliminando exitosamente**********");
            } else {
                System.out.println("******NO SE PUEDE ELIMINAR PORQUE HAY LIBROS DE ESE AUTOR**************");
            }
        } else {
            System.out.println("***********Id inresado no coincide con Id de algún autor***********");
        }

    }

    public void mostrarAutoresConLibros() {
        List<Autor> autores = autorDao.listarAutoresConLibros();

        if (autores.size() > 0) {
            for (Autor autor : autores) {
                String activo = "No";
                if (autor.getAlta()) {
                    activo = "Si";
                }
                System.out.println("Id: " + autor.getId() + "/ Nombre: " + autor.getNombre() + "/ Activo: " + activo);
            }
        } else {
            System.out.println("No hay autores que mostar");
        }
    }

    public void mostrarAutoresSinLibros() {
        List<Autor> autores = autorDao.listarAutoresSinLibros();

        if (autores.size() > 0) {
            for (Autor autor : autores) {
                String activo = "No";
                if (autor.getAlta()) {
                    activo = "Si";
                }
                System.out.println("Id: " + autor.getId() + "/ Nombre: " + autor.getNombre() + "/ Activo: " + activo);
            }
        } else {
            System.out.println("No hay autores que mostar");
        }
    }

}
