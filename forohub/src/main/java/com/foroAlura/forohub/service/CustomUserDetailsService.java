package com.foroAlura.forohub.service;

import com.foroAlura.forohub.model.Usuario;
import com.foroAlura.forohub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correoElectronico) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoElectronico);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con correo: " + correoElectronico);
        }
        return new org.springframework.security.core.userdetails.User(usuario.getCorreoElectronico(), usuario.getContrasena(), new ArrayList<>());
    }
}
