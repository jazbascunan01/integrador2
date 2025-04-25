package org.example.modelo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
@Entity
public class Estudiante {
    @Id
    private int dni;
    @Column (nullable = false)
    private String nombre;
    @Column (nullable = false)
    private String apellido;
    @Column (nullable = false)
    private int edad;
    @Column
    private String genero;
    @Column
    private String ciudad;
    @Column (nullable = false)
    private int num_lu;

    @OneToMany (mappedBy = "estudiante")
    private List<Estudiante_Carrera> carreras;


    public Estudiante(int dni, String nombre, String apellido,  int edad, String sexo, String ciudad, int LU ) {
        this.num_lu = LU;
        this.ciudad = ciudad;
        this.genero = genero;
        this.edad = edad;
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.carreras = new ArrayList<>();
    }
}
