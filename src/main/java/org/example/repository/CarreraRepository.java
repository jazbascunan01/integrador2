package org.example.repository;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import org.example.dto.CarreraConEstudiantesDTO;
import org.example.dto.ReporteCarreraDTO;
import org.example.factory.JPAUtil;
import org.example.modelo.Carrera;

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
                carrera.setCarrera(linea[1]);
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
                    "c.carrera, ec.inscripcion, " +
                    "COUNT(DISTINCT ec.estudiante), " +
                    "SUM(CASE WHEN ec.graduacion IS NOT NULL THEN 1 ELSE 0 END)) " +
                    "FROM Estudiante_Carrera ec " +
                    "JOIN ec.carrera c " +
                    "GROUP BY c.carrera, ec.inscripcion " +
                    "ORDER BY c.carrera ASC, ec.inscripcion ASC";

            return em.createQuery(jpql, ReporteCarreraDTO.class).getResultList();
        } finally {
            em.close();
        }
    }
    /**
     * Punto F: recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
     *
     * @return carreras
     */
    public List<CarreraConEstudiantesDTO> getCarrerasConEstudiantes() {
        EntityManager em = JPAUtil.getEntityManager();
        List<CarreraConEstudiantesDTO> carrerasDTO = new ArrayList<>();
        try {
            carrerasDTO = em.createQuery(
                            "SELECT new org.example.dto.CarreraConEstudiantesDTO(c.id_carrera, c.carrera, COUNT(ec)) " +
                                    "FROM Carrera c " +
                                    "JOIN c.carreras ec " +
                                    "GROUP BY c.id_carrera, c.carrera " +
                                    "HAVING COUNT(ec) > 0 " +
                                    "ORDER BY COUNT(ec) DESC",
                            CarreraConEstudiantesDTO.class)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error al recuperar carreras con estudiantes: " + e.getMessage());
        } finally {
            em.close();
        }
        return carrerasDTO;
    }
}
