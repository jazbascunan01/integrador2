package org.example.repository;

import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import org.example.dto.Estudiante_CarreraDTO;
import org.example.factory.JPAUtil;
import org.example.modelo.Carrera;
import org.example.modelo.Estudiante;
import org.example.modelo.Estudiante_Carrera;


import java.io.FileReader;
import java.util.List;
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

    /**
     * Inciso b. Guarda la matrícula de un estudiante en una carrera.
     *
     * @param ec el objeto que representa la inscripción del estudiante.
     */
    public void matricularEstudiante(Estudiante_Carrera ec) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(ec);
        em.getTransaction().commit();
        em.close();
    }


    /**
     * Punto G: recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
     *
     * @param carreraNombre el nombre de la carrera.
     * @param ciudad       la ciudad de residencia del estudiante.
     *
     * @return estudiantes
     */
    public List<Estudiante_CarreraDTO> getEstudiantesByCarreraYCiudad(String carreraNombre, String ciudad) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Estudiante_CarreraDTO> estudiantesCarreraDTO = new ArrayList<>();
        try {
            estudiantesCarreraDTO = em.createQuery(
                            "SELECT new org.example.dto.Estudiante_CarreraDTO(" +
                                    "ec.id, e.nombre, e.apellido, e.ciudad, c.nombre, ec.anio_inscripcion, ec.anio_graduacion, ec.antiguedad) " +
                                    "FROM Estudiante_Carrera ec " +
                                    "JOIN ec.estudiante e " +
                                    "JOIN ec.carrera c " +
                                    "WHERE c.nombre = :carreraNombre AND e.ciudad = :ciudad",
                            Estudiante_CarreraDTO.class)
                    .setParameter("carreraNombre", carreraNombre)
                    .setParameter("ciudad", ciudad)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error al recuperar estudiantes por carrera y ciudad: " + e.getMessage());
        } finally {
            em.close();
        }
        return estudiantesCarreraDTO;
    }
}
