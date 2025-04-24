package org.example.repository;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.factory.JPAUtil;
import org.example.modelo.Carrera;
import org.example.modelo.Estudiante;
import org.example.modelo.Estudiante_Carrera;


import java.io.FileReader;
import java.util.ArrayList;

public class Estudiante_CarreraRepository {
    public void insertarDesdeCSV(String path) {
        EntityManager em = JPAUtil.getEntityManager();
        String root = "src\\main\\resources\\" + path;
        try (CSVReader reader = new CSVReader(new FileReader(root))) {
            String[] linea;
            reader.readNext(); // salta cabecera
            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Estudiante_Carrera estudiante_carrera = new Estudiante_Carrera();

                Estudiante estudiante = em.find(Estudiante.class, Integer.parseInt(linea[1]));
                estudiante_carrera.setEstudiante(estudiante);

                Carrera carrera = em.find(Carrera.class, Integer.parseInt(linea[2]));
                estudiante_carrera.setCarrera(carrera);

                estudiante_carrera.setAnio_inscripcion(Integer.parseInt((linea[3])));
                estudiante_carrera.setAnio_graduacion(Integer.parseInt((linea[4])));
                estudiante_carrera.setAntiguedad(Integer.parseInt((linea[5])));


                em.persist(estudiante_carrera);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
