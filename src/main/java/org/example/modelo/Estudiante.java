package main.java.org.example.modelo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "Estudiante")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dni;
    @Column (nullable = false)
    private String nombre;
    @Column (nullable = false)
    private String apellido;
    @Column (nullable = false)
    private int edad;
    @Column (nullable = false)
    private String genero;
    @Column (nullable = false)
    private String ciudad;
    @Column (nullable = false)
    private int num_lu;

    @OneToMany (mappedBy = "estudiante")
    private List<Estudiante_Carrera> carreras;
}
