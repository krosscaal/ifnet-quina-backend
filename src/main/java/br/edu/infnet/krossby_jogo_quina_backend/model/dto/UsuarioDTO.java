/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UsuarioDTO(
        UUID id,
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @JsonIgnore
        String userLogin,
        @JsonIgnore
        String userSenha) {
}
