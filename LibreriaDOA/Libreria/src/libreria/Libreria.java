/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria;

import java.util.InputMismatchException;
import java.util.Scanner;
import libreria.servicio.AutorServicio;
import libreria.servicio.EditorialServicio;
import libreria.servicio.LibroServicio;

/**
 *
 * @author usuario
 */
public class Libreria {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Scanner leer = new Scanner(System.in);
        Scanner leer1 = new Scanner(System.in);

        AutorServicio as = new AutorServicio();
        LibroServicio ls = new LibroServicio();
        EditorialServicio es = new EditorialServicio();

        try {

            boolean salir = true;
            System.out.println("");
            System.out.println("Bienvenido a la aplicación de la librería");
            while (salir) {

                System.out.println("Desea realizar operaciones a:");
                System.out.println("1: Libros");
                System.out.println("2: Autores");
                System.out.println("3: Editoriales");
                System.out.println("Otro: Salir de la aplicación");
                int opcionLAE = leer1.nextInt();

                switch (opcionLAE) {
                    case 1:
                        boolean salirLibros = true;
                        System.out.println("");
                        System.out.println("********Entrando a libros********");
                        while (salirLibros) {
                            System.out.println("");
                            System.out.println("¿Qué operaciones desea realizar sobre libros?:");
                            System.out.println("1: Crear libro");
                            System.out.println("2: Mostrar todos los libros");
                            System.out.println("3: Mostrar libros de un autor");
                            System.out.println("4: Mostrar libros de una editorial");
                            System.out.println("5: Sacar un libro");
                            System.out.println("6: Entregar un libro");
                            System.out.println("7: Modificar editorial de un libro");
                            System.out.println("8: Modificar autor de un libro");
                            System.out.println("Otro: Menú principal");
                            int opcionLibro = leer1.nextInt();
                            switch (opcionLibro) {
                                case 1:
                                    ls.crearLibro();
                                    break;
                                case 2:
                                    ls.mostrarLibros();
                                    break;
                                case 3:
                                    ls.buscarLibroPorNombreAutor();
                                    break;
                                case 4:
                                    ls.buscarLibroPorNombreEditorial();
                                    break;
                                case 5:
                                    ls.sacarLibro();
                                    break;
                                case 6:
                                    ls.entregarLibro();
                                    break;
                                case 7:
                                    ls.aregarEditorialALibro();
                                    break;
                                case 8:
                                    ls.agregarAutorAlibro();
                                    break;
                                default:
                                    salirLibros = false;
                                    break;
                            }
                        }
                        break;

                    case 2:
                        boolean salirAutor = true;
                        System.out.println("");
                        System.out.println("********Entrando a Autores********");
                        while (salirAutor) {
                            System.out.println("");
                            System.out.println("¿Qué operaciones desea realizar sobre Autores?:");
                            System.out.println("1: Crear autor");
                            System.out.println("2: Mostrar todos los autores");
                            System.out.println("3: Modificar autor");
                            System.out.println("4: Dar de baja autor");
                            System.out.println("5: Dar de alta autor");
                            System.out.println("6: Eliminar autor");
                            System.out.println("7: Mostar autores con libros");
                            System.out.println("8: Mostar autores sin libros");
                            System.out.println("Otro: Menú principal");
                            int opcionLibro = leer1.nextInt();
                            switch (opcionLibro) {
                                case 1:
                                    as.crearAutor();
                                    break;
                                case 2:
                                    as.mostrarAutores();
                                    break;
                                case 3:
                                    as.modificarAutor();
                                    break;
                                case 4:
                                    as.darBajaAutor();
                                    break;
                                case 5:
                                    as.darAltaAutor();
                                    break;
                                case 6:
                                    as.eliminarAutor();
                                    break;
                                case 7:
                                    as.mostrarAutoresConLibros();
                                    break;
                                case 8:
                                    as.mostrarAutoresSinLibros();
                                    break;
                                default:
                                    salirAutor = false;
                                    break;
                            }
                        }
                        break;
                    case 3:
                        boolean salirEditorial = true;
                        System.out.println("");
                        System.out.println("********Entrando a Editoriales********");
                        while (salirEditorial) {
                            System.out.println("");
                            System.out.println("¿Qué operaciones desea realizar sobre Editoriales?:");
                            System.out.println("1: Crear editorial");
                            System.out.println("2: Mostrar todos los editoriales");
                            System.out.println("3: Modificar editorial");
                            System.out.println("4: Dar de baja editorial");
                            System.out.println("5: Dar de Alta editorial");
                            System.out.println("6: Eliminar editorial");
                            System.out.println("7: Motrar editorial con libros");
                            System.out.println("8: Mostrar editorial sin libros");
                            System.out.println("Otro: Menú principal");
                            int opcionLibro = leer1.nextInt();
                            switch (opcionLibro) {
                                case 1:
                                    es.crearEditorial();
                                    break;
                                case 2:
                                    es.mostrarEditoriales();
                                    break;
                                case 3:
                                    es.modificarEditorial();
                                    ;
                                    break;
                                case 4:
                                    es.darBajaEditoriales();
                                    break;
                                case 5:
                                    es.darAltaEditorial();
                                    break;
                                case 6:
                                    es.eliminarEditorial();
                                    break;
                                case 7:
                                    es.mostrarEditorialConLibros();
                                    break;
                                case 8:
                                    es.mostrarEditorialSinLibros();
                                    break;
                                default:
                                    salirEditorial = false;
                                    break;
                            }
                        }
                        break;
                    default:
                        System.out.println("********Gracias por usar el sistema************");
                        salir = false;
                        break;
                }

            }

        } catch (InputMismatchException e) {
            System.out.println("ERROR: debes ingresar una valor numérico");
            throw e;
        }

    }

}
