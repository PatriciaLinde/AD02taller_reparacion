package com.exampleapireparaciones.apireparaciones.repositories;

import com.exampleapireparaciones.apireparaciones.entities.Averia;
import com.exampleapireparaciones.apireparaciones.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
}
