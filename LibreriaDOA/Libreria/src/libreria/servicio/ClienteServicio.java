/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.servicio;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import libreria.DAO.ClienteDAO;
import libreria.entidades.Cliente;

/**
 *
 * @author usuario
 */
public class ClienteServicio {

    public ClienteDAO clienteDao;
    EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();
    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    Scanner leer1 = new Scanner(System.in).useDelimiter("\n");

    public ClienteServicio() {
        this.clienteDao = new ClienteDAO(em);
    }

    public Cliente crearCliente() throws Exception {
        Cliente cliente = new Cliente();

        System.out.println("Ingrese el nombre del cliente");
        cliente.setNombre(leer.nextLine());
        System.out.println("Inrese apellido del cliente");
        cliente.setApellido(leer.nextLine());
        System.out.println("Ingrese cedula del cliente");
        cliente.setDocumento(leer1.nextLong());
        System.out.println("Ingrese el teléfono del cliente");
        cliente.setTelefono(leer.nextLine());

        clienteDao.guardar(cliente);
        System.out.println("************Cliente guardado exitosamente**********");
        return cliente;

    }

    public void mostrarClientes() {

        System.out.println("Los clientes dispinibles son:");
        List<Cliente> clientes = clienteDao.listarClientes();

        if (clientes.size() > 0) {
            for (Cliente cliente : clientes) {
                System.out.println("Id: " + cliente.getId() + "/ Nombre: " + cliente.getNombre() + "/ Apellido: " + cliente.getApellido() + "/ Cedula: " + cliente.getDocumento() + "/ Telefono: " + cliente.getTelefono());
            }
        } else {
            System.out.println("No hay clientes que mostar");
        }

    }

    public void modificarCliente() throws Exception {
        
        try {
            System.out.println("Los clientes dispinibles son:");
        mostrarClientes();

        System.out.println("Ingrese el id del cliente a modificar");
        Integer id = leer1.nextInt();

        if (clienteDao.listarIdDeClientes().contains(id)) {

            Cliente cliente = clienteDao.buscarClientePorId(id);

            System.out.println("¿Desea modificar nombre? (S/N)");
            String siNo = leer.nextLine();
            if (siNo.equalsIgnoreCase("S")) {
                System.out.println("¿Cúal es el nuevo nombre del cliente?");
                cliente.setNombre(leer.nextLine());
            }

            System.out.println("¿Desea modificar apellido? (S/N)");
            siNo = leer.nextLine();
            if (siNo.equalsIgnoreCase("S")) {
                System.out.println("¿Cúal es el nuevo apellido del cliente?");
                cliente.setApellido(leer.nextLine());
            }

            System.out.println("¿Desea modificar la cedula? (S/N)");
            siNo = leer.nextLine();
            if (siNo.equalsIgnoreCase("S")) {
                System.out.println("¿Cúal es la  nueva cedula del cliente?");
                cliente.setDocumento(leer1.nextLong());
            }

            System.out.println("¿Desea modificar el teléfono? (S/N)");
            siNo = leer.nextLine();
            if (siNo.equalsIgnoreCase("S")) {
                System.out.println("¿Cúal es el nuevo telefono del cliente?");
                cliente.setTelefono(leer1.nextLine());
            }

            clienteDao.modificar(cliente);
            System.out.println("*********Cliente modificado exitosamente***************");

        } else {
            System.out.println("*******El id que ingresó no es válido**************");
        }
            
        } catch (InputMismatchException e) {
            throw e;
        }
        
        

    }

    public void eliminarCliente() throws Exception{
        try {
            mostrarClientes();
            System.out.println("Ingrese el id del cliente a eliminar");
            Integer id = leer1.nextInt();
                        
            if (clienteDao.listarIdDeClientes().contains(id)){
                Cliente cliente = clienteDao.buscarClientePorId(id);
                clienteDao.eliminar(cliente);
                System.out.println("********Eliminado cliente exitosamente************");
            }else{
                System.out.println("**********El id que introdujo no existe***********");
            }
            
        } catch (InputMismatchException e) {
            throw e;
        }
    }
    
    public List<Long> listarCedulaCliente(){
        return clienteDao.listarCedulaDeCliente();
    }
    
    public Cliente buscarClientePorCedula(Long documento){
        return clienteDao.buscarClientePorCedula(documento);
    }
    
     public Cliente buscarClientePorId(Integer id){
        return clienteDao.buscarClientePorId(id);
    }
    
}
