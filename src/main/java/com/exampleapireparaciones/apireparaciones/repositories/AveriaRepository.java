package com.exampleapireparaciones.apireparaciones.repositories;

import com.exampleapireparaciones.apireparaciones.entities.Averia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AveriaRepository extends JpaRepository<Averia, Long> {
}
