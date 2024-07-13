package com.foroAlura.forohub.repository;

import com.foroAlura.forohub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByCorreoElectronico(String correoElectronico);
}
