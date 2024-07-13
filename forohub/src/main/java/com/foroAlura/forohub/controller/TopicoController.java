package com.foroAlura.forohub.controller;

import com.foroAlura.forohub.dto.TopicoResponseDTO;
import com.foroAlura.forohub.dto.TopicoRequest;
import com.foroAlura.forohub.model.Topico;
import com.foroAlura.forohub.repository.CursoRepository;
import com.foroAlura.forohub.repository.TopicoRepository;
import com.foroAlura.forohub.service.TopicoService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private CursoRepository cursoRepository;


    @PostMapping
    public ResponseEntity<TopicoResponseDTO> registrarTopico(@RequestBody @Valid TopicoRequest topicoRequest) {
        try {
            Topico topico = topicoService.registrarTopico(topicoRequest);
            TopicoResponseDTO topicoResponse = topicoService.toDto(topico);
            URI location = URI.create("/topicos/" + topico.getId());
            return ResponseEntity.created(location).body(topicoResponse);
        } catch (IllegalArgumentException e) {
            System.out.println("Excepcion: " + e);
            return ResponseEntity.status(409).body(null); // Conflict
        }
    }

    @GetMapping
    public ResponseEntity<Page<TopicoResponseDTO>> listarTodosTopicos(
            @PageableDefault(sort = "curso", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) String nombreCurso){

        Page<TopicoResponseDTO> topicos = topicoService.listarTodosTopicos(pageable, nombreCurso);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> getTopicoById(@PathVariable Long id){
        TopicoResponseDTO topicoResponse = topicoService.getTopicoById(id);
        return ResponseEntity.ok(topicoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> actualizarTopico(
            @PathVariable Long id,
            @RequestBody TopicoRequest topicoRequest){
        TopicoResponseDTO topicoActualizado = topicoService.actualizarTopico(id, topicoRequest);
        return ResponseEntity.ok(topicoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id){
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }

}
