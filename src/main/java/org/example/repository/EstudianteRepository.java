package org.example.repository;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.dto.EstudianteDTO;
import org.example.factory.JPAUtil;
import org.example.modelo.Estudiante;


import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class EstudianteRepository {
    public void insertarDesdeCSV(String path) {
        EntityManager em = JPAUtil.getEntityManager();
        String root = "src\\main\\resources\\" + path;

        try (CSVReader reader = new CSVReader(new FileReader(root))) {
            String[] linea;
            reader.readNext(); // Salta la cabecera

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                int DNI = Integer.parseInt(linea[0]);
                String nombre = linea[1];
                String apellido = linea[2];
                int edad = Integer.parseInt(linea[3]);
                String genero = linea[4];
                String ciudad = linea[5];
                int LU = Integer.parseInt(linea[6]);

                // Verifica si el estudiante ya existe
                Estudiante estudiante = em.find(Estudiante.class, DNI);

                if (estudiante == null) {
                    // Crea y persiste un nuevo estudiante
                    estudiante = new Estudiante(DNI, nombre, apellido, edad, genero, ciudad, LU);
                    em.persist(estudiante);
                } else {
                    // Actualiza los datos del estudiante existente
                    estudiante.setNombre(nombre);
                    estudiante.setApellido(apellido);
                    estudiante.setEdad(edad);
                    estudiante.setGenero(genero);
                    estudiante.setCiudad(ciudad);
                    estudiante.setLU(LU);
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
     *
     * @param estudiante el objeto que representa el estudiante.
     */
    public void altaEstudiante(Estudiante estudiante) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            // Verificar si el estudiante ya existe
            Estudiante estudianteExistente = em.find(Estudiante.class, estudiante.getDNI());
            if (estudianteExistente == null) {
                em.persist(estudiante);
                System.out.println("\n══════════════════════════════════════════════");
                System.out.println("  ★ Estudiante dado de alta correctamente ★  ");
                System.out.println("══════════════════════════════════════════════");
            } else {
                System.out.println("\n══════════════════════════════════════════════════════════════════");
                System.out.println("     ➤ El estudiante con DNI " + estudiante.getDNI() + " ya está registrado.   ");
                System.out.println("══════════════════════════════════════════════════════════════════");

            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Error al dar de alta al estudiante: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Punto C: recuperar todos los estudiantes ordenados por DNI
     *
     * @return lista de estudiantes
     */
    public List<EstudianteDTO> verEstudiantesOrdenadosPorDNI() {
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudianteDTOS = new ArrayList<>();
        try {
            estudianteDTOS = em.createQuery(
                            "SELECT new org.example.dto.EstudianteDTO(e.DNI, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.LU) " +
                                    "FROM Estudiante e " +
                                    "ORDER BY e.DNI",
                            EstudianteDTO.class)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error al ordenar estudiantes por DNI: " + e.getMessage());
        } finally {
            em.close();
        }
        return estudianteDTOS;
    }
    /**
     * Punto D: recuperar un estudiante, en base a su número de libreta universitaria
     *
     * @return estudiante
     */
    public EstudianteDTO getEstudianteByLU(int LU) {
        EntityManager em = JPAUtil.getEntityManager();
        EstudianteDTO estudianteDTO = null;
        try {
            estudianteDTO = em.createQuery(
                            "SELECT new org.example.dto.EstudianteDTO(e.DNI, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.LU) " +
                                    "FROM Estudiante e WHERE e.LU = :LU",
                            EstudianteDTO.class)
                    .setParameter("LU", LU)
                    .getSingleResult();
        } catch (NoResultException nre) {
            System.out.println("No se encontró un estudiante con LU: " + LU);
            // estudianteDTO queda como null
        }catch (Exception e) {
            System.out.println("Error al recuperar estudiante por LU: " + e.getMessage());
        } finally {
            em.close();
        }
        return estudianteDTO;
    }


    /**
     * Punto E: recuperar todos los estudiantes, en base a su género.
     *
     * @return estudiantes por género
     */
    public List<EstudianteDTO> verEstudiantesPorGenero(String genero) {
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>();
        try {
            estudiantesDTO = em.createQuery(
                            "SELECT new org.example.dto.EstudianteDTO(e.DNI, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.LU) " +
                                    "FROM Estudiante e WHERE e.genero = :genero",
                            EstudianteDTO.class)
                    .setParameter("genero", genero)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error al recuperar estudiantes por género: " + e.getMessage());
        } finally {
            em.close();
        }
        return estudiantesDTO;
    }

}
