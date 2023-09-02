/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.servicio;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import libreria.DAO.PrestamoDAO;
import libreria.entidades.Cliente;
import libreria.entidades.Libro;
import libreria.entidades.Prestamo;
import libreria.entidades.Realizado;

/**
 *
 * @author usuario
 */
public class PrestamoServicio {

    public PrestamoDAO prestamoDao;
    public LibroServicio libroServicio;
    public ClienteServicio clienteServicio;

    EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();
    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    Scanner leer1 = new Scanner(System.in).useDelimiter("\n");

    public PrestamoServicio() {
        this.prestamoDao = new PrestamoDAO(em);
        this.libroServicio = new LibroServicio();
        this.clienteServicio = new ClienteServicio();
    }
    
    
//*******Falta validar que no se pueda sacar un libro que ya sacó una persona******
    public void sacarLibro() throws Exception {

        try {

            Prestamo prestamo = new Prestamo();

            System.out.println("***Ingresando la fecha de retiro***");
            Date fechaRetiro = new Date();
            System.out.println("Ingrese el dia (dd)");
            fechaRetiro.setDate(leer1.nextInt());
            System.out.println("Ingrese el mes (mm)");
            fechaRetiro.setMonth(leer1.nextInt() - 1);
            System.out.println("Ingrese el año (aaaa)");
            fechaRetiro.setYear(leer1.nextInt() - 1900);

            prestamo.setFechaPrestamo(fechaRetiro);

            System.out.println("*******Ingresando Cliente**********");
            clienteServicio.mostrarClientes();
            System.out.println("Ingrese la cedula del cliente");
            Long cedula = leer1.nextLong();
            Cliente cliente = new Cliente();
            if (clienteServicio.listarCedulaCliente().contains(cedula)) {
                cliente = clienteServicio.buscarClientePorCedula(cedula);
            } else {
                cliente = clienteServicio.crearCliente();
            }
            prestamo.setCliente(cliente);

            System.out.println("*******Libro**********");
            libroServicio.mostrarLibros();

            Realizado realizado = libroServicio.sacarLibro();
            Libro libro = realizado.getLibro();
            prestamo.setLibro(libro);

            if (realizado.getReaizado()) {
                prestamoDao.guardar(prestamo);
            }

        } catch (InputMismatchException e) {
            throw e;
        }
    }

    public void entregarLibro() throws Exception {

        try {
            //Guaradamos la fecha de retiro
            System.out.println("***Ingresando la fecha de entrega***");
            Date fechaRetiro = new Date();
            System.out.println("Ingrese el dia (dd)");
            fechaRetiro.setDate(leer1.nextInt());
            System.out.println("Ingrese el mes (mm)");
            fechaRetiro.setMonth(leer1.nextInt() - 1);
            System.out.println("Ingrese el año (aaaa)");
            fechaRetiro.setYear(leer1.nextInt() - 1900);


            //Buscamos el cliente
            System.out.println("*******Ingresando Cliente**********");
            System.out.println("Los clientes con prestamos activos son: ");
            mostrarClientesPrestamoActivo();
            Cliente cliente = new Cliente();

            Boolean band = true;
            while (band) {
                
                System.out.println("Ingrese la cedula del cliente");
                Long ci = leer1.nextLong();

                if (clienteServicio.listarCedulaCliente().contains(ci)) {
                    cliente = clienteServicio.buscarClientePorCedula(ci);
                    band = false;
                } else {
                    System.out.println("*****Cliente sin prestamo activo/Error en Id**********");
                }

            }
            

            //Buscamos el libro
            System.out.println("*******Libro**********");
            mostrarLibrosActivosPorCliente(cliente);
            List<Long> isbns = prestamoDao.listarIsbnLibroPrestamoActivo(cliente);
            Realizado realizado = libroServicio.entregarLibro(isbns);            
            Libro libro = realizado.getLibro();


            //Ubicamos el prestamo
            Prestamo prestamo = prestamoDao.ubicarPrestamo(cliente, libro);
            
            
            if (realizado.getReaizado()) {
                prestamo.setFechaDevolucion(fechaRetiro);
                prestamoDao.guardar(prestamo);
            }

        } catch (InputMismatchException e) {
            throw e;
        }
    }
    
    public void mostrarPrestamos(){
        List<Prestamo> prestamos = prestamoDao.listarPrestamos();
        
        
        for (Prestamo prestamo : prestamos) {
            
            if (prestamo.getFechaDevolucion() == null){
                System.out.println("Id: "+prestamo.getId()+" / fecha de retiro (dd/mm/aa): "+prestamo.getFechaPrestamo().getDate()+"/"
                    +(prestamo.getFechaPrestamo().getMonth()+1)+"/"+(prestamo.getFechaPrestamo().getYear()+1900)
                    +" / fecha de entrea (dd/mm/aa): NO ENTREGADO / Nombre Cliente: "+prestamo.getCliente().getNombre()+" "+prestamo.getCliente().getApellido()+" / Libro: "
                    + prestamo.getLibro().getTitulo());                
            }else{
                System.out.println("Id: "+prestamo.getId()+" / fecha de retiro (dd/mm/aa): "+prestamo.getFechaPrestamo().getDate()+"/"
                    +(prestamo.getFechaPrestamo().getMonth()+1)+"/"+(prestamo.getFechaPrestamo().getYear()+1900)
                    +" / fecha de entrea (dd/mm/aa): "+prestamo.getFechaDevolucion().getDate()+"/"
                    +(prestamo.getFechaDevolucion().getMonth()+1)+"/"+(prestamo.getFechaDevolucion().getYear()+1900)
                    + " / Nombre Cliente: "+prestamo.getCliente().getNombre()+" "+prestamo.getCliente().getApellido()+" / Libro: "
                    + prestamo.getLibro().getTitulo());
            }
        }
    }
    
    public void mostrarClientesPrestamoActivo(){
        
          System.out.println("Los clientes con prestamos activos son:");
        List<Cliente> clientes = prestamoDao.listarClientesPrestamosActivos();

        if (clientes.size() > 0) {
            for (Cliente cliente : clientes) {
                System.out.println("Id: " + cliente.getId() + "/ Nombre: " + cliente.getNombre() + "/ Apellido: " + cliente.getApellido() + "/ Cedula: " + cliente.getDocumento() + "/ Telefono: " + cliente.getTelefono());
            }
        } else {
            System.out.println("No hay clientes que mostar");
        }
        
        
    }
    
    public void mostrarClientesPrestamoNoActivo(){
        
          System.out.println("Los clientes con prestamos activos son:");
        List<Cliente> clientes = prestamoDao.listarClientesPrestamosNoActivos();

        if (clientes.size() > 0) {
            for (Cliente cliente : clientes) {
                System.out.println("Id: " + cliente.getId() + "/ Nombre: " + cliente.getNombre() + "/ Apellido: " + cliente.getApellido() + "/ Cedula: " + cliente.getDocumento() + "/ Telefono: " + cliente.getTelefono());
            }
        } else {
            System.out.println("No hay clientes que mostar");
        }
        
        
    }
    
    public void mostrarLibrosActivosPorCliente(Cliente cliente){
        System.out.println("Los libros por entrear que tiene el cliente CI: "+cliente.getDocumento()+" son:");
        List<Libro> libros = prestamoDao.listarLibrosActivosPorCliente(cliente);
        
                for (Libro libro : libros) {
            System.out.println("ISBN: " + libro.getIsbn() + "/ Título: " + libro.getTitulo() + "/ Año de pub: " + libro.getAnio() + "/ Autor: "
                    + libro.getAutor().getNombre() + "/ Editorial: " + libro.getEditorial().getNombre() + "/ Disponibles: "
                    + libro.getEjemplaresRestantes() + "/Prestados: " + libro.getEjemplaresPrestados());
        }
    }

}
