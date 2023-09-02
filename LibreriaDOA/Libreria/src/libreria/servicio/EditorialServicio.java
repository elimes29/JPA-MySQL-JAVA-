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
import libreria.DAO.EditorialDAO;
import libreria.DAO.LibroDAO;
import libreria.entidades.Editorial;
import libreria.entidades.Libro;

/**
 *
 * @author usuario
 */
public class EditorialServicio {

    private final EditorialDAO editorialDao;
    private final LibroDAO libroDao;
    private AutorServicio autorServicio;
    EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();
    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    Scanner leer1 = new Scanner(System.in).useDelimiter("\n");

    public EditorialServicio() {
        this.editorialDao = new EditorialDAO(em);
        this.libroDao = new LibroDAO(em);
    }

    public Editorial crearEditorial() throws Exception {

        try {
            Editorial editorial = new Editorial();
            System.out.println("Ingrese el nombre de la editorial");
            System.out.println("");
            String nombre = leer.nextLine().toUpperCase();
            editorial.setNombre(nombre);
            editorial.setAlta(true);
            System.out.println("Editroial Creado exitosamente");
            editorialDao.guardar(editorial);

            return editorial;

        } catch (Exception e) {
            throw e;
        }

    }

    public void mostrarEditoriales() {
        List<Editorial> editorialLista = editorialDao.listarEditoriales();
        for (Editorial editorial : editorialLista) {
            String activo = "No";
            if (editorial.getAlta()) {
                activo = "Si";
            }
            System.out.println("Id: " + editorial.getId() + "/ Nombre: " + editorial.getNombre() + "/ Activo: " + activo);
        }
    }

    public Editorial buscarEditorialPorId() {

        System.out.println("Ingrese el id del Editorial a buscar");
        int id = leer1.nextInt();
        Editorial editorial = editorialDao.buscarEditorialPorId(id);
        return editorial;

    }

    public List<Editorial> buscarEditorialPorNombre() {
        System.out.println("Ingrese el nombre del editorial a buscar");
        String nombre = leer.nextLine();
        List<Editorial> listaEditoriales = editorialDao.buscarEditorialPorNombre(nombre);

        if (listaEditoriales.size() > 0) {
            for (Editorial editorial : listaEditoriales) {
                String activo = "No";
                if (editorial.getAlta()) {
                    activo = "Si";
                }
                System.out.println("Id: " + editorial.getId() + "/ Nombre: " + editorial.getNombre() + "/ Activo: " + activo);
            }
        } else {
            System.out.println("No hay editoriales con ese nombre");
        }

        return listaEditoriales;
    }

    public void modificarEditorial() throws Exception {

        try {

            mostrarEditoriales();
            System.out.println("Cuál editorial desea modificar, indique el id?");
            int id = leer1.nextInt();
            //Validando id

            if (editorialDao.idEditorialDisponibles().contains(id)) {
                Editorial editorial = editorialDao.buscarEditorialPorId(id);

                if (!(editorial.getId().equals(null))) {
                    System.out.println("Desea eliminar editorial?(S/N)");
                    String siNo = leer.nextLine();
                    if (siNo.equalsIgnoreCase("S")) {
                        editorialDao.eliminar(editorial);
                    } else { //Modificando
                        System.out.println("Desea modificar el nombre del editorial? (S/N)");
                        String siNo1 = leer.nextLine();
                        if (siNo1.equalsIgnoreCase("S")) {
                            
                            
                             List<String> nombreEditoriales = editorialDao.nombreEditorialesDisponibles();
                            boolean editorialRepetido = true;

                            while (editorialRepetido) {
                                //Validando no ingresar un nombre de autor existente
                                System.out.println("Cual es el nuevo nombre del editorial?");
                                System.out.println("");
                                String nombreNuevo = leer.nextLine().toUpperCase();
                                if (nombreEditoriales.contains(nombreNuevo)) {
                                    System.out.println("El nombre de ese editorial ya se encuantra agreado");
                                } else {
                                    editorial.setNombre(nombreNuevo);
                                    editorialRepetido = false;
                                    System.out.println("Nombre de editorial correcto");
                                }
                            }
                            
                            editorialDao.modificar(editorial);

                        }
                        System.out.println("Desea dar de baja al editorial? (s/n)");
                        String siNo2 = leer.next();
                        if (siNo2.equalsIgnoreCase("S")) {
                            editorial.setAlta(false);
                            editorialDao.modificar(editorial);
                        }
                    }
                } else {
                    System.out.println("********error de id de editorial*****************");
                }

            } else {
                System.out.println("*************NO HAY EDITORIAL CON ESE ID********************");
            }

        } catch (Exception e) {
            throw e;
        }

    }

    public void darAltaEditorial() throws Exception {

        System.out.println("Los editoriales disponibles No Activos son:");
        if (mostrarEditorialNoActivos()) {
            System.out.println("Indique el Id del editorial que desea dar de alta");
            int id = leer.nextInt();
            editorialDao.darAlta(id);
        }
    }

    public void darBajaEditoriales() throws Exception {

        System.out.println("Los edsitoriales disponibles Activos son:");
        if (mostrarEditorialesActivos()) {
            System.out.println("Indique el Id del editorial que desea dar de baja");
            int id = leer.nextInt();
            editorialDao.darBaja(id);
        }
    }

    public Boolean mostrarEditorialesActivos() {
        System.out.println("Los autores dispinibles son:");
        List<Editorial> editorialLista;
        editorialLista = editorialDao.listarEsitorialesActivos();
        if (editorialLista.size() > 0) {
            for (Editorial editorial : editorialLista) {
                String activo = "No";
                if (editorial.getAlta()) {
                    activo = "Si";
                    System.out.println("Id: " + editorial.getId() + "/ Nombre: " + editorial.getNombre() + "/ Activo: " + activo);
                }
            }
        } else {
            System.out.println("No hay editoriales Activos");

        }
        return (editorialLista.size() > 0);
    }

    public Boolean mostrarEditorialNoActivos() {
        System.out.println("Los editoriales dispinibles son:");
        List<Editorial> editorialLista;
        editorialLista = editorialDao.listarEditorialesNoActivos();
        if (editorialLista.size() > 0) {
            for (Editorial editorial : editorialLista) {
                String activo = "Si";
                if (!(editorial.getAlta())) {
                    activo = "No";
                    System.out.println("Id: " + editorial.getId() + "/ Nombre: " + editorial.getNombre() + "/ Activo: " + activo);
                }
            }
        } else {
            System.out.println("No hay editoriales que no estén activos");
        }
        return (editorialLista.size() > 0);
    }

    public void eliminarEditorial() throws Exception {
        mostrarEditoriales();
        System.out.println("Cuál editorial desea eliminar?, indique el id");
        Integer id = leer1.nextInt();

        if (editorialDao.idEditorialDisponibles().contains(id)) {
            Editorial editorial = editorialDao.buscarEditorialPorId(id);
            List<Libro> libros = libroDao.buscarLibrosPorEditorial(editorial);
            if (libros.isEmpty()) {
                editorialDao.eliminar(editorial);
                System.out.println("*************Editorial Eliminando exitosamente**********");
            } else {
                System.out.println("******NO SE PUEDE ELIMINAR PORQUE HAY LIBROS DE ESA EDITORIAL**************");
            }
        } else {
            System.out.println("***********ID DE EDITORIAL NO EXISTE***************");
        }

    }

    public void mostrarEditorialConLibros() {
        List<Editorial> editoriales = editorialDao.listarEditorialesConLibros();

        if (editoriales.size() > 0) {
            for (Editorial editorial : editoriales) {
                String activo = "No";
                if (editorial.getAlta()) {
                    activo = "Si";
                }
                System.out.println("Id: " + editorial.getId() + "/ Nombre: " + editorial.getNombre() + "/ Activo: " + activo);
            }
        } else {
            System.out.println("No hay editoriales que mostar");
        }
    }
    
    public void mostrarEditorialSinLibros() {
        List<Editorial> editoriales = editorialDao.listarEditorialesSinLibros();

        if (editoriales.size() > 0) {
            for (Editorial editorial : editoriales) {
                String activo = "No";
                if (editorial.getAlta()) {
                    activo = "Si";
                }
                System.out.println("Id: " + editorial.getId() + "/ Nombre: " + editorial.getNombre() + "/ Activo: " + activo);
            }
        } else {
            System.out.println("No hay editoriales que mostar");
        }
    }
    

}
