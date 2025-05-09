package org.example.modelo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table
@Entity
public class Estudiante {
    @Id
    private int DNI;
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
    private int LU;
    @OneToMany (mappedBy = "estudiante", fetch = FetchType.LAZY)
    private List<Estudiante_Carrera> carreras;


    public Estudiante(int DNI, String nombre, String apellido,  int edad, String genero, String ciudad, int LU ) {
        this.LU = LU;
        this.ciudad = ciudad;
        this.genero = genero;
        this.edad = edad;
        this.apellido = apellido;
        this.nombre = nombre;
        this.DNI = DNI;
        this.carreras = new ArrayList<>();
    }
}
