package org.example.utils;

import org.example.modelo.*;
import org.example.repository.*;
import org.example.dto.*;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public static void mostrarMenu() {
        System.out.println("\n╔═════════════════════════════════════════════╗");
        System.out.println("║                    MENÚ                     ║");
        System.out.println("╠═════════════════════════════════════════════╣");
        System.out.println("║ 1. Insertar un estudiante                   ║");
        System.out.println("║ 2. Matricular un estudiante en una carrera  ║");
        System.out.println("║ 3. Buscar estudiante por Libreta            ║");
        System.out.println("║ 4. Buscar estudiantes por género            ║");
        System.out.println("║ 5. Buscar estudiantes por carrera y ciudad  ║");
        System.out.println("║ 6. Salir                                    ║");
        System.out.println("╚═════════════════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
    }

    public static void insertarEstudiante(Scanner scanner, EstudianteRepository repo) {
        System.out.println("Ingrese DNI:");
        int DNI = validarEntero(scanner);

        System.out.println("Ingrese nombre:");
        String nombre = validarString(scanner);

        System.out.println("Ingrese apellido:");
        String apellido = validarString(scanner);

        System.out.println("Ingrese edad:");
        int edad = validarEntero(scanner);
        if (edad <= 0) {
            System.out.println("Error: La edad debe ser mayor a 0.");
            return;
        }

        System.out.println("Ingrese género:");
        String genero = validarString(scanner);

        System.out.println("Ingrese ciudad:");
        String ciudad = validarString(scanner);

        System.out.println("Ingrese número de libreta universitaria:");
        int lu = validarEntero(scanner);

        Estudiante nuevo = new Estudiante(DNI, nombre, apellido, edad, genero, ciudad, lu);
        repo.altaEstudiante(nuevo);
    }

    public static void matricularEstudiante(Scanner scanner, Estudiante_CarreraRepository estudianteCarreraRepository) {
        System.out.println("Ingrese DNI del estudiante a matricular:");
        int DNIEstudiante = validarEntero(scanner);

        System.out.println("Ingrese ID de la carrera:");
        int idCarrera = validarEntero(scanner);

        System.out.println("Ingrese año de inscripción:");
        int inscripcion = validarEntero(scanner);

        System.out.println("Ingrese año de graduación (o 0 si no se graduó aún):");
        int graduacion = validarEntero(scanner);

        // Delegar la lógica al repositorio
        estudianteCarreraRepository.matricularEstudiante(DNIEstudiante, idCarrera, inscripcion, graduacion);
    }


    public static void buscarPorLU(Scanner scanner, EstudianteRepository repo) {
        System.out.println("Ingrese número de libreta universitaria:");
        int lu = validarEntero(scanner);

        EstudianteDTO est = repo.getEstudianteByLU(lu);
        if (est != null) {
            System.out.println("╔════════════╤══════════════╤══════════════╤══════╤════════════╤═════════════════╤════════╗");
            System.out.println("║ DNI        │ Nombre       │ Apellido     │ Edad │ Género     │ Ciudad          │ LU     ║");
            System.out.println("╠════════════╪══════════════╪══════════════╪══════╪════════════╪═════════════════╪════════╣");
            System.out.println(est);
            System.out.println("╚════════════╧══════════════╧══════════════╧══════╧════════════╧═════════════════╧════════╝");
        }
    }

    public static void buscarPorGenero(Scanner scanner, EstudianteRepository repo) {
        System.out.println("Ingrese género (por ejemplo: Male, Female):");
        String genero = validarString(scanner);

        List<EstudianteDTO> lista = repo.verEstudiantesPorGenero(genero);
        if (!lista.isEmpty()) {
            System.out.println("╔════════════╤══════════════╤══════════════╤══════╤════════════╤═════════════════╤════════╗");
            System.out.println("║ DNI        │ Nombre       │ Apellido     │ Edad │ Género     │ Ciudad          │ LU     ║");
            System.out.println("╠════════════╪══════════════╪══════════════╪══════╪════════════╪═════════════════╪════════╣");
            lista.forEach(System.out::println);
            System.out.println("╚════════════╧══════════════╧══════════════╧══════╧════════════╧═════════════════╧════════╝");
        } else {
            System.out.println("No se encontraron estudiantes con ese género.");
        }
    }

    public static void buscarPorCarreraCiudad(Scanner scanner, Estudiante_CarreraRepository repo) {
        System.out.println("Ingrese nombre de la carrera:");
        String carrera = validarString(scanner);

        System.out.println("Ingrese ciudad:");
        String ciudad = validarString(scanner);

        List<Estudiante_CarreraDTO> lista = repo.getEstudiantesByCarreraYCiudad(carrera, ciudad);
        if (!lista.isEmpty()) {
            System.out.println("╔═══════════════╤══════════════╤══════════════╤══════════════╤══════════════════════╤══════════════════╤═════════════════╤════════════╗");
            System.out.println("║ ID Estudiante │ Nombre       │ Apellido     │ Ciudad       │ Carrera              │  Año Inscripción │  Año Graduación │ Antigüedad ║");
            System.out.println("╠═══════════════╪══════════════╪══════════════╪══════════════╪══════════════════════╪══════════════════╪═════════════════╪════════════╣");
            lista.forEach(System.out::println);
            System.out.println("╚═══════════════╧══════════════╧══════════════╧══════════════╧══════════════════════╧══════════════════╧═════════════════╧════════════╝");
        } else {
            System.out.println("No se encontraron estudiantes en esa carrera y ciudad.");
        }
    }

    // Métodos de validación para evitar repetir código
    private static int validarEntero(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Error: Debe ingresar un número. Intente nuevamente:");
            scanner.next();
        }
        int numero = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer
        return numero;
    }

    private static String validarString(Scanner scanner) {
        String input;
        do {
            input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                System.out.println("Error: El campo no puede estar vacío. Intente nuevamente:");
            }
        } while (input.trim().isEmpty());
        return input;
    }

}
