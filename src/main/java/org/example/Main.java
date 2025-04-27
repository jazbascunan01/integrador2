package org.example;

import jakarta.persistence.EntityManager;
import org.example.dto.*;
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

        // Recuperar todos los estudiantes ordenados por DNI
        System.out.println("Estudiantes ordenados por DNI:");
        List<EstudianteDTO> estudiantesOrdenadosPorDNI = estudianteRepository.verEstudiantesOrdenadosPorDNI();
        System.out.println("╔════════════╤══════════════╤══════════════╤══════╤════════════╤═════════════════╤════════╗");
        System.out.println("║ DNI        │ Nombre       │ Apellido     │ Edad │ Género     │ Ciudad          │ LU     ║");
        System.out.println("╠════════════╪══════════════╪══════════════╪══════╪════════════╪═════════════════╪════════╣");
        estudiantesOrdenadosPorDNI.forEach(System.out::println);
        System.out.println("╚════════════╧══════════════╧══════════════╧══════╧════════════╧═════════════════╧════════╝");

        // Recuperar un estudiante por número de libreta universitaria
        EstudianteDTO estudiantePorLU = estudianteRepository.getEstudianteByLU(1001);
        if (estudiantePorLU != null) {
            System.out.println("Estudiante por LU: ");
            System.out.println("╔════════════╤══════════════╤══════════════╤══════╤════════════╤═════════════════╤════════╗");
            System.out.println("║ DNI        │ Nombre       │ Apellido     │ Edad │ Género     │ Ciudad          │ LU     ║");
            System.out.println("╠════════════╪══════════════╪══════════════╪══════╪════════════╪═════════════════╪════════╣");
            System.out.println(estudiantePorLU);
            System.out.println("╚════════════╧══════════════╧══════════════╧══════╧════════════╧═════════════════╧════════╝");
        } else {
            System.out.println("No se encontró un estudiante con el LU especificado.");
        }

        // Recuperar estudiantes por género
        List<EstudianteDTO> estudiantesPorGenero = estudianteRepository.verEstudiantesPorGenero("Male");
        if (!estudiantesPorGenero.isEmpty()) {
            System.out.println("Estudiantes por género:");
            System.out.println("╔════════════╤══════════════╤══════════════╤══════╤════════════╤═════════════════╤════════╗");
            System.out.println("║ DNI        │ Nombre       │ Apellido     │ Edad │ Género     │ Ciudad          │ LU     ║");
            System.out.println("╠════════════╪══════════════╪══════════════╪══════╪════════════╪═════════════════╪════════╣");
            estudiantesPorGenero.forEach(System.out::println);
            System.out.println("╚════════════╧══════════════╧══════════════╧══════╧════════════╧═════════════════╧════════╝");
        } else {
            System.out.println("No se encontraron estudiantes del género especificado.");
        }

        // Recuperar estudiantes por carrera y ciudad
        Estudiante_CarreraRepository estudianteCarreraRepository = new Estudiante_CarreraRepository();
        List<Estudiante_CarreraDTO> estudiantesPorCarreraYCiudad = estudianteCarreraRepository.getEstudiantesByCarreraYCiudad("Arte", "Jiaoyuan");
        if (!estudiantesPorCarreraYCiudad.isEmpty()) {
            System.out.println("Estudiantes por carrera y ciudad:");
            System.out.println("╔═══════════════╤══════════════╤══════════════╤══════════════╤══════════════════════╤══════════════════╤═════════════════╤════════════╗");
            System.out.println("║ ID Estudiante │ Nombre       │ Apellido     │ Ciudad       │ Carrera              │  Año Inscripción │  Año Graduación │ Antigüedad ║");
            System.out.println("╠═══════════════╪══════════════╪══════════════╪══════════════╪══════════════════════╪══════════════════╪═════════════════╪════════════╣");
            estudiantesPorCarreraYCiudad.forEach(System.out::println);
            System.out.println("╚═══════════════╧══════════════╧══════════════╧══════════════╧══════════════════════╧══════════════════╧═════════════════╧════════════╝");
        } else {
            System.out.println("No se encontraron estudiantes para la carrera y ciudad especificadas.");
        }


        // Recuperar las carreras con estudiantes inscriptos, ordenadas por cantidad de inscriptos
        List<CarreraConEstudiantesDTO> carrerasConEstudiantes = estudiante_carrera_repository_car.getCarrerasConEstudiantes();
        if (!carrerasConEstudiantes.isEmpty()) {
            System.out.println("Carreras con estudiantes inscriptos:");
            System.out.println("╔════════════════════════════════════╤══════════════╗");
            System.out.println("║ Carrera                            │ Inscriptos   ║");
            System.out.println("╠════════════════════════════════════╪══════════════╣");
            carrerasConEstudiantes.forEach(System.out::println);
            System.out.println("╚════════════════════════════════════╧══════════════╝");

        } else {
            System.out.println("No se encontraron carreras con estudiantes inscriptos.");
        }
        // Generar un reporte de las carreras con información de inscriptos y egresados por año
        System.out.println("Reporte de carreras ordenadas alfabética y cronológicamente");
        List<ReporteCarreraDTO> reporte = carrera_repository.getReporteCarreras();
        System.out.println("╔════════════════════════════════════╤═══════╤════════════╤════════════╗");
        System.out.println("║ Carrera                            │ Año   │ Inscriptos │ Egresados  ║");
        System.out.println("╠════════════════════════════════════╪═══════╪════════════╪════════════╣");
        reporte.forEach(System.out::println);
        System.out.println("╚════════════════════════════════════╧═══════╧════════════╧════════════╝");

    }
}