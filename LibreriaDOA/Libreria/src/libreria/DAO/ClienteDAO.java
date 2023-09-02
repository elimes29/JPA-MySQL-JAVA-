/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import libreria.entidades.Cliente;

/**
 *
 * @author usuario
 */
public class ClienteDAO extends DAO<Cliente> {

    public ClienteDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Cliente> listarClientes() {
        String sql = "select * from clientes;";
        Query query = entityManager.createNativeQuery(sql, Cliente.class);
        return query.getResultList();
    }

    public Cliente buscarClientePorId(Integer id){
        String sql = "select * from clientes where id like "+id+";";
        Query query = entityManager.createNativeQuery(sql, Cliente.class);
        return (Cliente) query.getSingleResult();
    }
    
    public List<Integer> listarIdDeClientes(){
        String sql = "select id from clientes;";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }
    
    public List<Long> listarCedulaDeCliente(){
        String sql = "select documento from clientes;";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }
    
    public Cliente buscarClientePorCedula(Long documento){
        String sql = "select * from clientes where documento like "+documento+";";
        Query query = entityManager.createNativeQuery(sql, Cliente.class);
        return (Cliente) query.getSingleResult();
    }
    

    
}
