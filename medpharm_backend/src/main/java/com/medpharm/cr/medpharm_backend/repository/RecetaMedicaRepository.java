package com.medpharm.cr.medpharm_backend.repository;

import com.medpharm.cr.medpharm_backend.model.RecetaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecetaMedicaRepository extends JpaRepository<RecetaMedica, Long> {
    List<RecetaMedica> findByEstado(String estado);
}
