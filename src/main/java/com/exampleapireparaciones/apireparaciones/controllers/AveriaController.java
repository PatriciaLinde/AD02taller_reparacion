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
import java.util.Optional;

@RestController
@RequestMapping("/api/averias")
public class AveriaController {

    @Autowired
    AveriaRepository averiaRepository;
    @Autowired
    VehiculoRepository vehiculoRepository;
    //devuelve la lista de averias
    @GetMapping
    public ResponseEntity< List<Averia> >findAllAverias(){
        return ResponseEntity.ok( averiaRepository.findAll());
    }


    //devolver una averia por su id
    @GetMapping("/{id}")
    public ResponseEntity<Averia> findAveriaId(@PathVariable Long id) {//recoge id de l url
        return averiaRepository.findById(id)
                .map( averiaencontrada->ResponseEntity.ok(averiaencontrada))//devuleve 200
                .orElseGet(() -> ResponseEntity.notFound().build());//si no devuelve 404
    }
    // crea una averia para un vehiculo
   @PostMapping
    public ResponseEntity<Averia> createAveriaParavehiculo(@RequestBody Averia averia) {
       Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findById(averia.getVehiculo().getId());//bucar vehiculo

       if (vehiculoOptional.isPresent()) {//comprobar si existe
           Vehiculo vehiculo = vehiculoOptional.get();//recuperar el vehiculo encontrado
           averia.setVehiculo(vehiculo);  //asignas la averia al vehiculo encontrado
           vehiculo.getAverias().add(averia);  // añadir averia en la lista de averias en la relacion con vehiculo
           averiaRepository.save(averia);  // Guardar la averia en la base de datos con el vehiculo asociado

            return new ResponseEntity<Averia>(averia, HttpStatus.CREATED); //devuelvo la averia en
           // el cuerpo del mendaje al cliente  y un 201 created
       }

       return ResponseEntity.notFound().build();   //Código 404 Not Found
   }



    // modificar una averia
    @PutMapping("/{id}")
    public ResponseEntity<Averia> updateAveria(@RequestBody Averia averiaNueva, @PathVariable Long id) {
        return averiaRepository.findById(id)
                .map(averia -> {
                    averia.setDescripcion(averiaNueva.getDescripcion());
                    averia.setEstado(averiaNueva.getEstado());
                    return ResponseEntity.ok(averiaRepository.save(averia));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAveria(@PathVariable Long id) {

        return averiaRepository.findById(id)
                .map(averiaencontrada -> {

                        if (averiaencontrada.getEstado() != EstadoReparacion.TERMINADO) {//distintadeterminado
                            return ResponseEntity.status(HttpStatus.CONFLICT).build();

                    }

                    averiaRepository.delete(averiaencontrada);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
