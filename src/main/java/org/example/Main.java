package org.example;

import jakarta.persistence.EntityManager;
import org.example.dto.CarreraDTO;
import org.example.dto.EstudianteDTO;
import org.example.dto.ReporteCarreraDTO;
import org.example.factory.JPAUtil;
import org.example.modelo.Carrera;
import org.example.modelo.Estudiante;
import org.example.modelo.Estudiante_Carrera;
import org.example.repository.CarreraRepository;
import org.example.repository.EstudianteRepository;
import org.example.repository.Estudiante_CarreraRepository;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        CarreraRepository carrera_repository = new CarreraRepository();
        EstudianteRepository estudiante_repository = new EstudianteRepository();
        Estudiante_CarreraRepository estudiante_carrera_repository_car = new Estudiante_CarreraRepository();

        estudiante_repository.insertarDesdeCSV("estudiantes.csv");
        carrera_repository.insertarDesdeCSV("carreras.csv");
        estudiante_carrera_repository_car.insertarDesdeCSV("estudianteCarrera.csv");


        EstudianteRepository estudianteRepository = new EstudianteRepository();

        // Crear un nuevo estudiante
        Estudiante nuevoEstudiante = new Estudiante(
                12345678, // DNI
                "Juan",   // Nombre
                "Pérez",  // Apellido
                25,       // Edad
                "Masculino", // Género
                "Buenos Aires", // Ciudad
                1001      // Número de libreta universitaria
        );

        // Dar de alta al estudiante
        estudianteRepository.altaEstudiante(nuevoEstudiante);

        //Matricular un estudiante a una carrera
        // Buscar un estudiante existente
        Estudiante estudiante = em.find(Estudiante.class, 12345678);

        // Buscar una carrera existente
        Carrera carrera = em.find(Carrera.class, 3);

        if (estudiante != null && carrera != null) {
            // Matricular al estudiante en la carrera
            Estudiante_Carrera estudianteCarrera = new Estudiante_Carrera();
            estudianteCarrera.setEstudiante(estudiante);
            estudianteCarrera.setCarrera(carrera);
            estudianteCarrera.setAnio_inscripcion(2023);
            estudianteCarrera.setAnio_graduacion(2027);
            estudianteCarrera.setAntiguedad(0);

            estudiante_carrera_repository_car.matricularEstudiante(estudianteCarrera);

            System.out.println("Estudiante matriculado correctamente.");
        } else {
            System.out.println("No se encontró el estudiante o la carrera.");
        }

        List<EstudianteDTO> estudiantesOrdenadosPorDNI = estudianteRepository.verEstudiantesOrdenadosPorDNI();
        estudiantesOrdenadosPorDNI.forEach(System.out::println);

        /*EstudianteDTO estudiantePorLU = estudianteRepository.getEstudianteByLU(1001);
        System.out.println(estudiantePorLU);

        List<EstudianteDTO> estudiantesPorGenero = estudianteRepository.verEstudiantesPorGenero("Male");
        estudiantesPorGenero.forEach(System.out::println);

        List<EstudianteDTO> estudiantesPorCarreraYCiudad = estudianteRepository.getEstudiantesByCarreraYCiudad("Arte", "Jiaoyuan");
        estudiantesPorCarreraYCiudad.forEach(System.out::println);

        List<CarreraDTO> carrerasConEstudiantes = carrera_repository.getCarrerasConEstudiantes();
        carrerasConEstudiantes.forEach(System.out::println);*/
        List<ReporteCarreraDTO> reporte = carrera_repository.getReporteCarreras();
        reporte.forEach(System.out::println);
    }
}