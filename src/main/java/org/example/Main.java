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
import org.example.utils.Menu;

import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        CarreraRepository carrera_repository = new CarreraRepository();
        EstudianteRepository estudiante_repository = new EstudianteRepository();
        Estudiante_CarreraRepository estudiante_carrera_repository_car = new Estudiante_CarreraRepository();
        Scanner scanner = new Scanner(System.in);

        estudiante_repository.insertarDesdeCSV("estudiantes.csv");
        carrera_repository.insertarDesdeCSV("carreras.csv");
        estudiante_carrera_repository_car.insertarDesdeCSV("estudianteCarrera.csv");

        System.out.println("╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                   Grupo N° 7: Bascuñan Jazmín, Fernández Mateo                     ║");
        System.out.println("║════════════════════════════════════════════════════════════════════════════════════║");
        System.out.println("║  ░█▀█░█▀▄░█▀█░█░█░█▀▀░█▀▀░▀█▀░█▀█░░░▀█▀░█▀█░▀█▀░█▀▀░█▀▀░█▀▄░█▀█░█▀▄░█▀█░█▀▄░░░▀▀▄  ║");
        System.out.println("║  ░█▀▀░█▀▄░█░█░░█░░█▀▀░█░░░░█░░█░█░░░░█░░█░█░░█░░█▀▀░█░█░█▀▄░█▀█░█░█░█░█░█▀▄░░░▄▀░  ║");
        System.out.println("║  ░▀░░░▀░▀░▀▀▀░░▀░░▀▀▀░▀▀▀░░▀░░▀▀▀░░░▀▀▀░▀░▀░░▀░░▀▀▀░▀▀▀░▀░▀░▀░▀░▀▀░░▀▀▀░▀░▀░░░▀▀▀  ║");
        System.out.println("║════════════════════════════════════════════════════════════════════════════════════║");
        System.out.println("║                     Carga y Consulta de Datos con JPA y JPQL                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝\n");

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

        estudiante_carrera_repository_car.matricularEstudiante(12345678, 3, 2023, 2027);

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

        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║    ¿Desea realizar otra operación?    ║");
        System.out.println("║   (S) Sí         |    (N) No          ║");
        System.out.println("╚═══════════════════════════════════════╝");


        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("S")) {
            boolean continuar = true;

            while (continuar) {
                Menu.mostrarMenu();
                int opcion = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer

                switch (opcion) {
                    case 1:
                        Menu.insertarEstudiante(scanner, estudianteRepository);
                        break;
                    case 2:
                        Menu.matricularEstudiante(scanner, estudiante_carrera_repository_car);
                        break;
                    case 3:
                        Menu.buscarPorLU(scanner, estudianteRepository);
                        break;
                    case 4:
                        Menu.buscarPorGenero(scanner, estudianteRepository);
                        break;
                    case 5:
                        Menu.buscarPorCarreraCiudad(scanner, estudianteCarreraRepository);
                        break;
                    case 6:
                        continuar = false;
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida, intente nuevamente.");
                }
                if (continuar) {
                    System.out.println("\n╔═══════════════════════════════════════╗");
                    System.out.println("║    ¿Desea realizar otra operación?    ║");
                    System.out.println("║   (S) Sí         |    (N) No          ║");
                    System.out.println("╚═══════════════════════════════════════╝");

                    String rta = scanner.nextLine();
                    if (!rta.equalsIgnoreCase("S")) {
                        continuar = false;
                        System.out.println("Saliendo del programa...");
                    }
                }
            }

            scanner.close();
        }else{
            System.out.println("Saliendo del programa...");
        }
        }

    }

