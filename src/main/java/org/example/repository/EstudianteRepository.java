package org.example.repository;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
     *
     * @param estudiante el objeto que representa el estudiante.
     */
    public void altaEstudiante(Estudiante estudiante) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(estudiante);
        em.getTransaction().commit();
        em.close();
        System.out.println("Estudiante dado de alta correctamente.");
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
                            "SELECT new org.example.dto.EstudianteDTO(e.dni, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.num_lu) " +
                                    "FROM Estudiante e " +
                                    "ORDER BY e.dni",
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
    public EstudianteDTO getEstudianteByLU(int num_lu) {
        EntityManager em = JPAUtil.getEntityManager();
        EstudianteDTO estudianteDTO = null;
        try {
            estudianteDTO = em.createQuery(
                            "SELECT new org.example.dto.EstudianteDTO(e.dni, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.num_lu) " +
                                    "FROM Estudiante e WHERE e.num_lu = :num_lu",
                            EstudianteDTO.class)
                    .setParameter("num_lu", num_lu)
                    .getSingleResult();
        } catch (Exception e) {
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
                            "SELECT new org.example.dto.EstudianteDTO(e.dni, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.num_lu) " +
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


    /**
     * Punto G: recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
     *
     * @return estudiantes
     */
    public List<EstudianteDTO> getEstudiantesByCarreraYCiudad(String carreraNombre, String ciudad) {
        EntityManager em = JPAUtil.getEntityManager();
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>();
        try {
            estudiantesDTO = em.createQuery(
                            "SELECT new org.example.dto.EstudianteDTO(e.dni, e.nombre, e.apellido, e.edad, e.genero, e.ciudad, e.num_lu) " +
                                    "FROM Estudiante e " +
                                    "JOIN e.carreras ec " +
                                    "JOIN ec.carrera c " +
                                    "WHERE c.nombre = :carreraNombre AND e.ciudad = :ciudad",
                            EstudianteDTO.class)
                    .setParameter("carreraNombre", carreraNombre)
                    .setParameter("ciudad", ciudad)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error al recuperar estudiantes por carrera y ciudad: " + e.getMessage());
        } finally {
            em.close();
        }
        return estudiantesDTO;
    }
}
