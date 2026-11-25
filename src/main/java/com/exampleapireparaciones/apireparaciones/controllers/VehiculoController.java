package com.exampleapireparaciones.apireparaciones.controllers;

import com.exampleapireparaciones.apireparaciones.entities.Averia;
import com.exampleapireparaciones.apireparaciones.entities.EstadoReparacion;
import com.exampleapireparaciones.apireparaciones.entities.Vehiculo;
import com.exampleapireparaciones.apireparaciones.repositories.AveriaRepository;
import com.exampleapireparaciones.apireparaciones.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    @Autowired
    VehiculoRepository vehiculoRepository;
    @Autowired
    AveriaRepository averiaRepository;


    // Obtener todos los vehiculos
    @GetMapping
    public ResponseEntity<List<Vehiculo>> findAllVehiculos() {
        return ResponseEntity.ok(vehiculoRepository.findAll());//devuelve 200
    }


    // Obtener un vehiculo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> findVehiculo(@PathVariable Long id) {//recoge id de l url
        return vehiculoRepository.findById(id)
                .map( vehiculo->ResponseEntity.ok(vehiculo))//devuleve 200
                .orElseGet(() -> ResponseEntity.notFound().build());//si no devuelve 404
    }
//crear un vehiculo
    @PostMapping
    public ResponseEntity<Vehiculo> createVehiculo(@RequestBody Vehiculo vehiculo) {
        return ResponseEntity.status(201).body(vehiculoRepository.save(vehiculo));
    }
//Actualizar un vehiculo
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> updateVehiculo(@RequestBody Vehiculo vehiculoNuevo, @PathVariable Long id) {
    return vehiculoRepository.findById(id)
            .map(vehiculo -> {
                vehiculo.setMatricula(vehiculoNuevo.getMatricula());
                vehiculo.setModelo(vehiculoNuevo.getModelo());
                vehiculo.setFechaMatriculacion(vehiculoNuevo.getFechaMatriculacion());
                return ResponseEntity.ok(vehiculoRepository.save(vehiculo));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());


}
//devolver lista de averias de un vehiculo
    @GetMapping("/{id}/averias")
    public ResponseEntity<List<Averia>>findById(@PathVariable Long id){
        return vehiculoRepository.findById(id)
                .map (vehiculoencontrado->ResponseEntity.ok(vehiculoencontrado.getAverias()))
                .orElseGet(()->ResponseEntity.notFound().build());
    }
//Borrar un vehiculo recorriendo la lista de averias si no son terminado no borra
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVehiculo(@PathVariable Long id) {

        return vehiculoRepository.findById(id)
                .map(vehiculoencontrado -> {

                    for (Averia averia : vehiculoencontrado.getAverias()) {//recorro la lista de averias

                        if (averia.getEstado() != EstadoReparacion.TERMINADO) {//distintadeterminado
                            return ResponseEntity.status(HttpStatus.CONFLICT).build();
                        }
                    }

                    vehiculoRepository.delete(vehiculoencontrado);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }




}
