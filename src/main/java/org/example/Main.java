package org.example;

import jakarta.persistence.EntityManager;
import org.example.factory.JPAUtil;
import org.example.repository.CarreraRepository;
import org.example.repository.EstudianteRepository;
import org.example.repository.Estudiante_CarreraRepository;


public class Main {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        CarreraRepository carrera_repository = new CarreraRepository();
        EstudianteRepository estudiante_repository= new EstudianteRepository();
        Estudiante_CarreraRepository estudiante_carrera_repository_car = new Estudiante_CarreraRepository();

        estudiante_repository.insertarDesdeCSV("estudiantes.csv");
        carrera_repository.insertarDesdeCSV("carreras.csv");
        estudiante_carrera_repository_car.insertarDesdeCSV("estudianteCarrera.csv");

    }
}