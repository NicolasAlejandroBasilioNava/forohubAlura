package com.foroAlura.forohub.dto;

import com.foroAlura.forohub.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CursoDTO {
    private String nombre;
    private Categoria categoria;
}
