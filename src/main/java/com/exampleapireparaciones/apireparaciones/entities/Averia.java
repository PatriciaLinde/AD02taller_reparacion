package com.exampleapireparaciones.apireparaciones.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "averias")
@Entity
public class Averia {
//Averia: id, descripcion, estado (ENUM PENDIENTE, EN_REPARACION, TERMINADO),
// fechaCreacion, fechaUltimaActualizacion,
// vehiculo (relación inversa con la entidad Vehiculo)

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String descripcion;

@Enumerated (EnumType.STRING)//indico enumerado tipo string en la base de datos
private EstadoReparacion estado = EstadoReparacion.PENDIENTE;// estado por defecto lo inicializo en pendiente

private LocalDateTime fechaCreacion;

private LocalDateTime fechaUltimaActualizacion;



    @ManyToOne(optional = false)//obliga que cada averia tenga un vehiculo asociado
    @JoinColumn(nullable = false)//no permite en ,la clave foranea tener vaor nulo
   private Vehiculo vehiculo;//la relacion con vehiculo

    //metodos para utilizar las fechas en creacion y actualizacion
    @PrePersist
private void CrearFecha() {
 fechaCreacion=LocalDateTime.now();
 fechaUltimaActualizacion=LocalDateTime.now();

    }
    @PreUpdate
private void ActualizarFecha() {
        fechaUltimaActualizacion=LocalDateTime.now();
}


}
