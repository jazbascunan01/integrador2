package org.example.repository;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import org.example.dto.ReporteCarreraDTO;
import org.example.factory.JPAUtil;
import org.example.modelo.Carrera;

import java.io.FileReader;
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


    public List<ReporteCarreraDTO> getReporteCarreras() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT new org.example.dto.ReporteCarreraDTO(" +
                    "c.nombre, ec.anio_inscripcion, " +
                    "COUNT(DISTINCT ec.estudiante), " +
                    "SUM(CASE WHEN ec.anio_graduacion IS NOT NULL THEN 1 ELSE 0 END)) " +
                    "FROM Estudiante_Carrera ec " +
                    "JOIN ec.carrera c " +
                    "GROUP BY c.nombre, ec.anio_inscripcion " +
                    "ORDER BY c.nombre ASC, ec.anio_inscripcion ASC";

            return em.createQuery(jpql, ReporteCarreraDTO.class).getResultList();
        } finally {
            em.close();
        }
    }
}
