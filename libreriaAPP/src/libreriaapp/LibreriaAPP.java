/*
d) Main
Esta clase tiene la responsabilidad de llevar adelante las funcionalidades necesarias para
interactuar con el usuario. En esta clase se muestra el menú de opciones con las operaciones
disponibles que podrá realizar el usuario.

e) Tareas a realizar
Al alumno le toca desarrollar, las siguientes funcionalidades:
1) Crear base de datos Librería
2) Crear unidad de persistencia
3) Crear entidades previamente mencionadas (excepto Préstamo)
4) Generar las tablas con JPA
5) Crear servicios previamente mencionados.
6) Crear los métodos para persistir entidades en la base de datos librería
7) Crear los métodos para dar de alta/bajo o editar dichas entidades.
8) Búsqueda de un Autor por nombre.
9) Búsqueda de un libro por ISBN.
10) Búsqueda de un libro por Título.
11) Búsqueda de un libro/s por nombre de Autor.
12) Búsqueda de un libro/s por nombre de Editorial.
13) Agregar las siguientes validaciones a todas las funcionalidades de la aplicación:
• Validar campos obligatorios.
• No ingresar datos duplicados.
 */
package libreriaapp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import libreriaapp.servicios.AutorServicio;
import libreriaapp.servicios.EditorialServicio;

/**
 *
 * @author usuario
 */
public class LibreriaAPP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        //PRIMERA PRUEBA
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("libreriaAPPPU");
        EntityManager em = emf.createEntityManager();
        
        AutorServicio autorS = new AutorServicio();
        EditorialServicio editorialS = new EditorialServicio();
        
        
//        //Autores
//        autorS.crearAutor();
//        autorS.eliminarAutor();
        
        //Editoriales
        editorialS.crearEditorial();
        editorialS.crearEditorial();
        editorialS.crearEditorial();
        editorialS.eliminarEditorial();
        editorialS.actualizarEditorial();


        
        
        
    }
    
}
