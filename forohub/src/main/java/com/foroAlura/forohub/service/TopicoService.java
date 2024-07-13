package com.foroAlura.forohub.service;

import com.foroAlura.forohub.dto.AutorDTO;
import com.foroAlura.forohub.dto.CursoDTO;
import com.foroAlura.forohub.dto.TopicoResponseDTO;
import com.foroAlura.forohub.dto.TopicoRequest;
import com.foroAlura.forohub.model.Curso;
import com.foroAlura.forohub.model.Status;
import com.foroAlura.forohub.model.Topico;
import com.foroAlura.forohub.model.Usuario;
import com.foroAlura.forohub.repository.CursoRepository;
import com.foroAlura.forohub.repository.TopicoRepository;
import com.foroAlura.forohub.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Transactional
    public Topico registrarTopico(TopicoRequest topicoRequest) {
        if (topicoRepository.existsByTituloAndMensaje(topicoRequest.getTitulo(), topicoRequest.getMensaje())) {
            throw new IllegalArgumentException("El tópico ya existe con el mismo título y mensaje.");
        }

        Curso curso = cursoRepository.findById(topicoRequest.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        Usuario autor = usuarioRepository.findById(topicoRequest.getAutorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado"));

        Topico topico = new Topico();
        topico.setTitulo(topicoRequest.getTitulo());
        topico.setMensaje(topicoRequest.getMensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);
        topico.setFechaCreacion(new Date()); // O cualquier otro valor por defecto
        topico.setStatus(Status.ABIERTO); // O cualquier otro valor por defecto

        return topicoRepository.save(topico);
    }

    public TopicoResponseDTO toDto(Topico topico) {
        AutorDTO autorDTO = new AutorDTO(topico.getAutor().getNombre(), topico.getAutor().getCorreoElectronico());
        CursoDTO cursoDTO = new CursoDTO(topico.getCurso().getNombre(), topico.getCurso().getCategoria());
        return new TopicoResponseDTO(
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                autorDTO,
                cursoDTO
        );
    }

    public Page<TopicoResponseDTO> listarTodosTopicos(Pageable pageable, String nombreCurso) {
        Page<Topico> topicos;
        if(nombreCurso != null && !nombreCurso.isEmpty()){
            topicos = topicoRepository.findByCurso_NombreContainingIgnoreCase(nombreCurso, pageable);
        }else{
            topicos = topicoRepository.findAll(pageable);
        }
        return topicos.map(this::toDto);
    }

    public TopicoResponseDTO getTopicoById(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topico no encontrado"));
        return toDto(topico);
    }

    public void eliminarTopico(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado");
        }
        topicoRepository.deleteById(id);
    }

    public TopicoResponseDTO actualizarTopico(Long id, TopicoRequest topicoRequest) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));

        Curso curso = cursoRepository.findById(topicoRequest.getCursoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));

        Usuario autor = usuarioRepository.findById(topicoRequest.getAutorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado"));


        topico.setTitulo(topicoRequest.getTitulo());
        topico.setMensaje(topicoRequest.getMensaje());
        topico.setStatus(topicoRequest.getStatus());
        topico.setCurso(curso);
        topico.setAutor(autor);

        Topico topicoActualizado = topicoRepository.save(topico);
        return toDto(topicoActualizado);
    }
}
