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
import libreria.DOA.EditorialDAO;
import libreria.entidades.Editorial;

/**
 *
 * @author usuario
 */
public class EditorialServicio {

    private final EditorialDAO editorialDao;
    private AutorServicio autorServicio;
    EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();
    Scanner leer = new Scanner(System.in);
    Scanner leer1 = new Scanner(System.in);

    public EditorialServicio() {
        this.editorialDao = new EditorialDAO(em);
    }

    public Editorial crearEditorial() throws Exception {

        try {
            Editorial editorial = new Editorial();
            System.out.println("Ingrese el nombre de la editorial");
            String nombre = leer.nextLine();
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
}
