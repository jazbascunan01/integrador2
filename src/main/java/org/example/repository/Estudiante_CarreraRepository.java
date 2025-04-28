package org.example.repository;

import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import org.example.dto.CarreraConEstudiantesDTO;
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
     * @param dniEstudiante      el DNI del estudiante.
     * @param idCarrera         el ID de la carrera.
     * @param anioInscripcion   el año de inscripción.
     * @param anioGraduacion   el año de graduación (0 si no se ha graduado).
     */
    public void matricularEstudiante(int dniEstudiante, int idCarrera, int anioInscripcion, int anioGraduacion) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            em.getTransaction().begin();

            // Buscar el estudiante y la carrera
            Estudiante estudiante = em.find(Estudiante.class, dniEstudiante);
            Carrera carrera = em.find(Carrera.class, idCarrera);

            if (estudiante != null && carrera != null) {
                // Verificar si ya está matriculado
                boolean yaMatriculado = estaMatriculado(dniEstudiante, idCarrera);
                if (yaMatriculado) {
                    System.out.println("\n══════════════════════════════════════════════════════════════════");
                    System.out.println("     ➤ El estudiante ya está matriculado en esa carrera.   ");
                    System.out.println("══════════════════════════════════════════════════════════════════");
                    return;
                }

                // Crear y persistir la matrícula
                Estudiante_Carrera estudianteCarrera = new Estudiante_Carrera();
                estudianteCarrera.setEstudiante(estudiante);
                estudianteCarrera.setCarrera(carrera);
                estudianteCarrera.setAnio_inscripcion(anioInscripcion);

                if (anioGraduacion > 0) {
                    estudianteCarrera.setAnio_graduacion(anioGraduacion);
                    estudianteCarrera.setAntiguedad(anioGraduacion - anioInscripcion);
                } else {
                    int anioActual = java.time.Year.now().getValue();
                    estudianteCarrera.setAntiguedad(anioActual - anioInscripcion);
                }

                em.persist(estudianteCarrera);
                em.getTransaction().commit();
                System.out.println("\n═══════════════════════════════════════════════");
                System.out.println("   ★ Estudiante matriculado correctamente ★   ");
                System.out.println("═══════════════════════════════════════════════");
            } else {
                System.out.println("\n══════════════════════════════════════════════════════════════════");
                System.out.println("     ➤ No se encontró el estudiante o la carrera.   ");
                System.out.println("══════════════════════════════════════════════════════════════════");
            }
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("\n══════════════════════════════════════════════════════════════════");
            System.out.println("     ➤ Error al matricular al estudiante: " + e.getMessage() + "   ");
            System.out.println("══════════════════════════════════════════════════════════════════");
        } finally {
            if (em != null) {
                em.close();
            }
        }
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
                            "SELECT new org.example.dto.CarreraConEstudiantesDTO(c.id, c.nombre, COUNT(ec)) " +
                                    "FROM Estudiante_Carrera ec " +
                                    "JOIN ec.carrera c " +
                                    "GROUP BY c.id, c.nombre " +
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
    public static boolean estaMatriculado(int dniEstudiante, int idCarrera) {
        EntityManager em = JPAUtil.getEntityManager();
        Estudiante_Carrera resultado = em.createQuery(
                        "SELECT ec FROM Estudiante_Carrera ec WHERE ec.estudiante.dni = :dni AND ec.carrera.id = :id",
                        Estudiante_Carrera.class)
                .setParameter("dni", dniEstudiante)
                .setParameter("id", idCarrera)
                .getResultStream()
                .findFirst()
                .orElse(null);

        return resultado != null;
    }
}
