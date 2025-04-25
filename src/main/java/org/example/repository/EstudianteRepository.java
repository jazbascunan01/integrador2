package org.example.repository;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.factory.JPAUtil;
import org.example.modelo.Estudiante;
import org.example.modelo.Estudiante_Carrera;


import java.io.FileReader;
import java.util.ArrayList;

public class EstudianteRepository {
    public void insertarDesdeCSV(String path) {
        EntityManager em = JPAUtil.getEntityManager();
        String root = "src\\main\\resources\\" + path;

        try (CSVReader reader = new CSVReader(new FileReader(root))) {
            String[] linea;
            reader.readNext(); // Salta la cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                int dni = Integer.parseInt(linea[0]);
                String nombre = linea[1];
                String apellido = linea[2];
                int edad = Integer.parseInt(linea[3]);
                String genero = linea[4];
                String ciudad = linea[5];
                int LU = Integer.parseInt(linea[6]);

                // Verifica si el estudiante ya existe
                Estudiante estudiante = em.find(Estudiante.class, dni);

                if (estudiante == null) {
                    // Crea y persiste un nuevo estudiante
                    estudiante = new Estudiante(dni, nombre, apellido, edad, genero, ciudad, LU);
                    em.persist(estudiante);
                } else {
                    // Actualiza los datos del estudiante existente
                    estudiante.setNombre(nombre);
                    estudiante.setApellido(apellido);
                    estudiante.setEdad(edad);
                    estudiante.setGenero(genero);
                    estudiante.setCiudad(ciudad);
                    estudiante.setNum_lu(LU);
                    em.merge(estudiante);
                }
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }
    /**
     * Punto A: dar de alta un estudiante
     */
    public void altaEstudiante(Estudiante estudiante) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(estudiante);
        em.getTransaction().commit();
        em.close();
        System.out.println("Estudiante dado de alta correctamente.");
    }

}
