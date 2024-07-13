package com.foroAlura.forohub.repository;

import com.foroAlura.forohub.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    Page<Topico> findByCurso_NombreContainingIgnoreCase(String nombreCurso, Pageable pageable);

}
