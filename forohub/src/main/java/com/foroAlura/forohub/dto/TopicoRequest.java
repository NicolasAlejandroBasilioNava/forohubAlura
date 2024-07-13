package com.foroAlura.forohub.dto;


import com.foroAlura.forohub.model.Status;
import com.foroAlura.forohub.model.Usuario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TopicoRequest {

    @NotNull
    @NotEmpty
    private String titulo;

    @NotNull
    @NotEmpty
    private String mensaje;

    @NotNull
    private Long autorId;

    @NotNull
    private Long cursoId;

    private Status status;

    // Getters and Setters
}
