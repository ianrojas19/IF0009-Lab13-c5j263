package com.medpharm.cr.medpharm_backend.repository;

import com.medpharm.cr.medpharm_backend.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
}
