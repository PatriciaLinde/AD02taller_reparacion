package com.exampleapireparaciones.apireparaciones.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehiculos")
@Entity
public class Vehiculo {
    //Vehiculo: id, matricula, modelo, fechaMatriculacion, averias
    // (relación con la entidad Averia)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(unique = true,nullable = false)
    private  String matricula;
    private  String modelo;
    private   LocalDate fechaMatriculacion;
    //falta averias que es la relacion una lista
    @JsonIgnore //Para que Jackson no serielice esta propiedad a JSON y no se forme un buble infinitos
    @OneToMany(mappedBy = "vehiculo", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, orphanRemoval = true)
    private List<Averia> averias = new ArrayList<>();

}
