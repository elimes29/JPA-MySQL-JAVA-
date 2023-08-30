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
import libreria.DOA.LibroDAO;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;
import libreria.entidades.Libro;

/**
 *
 * @author usuario
 */
public class LibroServicio {

    private final LibroDAO libroDao;
    private final EditorialServicio editorialServicio;
    private final AutorServicio autorServicio;
    EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();
    Scanner leer = new Scanner(System.in);
    Scanner leer1 = new Scanner(System.in);

    public LibroServicio() {
        this.libroDao = new LibroDAO(em);
        this.autorServicio = new AutorServicio();
        this.editorialServicio = new EditorialServicio();

    }

    public void crearLibro() throws Exception {

        try {
            Libro libro = new Libro();
            System.out.println("Ingrese el ISBN del libro");
            Long isbn = leer1.nextLong();
            libro.setIsbn(isbn);
            System.out.println("Ingrese el título del libro");
            String titulo = leer.nextLine();
            libro.setTitulo(titulo);
            System.out.println("Inrese el año de publicación");
            Integer anio = leer1.nextInt();
            libro.setAnio(anio);
            System.out.println("Ingrese la cantidad de ejemplares totales que hay");
            int cantEjemplares = leer1.nextInt();
            libro.setEjemplares(cantEjemplares);
            libro.setEjemplaresPrestados(0);
            libro.setEjemplaresRestantes(cantEjemplares);
            libro.setAlta(true);

            //Agregando Autor
            autorServicio.mostrarAutores();
            System.out.println("Es uno de ellos el autor del libro? (S/N)");
            String siNo = leer.nextLine();

            Autor autor = new Autor();
            if (siNo.equalsIgnoreCase("S")) {
                autor = autorServicio.buscarAutorPorId();
            } else {
                autor = autorServicio.crearAutor();
            }
            libro.setAutor(autor);

            //Agregando Editorial
            System.out.println("Las editoriales disponibles son: ");
            editorialServicio.mostrarEditoriales();
            System.out.println("Es uno de ellos la editorial del libro? (S/N)");
            siNo = leer.nextLine();

            Editorial editorial = new Editorial();
            if (siNo.equalsIgnoreCase("S")) {
                editorial = editorialServicio.buscarEditorialPorId();
            } else {
                editorial = editorialServicio.crearEditorial();
            }
            libro.setEditorial(editorial);

            libroDao.guardar(libro);
            System.out.println("Libro creado exitosamente");

        } catch (Exception e) {
            throw e;

        }

    }

    public Libro buscarLibroPorIsbm() {
        System.out.println("Ingrese el ISBN del libro a buscar");
        long isbn = leer.nextLong();
        Libro libroIsbn = libroDao.buscarLibroPorIsbn(isbn);
        if (libroIsbn != null) {
            System.out.println(libroIsbn.toString());
        } else {
            System.out.println("No hay coincidencia");
        }
        return libroIsbn;

    }

    public void buscarLibroPorTitulo() {
        System.out.println("Ingrese el titulo del libro a buscar");
        String nombre = leer.nextLine();
        List<Libro> listaLibros = libroDao.buscarLibroPorTitulo(nombre);

        if (listaLibros.size() > 0) {
            for (Libro libro : listaLibros) {
                String activo = "No";
                if (libro.getAlta()) {
                    activo = "Si";
                }
                System.out.println(libro.toString());
            }
        } else {
            System.out.println("No hay autores con ese nombre");
        }
    }

    public void mostrarLibros() {
        List<Libro> libroLista;
        libroLista = libroDao.listarLibros();
        for (Libro libro : libroLista) {

            System.out.println("ISBN: " + libro.getIsbn() + "/ Título: " + libro.getTitulo() + "/ Año de pub: " + libro.getAnio() + "/ Autor: "
                    + libro.getAutor().getNombre() + "/ Editorial: " + libro.getEditorial().getNombre() + "/ Disponibles: "
                    + libro.getEjemplaresRestantes() + "/Prestados: " + libro.getEjemplaresPrestados());

        }
    }

    public void eliminarlibro() throws Exception {

        try {
            System.out.println("Los libros disponibles son:");
            mostrarLibros();
            System.out.println("Ingrese el ISBN del libro que desea eliminar");
            long isbn = leer1.nextLong();
            Libro libro = libroDao.buscarLibroPorIsbn(isbn);
            libroDao.eliminar(libro);
            System.out.println("Eliminado exitosamente");
        } catch (Exception e) {
            throw e;
        }

    }

//11) Búsqueda de un libro/s por nombre de Autor.
    public void buscarLibroPorNombreAutor() {
        autorServicio.mostrarAutores();
        List<Autor> autores = autorServicio.buscarAutorporNombre();

        for (Autor autor : autores) {
            List<Libro> libros = libroDao.buscarLibrosPorAutor(autor);
            if (!(libros.size() < 1)) {
                for (Libro libro : libros) {
                    System.out.println("ISBN: " + libro.getIsbn() + "/ Título: " + libro.getTitulo() + "/ Año de pub: " + libro.getAnio() + "/ Autor: "
                            + libro.getAutor().getNombre() + "/ Editorial: " + libro.getEditorial().getNombre() + "/ Disponibles: "
                            + libro.getEjemplaresRestantes() + "/Prestados: " + libro.getEjemplaresPrestados());
                }
            } else {
                System.out.println("Este autor no tiene libros asociados");
            }

        }

    }

//12) Búsqueda de un libro/s por nombre de Editorial.
    public void buscarLibroPorNombreEditorial() {
        editorialServicio.mostrarEditoriales();
        List<Editorial> editoriales = editorialServicio.buscarEditorialPorNombre();

        for (Editorial editorial : editoriales) {
            List<Libro> libros = libroDao.buscarLibrosPorEditorial(editorial);
            if (!(libros.size() < 1)) {
                for (Libro libro : libros) {
                    System.out.println("ISBN: " + libro.getIsbn() + "/ Título: " + libro.getTitulo() + "/ Año de pub: " + libro.getAnio() + "/ Autor: "
                            + libro.getAutor().getNombre() + "/ Editorial: " + libro.getEditorial().getNombre() + "/ Disponibles: "
                            + libro.getEjemplaresRestantes() + "/Prestados: " + libro.getEjemplaresPrestados());
                }
            } else {
                System.out.println("Este autor no tiene libros asociados");
            }

        }

    }

    public void sacarLibro() {
        System.out.println("Los libros disponibles son:");
        mostrarLibros();
        System.out.println("Ingrese el ISBN del libro que desea sacar");
        long isbn = leer1.nextLong();
        Libro libro = libroDao.buscarLibroPorIsbn(isbn);

        if (libro.getEjemplaresRestantes() > 0) {
            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() + 1);
            libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() - 1);
            System.out.println("*******LIbro entregado***********");
        } else {
            System.out.println("No hay ejemplares dispinibles de ese libro");
        }
    }

    public void entregarLibro() {
        System.out.println("Los libros disponibles son:");
        mostrarLibros();
        System.out.println("Ingrese el ISBN del libro que desea entregar");
        long isbn = leer1.nextLong();
        Libro libro = libroDao.buscarLibroPorIsbn(isbn);

        if (libro.getEjemplares() > libro.getEjemplaresRestantes()) {
            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() - 1);
            libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() + 1);
            System.out.println("*******LIbro recibido***********");
        } else {
            System.out.println("No es posible devolver ese libro porque la cantidad de ejemlares disponibles es la cantidad de ejemplares totales");
        }
    }

    public void aregarEditorialALibro() throws Exception {
        System.out.println("Los libros disponibles son: ");
        mostrarLibros();
        Libro libro = buscarLibroPorIsbm();
        String siNo;
        System.out.println("Las editoriales disponibles son: ");
        editorialServicio.mostrarEditoriales();
        System.out.println("Es uno de ellos la editorial del libro? (S/N)");
        siNo = leer.next();

        Editorial editorial = new Editorial();
        if (siNo.equalsIgnoreCase("S")) {
            editorial = editorialServicio.buscarEditorialPorId();
        } else {
            editorial = editorialServicio.crearEditorial();
        }
        libro.setEditorial(editorial);

        libroDao.modificar(libro);
        System.out.println("************Modificado el editorial exitosamente****************");
    }

    public void agregarAutorAlibro() throws Exception {
        System.out.println("Los libros disponibles son: ");
        mostrarLibros();
        Libro libro = buscarLibroPorIsbm();

        System.out.println("Los autores disponibles son: ");
        autorServicio.mostrarAutores();
        System.out.println("Es uno de ellos el autor del libro? (S/N)");
        String siNo = leer.nextLine();

        Autor autor = new Autor();
        if (siNo.equalsIgnoreCase("S")) {
            autor = autorServicio.buscarAutorPorId();
        } else {
            autor = autorServicio.crearAutor();
        }
        libro.setAutor(autor);
        libroDao.modificar(libro);
        System.out.println("************Modificado el Autor exitosamente****************");
    }
}
