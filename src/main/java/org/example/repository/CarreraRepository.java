package org.example.repository;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import org.example.factory.JPAUtil;
import org.example.modelo.Carrera;
import org.example.modelo.Estudiante;
import org.example.modelo.Estudiante_Carrera;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CarreraRepository {
    public void insertarDesdeCSV(String path) {
        EntityManager em = JPAUtil.getEntityManager();
        String root = "src\\main\\resources\\" + path;
        try (CSVReader reader = new CSVReader(new FileReader(root))) {
            String[] linea;
            reader.readNext(); // salta cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Carrera carrera = new Carrera();
                carrera.setNombre(linea[1]);
                carrera.setDuracion(Integer.parseInt(linea[2]));

                em.persist(carrera);

            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Punto F: recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
     *
     * @return carreras
     */
    public List<Carrera> getCarrerasConEstudiantes() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Carrera> carreras = em.createQuery(
                "SELECT c FROM Carrera c " +
                        "WHERE SIZE(c.carreras) > 0 " +  // Filtra carreras con al menos un inscripto
                        "ORDER BY SIZE(c.carreras) DESC",  // Ordena por cantidad de inscriptos (descendente)
                Carrera.class
        ).getResultList();
        em.close();
        return carreras;
    }
}
