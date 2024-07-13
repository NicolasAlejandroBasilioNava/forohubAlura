package com.foroAlura.forohub.repository;

import com.foroAlura.forohub.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
