/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package br.edu.infnet.krossby_jogo_quina_backend.model.dto;

import jakarta.validation.constraints.NotBlank;

import static br.edu.infnet.krossby_jogo_quina_backend.util.CentroDeMensagens.E_UM_CAMPO_OBRIGATORIO;

public record LoginDTO(
        @NotBlank(message = E_UM_CAMPO_OBRIGATORIO)
        String userLogin,
        @NotBlank(message = E_UM_CAMPO_OBRIGATORIO)
        String userSenha) {
}
