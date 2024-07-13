package com.foroAlura.forohub.dto;

import com.foroAlura.forohub.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@Setter
@Getter
public class TopicoResponseDTO {

    private String titulo;
    private String mensaje;
    private Date fechaCreacion;
    private Status status;
    private AutorDTO autor;
    private CursoDTO curso;

}
