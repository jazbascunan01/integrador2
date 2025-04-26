package org.example.repository;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import org.example.dto.CarreraDTO;
import org.example.dto.ReporteCarreraDTO;
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
    public List<CarreraDTO> getCarrerasConEstudiantes() {
        EntityManager em = JPAUtil.getEntityManager();
        List<CarreraDTO> carrerasDTO = new ArrayList<>();
        try {
            carrerasDTO = em.createQuery(
                            "SELECT new org.example.dto.CarreraDTO(c.nombre, SIZE(c.carreras)) " +
                                    "FROM Carrera c " +
                                    "WHERE SIZE(c.carreras) > 0 " +
                                    "ORDER BY SIZE(c.carreras) DESC",
                            CarreraDTO.class)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error al recuperar carreras con estudiantes: " + e.getMessage());
        } finally {
            em.close();
        }
        return carrerasDTO;
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
